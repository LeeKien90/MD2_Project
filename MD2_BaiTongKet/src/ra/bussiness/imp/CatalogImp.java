package ra.bussiness.imp;

import ra.bussiness.design.ICatalog;
import ra.bussiness.entity.Catalog;
import ra.config.ShopMessage;
import ra.config.ShopValidation;
import ra.data.DataURL;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CatalogImp implements ICatalog<Catalog, Integer> {

    @Override
    public List<Catalog> findAll()  {
        return readFromFile();
    }


    @Override
    public Catalog findByName(Integer name) {
        List<Catalog> listCatalogFull = readFromFile();
        List<Catalog> listCatalog = new ArrayList<>();
        for (Catalog cat : listCatalogFull) {
            if (Integer.parseInt(cat.getCatalogName()) == name) {
                listCatalog.add(cat);
            }
        }
        return null;
    }

    @Override
    public boolean create(Catalog catalog) {
        List<Catalog> listCatalog = readFromFile();
        if (listCatalog == null) {
            listCatalog = new ArrayList<>();
        }
        listCatalog.add(catalog);
        boolean result = writeFromFile(listCatalog);
        return result;
    }

    @Override
    public boolean update(Catalog catalog) {
        List<Catalog> listCatalog = readFromFile();
        boolean returnData = false;
        for (int i = 0; i < listCatalog.size(); i++) {
            if (listCatalog.get(i).getCatalogId() == catalog.getCatalogId()) {
//                Thực hiện cập nhập
                listCatalog.set(i, catalog);
                returnData = true;
                break;
            }
        }
        boolean result = writeFromFile(listCatalog);
        if (result && returnData) {
            return true;
        }
        return returnData;
    }

    @Override
    public boolean delete(Integer id) {
        List<Catalog> listCatalog = readFromFile();
        if (listCatalog == null) {
            listCatalog = new ArrayList<>();
        }
        boolean returnData = false;
        for (int i = 0; i < listCatalog.size(); i++) {
            if (listCatalog.get(i).getCatalogId() == id) {
                listCatalog.remove(i);
                returnData = true;
                break;
            }
        }
        boolean result = writeFromFile(listCatalog);
        if (result && returnData) {
            return true;
        }
        return returnData;
    }

    @Override
    public List<Catalog> readFromFile() {
        List<Catalog> listCatalog = null;
        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            file = new File(DataURL.URL_CATALOG_FILE);
            if (file.exists()) {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                listCatalog = (List<Catalog>) ois.readObject();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex2) {
                ex2.printStackTrace();
            }
        }
        return listCatalog;
    }

    @Override
    public boolean writeFromFile(List<Catalog> list) {
        File file = null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        boolean returnData = true;
        try {
            file = new File(DataURL.URL_CATALOG_FILE);
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        } catch (Exception ex) {
            returnData = false;
            ex.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex2) {
                ex2.printStackTrace();
            }
        }
        return returnData;
    }

    @Override
    public Catalog inputData(Scanner sc) {
        List<Catalog> listCatalog = readFromFile();
        if (listCatalog == null) {
            listCatalog = new ArrayList<>();
        }
        Catalog catNew = new Catalog();
        if (listCatalog.size() == 0) {
            catNew.setCatalogId(1);
        } else {
            int max = 0;
            for (Catalog cat : listCatalog) {
                if (max < cat.getCatalogId()) {
                    max = cat.getCatalogId();
                }
            }
            catNew.setCatalogId(max + 1);
        }

        System.out.println("Nhập vào tên danh mục");
        do {
            String catalogName = sc.nextLine();
            if (ShopValidation.checkEmptyString(catalogName)) {
                if (ShopValidation.checkLength(catalogName, 6, 30)) {
                    boolean checkNameExist = true;
                    for (Catalog catalog : listCatalog) {
                        if (catalog.getCatalogName().equals(catalogName)) {
                            checkNameExist = false;
                        }
                    }
                    if (checkNameExist) {
                        catNew.setCatalogName(catalogName);
                        break;
                    } else {
                        System.out.println("Tên danh mục đã tồn tại, vui lòng nhập lại");
                    }
                } else {
                    System.out.println(ShopMessage.NOTIFY_CATALOGNAME_FAIL);
                }
            } else {
                System.out.println("Tên danh mục không được để trống");
            }
        } while (true);
        //Chon danh muc cha
        System.out.println("Chon danh muc cha: ");
        displayListCatalog();
        System.out.println("Su lua chon cua ban: ");
        String choiceCat = sc.nextLine();
        if (choiceCat == "" || choiceCat.length() == 0) {
            catNew.setCatalog(null);
        } else {
            for (Catalog cat : listCatalog) {
                if (cat.getCatalogId() == Integer.parseInt(choiceCat)) {
                    catNew.setCatalog(cat);
                    break;
                }
            }
        }

        System.out.println("Mô tả danh mục");
        do {
            String content = sc.nextLine();
            if (ShopValidation.checkEmptyString(content)) {
                catNew.setCatalogDescriptions(content);
                break;
            } else {
                System.err.println("Mô tả sản phẩm không được để trống");
            }
        } while (true);

        System.out.println("****************************************************");
        System.out.println("*| Trạng thái danh mục                            |*");
        System.out.println("*| 1. Hoạt động                                   |*");
        System.out.println("*| 2. Không hoạt động                             |*");
        System.out.println("*| Lựa chọn của bạn:                              |*");
        System.out.println("****************************************************");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 1) {
                catNew.setCatalogStatus(true);
            } else {
                catNew.setCatalogStatus(false);
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return catNew;
    }

    @Override
    public void displayData() {
        List<Catalog> catalogs = readFromFile();
        for (Catalog cal : catalogs) {
            String status = "Không hoạt động";
            if (cal.isCatalogStatus()) {
                status = "Hoạt động";
            }
            System.out.printf("%-10d. %-20s - %-30s - %-10s\n", cal.getCatalogId(), cal.getCatalogName(), cal.getCatalogDescriptions(), status);
        }
    }

    public static void displayListCatalog() {
        CatalogImp catImp = new CatalogImp();
        List<Catalog> listCatalog = catImp.readFromFile();
        if (listCatalog == null) {
            listCatalog = new ArrayList<>();
        }

        for (Catalog cat : listCatalog) {
            if (cat.getCatalog() == null) {
                displayListCatalogData(cat, listCatalog, 0);
            }
        }
    }

    public static void displayListCatalogData(Catalog root, List<Catalog> listCatalog, int cnt) {
        for (int i = 0; i < cnt; i++) {
            System.out.printf("\t");
        }
        System.out.printf("%d.%s\n", root.getCatalogId(), root.getCatalogName());
        //In de quy cac danh muc con cua root
        //-Lay ra cac danh muc con cua root
        List<Catalog> listChild = new ArrayList<>();
        for (Catalog cat : listCatalog) {
            if (cat.getCatalog() != null && cat.getCatalog().getCatalogId() == root.getCatalogId()) {
                listChild.add(cat);
            }
        }
        //-In ra thong tin cac danh muc con cua root
        if (listChild.size() != 0) {
            cnt++;
        }
        for (Catalog cat : listChild) {
            displayListCatalogData(cat, listCatalog, cnt);
        }
    }
}
