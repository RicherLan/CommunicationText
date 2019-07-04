package thefirstchange.example.com.communicationtext.usersignin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
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
import thefirstchange.example.com.communicationtext.activity.myselfactivity.ForgetPassSubmitActivity;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.SearchEmptyClassroomActivity;
import thefirstchange.example.com.communicationtext.https.BaseCallBack;
import thefirstchange.example.com.communicationtext.https.BaseOkHttpClient;
import thefirstchange.example.com.communicationtext.login.LoginActivity;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.routePath.UserRoutePath;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;


public class RegisterPhonenumberActivity extends BaseForCloseActivity implements View.OnClickListener{

    private static final String MY="thefirstchange.example.com.communicationtext.REGISTERPHONENUMBER";
    private Timer timer = new Timer();


    private ImageView registerphonenumberback_tv;
    private TextView registerphonenumbernext_tv;

    private EditText registerphonenumberphone_et;
    private EditText registerphonenumberpassword_et;

    private TextView registerphonenumberschoolname_tv;
    private TextView registerphonenumbercollegename_tv;
    private TextView registerphonenumbermajorname_tv;



    private String schoolname;            //学校名字
    private String collegename ;          //学院名字
    private String majorname;             //所修专业
    private int ruxueyear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerphonenumber);

        Intent intent = getIntent();
        schoolname = intent.getStringExtra("schoolname");
        collegename = intent.getStringExtra("collegename");
        majorname = intent.getStringExtra("majorname");
        ruxueyear = intent.getIntExtra("ruxueyear",2016);
        init();
        initevent();

    }

    public void init(){

        registerphonenumberback_tv = (ImageView)this.findViewById(R.id.registerphonenumberback_tv);
        registerphonenumbernext_tv = (TextView)this.findViewById(R.id.registerphonenumbernext_tv);

        registerphonenumberschoolname_tv = (TextView)this.findViewById(R.id.registerphonenumberschoolname_tv);
        registerphonenumbercollegename_tv = (TextView)this.findViewById(R.id.registerphonenumbercollegename_tv);
        registerphonenumbermajorname_tv = (TextView)this.findViewById(R.id.registerphonenumbermajorname_tv);

        registerphonenumberschoolname_tv.setText("学校   "+schoolname);
        registerphonenumbercollegename_tv.setText("院系   "+collegename);
        registerphonenumbermajorname_tv.setText("专业   "+majorname);

        registerphonenumberphone_et = (EditText) this.findViewById(R.id.registerphonenumberphone_et);
        registerphonenumberpassword_et = (EditText) this.findViewById(R.id.registerphonenumberpassword_et);


    }

    public void initevent(){

        registerphonenumberback_tv.setOnClickListener(this);
        registerphonenumbernext_tv.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.registerphonenumberback_tv:

                finish();
                break;

                case R.id.registerphonenumbernext_tv:

                    if(!NetworkUtils.isConnected(RegisterPhonenumberActivity.this)){
                        Toast.makeText(RegisterPhonenumberActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    final String phonenumber = registerphonenumberphone_et.getText().toString().trim();
                    final String password = registerphonenumberpassword_et.getText().toString().trim();

                    if(phonenumber.isEmpty()){
                        registerphonenumberphone_et.setText("");
                        registerphonenumberphone_et.requestFocus();
                        Toast.makeText(this,"请填写手机号",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(!phonenumber.startsWith("1")||phonenumber.length()!=11|| !isNumber(phonenumber)){
                        registerphonenumberphone_et.setText("");
                        registerphonenumberphone_et.requestFocus();
                        Toast.makeText(this,"手机号格式不正确",Toast.LENGTH_SHORT).show();
                        return;

                    }

                    if(password.isEmpty()){
                        registerphonenumberpassword_et.setText("");
                        registerphonenumberpassword_et.requestFocus();
                        Toast.makeText(this,"请填写密码",Toast.LENGTH_SHORT).show();
                        return;

                    }

                    MyDialog.showBottomLoadingDialog(RegisterPhonenumberActivity.this);
                    //SendToServer.registest( schoolname, collegename, majorname, ruxueyear, phonenumber, password);


                    BaseOkHttpClient.newBuilder()
                            .addParam("phone",phonenumber)
                            .post()
                            .url(Config.HTTPURL + UserRoutePath.API_REG_IMAGE_CODE)
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

                                    if(jsonObject.get("imageCode")==null){
                                        Toast.makeText(RegisterPhonenumberActivity.this, "error!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    String imageCode = jsonObject.get("imageCode").getAsString();
                                    if(jsonObject.get("encrypt")==null){
                                        Toast.makeText(RegisterPhonenumberActivity.this, "error!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    String encrypt = jsonObject.get("encrypt").getAsString();


                                    Intent intent2 = new Intent(RegisterPhonenumberActivity.this,RegisterCaptchaActivity.class);
                                    intent2.putExtra("schoolname",schoolname);
                                    intent2.putExtra("collegename",collegename);
                                    intent2.putExtra("majorname",majorname);
                                    intent2.putExtra("ruxueyear",ruxueyear);
                                    intent2.putExtra("phonenumber",phonenumber);
                                    intent2.putExtra("password",password);
                                    intent2.putExtra("imageCode",imageCode);
                                    intent2.putExtra("encrypt",encrypt);


                                    startActivity(intent2);


                                }

                                @Override
                                public void onError(int code) {
                                    if(timer!=null){
                                        timer.cancel();
                                    }
                                    MyDialog.dismissBottomLoadingDialog();

                                    Toast.makeText(RegisterPhonenumberActivity.this, "服务器飞走啦，请稍后再试!", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onFailure(Call call, IOException e) {
                                    if(timer!=null){
                                        timer.cancel();
                                    }
                                    MyDialog.dismissBottomLoadingDialog();

                                    Toast.makeText(RegisterPhonenumberActivity.this, "服务器飞走啦，请稍后再试!", Toast.LENGTH_SHORT).show();

                                }
                            });



                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            RegisterPhonenumberActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MyDialog.dismissBottomLoadingDialog();
                                    Toast.makeText(RegisterPhonenumberActivity.this, "服务器繁忙,请稍后再试!", Toast.LENGTH_SHORT).show();
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

    protected void onResume(){
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(MY);
        registerReceiver(MyMessageReceiver, filter);

    }

    protected void onDestroy(){

        super.onDestroy();

        unregisterReceiver(MyMessageReceiver);
        if(timer!=null){
            timer.cancel();
        }

    }
    BroadcastReceiver MyMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MY)) {

                if(timer!=null){
                    timer.cancel();
                }
                MyDialog.dismissBottomLoadingDialog();
                String rs = intent.getStringExtra("rs");
                if(rs.equals("ok")){
                    Toast.makeText(RegisterPhonenumberActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent1= new Intent(RegisterPhonenumberActivity.this, LoginActivity.class);
                    RegisterPhonenumberActivity.this.startActivity(intent1);
                    finish();

                }else if(rs.equals("hasregisted")){   //该号码已被注册
                    Toast.makeText(RegisterPhonenumberActivity.this, "注册失败,该号码已被注册!", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(RegisterPhonenumberActivity.this, "服务器繁忙,请稍后再试!", Toast.LENGTH_SHORT).show();
                }


            }
        }
    };


}
