package ra.presentation;

import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.Size;
import ra.bussiness.imp.CatalogImp;
import ra.bussiness.imp.SizeImp;
import ra.config.ShopMessage;
import ra.config.ShopValidation;

import java.util.*;

public class SizeMenu {
    private static SizeImp sizeImp = new SizeImp();
    public static void displayMenuCatalogManagement(Scanner sc){
        boolean exit = true;
        do {
            System.out.println("**************QUẢN LÝ DANH MỤC**********************");
            System.out.println("*| 1. Danh sách size                              |*");
            System.out.println("*| 2. Thêm mới size                               |*");
            System.out.println("*| 3. Cập nhập size                               |*");
            System.out.println("*| 4. Xóa size                                    |*");
            System.out.println("*| 5. Thoát                                       |*");
            System.out.println("*| Lựa chọn của bạn:                              |*");
            System.out.println("****************************************************");

            int choice = Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:
                    displayListSize();
                    break;
                case 2:
                    inputListSize(sc);
                    break;
                case 3:

                    break;
                case 4:
                    deleteSize(sc);
                    break;
                case 5:
                    exit = false;
                    break;
                default:
                    System.err.println(ShopMessage.NOTIFY_PRODUCT_MANAGEMENT_CHOICE);
            }
        }while(exit);
    }
    public static void displayListSize() {

    }

    public static void inputListSize(Scanner sc) {
        List<Size> listSize = sizeImp.readFromFile();
        if (listSize==null){
            listSize = new ArrayList<>();
        }
        System.out.println("Nhập số lượng kích cỡ muốn thêm vào");
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
            System.out.println("Nhập dữ liệu cho kích cỡ: " + (i + 1));

            Size size = sizeImp.inputData(sc);
            listSize.add(size);
            sizeImp.writeFromFile(listSize);
        }
    }
    public static void deleteSize(Scanner sc) {
        List<Size> listSize = sizeImp.readFromFile();
        if (listSize == null) {
            listSize = new ArrayList<>();
        }
        System.out.println("Nhập id kích cỡ bạn muốn xóa vào");
        int sizeId = 0;
        do {
            String str = sc.nextLine();
            sizeId = Integer.parseInt(str);
            if (ShopValidation.checkEmptyString(str)) {
                if (ShopValidation.checkInteger(str)) {
                    break;
                } else {
                    System.err.println("Không được để trống");
                }
            } else {
                System.err.println("Vui lòng nhập vào 1 số nguyên");
            }
        } while (true);
        boolean result = sizeImp.delete(sizeId);
        if (result) {
            System.out.println("Xóa kích cỡ thành công");
        } else {
            System.out.println("Xóa kích cỡ thất bại");
        }

    }

}
