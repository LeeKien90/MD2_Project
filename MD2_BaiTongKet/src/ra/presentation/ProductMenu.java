package ra.presentation;

import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.Product;
import ra.bussiness.imp.ProductImp;
import ra.bussiness.imp.UserImp;
import ra.config.ShopMessage;
import ra.config.ShopValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductMenu {
    private static ProductImp productImp = new ProductImp();
    private static UserImp userImp = new UserImp();
    public static void displayMenuProductManagement(Scanner sc){
        boolean exit = true;
        do {
            System.out.println("**************QUẢN LÝ SẢN PHẨM**********************");
            System.out.println("*| 1. Danh sách sản phẩm                          |*");
            System.out.println("*| 2. Thêm mới sản phẩm                           |*");
            System.out.println("*| 3. Cập nhập sản phẩm                           |*");
            System.out.println("*| 4. Xóa sản phẩm                                |*");
            System.out.println("*| 5. Thoát                                       |*");
            System.out.println("*| Lựa chọn của bạn:                              |*");
            System.out.println("****************************************************");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:
                    productImp.displayProductByCatalog();
                    break;
                case 2:
                    inputListProduct(sc);
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:
                    exit = false;
                    break;
                default:
                        System.err.println(ShopMessage.NOTIFY_PRODUCT_MANAGEMENT_CHOICE);
            }
        }while(exit);
    }

    public static void displayMenuUser(Scanner sc) {
        boolean exit = true;
        do {
            System.out.println("********************NEMSHOP.VN**********************");
            System.out.println("*| 1. Danh sách sản phẩm theo cây thư mục         |*");
            System.out.println("*| 2. Danh sách sản phẩm mới                      |*");
            System.out.println("*| 3. Danh sách sản phẩm giảm giá                 |*");
            System.out.println("*| 4. Tìm kiếm sản phẩm                           |*");
            System.out.println("*| 5. Thoát                                       |*");
            System.out.println("*| Lựa chọn của bạn:                              |*");
            System.out.println("****************************************************");


            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:

                    break;
                case 2:
                    productImp.listProductNew(sc);
                    break;
                case 3:
                    productImp.listProductDiscount(sc);
                    break;
                case 4:
                    displaySeachManagement(sc);
                    break;
                case 5:
                    exit = false;
                    break;
                default:
                    System.err.println(ShopMessage.NOTIFY_PRODUCT_MANAGEMENT_CHOICE);
            }
        } while (exit);
    }
    public  static void displaySeachManagement(Scanner sc){
        boolean exit = true;
        do {
            System.out.println("****************TÌM KIẾM SẢN PHẨM*******************");
            System.out.println("*| 1. Tìm kiếm theo tên sản phẩm                  |*");
            System.out.println("*| 2. Tìm kiếm sản phẩm theo cây danh mục         |*");
            System.out.println("*| 3. Tìm kiếm sản phẩm theo giá bán              |*");
            System.out.println("*| 4. Tìm kiếm sản phẩm theo khoảng giảm giá      |*");
            System.out.println("*| 5. Thoát                                       |*");
            System.out.println("*| Lựa chọn của bạn:                              |*");
            System.out.println("****************************************************");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:
                    productImp.diplayProductByName(sc);
                    break;
                case 2:

                    break;
                case 3:
                    productImp.displayProductByExportPrice(sc);
                    break;
                case 4:
                    productImp.displayProductByDiscount(sc);
                    break;
                case 5:
                    exit = false;
                    break;
                default:
                    System.err.println(ShopMessage.NOTIFY_PRODUCT_MANAGEMENT_CHOICE);
            }
        }while(exit);
    }
    public static void inputListProduct(Scanner sc) {
        List<Product> productList = productImp.readFromFile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        System.out.println("Nhập số lượng sản phẩm mới thêm vào");
        int num = 0;
        do {
            String strNum = sc.nextLine();
            if (ShopValidation.checkEmptyString(strNum)) {
                if (ShopValidation.checkInteger(strNum)) {
                    num = Integer.parseInt(strNum);
                    break;
                } else {
                    System.err.println("Vui lòng nhập vào 1 số nguyên");
                }
            } else {
                System.err.println("Không được để trống vui lòng lựa chọn");
            }
        } while (true);
        for (int i = 0; i < num; i++) {
            System.out.println("Nhập dữ liệu cho sản phẩm: " + (i + 1));

            Product pro = productImp.inputData(sc);
            productList.add(pro);
            productImp.writeFromFile(productList);
        }
    }
}
