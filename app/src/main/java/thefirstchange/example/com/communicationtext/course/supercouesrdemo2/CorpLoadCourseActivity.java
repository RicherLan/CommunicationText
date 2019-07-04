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
import thefirstchange.example.com.communicationtext.course.database.dao.StuDao;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.widget.dialog.WheelDialogFragment;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;


/*
账号和密码  存放的SharedPreferences  文件名是loginEdu
 */
public class CorpLoadCourseActivity extends BaseForCloseActivity implements View.OnClickListener{

    private static final String MY="thefirstchange.example.com.communicationtext.CORPLOADSCOREACTIVITY";
    private boolean islogining = false;
    private Timer timer = new Timer();

    private ImageView corp_load_course_Back_tv;
    private TextView corp_load_course_name_tv;
    private TextView corp_load_course_Grade_tv;
    private TextView corp_load_course_Xueqi_tv;

    private EditText mEtUserName;
    private EditText mEtPwd;
    private Button mBtnLogin;

    private int year ;
    private int xueqi ;

    boolean iswrong = false;

    // private EditText mEtCodes;
    //private ImageView mIvCodesIcon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corp_load_course);
        Intent intent = getIntent();
        year = intent.getIntExtra("year",-1);
        xueqi = intent.getIntExtra("xueqi",-1);
        if(year==-1||xueqi==-1){
            Toast.makeText(CorpLoadCourseActivity.this,"系统错误,请稍后再试!",Toast.LENGTH_SHORT).show();
            iswrong = true;
            finish();
        }
        initView();
        initevent();
    }

    public void initView(){

        corp_load_course_Back_tv = (ImageView)this.findViewById(R.id.corp_load_course_Back_tv);
        corp_load_course_name_tv = (TextView)this.findViewById(R.id.corp_load_course_name_tv);


        String string = "一";
        if(ObjectService.personalInfo.getRuxueyear()==0){
            string="";
        }else{
            int ss = year- ObjectService.personalInfo.getRuxueyear();

            if(ss==0){
                string = "一";
            }else if(ss==1){
                string = "二";
            }else if(ss==2){
                string = "三";
            }else if(ss==3){
                string = "四";
            }else if(ss==4){
                string = "五";
            }else if(ss==5){
                string = "六";
            }
        }


        corp_load_course_Grade_tv = (TextView)this.findViewById(R.id.corp_load_course_Grade_tv);
        corp_load_course_Grade_tv.setText(year+"-"+(year+1)+" 大"+string);

        corp_load_course_Xueqi_tv = (TextView)this.findViewById(R.id.corp_load_course_Xueqi_tv);
        corp_load_course_Xueqi_tv.setText("第"+xueqi +"学期");

        mEtUserName = findViewById(R.id.corp_load_course_username);
        mEtPwd = findViewById(R.id.corp_load_course_pwd);
        // mEtCodes = getViewById(R.id.login_et_codes);
        // mIvCodesIcon = getViewById(R.id.login_iv_codes_img);
        mBtnLogin = findViewById(R.id.corp_load_course_login);
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
        corp_load_course_Back_tv.setOnClickListener(this);
        corp_load_course_Grade_tv.setOnClickListener(this);
        corp_load_course_Xueqi_tv.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.corp_load_course_Back_tv:
                finish();
                break;
            case R.id.corp_load_course_login:

                if(!StaticAllList.StuEduSchoolLists.contains(ObjectService.personalInfo.getSchoolname())){
                    Toast.makeText(CorpLoadCourseActivity.this, "抱歉,暂不支持贵校查询!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!NetworkUtils.isConnected(CorpLoadCourseActivity.this)){
                    Toast.makeText(CorpLoadCourseActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
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

                SendToServer.loadCourse(year,xueqi,count,password);

                if(islogining){
                    return;
                }
                islogining = true;

               MyDialog.showBottomLoadingDialog(CorpLoadCourseActivity.this);
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               MyDialog.dismissBottomLoadingDialog();
                                islogining = false;
                                Toast.makeText(CorpLoadCourseActivity.this,"服务器繁忙,请稍后再试",Toast.LENGTH_SHORT).show();

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
                        corp_load_course_Grade_tv.setText(value);
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
                        corp_load_course_Xueqi_tv.setText(value);
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
        if(!iswrong){
            unregisterReceiver(MyMessageReceiver);
        }

        if(timer!=null){
            timer.cancel();
        }

    }
    BroadcastReceiver MyMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MY)){
                String type = intent.getStringExtra("type");

                if(type.equals("getkBRs")){                //获得教务课表
                    if(timer!=null){
                        timer.cancel();
                    }
                    MyDialog.dismissBottomLoadingDialog();
                    islogining = false;
                    String rs = intent.getStringExtra("rs");
                    if(rs.equals("errUNOrPwd")){
                        Toast.makeText(CorpLoadCourseActivity.this,"账号或密码错误!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    boolean iskong = intent.getBooleanExtra("iskong",false);
                    if(!iskong){

                        CourseAndScore.courseph = ObjectService.personalInfo.getPhonenumber();
                        CourseAndScore.initcourseSameTime();

                        Toast.makeText(CorpLoadCourseActivity.this,"导入成功,课表在课程表中可查看!",Toast.LENGTH_SHORT).show();

//                        Intent intent4=new Intent(CorpLoadCourseActivity.this, CSSActivity.class);
//                        startActivity(intent4);


                        new Thread(){
                            public void run(){
                                SharedPreferences mSharedPreferences = getSharedPreferences(Config.sharedPreferences_edu_year_grade, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mSharedPreferences.edit();

                                editor.putInt("nowzhoushu", CourseAndScore.nowzhoushu);
                                editor.putLong("zhou_time", CourseAndScore.zhou_time);
                                editor.putInt("courseXueqi", CourseAndScore.courseXueqi);
                                editor.putInt("courseGrade", CourseAndScore.courseGrade);
                                editor.putInt("scoreXueqi", CourseAndScore.scoreXueqi);
                                editor.putInt("scoreGrade", CourseAndScore.scoreGrade);
                                editor.commit();


                                SharedPreferences mSharedPreferences2 = getSharedPreferences(Config.sharedPreferences_course_ph, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor2 = mSharedPreferences2.edit();
                                editor2.putString("course_ph",ObjectService.personalInfo.getPhonenumber());
                                editor2.commit();

                                StuDao stuDao = StuDao.getInstance(CorpLoadCourseActivity.this);
                                stuDao.removeAllCourse();
                                stuDao.saveStuCouList(CourseAndScore.courses);
                            }

                        }.start();

                        finish();


                    }else{
                        Toast.makeText(CorpLoadCourseActivity.this,"该课表不存在",Toast.LENGTH_SHORT).show();
                    }
                }


            }

        }
    };



}
