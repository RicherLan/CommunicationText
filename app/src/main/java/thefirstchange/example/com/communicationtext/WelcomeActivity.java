package thefirstchange.example.com.communicationtext;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.google.gson.Gson;

import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.gson.PersonalInfo;
import thefirstchange.example.com.communicationtext.login.LoginActivity;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.service.ObjectService;

public class WelcomeActivity extends Activity {

    //WelcomeActivity welcomeActivity;


     @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       //  setContentView(R.layout.activity_welcome);
//         welcomeActivity = this;

//         Config.kipWelcomePage
         Intent intent = new Intent(WelcomeActivity.this, NettyService.class);
         startService(intent);

        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

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


                            SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(WelcomeActivity.this);
                            boolean keep_login=pref.getBoolean("keep_login",false);
                            if (keep_login) {
                                keeplogin = true;
                            }
                        }

                    }
                }

                if(ObjectService.personalInfo==null){
                    ObjectService.personalInfo=new PersonalInfo();
                }

            if(keeplogin){
                Intent intent2 = new Intent(WelcomeActivity.this, MainActivity.class);
                intent2.putExtra("isneedlogin","yes");
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
            }else{
                Intent intent2=new Intent(WelcomeActivity.this,LoginActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
            }

                WelcomeActivity.this.finish();
                return true;

            }
        }).sendEmptyMessageDelayed(0,2000);

     }

}
