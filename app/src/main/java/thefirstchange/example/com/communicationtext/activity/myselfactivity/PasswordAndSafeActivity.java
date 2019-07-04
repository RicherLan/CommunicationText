package thefirstchange.example.com.communicationtext.activity.myselfactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.CourseAndScore;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.login.LoginActivity;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.ActivityCollector;

public class PasswordAndSafeActivity extends BaseForCloseActivity implements View.OnClickListener{
    private RelativeLayout finishThisAccount;
    private ImageView finishSelf;
    private RelativeLayout alterPassword;
    private CircleImageView ivHead;
    private TextView nickName;
    private TextView accountNumber;
    private TextView phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_and_safe);
      //  StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);
        initView();
    }

    private void initView(){
        finishThisAccount=(RelativeLayout)findViewById(R.id.finish_this_account_lay);
        alterPassword=(RelativeLayout)findViewById(R.id.user_alter_password_lay);
        ivHead=(CircleImageView)findViewById(R.id.personal_iv_image);
        nickName=(TextView)findViewById(R.id.personal_nickname_text);
        accountNumber=(TextView)findViewById(R.id.personal_phone_text);
        phoneNumber=(TextView)findViewById(R.id.personal_phonenumber_text);
        finishSelf=(ImageView)findViewById(R.id.finish_password_safe);
        finishSelf.setOnClickListener(this);
        nickName.setText(ObjectService.personalInfo.getNickname());
        accountNumber.setText(ObjectService.personalInfo.getPhonenumber());
        phoneNumber.setText(ObjectService.personalInfo.getPhonenumber());
        alterPassword.setOnClickListener(this);
        finishThisAccount.setOnClickListener(this);

        setUserImage();
    }

    private void setUserImage(){


//        Bitmap bitmap  = UserUtil.getPersonalIcon();
        if(ObjectService.personalIcon!=null) {
            ivHead.setImageBitmap(ObjectService.personalIcon);
        }

//        else{
//            ivHead.setImageDrawable(getResources().getDrawable(R.drawable.user_image1));
//        }
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.finish_this_account_lay:

                SendToServer.loginout();
                SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor=pref.edit();
                editor.putBoolean("remember_password",true);
                editor.putBoolean("keep_login",false);
                editor.putString("account",ObjectService.personalInfo.getPhonenumber());
                editor.putString("password",ObjectService.personalInfo.getPassword());
                editor.apply();

                CourseAndScore.removeAll();
                MyMessageQueue.removeAll();
                StaticAllList.removeAll();
                ActivityCollector.finishAll();
                Intent intent=new Intent(this, LoginActivity.class);
                startActivity(intent);

                break;
            case R.id.user_alter_password_lay:
                Intent intent1=new Intent(this,AlterPasswordActivity.class);
                startActivity(intent1);
                break;
            case R.id.finish_password_safe:
                finish();
                break;
                default:
                    break;
        }
    }
}
