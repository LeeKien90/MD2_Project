package ra.bussiness.imp;

import ra.bussiness.design.IShop;
import ra.bussiness.entity.Size;
import ra.config.ShopValidation;
import ra.data.DataURL;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SizeImp implements IShop<Size, Integer> {

    @Override
    public boolean create(Size size) {
        List<Size> listSize = readFromFile();
        if (listSize == null) {
            listSize = new ArrayList<>();
        }
        listSize.add(size);
        boolean result = writeFromFile(listSize);
        return result;
    }

    @Override
    public boolean update(Size size) {
        List<Size> listSize = readFromFile();
        boolean returnData = false;
        for (int i = 0; i < listSize.size(); i++) {
            if (listSize.get(i).getSizeId() == size.getSizeId()) {
//                Thực hiện cập nhập
                listSize.set(i, size);
                returnData = true;
                break;
            }
        }
        boolean result = writeFromFile(listSize);
        if (result && returnData) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        List<Size> listSize = readFromFile();
        boolean returnData =false;
        for (int i = 0; i < listSize.size(); i++) {
            if (listSize.get(i).getSizeId() == id){
                listSize.remove(i);
                returnData = true;
                break;
            }
        }
        boolean result = writeFromFile(listSize);
        if (result && returnData) {
            return true;
        }
        return false;
    }

    @Override
    public List<Size> readFromFile() {
        List<Size> listSize = null;
        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            file = new File(DataURL.URL_SIZE_FILE);
            if (file.exists()) {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                listSize = (List<Size>) ois.readObject();
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
        return listSize;
    }

    @Override
    public boolean writeFromFile(List<Size> list) {
        File file = null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        boolean returnData = true;
        try {
            file = new File(DataURL.URL_SIZE_FILE);
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
    public Size inputData(Scanner sc) {
        List<Size> listSize = readFromFile();
        if (listSize == null) {
            listSize = new ArrayList<>();
        }
        Size sizenew = new Size();
        if (listSize.size() == 0) {
            sizenew.setSizeId(1);
        } else {
            int max = 0;
            for (Size size : listSize) {
                if (max < size.getSizeId()) {
                    max = size.getSizeId();
                }
            }
            sizenew.setSizeId(max + 1);
        }
        System.out.println("Nhập tên kích cỡ vào");
        do {
            String sizeName = sc.nextLine();
            if (ShopValidation.checkEmptyString(sizeName)){
                if (ShopValidation.checkLength(sizeName,1,10)){
                    boolean check = true;
                    for (Size size : listSize) {
                        if (size.getSizeName().equals(sizeName)){
                            check = false;
                        }
                    }
                    if (check){
                        sizenew.setSizeName(sizeName);
                        break;
                    } else {
                        System.err.println("Tên kích thước đã tồn tại, vui lòng nhập lại ");
                    }
                } else {
                    System.out.println("Vui lòng nhập tên kích cỡ từ 1-10 ký tự");
                }
            } else {
                System.err.println("Không được để trống");
            }
        } while (true);
        System.out.println("****************************************************");
        System.out.println("*| Trạng thái kích thứớc                          |*");
        System.out.println("*| 1. Hoạt động                                   |*");
        System.out.println("*| 2. Không hoạt động                             |*");
        System.out.println("*| Lựa chọn của bạn:                              |*");
        System.out.println("****************************************************");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 1) {
                sizenew.setSizeStatus(true);
            } else {
                sizenew.setSizeStatus(false);
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return sizenew;
    }

    @Override
    public void displayData() {
        Size size = new Size();
        String status = "không hoạt động";
        if (size.isSizeStatus()){
            status = "hoạt động";
        }
        System.out.printf("%-10d - %-30s - %-20s\n", size.getSizeId(), size.getSizeName(), status);
    }
}
