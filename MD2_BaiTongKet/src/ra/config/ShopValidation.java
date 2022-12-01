package ra.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopValidation {
    public static boolean checkEmail(String str) {
        String regex = "^(.+)@(.+)$";
        Pattern path = Pattern.compile(regex);
        Matcher matcher = path.matcher(str);
        return matcher.matches();
    }

    public static boolean checkLength(String str,int min, int max){
        if (str.trim().length()>=min&&str.trim().length()<=max){
            return true;
        }
        return false;
    }
    public static boolean checkEmptyString(String str) {
        if (str.trim().length() != 0) {
            return true;
        }
        return false;
    }

    public static boolean checkUserNameLength(String str) {
        if (str.trim().length() > 6) {
            return true;
        }
        return false;
    }

    public static boolean checkValidateUserName(String str) {
        String regex = "^[a-zA-Z0-9._-]{3,}$";
        Pattern path = Pattern.compile(regex);
        Matcher matcher = path.matcher(str);
        return matcher.matches();
    }

    public static boolean checkPassword(String str) {
        if (str.trim().length() >= 6) {
            return true;
        }
        return false;
    }

    public static boolean checkPhoneNumber(String str) {
        if (str.charAt(0) == '8' && str.charAt(1) == '4' && str.length() == 11) {
            return true;
        }
        return false;
    }

    public static boolean checkProductId_length (String str){
        if (str.trim().length()==5){
            return true;
        }
        return false;
    }
    public static boolean checkProductId (String str){
        if (str.charAt(0)=='P'){
            return true;
        }
        return false;
    }

    public static boolean checkInteger(String str ){
        try {
            int number = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    }


}
