package thefirstchange.example.com.communicationtext.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.db.dao.BasicDataDao;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.service.NettyService;

public class MyFileTools {

    //获得本机中存储的某账户的头像位置
    public static String getUserIconPath(String ph){
        String path =  Config.usericonpath+"/"+ ph+".jpg";;
        File file = new File(path);
        if (file.exists()) {
            return path;
        }

        return null;
    }

    //保存用户头像至本地    返回路径
    public static String saveUserIcon(String ph,byte[] ic){
        String path  = Config.usericonpath+"/"+ ph+".jpg";
        // byte[] bs3 = Base64.decode(bs2,Base64.DEFAULT);
        OutputStream out = null;
        try {
            out = new FileOutputStream(path);
            InputStream is = new ByteArrayInputStream(ic);
            byte[] buff = new byte[1024];
            int len = 0;
            while((len=is.read(buff))!=-1){
                out.write(buff, 0, len);
            }
            is.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }

    //获得指定路径的bitmap图像
    public static Bitmap getBitmapByPath(String path){
        if(path==null){
            path="";
        }
        Bitmap bitmap = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
             bitmap  = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    //获得本机中存储的某群的头像位置
    public static String getGroupIconPath(int groupid){
        String path =  Config.groupiconpath+"/"+ (groupid+"")+".jpg";;
        File file = new File(path);
        if (file.exists()) {
            return path;
        }

        return "";
    }


    //保存群头像至本地    返回路径
    public static String saveGroupIcon(int groupid,byte[] ic){
        String path  = Config.groupiconpath+"/"+ (groupid+"")+".jpg";
        // byte[] bs3 = Base64.decode(bs2,Base64.DEFAULT);
        OutputStream out = null;
        try {
            out = new FileOutputStream(path);
            InputStream is = new ByteArrayInputStream(ic);
            byte[] buff = new byte[1024];
            int len = 0;
            while((len=is.read(buff))!=-1){
                out.write(buff, 0, len);
            }
            is.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }


    //保存群头像至本地    返回路径
    public static String saveGroupIcon(int groupid,Bitmap ic){
        String path  = Config.groupiconpath+"/"+ (groupid+"")+".jpg";

        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(f);
            ic.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }


    //保存群头像至本地    返回路径           并更新群列表和数据库
    public static String saveGroupIconAndUpdate(int groupid,Bitmap ic){
        String path  = Config.groupiconpath+"/"+ (groupid+"")+".jpg";

        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(f);
            ic.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        //更新群列表和数据库
        for(int gidtemp: StaticAllList.groupList.keySet()){
            if(gidtemp==groupid){
                StaticAllList.groupList.get(gidtemp).setGroupicon(path);
            }
        }

        //更新数据库
        //保存到数据库
        BasicDataDao basicDataDao = BasicDataDao.getInstance(NettyService.nettyService);
        basicDataDao.updateGroupIcon(groupid,path);


        return path;
    }


}
