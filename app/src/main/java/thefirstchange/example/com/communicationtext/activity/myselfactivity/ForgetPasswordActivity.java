package thefirstchange.example.com.communicationtext.activity.myselfactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.https.BaseCallBack;
import thefirstchange.example.com.communicationtext.https.BaseOkHttpClient;
import thefirstchange.example.com.communicationtext.routePath.UserRoutePath;
import thefirstchange.example.com.communicationtext.usersignin.RegisterCaptchaActivity;
import thefirstchange.example.com.communicationtext.usersignin.RegisterPhonenumberActivity;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class ForgetPasswordActivity extends BaseForCloseActivity implements View.OnClickListener{

    private String phonenumber;

    private ImageView forgetpassword_back_tv;
    private TextView forgetpass_next_tv;
    private TextView forgetpass_phone_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        initView();
        initEvent();

    }

    private void initView(){
        forgetpassword_back_tv = (ImageView)this.findViewById(R.id.forgetpassword_back_tv);
        forgetpass_next_tv = (TextView)this.findViewById(R.id.forgetpass_next_tv);
        forgetpass_phone_tv = findViewById(R.id.forgetpass_phone_tv);
    }

    private void initEvent(){
        forgetpassword_back_tv.setOnClickListener(this);
        forgetpass_next_tv.setOnClickListener(this);

    }

    private Timer timer;


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forgetpassword_back_tv:
                finish();
                break;

            case R.id.forgetpass_next_tv:

                if(!NetworkUtils.isConnected(ForgetPasswordActivity.this)){
                    Toast.makeText(ForgetPasswordActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

                phonenumber =forgetpass_phone_tv.getText().toString();
                if(phonenumber.isEmpty()){
                    Toast.makeText(this,"请填写手机号",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!phonenumber.startsWith("1")||phonenumber.length()!=11|| !isNumber(phonenumber)){
                    Toast.makeText(this,"手机号格式不正确",Toast.LENGTH_SHORT).show();
                    return;

                }

                MyDialog.showBottomLoadingDialog(ForgetPasswordActivity.this);
                //SendToServer.registest( schoolname, collegename, majorname, ruxueyear, phonenumber, password);


                BaseOkHttpClient.newBuilder()
                        .addParam("phone",phonenumber)
                        .post()
                        .url(Config.HTTPURL + UserRoutePath.API_FORGETPASS_IMAGE_CODE)
                        .build()
                        .enqueue(new BaseCallBack() {
                            @Override
                            public void onSuccess(Object o) {

                                if(timer!=null){
                                    timer.cancel();
                                }
                                MyDialog.dismissBottomLoadingDialog();

                                String json = (String)o;
                                JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);

                                if(jsonObject.get("message")==null){
                                    Toast.makeText(ForgetPasswordActivity.this, "error!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                String message = jsonObject.get("message").getAsString();
                                if(message.equals("ok")){
                                    if(jsonObject.get("imageCode")==null){
                                        Toast.makeText(ForgetPasswordActivity.this, "error!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    String imageCode = jsonObject.get("imageCode").getAsString();
                                    if(jsonObject.get("encrypt")==null){
                                        Toast.makeText(ForgetPasswordActivity.this, "error!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    String encrypt = jsonObject.get("encrypt").getAsString();


                                    Intent intent2 = new Intent(ForgetPasswordActivity.this, ForgetPassSetPassActivity.class);
                                    intent2.putExtra("phonenumber",phonenumber);
                                    intent2.putExtra("imageCode",imageCode);
                                    intent2.putExtra("encrypt",encrypt);

                                    startActivity(intent2);
                                }else{
                                    Toast.makeText(ForgetPasswordActivity.this, message, Toast.LENGTH_SHORT).show();

                                }

                            }

                            @Override
                            public void onError(int code) {
                                if(timer!=null){
                                    timer.cancel();
                                }
                                MyDialog.dismissBottomLoadingDialog();

                                Toast.makeText(ForgetPasswordActivity.this, "服务器飞走啦，请稍后再试!", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onFailure(Call call, IOException e) {
                                if(timer!=null){
                                    timer.cancel();
                                }
                                MyDialog.dismissBottomLoadingDialog();

                                Toast.makeText(ForgetPasswordActivity.this, "服务器飞走啦，请稍后再试!", Toast.LENGTH_SHORT).show();

                            }
                        });



                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ForgetPasswordActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyDialog.dismissBottomLoadingDialog();
                                Toast.makeText(ForgetPasswordActivity.this, "服务器繁忙,请稍后再试!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                },30000);


                break;
        }
    }

    public boolean isNumber(String phonenumber) {
        for (int i = 0; i < phonenumber.length(); i++) {
            if ('9' < phonenumber.charAt(i) || '0' > phonenumber.charAt(i)) {
                return false;
            }
        }
        return true;
    }

}
