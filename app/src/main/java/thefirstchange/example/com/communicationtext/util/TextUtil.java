package thefirstchange.example.com.communicationtext.util;

import java.util.regex.Pattern;

public class TextUtil {
    //  判断字符串是不是整数
    public static boolean isInteger(String str) {

        if (null == str || str.trim().equals("")) {
            return false;
        }
        //不允许负数
        if(str.contains("-")) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    //  判断字符串是不是浮点数
    public  static boolean isDouble(String str) {

        if (null == str || str.trim().equals("")) {
            return false;
        }
        //不允许负数
        if(str.contains("-")) {
            return false;
        }
        if(str.startsWith("."))
            return false;
        if(str.endsWith("."))
            return false;
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

}
