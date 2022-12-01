package ra.presentation;

import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.User;
import ra.bussiness.imp.CatalogImp;
import ra.bussiness.imp.ProductImp;
import ra.bussiness.imp.UserImp;
import ra.config.ShopMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShopRun {
    private static CatalogImp catalogImp = new CatalogImp();
    private static UserImp userImp = new UserImp();
    private static ProductImp productImp = new ProductImp();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<User> listUser = new ArrayList<>();
        User user = new User(1, "kien", "123456", "123456", "012345678", "kien@gmail.com", "LeKien", true, true);
        userImp.create(user);

        do {
            System.out.println("*********************NEMSHOP.VN*********************");
            System.out.println("*| 1. Đăng nhập                                   |*");
            System.out.println("*| 2. Đăng ký                                     |*");
            System.out.println("*| 3. Thoát                                       |*");
            System.out.println("*| Sự lựa chọn của bạn:                           |*");
            System.out.println("****************************************************");

            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    login(sc);
                    break;
                case 2:
                    register(sc);
                    break;
                case 3:
                    sc.close();
                    System.exit(0);
                default:
                    System.err.println(ShopMessage.NOTIFY_SHOP_CHOICE);
            }
        } while (true);


    }

    public static void displayMenuShopManagement(Scanner sc) {
        boolean exit = true;
        do {
            System.out.println("********************QUẢN LÝ SHOP********************");
            System.out.println("*| 1. Quản lý danh mục                            |*");
            System.out.println("*| 2. Quản lý sản phẩm                            |*");
            System.out.println("*| 3. Quản lý user                                |*");
            System.out.println("*| 4. Quản lý màu sắc                             |*");
            System.out.println("*| 5. Quản lý kích thước                          |*");
            System.out.println("*| 6. Thoát                                       |*");
            System.out.println("*| Lựa chọn của bạn:                              |*");
            System.out.println("****************************************************");


            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    CatalogMenu.displayMenuCatalogManagement(sc);
                    break;
                case 2:
                    ProductMenu.displayMenuProductManagement(sc);
                    break;
                case 3:
                    UserMenu.displayMenuUserManagement(sc);
                    break;
                case 4:
                    ColorMenu.displayMenuColorManagement(sc);
                    break;
                case 5:
                    SizeMenu.displayMenuCatalogManagement(sc);
                    break;
                case 6:
                    exit = false;
                    break;
                default:
                    System.err.println(ShopMessage.NOTIFY_SHOP_MANAGEMENT_CHOICE);
            }
        } while (exit);
    }

    public static void login(Scanner sc) {
        do {
            System.out.print("Tên đăng nhập: ");
            String userName = sc.nextLine();
            System.out.print("Mật khẩu: ");
            String password = sc.nextLine();
            User user = userImp.checkLogin(userName, password);
            if (user != null) {
                //Dang nhap thanh cong
                if (user.isUserPermission()) {
                    //Tai khoan admin
                    displayMenuShopManagement(sc);
                } else {
                    //Tai khoan user
                    ProductMenu.displayMenuUser(sc);
                }
                break;
            } else {
                //Dang nhap that bai
                System.err.println(ShopMessage.NOTIFY_LOGIN_FAIL);
                System.out.println("****************************************************");
                System.out.println("*| 1. Đăng nhập lại                               |*");
                System.out.println("*| 2. Đăng ký tài khoản mới                       |*");
                System.out.println("*| 3. Thoát                                       |*");
                System.out.println("*| Sự lựa chọn của bạn :                          |*");
                System.out.println("****************************************************");


                int choice = Integer.parseInt(sc.nextLine());
                if (choice == 2) {
                    register(sc);
                } else if (choice == 3) {
                    break;
                }
            }
        } while (true);
    }

    public static void register(Scanner sc) {
        User user = userImp.inputData(sc);
        boolean result = userImp.create(user);
        if (result) {
            System.out.println("Đăng kí thành công");
            login(sc);
        } else {
            System.out.println("Đã xảy ra lỗi trong quá trình đăng kí.");
            register(sc);
        }
    }

}
