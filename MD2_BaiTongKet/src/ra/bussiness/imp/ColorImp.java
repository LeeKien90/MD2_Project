package ra.bussiness.imp;

import ra.bussiness.design.IShop;
import ra.bussiness.entity.Color;
import ra.config.ShopValidation;
import ra.data.DataURL;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ColorImp implements IShop<Color, Integer> {

    @Override
    public boolean create(Color color) {
        List<Color> listColor = readFromFile();
        if (listColor == null) {
            listColor = new ArrayList<>();
        }
        listColor.add(color);
        boolean result = writeFromFile(listColor);
        return result;
    }

    @Override
    public boolean update(Color color) {
        List<Color> listColor = readFromFile();
        boolean returnData = false;
        for (int i = 0; i < listColor.size(); i++) {
            if (listColor.get(i).getColorId() == color.getColorId()) {
//                Thực hiện cập nhập
                listColor.set(i, color);
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
        List<Color> listColor = readFromFile();
        boolean returnData =false;
        for (int i = 0; i < listColor.size(); i++) {
            if (listColor.get(i).getColorId() == id){
                listColor.remove(i);
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
    public List<Color> readFromFile() {
        List<Color> listColor = null;
        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            file = new File(DataURL.URL_COLOR_FILE);
            if (file.exists()) {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                listColor = (List<Color>) ois.readObject();
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
        return listColor;
    }

    @Override
    public boolean writeFromFile(List<Color> list) {
        File file = null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        boolean returnData = true;
        try {
            file = new File(DataURL.URL_COLOR_FILE);
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
    public Color inputData(Scanner sc) {
        List<Color> listColor = readFromFile();
        if (listColor == null){
            listColor = new ArrayList<>();
        }
        Color colorNew = new Color();
        if (listColor.size() == 0) {
            colorNew.setColorId(1);
        } else {
            int max = 0;
            for (Color color: listColor) {
                if (max < color.getColorId()){
                    max = color.getColorId();
                }
            }
            colorNew.setColorId(max + 1);
        }
        System.out.println("Nhập tên màu sắc: ");
        do {
            String colorName =sc.nextLine();
            if (ShopValidation.checkEmptyString(colorName)){
                if (ShopValidation.checkLength(colorName,1,10)){
                    boolean check =true;
                    for (Color color: listColor) {
                        if (color.getColorName().equals(colorName)){
                            check = false;
                        }
                    }
                    if (check) {
                        colorNew.setColorName(colorName);
                        break;
                    } else {
                        System.out.println("Tên màu sắc bị trùng, vui lòng nhập lại");
                    }
                }else {
                    System.out.println("Tên màu sắc phải từ 4-30 ký tự");
                }
            }else {
                System.out.println("Tên màu không được để trống");
            }
        }while (true);

        System.out.println("****************************************************");
        System.out.println("*| Trạng thái màu sắc                             |*");
        System.out.println("*| 1. Hoạt động                                   |*");
        System.out.println("*| 2. Không hoạt động                             |*");
        System.out.println("*| Lựa chọn của bạn:                              |*");
        System.out.println("****************************************************");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 1) {
                colorNew.setColorStatus(true);
            } else {
                colorNew.setColorStatus(false);
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return colorNew;
    }

    @Override
    public void displayData() {
        Color color = new Color();
        String status = "không hoạt động";
        if (color.isColorStatus()){
            status = "hoạt động";
        }
        System.out.printf("%-10d %-30s  %-20s\n", color.getColorId(), color.getColorName(), status);

    }

}
