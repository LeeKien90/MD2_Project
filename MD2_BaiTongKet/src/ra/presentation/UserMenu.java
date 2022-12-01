package ra.presentation;

import ra.bussiness.imp.UserImp;
import ra.config.ShopMessage;

import java.util.Scanner;

public class UserMenu {
    private static UserImp userImp = new UserImp();
    public static void displayMenuUserManagement(Scanner sc){
        boolean exit = true;
        do {
            System.out.println("*******************QUẢN LÝ USER*********************");
            System.out.println("*| 1. Danh sách khách hàng                        |*");
            System.out.println("*| 2. Thêm tài khoản quản trị                     |*");
            System.out.println("*| 3. Cập nhập tài khoản quản trị                 |*");
            System.out.println("*| 4. Cập nhập trạng thái tài khoản khách hàng    |*");
            System.out.println("*| 5. Tìm kiếm khách hàng theo ID đăng nhập       |*");
            System.out.println("*| 6. Thoát                                       |*");
            System.out.println("*| Lựa chọn của bạn:                              |*");
            System.out.println("****************************************************");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:

                    break;
                case 2:
                    userImp.addRoot(sc);
                    break;
                case 3:
                   userImp.updateRoot(sc);
                    break;
                case 4:
                    userImp.updateStatus(sc);
                    break;
                case 5:
                    userImp.seachUserById(sc);
                    break;
                case 6:
                    exit = false;
                    break;
                default:
                    System.err.println(ShopMessage.NOTIFY_CATALOG_MANAGEMENT_CHOICE);
            }
        }while(exit);
    }
}
