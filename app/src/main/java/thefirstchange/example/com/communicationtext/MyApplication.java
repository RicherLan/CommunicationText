package thefirstchange.example.com.communicationtext;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Vector;

import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.gson.PersonalInfo;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.service.ObjectService;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        boolean keeplogin = false;
        SharedPreferences sh = getSharedPreferences(Config.sharedPreferences_personal_info, Activity.MODE_PRIVATE);
        String json = sh.getString(Config.sharedPreferences_personal_info,"");
        if(json!=null&&!json.equals("")){
            Gson gson = new Gson();
            PersonalInfo personalInfo = gson.fromJson(json,PersonalInfo.class);
            if(personalInfo!=null){
                ObjectService.personalInfo = personalInfo;
                if(ObjectService.personalInfo.getPhonenumber()!=null&&!ObjectService.personalInfo.getPhonenumber().trim().equals("")
                        &&ObjectService.personalInfo.getPassword()!=null&&!ObjectService.personalInfo.getPassword().trim().equals("")){
                    keeplogin=true;
                }

            }
        }

        if(ObjectService.personalInfo==null){
            ObjectService.personalInfo=new PersonalInfo();
        }

     /*   if(keeplogin){

            Vector<String> user_icon_path = new Vector<>();
             Vector<String> user_icon_name = new Vector<>();
            MyTools.getAllFileName(Config.usericonpath,user_icon_path,user_icon_name);
            setUserImage2(user_icon_path,user_icon_name,ObjectService.personalInfo.getPhonenumber());

            Intent intent2 = new Intent(MyApplication.this, MainActivity.class);
            intent2.putExtra("isneedlogin","yes");
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent2);

            Intent intent = new Intent(this, NettyService.class);
            //intent.putExtra("needlogin","yes");
            startService(intent);


        }else*/
        {

//            Intent intent2=new Intent(MyApplication.this,LoginActivity.class);
//            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent2);


            Intent intent = new Intent(this, NettyService.class);
//            intent.putExtra("type","notneedlogin");
            startService(intent);

        }


    }

    private void setUserImage2(Vector<String> user_icon_path,Vector<String> user_icon_name,String account){
        String path = "";
        boolean flag = false;
        for(int i=0;i<user_icon_name.size();++i){
            String p = user_icon_name.get(i);
            int index = p.lastIndexOf(".");
            String acount2 = p.substring(0,index);
            if(acount2.equals(account)){
                flag = true;
                path = user_icon_path.get(i);
                ObjectService.personalInfo.setIcon(path);
            }
        }

        if(flag){
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(path);
                Bitmap bitmap  = BitmapFactory.decodeStream(fis);
                ObjectService.personalIcon = bitmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else{
            ObjectService.personalInfo.setIcon("");
            ObjectService.personalIcon = null;
        }
    }


}
