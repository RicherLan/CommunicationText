package thefirstchange.example.com.communicationtext.util;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.gson.PersonalInfo;

public class MyTools {

    //实现判断某天是星期几
    public static int getWeekday(Date date){//必须yyyy-MM-dd

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int week = calendar.get(Calendar.DAY_OF_WEEK);
        if(week==0){
            week=7;
        }
        return week;
    }


    public static boolean isInteger(String str) {

        if (null == str || str.trim().equals("")) {
            return false;
        }
        //不允许负数
        if (str.contains("-")) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static PersonalInfo handlePeopleInResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    PersonalInfo personalInfo = new PersonalInfo();

                    return personalInfo;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    //将文件编码成字符串
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);

    }

    //将字符串解码为文件
    public static void decoderBase64File(String base64Code, String savePath) throws Exception {
        byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
        FileOutputStream out = new FileOutputStream(savePath);
        out.write(buffer);
        out.close();
    }

    public static final byte[] compress(String paramString) {
        if (paramString == null)
            return null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ZipOutputStream zipOutputStream = null;
        byte[] arrayOfByte;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
            zipOutputStream.putNextEntry(new ZipEntry("0"));
            zipOutputStream.write(paramString.getBytes());
            zipOutputStream.closeEntry();
            arrayOfByte = byteArrayOutputStream.toByteArray();
        } catch (IOException localIOException5) {
            arrayOfByte = null;
        } finally {
            if (zipOutputStream != null)
                try {
                    zipOutputStream.close();
                } catch (IOException localIOException6) {
                }
            if (byteArrayOutputStream != null)
                try {
                    byteArrayOutputStream.close();
                } catch (IOException localIOException7) {
                }
        }
        return arrayOfByte;


    }

    public static  String string2Json(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            switch (c){
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {

        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }

        return unicode.toString();
    }


    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }


    //从字符串中  解析出所有数
    public static Vector<Integer> getIntFromString(String string){

        Vector<Integer> nums = new Vector<>();
        int num = 0;
        boolean flag = true;
        for(int j=0;j<string.length();++j) {

            if (MyTools.isInteger(string.charAt(j) + "")) {
                num = num * 10 + Integer.parseInt(string.charAt(j) + "");

            }else if(num!=0){
                flag = false;
            }
            if(!flag||(num!=0&&j+1==string.length())){
                flag=true;
                nums.add(num);
                num=0;
            }
        }
        return nums;
    }

    /**
     * 把Bitmap转Byte
     */
    public static byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    public static String trimlast(String str){

        int index = -1;
        for(int i=str.length()-1;i>=0;--i){
            if(str.charAt(i)!=' '){
                index = i;
                break;
            }
        }
        if(index!=-1){
            return str.substring(0,index+1);
        }
        return str;
    }

    public static boolean stringIsNumber(String string){


        for(int i=0;i<string.length();++i){
            if(!isInteger(string.charAt(i)+"")){
                return false;
            }
        }
        return true;
    }

    /**
     * 获取某个文件夹下的所有文件
     *
     * @param fileNameList 存放文件名称的list
     * @param path 文件夹的路径
     * @return
     */
    public static void getAllFileName(String path,Vector<String> filePathNameList,Vector<String> fileNameList) { //ArrayList<String> files = new ArrayList<String>();
        boolean flag = false;
        File file = new File(path);
        File[] tempList = file.listFiles();
        if(tempList==null){
            return;
        }
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                // System.out.println("文     件：" + tempList[i]);
                filePathNameList.add(tempList[i].toString());
                fileNameList.add(tempList[i].getName());
            }
//            if (tempList[i].isDirectory()) { /
//                // System.out.println("文件夹：" + tempList[i]);
//            getAllFileName(tempList[i].getAbsolutePath(),fileNameList);
//            }
        }
        return;
    }

    //获得某账号的头像
    public static String getIconPath(String ph) {

        String path = Config.usericonpath;
        File file = new File(path);
        File[] tempList = file.listFiles();
        if (tempList == null) {
            return null;
        }
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                // System.out.println("文     件：" + tempList[i]);
                String filePathName = tempList[i].toString();
                String fileName = tempList[i].getName();

                int index = fileName.lastIndexOf(".");
                String acount = fileName.substring(0, index);
                String s = ph;
                if (acount.equals(ph)) {
                    return filePathName;
                }

            }

        }
        return null;
    }
}
