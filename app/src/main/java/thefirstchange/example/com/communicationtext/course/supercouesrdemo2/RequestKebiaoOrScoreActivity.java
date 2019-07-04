package thefirstchange.example.com.communicationtext.course.supercouesrdemo2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.widget.dialog.WheelDialogFragment;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.service.MyEduThread;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;


/*
请求教务处课表 或 成绩
账号和密码  存放的SharedPreferences  文件名是loginEdu
 */
public class RequestKebiaoOrScoreActivity extends BaseForCloseActivity implements View.OnClickListener{

    private static final String MY="thefirstchange.example.com.communicationtext.REQUESTKEBIAOORSCORE";
    private boolean islogining = false;
    private Timer timer = new Timer();

    private ImageView requestCourseOrScoreBack_tv;
    private TextView requestCourseOrScorename_tv;
    private TextView requestCourseOrScoreGrade_tv;
    private TextView requestCourseOrScoreXueqi_tv;

    private EditText mEtUserName;
    private EditText mEtPwd;
    private Button mBtnLogin;

    String type="getKBByPh";

    private int year ;
    private int xueqi ;

    // private EditText mEtCodes;
    //private ImageView mIvCodesIcon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirekebiaoorscore);
        Intent intent = getIntent();
        type=intent.getStringExtra("type");
        init();
        initevent();
    }

    public void init(){

        requestCourseOrScoreBack_tv = (ImageView)this.findViewById(R.id.requestCourseOrScoreBack_tv);
        requestCourseOrScorename_tv = (TextView)this.findViewById(R.id.requestCourseOrScorename_tv);
        if(type.equals("getKBByPh")){
            requestCourseOrScorename_tv.setText("更换课程表");
        }else{
            requestCourseOrScorename_tv.setText("查询成绩");
        }

     //   Calendar c = Calendar.getInstance();
        //year =  c.get(Calendar.YEAR) ;// 获取当前年
        //int month =  c.get(Calendar.MONTH)+1;// 获取当前月

          String string = "一";
          if(ObjectService.personalInfo.grade==1){
              string = "一";
          }else if(ObjectService.personalInfo.grade==2){
              string = "二";
          }else if(ObjectService.personalInfo.grade==3){
              string = "三";
          }else if(ObjectService.personalInfo.grade==4){
              string = "四";
          }else if(ObjectService.personalInfo.grade==5){
              string = "五";
          }else if(ObjectService.personalInfo.grade==6){
              string = "六";
          }

          xueqi = ObjectService.personalInfo.xueqi;
        year = ObjectService.personalInfo.getRuxueyear()+(ObjectService.personalInfo.grade-1);

        requestCourseOrScoreGrade_tv = (TextView)this.findViewById(R.id.requestCourseOrScoreGrade_tv);
        requestCourseOrScoreGrade_tv.setText(year+"-"+(year+1)+" 大"+string);

        requestCourseOrScoreXueqi_tv = (TextView)this.findViewById(R.id.requestCourseOrScoreXueqi_tv);
        requestCourseOrScoreXueqi_tv.setText("第"+ObjectService.personalInfo.xueqi +"学期");

        mEtUserName = findViewById(R.id.login_et_username);
        mEtPwd = findViewById(R.id.login_et_pwd);
        // mEtCodes = getViewById(R.id.login_et_codes);
        // mIvCodesIcon = getViewById(R.id.login_iv_codes_img);
        mBtnLogin = findViewById(R.id.login_btn_login);
        mEtUserName.clearFocus();
        mEtPwd.clearFocus();

        SharedPreferences sp = getSharedPreferences(Config.sharedPreferences_edu_account, Context.MODE_PRIVATE);
        String  username = sp.getString("username", "");  //如果取不到值就取后面的""
        String password = sp.getString("password", "");

        if(!username.trim().isEmpty()){
            mEtUserName.setText(username);
            if(!password.trim().isEmpty()){
                mEtPwd.setText(password);
            }
        }



    }


    public void initevent(){
        requestCourseOrScoreBack_tv.setOnClickListener(this);
        requestCourseOrScoreGrade_tv.setOnClickListener(this);
        requestCourseOrScoreXueqi_tv.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.requestCourseOrScoreBack_tv:
                finish();
                break;
            case R.id.login_btn_login:

                if(!StaticAllList.StuEduSchoolLists.contains(ObjectService.personalInfo.getSchoolname())){
                    Toast.makeText(RequestKebiaoOrScoreActivity.this, "抱歉,暂不支持贵校查询!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!NetworkUtils.isConnected(RequestKebiaoOrScoreActivity.this)){
                    Toast.makeText(RequestKebiaoOrScoreActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }


                final String count = mEtUserName.getText().toString().trim();
                final String password = mEtPwd.getText().toString().trim();
                if(count.isEmpty()){

                    Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
                    mEtUserName.setText("");
                    mEtUserName.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    mEtPwd.setText("");
                    mEtPwd.requestFocus();
                    return;
                }

                MyEduThread myEduThread = new MyEduThread(type,year,xueqi,count,password);
                myEduThread.start();

                if(islogining){
                    return;
                }
                islogining = true;

                MyDialog.showBottomLoadingDialog(RequestKebiaoOrScoreActivity.this);
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               MyDialog.dismissBottomLoadingDialog();
                                islogining = false;
                                Toast.makeText(RequestKebiaoOrScoreActivity.this,"服务器繁忙,请稍后再试",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                },120000);





                SharedPreferences mSharedPreferences = getSharedPreferences(Config.sharedPreferences_edu_account, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString("username", count);
                editor.putString("password",password);
                editor.commit();

//                setResult(RESULT_OK);
//                finish();

                break;
            case R.id.requestCourseOrScoreGrade_tv:
                Bundle bundle = new Bundle();
                bundle.putBoolean(WheelDialogFragment.DIALOG_BACK, false);
                bundle.putBoolean(WheelDialogFragment.DIALOG_CANCELABLE, false);
                bundle.putBoolean(WheelDialogFragment.DIALOG_CANCELABLE_TOUCH_OUT_SIDE, false);
                bundle.putString(WheelDialogFragment.DIALOG_LEFT, "取消");
                bundle.putString(WheelDialogFragment.DIALOG_RIGHT, "确定");
                String[] grades = new String[15];
                int index = 0;
                for(int i=2012;i<2016;++i){
                    String str = i+"-"+(i+1);
                    grades[index++] = str;
                }
                for(int i=2016;i<2016+7;++i){
                    String str = i+"-"+(i+1)+" (大";
                    int x = i-2016;
                    if(x==0){
                        str+="一";
                    }else if(x==1){
                        str+="二";
                    }else if(x==2){
                        str+="三";
                    }else if(x==3){
                        str+="四";
                    }else if(x==4){
                        str+="五";
                    }else if(x==5){
                        str+="六";
                    }else if(x==6){
                        str+="七";
                    }
                    str+=")";
                    grades[index++] = str;
                }

                for(int i=2016+7;i<=2016+10;++i){
                    String str = i+"-"+(i+1);
                    grades[index++] = str;
                }
                bundle.putStringArray(WheelDialogFragment.DIALOG_WHEEL,grades);

                WheelDialogFragment dialogFragment = WheelDialogFragment.newInstance(WheelDialogFragment.class, bundle);

                dialogFragment.setWheelDialogListener(new WheelDialogFragment.OnWheelDialogListener() {
                    @Override
                    public void onClickLeft(DialogFragment dialog, String value) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onClickRight(DialogFragment dialog, String value) {
                        dialog.dismiss();

                        year = Integer.parseInt(value.substring(0,4));
                        requestCourseOrScoreGrade_tv.setText(value);
                    }

                    @Override
                    public void onValueChanged(DialogFragment dialog, String value) {

                    }
                });

                dialogFragment.show(getSupportFragmentManager(), "");
                break;
            case R.id.requestCourseOrScoreXueqi_tv:
                Bundle bundle2 = new Bundle();
                bundle2.putBoolean(WheelDialogFragment.DIALOG_BACK, false);
                bundle2.putBoolean(WheelDialogFragment.DIALOG_CANCELABLE, false);
                bundle2.putBoolean(WheelDialogFragment.DIALOG_CANCELABLE_TOUCH_OUT_SIDE, false);
                bundle2.putString(WheelDialogFragment.DIALOG_LEFT, "取消");
                bundle2.putString(WheelDialogFragment.DIALOG_RIGHT, "确定");
                String[] xueqis = new String[]{"第1学期","第2学期","第3学期"};
                bundle2.putStringArray(WheelDialogFragment.DIALOG_WHEEL,xueqis);

                WheelDialogFragment dialogFragment2 = WheelDialogFragment.newInstance(WheelDialogFragment.class, bundle2);

                dialogFragment2.setWheelDialogListener(new WheelDialogFragment.OnWheelDialogListener() {
                    @Override
                    public void onClickLeft(DialogFragment dialog, String value) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onClickRight(DialogFragment dialog, String value) {
                        dialog.dismiss();
                        xueqi = Integer.parseInt(value.charAt(1)+"");
                        requestCourseOrScoreXueqi_tv.setText(value);
                    }

                    @Override
                    public void onValueChanged(DialogFragment dialog, String value) {

                    }
                });

                dialogFragment2.show(getSupportFragmentManager(), "");
                break;
                default:
                    break;

        }

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
            if (intent.getAction().equals(MY)){
                String type = intent.getStringExtra("type");

                if(type.equals("getscoRs")){              //获得教务成绩
                    if(timer!=null){
                        timer.cancel();
                    }
                   MyDialog.dismissBottomLoadingDialog();

                    islogining = false;
                    String rs = intent.getStringExtra("rs");
                    if(rs.equals("errUNOrPwd")){
                        Toast.makeText(RequestKebiaoOrScoreActivity.this,"账号或密码错误!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    boolean iskong = intent.getBooleanExtra("iskong",false);
                    if(!iskong){
                        finish();
                    }else{
                        Toast.makeText(RequestKebiaoOrScoreActivity.this,"该成绩不存在",Toast.LENGTH_SHORT).show();
                    }


                }else if(type.equals("getkBRs")){                //获得教务课表
                    if(timer!=null){
                        timer.cancel();
                    }
                    MyDialog.dismissBottomLoadingDialog();
                    islogining = false;
                    String rs = intent.getStringExtra("rs");
                    if(rs.equals("errUNOrPwd")){
                        Toast.makeText(RequestKebiaoOrScoreActivity.this,"账号或密码错误!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    boolean iskong = intent.getBooleanExtra("iskong",false);
                    if(!iskong){
                        finish();
                    }else{
                        Toast.makeText(RequestKebiaoOrScoreActivity.this,"该课表不存在",Toast.LENGTH_SHORT).show();
                    }
                }


            }

        }
    };


}
