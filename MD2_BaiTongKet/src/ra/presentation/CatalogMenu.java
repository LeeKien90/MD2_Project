package ra.presentation;

import ra.bussiness.entity.Catalog;
import ra.bussiness.imp.CatalogImp;
import ra.config.ShopMessage;
import ra.config.ShopValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CatalogMenu {
    private static CatalogImp catImp = new CatalogImp();
    public static void displayMenuCatalogManagement(Scanner sc){

        boolean exit = true;
            do {
                System.out.println("**************QUẢN LÝ DANH MỤC**********************");
                System.out.println("*| 1. Danh sách danh mục                          |*");
                System.out.println("*| 2. Thêm mới danh mục                           |*");
                System.out.println("*| 3. Cập nhập danh mục                           |*");
                System.out.println("*| 4. Xóa danh mục                                |*");
                System.out.println("*| 5. Tìm kiếm danh mục theo tên                  |*");
                System.out.println("*| 6. Thoát                                       |*");
                System.out.println("*| Lựa chọn của bạn:                              |*");
                System.out.println("****************************************************");

                int choice = Integer.parseInt(sc.nextLine());
                switch (choice){
                    case 1:
                        catImp.displayListCatalog();
                        break;
                    case 2:
                        Catalog catNew = catImp.inputData(sc);
                        boolean result = catImp.create(catNew);
                        if (result){
                            System.out.println(ShopMessage.NOTIFY_CATALOG_CREATE_SUCCESS);
                        }else{
                            System.err.println(ShopMessage.NOTIFY_CATALOG_CREATE_FAIL);
                        }

                        break;
                    case 3:

                        break;
                    case 4:
                        deleteCataloglist(sc);
                        break;
                    case 5:
                        searchCatalogByName(sc);
                        break;
                    case 6:
                        exit = false;
                        break;
                    default:
                        System.err.println(ShopMessage.NOTIFY_CATALOG_MANAGEMENT_CHOICE);
                }
            }while(exit);
    }
    public static void deleteCataloglist(Scanner sc) {
        System.out.println("Nhập id danh mục bạn muốn xóa vào");
        int catalogId = 0;
        do {
            String str = sc.nextLine();
            if (ShopValidation.checkEmptyString(str)) {
                if (ShopValidation.checkInteger(str)) {
                    catalogId = Integer.parseInt(str);
                    break;
                } else {
                    System.err.println("Vui lòng nhạp vào 1 số nguyên");
                }
            } else {
                System.err.println("Không được để trống");
            }
        } while (true);

        boolean result = catImp.delete(catalogId);
        if (result) {
            System.out.println("Xóa thành công");
        } else {
            System.out.println("Đã xảy ra lỗi xóa thất bại");
        }
    }

    public static void searchCatalogByName(Scanner sc) {
        List<Catalog> catalogList = catImp.readFromFile();
        if (catalogList == null) {
            catalogList = new ArrayList<>();
        }
        System.out.println("Nhập tên danh mục muốn tìm kiêm vào");
        do {
            String catName = sc.nextLine();
            if (ShopValidation.checkEmptyString(catName)) {
                boolean check = true;
                for (Catalog cat : catalogList) {
                    if (cat.getCatalogName().equals(catName)) {
                        System.out.printf("%-10s %-30s  %-20s\n", "Mã danh mục", "Tên danh mục", "Trạng thái");
                        catImp.displayData();
                        check = false;
                        break;
                    }
                }
                if (check) {
                    System.out.println("Không tím thấy thư mục");
                    break;
                }
            } else {
                System.out.println("Không đươc để trống vui lòng nhập tên vào");
            }
        } while (true);
    }
}
