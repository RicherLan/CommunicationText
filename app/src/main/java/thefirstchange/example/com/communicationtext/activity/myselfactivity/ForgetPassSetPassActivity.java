package thefirstchange.example.com.communicationtext.activity.myselfactivity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.login.LoginActivity;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.usersignin.RegisterCaptchaActivity;
import thefirstchange.example.com.communicationtext.usersignin.RegisterPhonenumberActivity;
import thefirstchange.example.com.communicationtext.util.ActivityCollector;
import thefirstchange.example.com.communicationtext.util.EncryptTool;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

/*
    忘记密码
 */
public class ForgetPassSetPassActivity extends BaseForCloseActivity implements View.OnClickListener{

    private Timer timer = new Timer();

    private ImageView finishSelf;
    private EditText newEdit;
    private EditText secnewEdit;
    private Button alterBu;

    String phonenumber;
    String newPassword;
    String secnewPassword;

    private String imageCode ;    //图片验证码
    private String noteCode;     //短信验证码
    private String encrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpas_setpas);
        // StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);

        Intent intent = getIntent();
        imageCode = intent.getStringExtra("imageCode");
        encrypt = intent.getStringExtra("encrypt");
        encrypt = (encrypt);
        phonenumber = intent.getStringExtra("phonenumber");


        finishSelf=(ImageView)findViewById(R.id.alter_password_finish);
        newEdit=(EditText)findViewById(R.id.new_password_edit);
        secnewEdit=(EditText)findViewById(R.id.sec_new_password_edit);
        alterBu=(Button)findViewById(R.id.alter_password_bu);
        finishSelf.setOnClickListener(this);
        alterBu.setOnClickListener(this);

    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.alter_password_finish:
                finish();
                break;
            case R.id.alter_password_bu:

                if(!NetworkUtils.isConnected(ForgetPassSetPassActivity.this)){
                    Toast.makeText(ForgetPassSetPassActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }


                newPassword=newEdit.getText().toString();
                secnewPassword=secnewEdit.getText().toString();

                if(newPassword.isEmpty()){
                    Toast.makeText(ForgetPassSetPassActivity.this,"请输入新密码!",Toast.LENGTH_SHORT).show();
                    newEdit.requestFocus();
                    return;
                }
                if(secnewPassword.isEmpty()){
                    Toast.makeText(ForgetPassSetPassActivity.this,"请再次输入新密码!",Toast.LENGTH_SHORT).show();
                    secnewEdit.requestFocus();
                    return;
                }

                if(!newPassword.equals(secnewPassword)){
                    Toast.makeText(ForgetPassSetPassActivity.this,"两次新密码输入不正确!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isOkPwd(newPassword)){
                    Toast.makeText(ForgetPassSetPassActivity.this,"密码格式应为8-16位至少包含数字/字母/字符2种组合!",Toast.LENGTH_SHORT).show();
                    secnewEdit.setText("");
                    newEdit.setText("");
                    newEdit.requestFocus();
                    return;
                }

                Intent intent2 = new Intent(ForgetPassSetPassActivity.this, ForgetPassSubmitActivity.class);
                intent2.putExtra("phonenumber",phonenumber);
                intent2.putExtra("password",newPassword);
                intent2.putExtra("imageCode",imageCode);
                intent2.putExtra("encrypt",encrypt);

                startActivity(intent2);

                break;
        }
    }

    //8-16位至少包含数字/字母/字符2种组合
    private boolean isOkPwd(String pwd){
        if(pwd.length()<8||pwd.length()>16){
            return false;
        }

        boolean isnum = false;
        boolean isletter = false;
        boolean ischaracter = false;
        for(int i=0;i<pwd.length();++i){
            char ch = pwd.charAt(i);
            if(ch>='0'&&ch<='9'){
                isnum = true;
            }else if(ch>='a'&&ch<='z'||ch>='A'&&ch<='Z'){
                isletter = true;
            }else{
                ischaracter = true;
            }
        }

        int sum = 0;
        if(isnum){
            ++sum;
        }
        if(isletter){
            ++sum;
        }
        if(ischaracter){
            ++sum;
        }
        if(sum<2){
            return false;
        }
        return true;
    }



    protected void onDestroy(){
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
        }
    }

}
