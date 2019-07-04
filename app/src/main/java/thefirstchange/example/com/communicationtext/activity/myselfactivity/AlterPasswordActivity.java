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
import thefirstchange.example.com.communicationtext.util.ActivityCollector;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class AlterPasswordActivity extends BaseForCloseActivity implements View.OnClickListener{

    private Timer timer = new Timer();
    private static final String MY="thefirstchange.example.com.communicationtext.ALTERPASSWORD";

    private ImageView finishSelf;
    private EditText oldEdit;
    private EditText newEdit;
    private EditText secnewEdit;
    private Button alterBu;
    String oldPassword;
    String newPassword;
    String secnewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_password);
       // StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);
        finishSelf=(ImageView)findViewById(R.id.alter_password_finish);
        oldEdit=(EditText)findViewById(R.id.old_password_edit);
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

                if(!NetworkUtils.isConnected(AlterPasswordActivity.this)){
                    Toast.makeText(AlterPasswordActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

                oldPassword=oldEdit.getText().toString();
                newPassword=newEdit.getText().toString();
                secnewPassword=secnewEdit.getText().toString();

                if(oldPassword.isEmpty()){
                    Toast.makeText(AlterPasswordActivity.this,"请输入旧密码!",Toast.LENGTH_SHORT).show();
                    oldEdit.requestFocus();
                    return;
                }else if(newPassword.isEmpty()){
                   Toast.makeText(AlterPasswordActivity.this,"请输入新密码!",Toast.LENGTH_SHORT).show();
                   newEdit.requestFocus();
                    return;
               }else if(secnewPassword.isEmpty()){
                   Toast.makeText(AlterPasswordActivity.this,"请再次输入新密码!",Toast.LENGTH_SHORT).show();
                   secnewEdit.requestFocus();
                   return;
               }

               if(!newPassword.equals(secnewPassword)){
                   Toast.makeText(AlterPasswordActivity.this,"两次新密码输入不正确!",Toast.LENGTH_SHORT).show();
                   return;
               }

               if(!isOkPwd(newPassword)){
                   Toast.makeText(AlterPasswordActivity.this,"密码格式应为8-16位至少包含数字/字母/字符2种组合!",Toast.LENGTH_SHORT).show();
                  secnewEdit.setText("");
                   newEdit.setText("");
                  newEdit.requestFocus();
                   return;
               }


               SendToServer.changePassword(oldPassword,newPassword);


               timer = new Timer();
               timer.schedule(new TimerTask() {
                   @Override
                   public void run() {
                        AlterPasswordActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AlterPasswordActivity.this,"服务器繁忙,请稍后再试!",Toast.LENGTH_SHORT).show();
                            }
                        });
                   }
               },3000);

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

    protected void onResume(){
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MY);
        registerReceiver(broadcastReceiver, filter);
    }

    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        if(timer!=null){
            timer.cancel();
        }
    }

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MY.equals(intent.getAction())){

                String type = intent.getStringExtra("type");
                if(type.equals("upPwdRs")){
                    if(timer!=null){
                        timer.cancel();
                    }
                    String rs = intent.getStringExtra("rs");
                    if(rs.equals("ok")){
                        Toast.makeText(AlterPasswordActivity.this,"密码修改成功!",Toast.LENGTH_SHORT).show();
                        AlertDialog alertDialog=new AlertDialog.Builder(AlterPasswordActivity.this).create();
                        alertDialog.setTitle("提示");
                        alertDialog.setMessage("检测到密码修改是否重新登录");
                        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                SendToServer.loginout();
                                StaticAllList.removeAll();
                                ActivityCollector.finishAll();
                                Intent intent=new Intent(AlterPasswordActivity.this,LoginActivity.class);
                                intent.putExtra("kipeLogin",false);
                                startActivity(intent);

                            }
                        });
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SendToServer.loginout();
                                StaticAllList.removeAll();
                                ActivityCollector.finishAll();
                                Intent intent=new Intent(AlterPasswordActivity.this,LoginActivity.class);
                                intent.putExtra("kipeLogin",false);
                                startActivity(intent);
                            }
                        });
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        Button positiveBu=alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
                        Button negativeBu=alertDialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE);
                        positiveBu.setTextColor(getResources().getColor(R.color.black));
                        negativeBu.setTextColor(getResources().getColor(R.color.black));
                    }else if(rs.equals("erropwd")){   //旧密码  输入错误
                        Toast.makeText(AlterPasswordActivity.this,"旧密码错误,请重新输入!",Toast.LENGTH_SHORT).show();
                        oldEdit.setText("");
                        oldEdit.requestFocus();
                    }else if(rs.equals("errph")){    //账号不存在
                        Toast.makeText(AlterPasswordActivity.this,"该账号不存在!",Toast.LENGTH_SHORT).show();
                    }else{                        //其他错误
                        Toast.makeText(AlterPasswordActivity.this,"服务器繁忙,请稍后再试!",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }
    };

}
