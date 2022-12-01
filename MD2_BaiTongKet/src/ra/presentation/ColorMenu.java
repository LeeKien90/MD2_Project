package ra.presentation;

import ra.bussiness.entity.Color;
import ra.bussiness.imp.ColorImp;
import ra.config.ShopMessage;
import ra.config.ShopValidation;

import java.util.*;

public class ColorMenu {
    private static ColorImp colorImp = new ColorImp();
    public static void displayMenuColorManagement(Scanner sc){
        boolean exit = true;
        do {
            System.out.println("***************QUẢN LÝ MÀU SẮC**********************");
            System.out.println("*| 1. Danh sách màu sắc                           |*");
            System.out.println("*| 2. Thêm mới màu sắc                            |*");
            System.out.println("*| 3. Cập nhập màu sắc                            |*");
            System.out.println("*| 4. Xóa màu sắc                                 |*");
            System.out.println("*| 5. Thoát                                       |*");
            System.out.println("*| Lựa chọn của bạn:                              |*");
            System.out.println("****************************************************");

            int choice = Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:
                    displayListColor();
                    break;
                case 2:
                    inputListColor(sc);
                    break;
                case 3:

                    break;
                case 4:
                    deleteColor(sc);
                    break;
                case 5:
                    exit = false;
                    break;
                default:
                    System.err.println(ShopMessage.NOTIFY_PRODUCT_MANAGEMENT_CHOICE);
            }
        }while(exit);
    }
    public static void displayListColor() {

    }

    public static void inputListColor(Scanner sc) {
        List<Color> listColor = colorImp.readFromFile();
        if (listColor == null) {
            listColor = new ArrayList<>();
        }
        System.out.println("Nhập số lượng màu sắc muốn thêm vào");
        int number = 0;
        do {
            String str = sc.nextLine();
            number = Integer.parseInt(str);
            if (ShopValidation.checkEmptyString(str)) {
                if (ShopValidation.checkInteger(str)) {
                    break;
                } else {
                    System.err.println("Vui lòng nhập vào 1 số nguyên");
                }
            } else {
                System.err.println("Không được để trống vui lòng nhập 1 số nguyên vào");
            }
        } while (true);
        for (int i = 0; i < number; i++) {
            System.out.println("Nhập dữ liệu cho màu sắc: " + (i + 1));

            Color color = colorImp.inputData(sc);
            listColor.add(color);
            colorImp.writeFromFile(listColor);
        }
    }
    public static void deleteColor(Scanner sc) {
        List<Color> listColor = colorImp.readFromFile();
        if (listColor == null) {
            listColor = new ArrayList<>();
        }
        System.out.println("Nhập id màu sắc bạn muốn xóa vào");
        int colorId = 0;
        do {
            String str = sc.nextLine();
            colorId = Integer.parseInt(str);
            if (ShopValidation.checkEmptyString(str)) {
                if (ShopValidation.checkInteger(str)) {
                    break;
                } else {
                    System.err.println("Không được để trống");
                }
            } else {
                System.err.println("Vui lòng nhạp vào 1 số nguyên");
            }
        } while (true);
        boolean result = colorImp.delete(colorId);
        if (result) {
            System.out.println("Xóa màu sắc thành công");
        } else {
            System.out.println("Xóa màu sắc thất bại");
        }

    }
}
