package thefirstchange.example.com.communicationtext.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;

public class MyViewTools {
    //设置头像
    public static void setCircleImage(CircleImageView circleImageView, String path) {
        if(path==null){
            path="";
        }
        File file = new File(path);
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(path);
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                circleImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                circleImageView.setImageResource((R.drawable.ic_person_default));
            }
        }else{
            circleImageView.setImageResource((R.drawable.ic_person_default));
        }
    }



    //设置群头像
    public static void setCircleGroupIcon(CircleImageView imageView, String path) {
        if(path==null){
            path="";
        }
        File file = new File(path);
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(path);
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                imageView.setImageResource((R.mipmap.image_gr2));
            }
        }else{
            imageView.setImageResource((R.mipmap.image_gr2));
        }
    }

    //设置群头像
    public static void setGroupIcon(ImageView imageView, String path) {
        if(path==null){
            path="";
        }
        File file = new File(path);
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(path);
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                imageView.setImageResource((R.mipmap.image_gr2));
            }
        }else{
            imageView.setImageResource((R.mipmap.image_gr2));
        }
    }



}
