package thefirstchange.example.com.communicationtext.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import thefirstchange.example.com.communicationtext.service.ObjectService;

public class UserUtil {


    public static Bitmap getPersonalIcon(){
        if(ObjectService.personalInfo.getIcon()==null||ObjectService.personalInfo.getIcon().equals("")){
            return null;
        }
        File file = new File(ObjectService.personalInfo.getIcon());
        if(file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(ObjectService.personalInfo.getIcon());
                Bitmap bitmap  = BitmapFactory.decodeStream(fis);
                return bitmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }

        }

        return null;
    }

}
