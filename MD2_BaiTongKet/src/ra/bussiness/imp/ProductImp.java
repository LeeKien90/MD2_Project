package ra.bussiness.imp;

import ra.bussiness.design.IShop;
import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.Color;
import ra.bussiness.entity.Product;
import ra.bussiness.entity.Size;
import ra.config.ShopValidation;
import ra.data.DataURL;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductImp implements IShop<Product, Integer> {
    @Override
    public boolean create(Product product) {
        List<Product> listProduct = readFromFile();
        if (listProduct == null) {
            listProduct = new ArrayList<>();
        }
        listProduct.add(product);
        boolean result = writeFromFile(listProduct);
        return result;
    }

    @Override
    public boolean update(Product product) {
        List<Product> listColor = readFromFile();
        boolean returnData = false;
        for (int i = 0; i < listColor.size(); i++) {
            if (listColor.get(i).getProductId() == product.getProductId()) {
//                Thực hiện cập nhập
                listColor.set(i, product);
                returnData = true;
                break;
            }
        }
        boolean result = writeFromFile(listColor);
        if (result && returnData) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public List<Product> readFromFile() {
        List<Product> listProduct = null;
        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            file = new File(DataURL.URL_PRODUCT_FILE);
            if (file.exists()) {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                listProduct = (List<Product>) ois.readObject();
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
        return listProduct;
    }

    @Override
    public boolean writeFromFile(List<Product> list) {
        File file = null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        boolean returnData = true;
        try {
            file = new File(DataURL.URL_PRODUCT_FILE);
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
    public Product inputData(Scanner sc) {
        Product product = new Product();
        List<Product> listProduct = readFromFile();
        List<Size> listSize = product.getSizes();
        if (listSize == null) {
            listSize = new ArrayList<>();
        }
        List<Color> listColor = product.getColors();
        if (listProduct == null) {
            listProduct = new ArrayList<>();
        }
        Product productNew = new Product();
        System.out.println("Nhập mã sản phẩm: ");
        do {
            String productId = sc.nextLine();
            if (ShopValidation.checkEmptyString(productId)) {
                if (ShopValidation.checkProductId_length(productId)) {
                    if (ShopValidation.checkProductId(productId)) {
                        boolean checkexist = true;
                        for (Product pro : listProduct) {
                            if (pro.getProductId().equals(productId)) {
                                checkexist = false;
                            }
                        }
                        if (checkexist) {
                            productNew.setProductId(productId);
                            break;
                        } else {
                            System.out.println("Mã sản phẩm đã tồn tại, vui lòng nhập lại");
                        }
                    } else {
                        System.out.println("Mã sản phẩm phải bắt đầu bằng chữ P");
                    }
                } else {
                    System.out.println("Vui lòng nhập mã sản phẩm gồm 5 ký tự");
                }
            } else {
                System.out.println("Mã sản phẩm không được để trống");
            }
        } while (true);
        System.out.println("Nhập tên sản phẩm vào");
        do {
            String productName = sc.nextLine();
            if (ShopValidation.checkEmptyString(productName)) {
                if (ShopValidation.checkLength(productName, 6, 30)) {
                    boolean checkNameExist = true;
                    for (Product pro : listProduct) {
                        if (pro.getProductName().equals(productName)) {
                            checkNameExist = false;
                        }
                    }
                    if (checkNameExist) {
                        productNew.setProductName(productName);
                        break;
                    } else {
                        System.out.println("Tên sản phẩm đã tồn tại, vui lòng nhập lại");
                    }
                } else {
                    System.out.println("Tên sản phẩm từ 6 đến 30 ký tự");
                }
            } else {
                System.out.println("Tên sản phẩm không được để trống");
            }

        } while (true);
        System.out.println("Nhập giá tiền sản phẩm");
        do {
            String str = sc.nextLine();
            Float price = Float.parseFloat(sc.nextLine());
            if (ShopValidation.checkEmptyString(str)){
                if (price > 0){
                    productNew.setProductPrice(price);
                    break;
                }else {
                    System.out.println("Giá sản phẩm phải lớn hơn 0");
                }
            }else {
                System.out.println("Giá sản phẩm không được để trống");
            }
        }while (true);
        System.out.println("Phần trăm giảm giá của sản phẩm: ");
        do {
            String str = sc.nextLine();
            Float discount = Float.parseFloat(sc.nextLine());
            if (ShopValidation.checkEmptyString(str)){
                if (discount >= 0 || discount <= 100){
                    productNew.setProductDiscount(discount);
                    break;
                }else {
                    System.out.println("Phần trăm giảm giá trong khoảng từ 0 đến 100");
                }
            }else {
                System.out.println("Phần trăm giảm giá sản phẩm không được để trống");
            }
        }while (true);
        System.out.println("Tiêu đề sản phẩm");
        do {
            String title = sc.nextLine();
            if (ShopValidation.checkEmptyString(title)){
                if (ShopValidation.checkLength(title, 10, 50)){
                productNew.setProductTille(title);
                break;
                }else {
                    System.out.println("Tiêu đề sản phẩm từ 10 đến 50 ký tự");
                }
            } else {
                System.out.println("Tiêu đề sản phẩm không được để trống");
            }
        }while (true);
        System.out.println("Nhập mô tả sản phẩm vào");
        do {
            String content = sc.nextLine();
            if (ShopValidation.checkEmptyString(content)) {
                productNew.setProductDescriptions(content);
                break;
            } else {
                System.err.println("Mô tả sản phẩm không được để trống");
            }
        } while (true);
        System.out.println("Màu sắc của sản phẩm");

        do {
            ColorImp colorImp = new ColorImp();
            List<Color> colorList = colorImp.readFromFile();
            if (colorList.size() == 0) {
                colorList = new ArrayList<>();
            }
            for (Color color : colorList) {
                System.out.printf("%d. %s\n", color.getColorId(), color.getColorName());
            }
            System.out.println("chọn màu sắc");
            int choice = 0;
            do {
                String str = sc.nextLine();
                choice = Integer.parseInt(str);
                if (ShopValidation.checkEmptyString(str)) {
                    if (ShopValidation.checkInteger(str)) {
                        break;
                    } else {
                        System.err.println("Vui lòng nhập vào 1 số nguyên");
                    }
                } else {
                    System.err.println("Danh sác màu sắc không được để trống, vui lòng lựa chọn màu sắc");
                }
            } while (true);
            if (choice > 0 && choice <= colorList.size()) {

                if (listColor==null){
                    listColor = new ArrayList<>();
                }
                boolean checkColorExist = false;
                for (Color colorExist : listColor) {
                    if (colorExist.getColorId() == colorList.get(choice - 1).getColorId()) {
                        checkColorExist = true;
                    }
                }
                if (!checkColorExist) {
                    listColor.add(colorList.get(choice - 1));
                } else {
                    System.err.println("Màu sắc đã tồn tại trọng danh sách màu sắc");
                }
                System.out.println("****************************************************");
                System.out.println("*| Bạn muốn chọn thêm màu sắc không?              |*");
                System.out.println("*| 1. Có                                          |*");
                System.out.println("*| 2. Không                                       |*");
                System.out.println("*| Lựa chọn của bạn:                              |*");
                System.out.println("****************************************************");

                try {
                    int choiceColor = Integer.parseInt(sc.nextLine());
                    if (choiceColor == 1) {
                        productNew.setProductStatus(true);
                    } else {
                        productNew.setProductStatus(false);
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
                break;
            } else {
                System.err.println("Vui lòng chọn màu sắc có trong danh sách");
            }
        } while (true);

        System.out.println("Chọn danh sách các kích cỡ");
        do {
            SizeImp sizeImp = new SizeImp();
            List<Size> listSize1 = sizeImp.readFromFile();
            if (sizeImp==null){
                listSize1 = new ArrayList<>();
            }
            for (Size size : listSize1) {
                System.out.printf("%d. %s\n", size.getSizeId(), size.getSizeName());
            }
            System.out.println("lựa chọn kích cỡ");
            int choiceSize;
            do {
                String strChoiceSize = sc.nextLine();
                if (ShopValidation.checkEmptyString(strChoiceSize)) {
                    if (ShopValidation.checkInteger(strChoiceSize)) {
                        choiceSize = Integer.parseInt(strChoiceSize);
                        break;
                    } else {
                        System.err.println("Vui lòng nhập vào 1 số nguyên");
                    }
                } else {
                    System.err.println("Không được để trống");
                }
            } while (true);
            if (choiceSize > 0 && choiceSize < listSize1.size()) {

                boolean check = false;
                for (Size sizeExist :listSize1 ) {
                    if (sizeExist.getSizeId() == listSize1.get(choiceSize - 1).getSizeId()) {
                        check = true;
                    }
                }
                if (!check) {
                    listSize1.add(listSize1.get(choiceSize - 1));
                } else {
                    System.err.println("Kich cỡ đã tồn tại");
                }
                System.out.println("****************************************************");
                System.out.println("*| Bạn muốn chọn thêm kích cỡ không?                ");
                System.out.println("*| 1. Có                                            ");
                System.out.println("*| 2. Không                                         ");
                System.out.println("*| Lựa chọn của bạn:                                ");
                System.out.println("****************************************************");
                int choiceExit;
                do {
                    String strchoice4 = sc.nextLine();
                    if (ShopValidation.checkEmptyString(strchoice4)) {
                        if (ShopValidation.checkInteger(strchoice4)) {
                            choiceExit = Integer.parseInt(strchoice4);
                            break;
                        } else {
                            System.err.println("Vui lòng nhập vào một số nguyên");
                        }
                    } else {
                        System.err.println("Không được để trống vui lòng nhập vào 1 hoặc 2");
                    }
                } while (true);
                if (choiceExit != 1) {
                    break;
                }
            } else {
                System.err.println("kích cỡ không tồn tại vui lòng chọn kích cỡ trong danh sách");
            }
        } while (true);
        CatalogImp catalogImp = new CatalogImp();
        List<Catalog> catalogList = catalogImp.readFromFile();
        if (catalogList==null){
            catalogList = new ArrayList<>();
        }
        List<Catalog> listCatalogChild = new ArrayList<>();
        int cnt = 1;
        for (Catalog cat : catalogList) {
            if (checkCatalogNotChild(cat, catalogList)) {
                if (cat.isCatalogStatus()){
                    listCatalogChild.add(cat);
                }
            }
        }
        for (Catalog cat : listCatalogChild) {
            System.out.printf("%d. %s \n", cnt, cat.getCatalogName());
            cnt++;
        }
        System.out.println("chọn danh mục sản phẩm");
        int choiceCatalog;
        do {
            String strCatalog = sc.nextLine();
            choiceCatalog = Integer.parseInt(strCatalog);
            if (ShopValidation.checkEmptyString(strCatalog)) {
                if (ShopValidation.checkInteger(strCatalog)) {
                    if (choiceCatalog > 0 && choiceCatalog < listCatalogChild.size()) {
                        productNew.setCatalogs(listCatalogChild.get(choiceCatalog - 1));
                        break;
                    } else {
                        System.err.println("Danh mục không tồn tại vui lòng chọn danh mục khác");
                    }
                } else {
                    System.err.println("Vui lòng nhập vào 1 số nguyên");
                }
            } else {
                System.err.println("Không được để trống vui lòng lựa chọn danh mục");
            }
        } while (true);
        System.out.println("****************************************************");
        System.out.println("*| Trạng thái sản phẩm                            |*");
        System.out.println("*| 1. Hoạt động                                   |*");
        System.out.println("*| 2. Không hoạt động                             |*");
        System.out.println("*| Lựa chọn của bạn:                              |*");
        System.out.println("****************************************************");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 1) {
                productNew.setProductStatus(true);
            } else {
                productNew.setProductStatus(false);
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return productNew;
    }



    @Override
    public void displayData() {
        Product product = new Product();
        String status = "không hoạt động";
        if (product.isProductStatus()){
            status = "hoạt động";
        }
        System.out.printf("%-10d - %-20s - %-10f - %-10f - %-10f - %-20s - %-20s - %-20s\n",product.getProductId(),product.getProductName(),product.getProductPrice(),product.getProductDiscount(),product.getProductExportPrice(),product.getProductTille(),product.getProductDescriptions(), status);
    }

    public static void displayListCatalogAndProduct(Catalog root, List<Catalog> list, int cnt) {
        ProductImp proImp = new ProductImp();
        List<Product> productList = proImp.readFromFile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        for (int i = 0; i < cnt; i++) {
            System.out.print("\t");
        }
        CatalogImp catalogImp = new CatalogImp();
        String status = "Không hoạt động";
        if (root.isCatalogStatus()) {
            status = "Hoạt động";
        }
        System.out.printf("%d. %s - %s\n", root.getCatalogId(), root.getCatalogName(), status);
        List<Catalog> listchild = new ArrayList<>();
        for (Catalog cat : list) {
            if (cat.getCatalog() != null && cat.getCatalog().getCatalogId() == root.getCatalogId()) {
                listchild.add(cat);
            }
        }
        if (listchild.size() != 0) {
            cnt++;
        }
        int cnt1 = cnt;
        for (Catalog cat : listchild) {
            displayListCatalogAndProduct(cat, list, cnt);
            int cntproduct = cnt + 1;
            for (Product pro : productList) {
                if (pro.getCatalogs().getCatalogId() == cat.getCatalogId() && ProductImp.checkCatalogNotChild(cat, list)) {
                    for (int i = 0; i < cntproduct; i++) {
                        System.out.printf("\t");
                    }
                    proImp.displayData();
                }
            }
        }
    }


    public void listProductNew(Scanner sc) {

    }

    public void listProductDiscount(Scanner sc) {

    }

    public void diplayProductByName(Scanner sc) {
        System.out.println("Nhập vào tên sản phẩm cần tìm: ");
        String productNameSearch = sc.nextLine();
        List<Product> listPro = readFromFile();
        for (Product pro : listPro) {
            if (pro.getProductName().contains(productNameSearch)){
                displayData();
            }
        }
    }
    public void displayProductByCatalog() {
        CatalogImp catImp = new CatalogImp();
        List<Catalog> catalogList = catImp.readFromFile();
        if (catalogList == null) {
            catalogList = new ArrayList<>();
        }
        for (Catalog cat : catalogList) {
            if (cat.getCatalog() == null) {
                displayListCatalogAndProduct(cat, catalogList, 0);
            }
        }

    }

    public void displayProductByExportPrice(Scanner sc){
        System.out.println("Nhập khoảng giá từ: ");
        List<Product> products = readFromFile();
        int min = Integer.parseInt(sc.nextLine());
        System.out.println("đến");
        int max = Integer.parseInt(sc.nextLine());
        for (Product pro: products) {
            if (pro.getProductExportPrice() > min && pro.getProductExportPrice() < max){
                displayData();
            }
        }
    }

    public void displayProductByDiscount(Scanner sc) {
        System.out.println("Nhập khoảng giảm giá: ");
        List<Product> products = readFromFile();
        int discout = Integer.parseInt(sc.nextLine());
        for (Product pro: products) {
            if (pro.getProductDiscount() == discout){
                displayData();
            }
        }

    }

    public float calExportPrice(float price, float discount) {
        float exportPrice = price*(100-discount)/100;
        return exportPrice;
    }
    public static boolean checkCatalogNotChild(Catalog child, List<Catalog> list){
        boolean check =true;
        for (Catalog cat: list) {
            if(cat.getCatalog() != null && child.getCatalogId() == cat.getCatalogId()){
                check = false;
                break;
            }
        } if (check){
        return true;
        }else {
            return false;
        }

    }

}
