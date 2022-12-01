package ra.bussiness.imp;
import ra.bussiness.design.IShop;
import ra.bussiness.entity.User;
import ra.config.ShopValidation;
import ra.data.DataURL;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserImp implements IShop<User, Integer> {


    @Override
    public boolean create(User user) {
        List<User> listUser = readFromFile();
        if (listUser == null) {
            listUser = new ArrayList<>();
        }
        listUser.add(user);
        boolean result = writeFromFile(listUser);
        return result;

    }

    @Override
    public boolean update(User user) {
        List<User> listUser = readFromFile();
        boolean returnData = false;
        for (int i = 0; i < listUser.size(); i++) {
            if (listUser.get(i).getUserId() == user.getUserId()) {
//                Thực hiện cập nhập
                listUser.set(i, user);
                returnData = true;
                break;
            }
        }
        boolean result = writeFromFile(listUser);
        if (result && returnData) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        List<User> listUser = readFromFile();
        boolean returnData =false;
        for (int i = 0; i < listUser.size(); i++) {
            if (listUser.get(i).getUserId() == id){
                listUser.remove(i);
                returnData = true;
                break;
            }
        }
        boolean result = writeFromFile(listUser);
        if (result && returnData) {
            return true;
        }
        return false;
    }


    @Override
    public List<User> readFromFile() {
        List<User> listUser = null;
        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            file = new File(DataURL.URL_USER_FILE);
            if (file.exists()) {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                listUser = (List<User>) ois.readObject();
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
        return listUser;
    }

    @Override
    public boolean writeFromFile(List<User> list) {
        File file = null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        boolean returnData = true;
        try {
            file = new File(DataURL.URL_USER_FILE);
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
    public User inputData(Scanner sc) {
        List<User> listUser = readFromFile();
        if (listUser.size()==0){
            listUser = new ArrayList<>();
        }
        User user = new User();
        user.setUserId(listUser.size()+1);
        do {
            System.out.print("Nhập vào tên tài khoản: ");
            String name = sc.nextLine();
            System.out.print("\n");
            boolean check = ShopValidation.checkUserNameLength(name);
            if (check){
                check = ShopValidation.checkValidateUserName(name);
                if (check){
                    for (User userNew :listUser) {
                        if (userNew.getUserName().equals(name)){
                            check = false;
                            break;
                        }
                    }
                    if (check){
                        user.setUserName(name);
                        break;
                    }else {
                        System.err.println("Tài khoản đã tồn tại");
                    }
                }
            }else {
                System.err.println("Tên đăng nhập trên 6 ký tự");
            }
        }while (true);
        do {
            System.out.print("Nhập vào mật khẩu: ");
            String password = sc.nextLine();
            System.out.print("\n");
            boolean check = ShopValidation.checkPassword(password);
            if (check){
                System.out.print("Nhập lại mật khẩu: ");
                String repeatPasswood = sc.nextLine();
                check = ShopValidation.checkPassword(repeatPasswood);
                if (check){
                    if (password.equals(repeatPasswood)){
                        user.setUserPassword(password);
                        break;
                    }else {
                        System.err.println("Mật khẩu không chính xác, xin vui lòng nhập lại");
                    }
                }
            }else {
                System.err.println("Mật khẩu phải có ít nhất 6 ký tự");
            }
        }while (true);
        do {
            System.out.print("Nhập họ và tên của bạn: ");
            String name = sc.nextLine();
            System.out.print("\n");
            boolean check = ShopValidation.checkEmptyString(name);
            if (check){
                user.setUserFullName(name);
                break;
            }else {
                System.err.println("Không được để trống");
            }
        }while (true);
        do {
            System.out.print("Nhập vào email:");
            String email = sc.nextLine();
            System.out.print("\n");
            boolean check = ShopValidation.checkEmail(email);
            if (check){
                user.setUserEmail(email);
                break;
            }else {
                System.err.println("Email không đúng, Vui lòng nhập lại");
            }
        }while (true);
        do {
            System.out.print("Nhập vào số điện thoại (Số điện thoại bắt đầu bằng 84) :  ");
            String phoneNumber = sc.nextLine();
            System.out.println("\n");
            boolean checkPhone = ShopValidation.checkPhoneNumber(phoneNumber);
               if (checkPhone){
                    user.setUserPhoneNumber(phoneNumber);
                    break;
                }else {
                    System.err.println("Số điện thoại của bạn không đúng");
                }

        }while (true);
        return user;
    }

    @Override
    public void displayData() {
        List<User> listUser = readFromFile();
        for (User user: listUser) {
            String status = "Không hoạt động";
            if (user.isUserStatus()) {
                status = "Hoạt động";
            }
            System.out.printf("Mã tài khoản:%-10d  Tên tài khoản: %-20s Họ và tên: %-30s Số điện thoại: %-15s \n",user.getUserId(),user.getUserName(),user.getUserFullName(),user.getUserPhoneNumber());
            System.out.printf("Email: %-20s Loại tài khoản: %-25s Trạng thái: %-10s Ngày đăng ký: %-20s\n",user.getUserEmail(),user.isUserPermission(), status);
        }
    }

    public User checkLogin(String userName, String password) {
        List<User> listUser = readFromFile();
        for (User user : listUser) {
            if (user.getUserName().equals(userName)&&user.getUserPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public void addRoot(Scanner sc){
        List<User> userList = readFromFile();
        System.out.println("Nhập số lượng tài khoản quản trị muốn thêm vào");
        do {
            String str = sc.nextLine();
            if (ShopValidation.checkEmptyString(str)) {
                if (ShopValidation.checkInteger(str)) {
                    int num = Integer.parseInt(str);
                    for (int i = 0; i < num; i++) {
                        System.out.println("Nhập dữ liệu cho tài khoản: " + (i + 1));
                        UserImp userImp1 = new UserImp();
                        User user = userImp1.inputData(sc);
                        userList.add(user);
                        userImp1.create(user);
                    }
                } else {
                    System.err.println("Vui lòng nhập vào 1 số nguyên");
                }
            } else {
                System.err.println("Không được để trống vui lòng nhập 1 số nguyên vào");
            }
        } while (true);
    }

    public void updateRoot(Scanner sc) {

    }

    public void updateStatus(Scanner sc) {
        List<User> userList = readFromFile();
        System.out.println("Nhập id tài khoản khách hàng bạn muốn cập nhật trạng thái vào");
        do {
            String strUserId = sc.nextLine();
            if (ShopValidation.checkEmptyString(strUserId)){
                if (ShopValidation.checkInteger(strUserId)){
                    boolean checkexist = true;
                    int userId = Integer.parseInt(strUserId);
                    for (User user:userList) {
                        if (user.getUserId()==userId&&!user.isUserStatus()){
                            checkexist = false;
                        }
                    }
                    if (checkexist){
                        System.out.println("Không tìm thấy tài khoản khách hàng có id này");
                    } else {
                        userList.get(userId).setUserStatus(! userList.get(userId).isUserStatus());
                    }
                } else {
                    System.out.println("");
                }
            } else {
                System.out.println("");
            }
        } while (true);
    }

    public void seachUserById(Scanner sc) {
        List<User> userList = readFromFile();
        System.out.println("Nhập tên tài khoản cần tìm kiếm vào");
        do {
            String searchName = sc.nextLine();
            boolean check = true;
            for (User user : userList) {
                if (!user.isUserPermission()&&user.getUserName().equals(searchName)||!user.isUserPermission()&&user.getUserFullName().equals(searchName)){
                    displayData();
                    check = false;
                    break;
                }
            }
            if (check){
                System.out.println("Không tìm thấy tài khoản khách hàng");
            }
        } while (true);
    }

}
