package thefirstchange.example.com.communicationtext.activity;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.datescroller.widget.DateScrollerDialog;
import thefirstchange.example.com.communicationtext.datescroller.widget.data.Type;
import thefirstchange.example.com.communicationtext.datescroller.widget.listener.OnDateSetListener;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.DensityUtiltwo;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.MyTools;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class UpdatePersonInfoActivity extends BaseForCloseActivity implements View.OnClickListener{


    private static String MY = "thefirstchange.example.com.communicationtext.UPDATEPERSONALINFO";
    private Timer timer = new Timer();

    private ImageView finishSelf;
    private TextView saveInfo;
    private EditText nickName;
    private TextView sexText;
    private EditText fromWhere;
    private EditText nowWhere;
    private EditText schoolName;
    private EditText partName;
    private EditText majorName;
    private TextView birthText;
    private TextView startText;
    private TextView schoolTime;
    private EditText introduceEdit;

    private boolean isBirth=true;

    String oldNickName=ObjectService.personalInfo.getNickname();
    String oldSex=ObjectService.personalInfo.getSex();
    String oldfrom=ObjectService.personalInfo.getFrom();
    String oldPlace=ObjectService.personalInfo.getAddress();
    String oldSchool=ObjectService.personalInfo.getSchoolname();
    String oldPart=ObjectService.personalInfo.getDepartmentname();
    String oldMajor=ObjectService.personalInfo.getMajorname();
    String oldBirth=ObjectService.personalInfo.getBirthday();
    String oldStar="";
    String oldSchoolTime=ObjectService.personalInfo.getRuxueyear()+"";
    String oldIntroduce= ObjectService.personalInfo.getIntroduce();



    private String[][] constellations = {{"摩羯座", "水瓶座"}, {"水瓶座", "双鱼座"}, {"双鱼座", "白羊座"}, {"白羊座", "金牛座"}, {"金牛座", "双子座"}, {"双子座", "巨蟹座"}, {"巨蟹座", "狮子座"},
            {"狮子座", "处女座"}, {"处女座", "天秤座"}, {"天秤座", "天蝎座"}, {"天蝎座", "射手座"}, {"射手座", "摩羯座"}};
    //星座分割时间
    private int[] date = {20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};


    private static final long HUNDRED_YEARS = 100L * 365 * 1000 * 60 * 60 * 24L; // 100年

    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private long mLastTime = System.currentTimeMillis(); // 上次设置的时间

    // 数据的回调
    private OnDateSetListener mOnDateSetListener =new OnDateSetListener() {
        @Override
        public void onDateSet(DateScrollerDialog timePickerView, long milliseconds) {
            mLastTime = milliseconds;
            String text = getDateToString(milliseconds);
            if (isBirth){
                birthText.setText(text);
                getConstellations(text);
            }else {
                schoolTime.setText(text.substring(0,4));
            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_person_info);
        initView();




    }

    private void initView(){

        finishSelf=(ImageView)findViewById(R.id.finish_update_person_info);
        finishSelf.setOnClickListener(this);
        saveInfo=(TextView)findViewById(R.id.save_update_person_info);
        saveInfo.setOnClickListener(this);
        nickName=(EditText)findViewById(R.id.update_person_info_nickname);
        nickName.setText(oldNickName);
        sexText=(TextView)findViewById(R.id.update_person_info_sex);
        sexText.setText(oldSex);
        sexText.setOnClickListener(this);
        fromWhere=(EditText)findViewById(R.id.update_person_info_from);
        fromWhere.setText(oldfrom);
        nowWhere=(EditText)findViewById(R.id.update_person_info_nowplace);
        nowWhere.setText(oldPlace);
        schoolName=(EditText)findViewById(R.id.update_person_info_school);
        schoolName.setText(oldSchool);
        partName=(EditText)findViewById(R.id.update_person_info_part);
        partName.setText(oldPart);
        majorName=(EditText)findViewById(R.id.update_person_info_major);
        majorName.setText(oldMajor);
        birthText=(TextView)findViewById(R.id.update_person_info_birth);
        birthText.setText(oldBirth);
        birthText.setOnClickListener(this);
        startText=(TextView)findViewById(R.id.update_person_info_star);
        if (oldBirth!=null&&!oldBirth.isEmpty()){
            getConstellations(oldBirth);
        }

        schoolTime=(TextView)findViewById(R.id.update_person_info_school_time);
        schoolTime.setText(oldSchoolTime);
        schoolTime.setOnClickListener(this);
        introduceEdit=(EditText)findViewById(R.id.update_person_info_introduce);
        introduceEdit.setText(oldIntroduce);

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.finish_update_person_info:
                finish();
                break;
            case R.id.save_update_person_info:

                if(!NetworkUtils.isConnected(UpdatePersonInfoActivity.this)){
                    Toast.makeText(UpdatePersonInfoActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String newNickName="";
                String newSex="";
                String newfrom="";
                String newPlace="";
                String newSchool="";
                String newPart="";
                String newMajor="";
                String newBirth="";
                String newStar="";
                String newSchoolTime="";
                String newIntroduce="";
                newNickName=nickName.getText().toString().trim();
                if (newNickName.isEmpty()){
                    newNickName=oldNickName;
                }
                newSex=sexText.getText().toString();
                newfrom=fromWhere.getText().toString().trim();
//                if (newfrom.isEmpty()){
//                    newfrom=oldfrom;
//                }
                newPlace=nowWhere.getText().toString().trim();
//                if (newPlace.isEmpty()){
//                    newPlace=oldPlace;
//                }
                newSchool=schoolName.getText().toString().trim();
                if (newSchool.isEmpty()){
                    newSchool=oldSchool;
                }
                newPart=partName.getText().toString().trim();
                if (newPart.isEmpty()){
                    newPart=oldPart;
                }
               final String newIntroduce2=introduceEdit.getText().toString();

                newMajor=majorName.getText().toString().trim();

                newBirth=birthText.getText().toString().trim();
                newStar=startText.getText().toString();
                newSchoolTime=schoolTime.getText().toString();

                 final String newNickName2= MyTools.trimlast(newNickName);
                final String newSex2= MyTools.trimlast(newSex);
                final String  newfrom2= MyTools.trimlast(newfrom);
                final String newPlace2= MyTools.trimlast(newPlace);
                final String  newSchool2= MyTools.trimlast(newSchool);
                final String  newPart2= MyTools.trimlast(newPart);
                final String  newMajor2= MyTools.trimlast(newMajor);
                final String  newBirth2= MyTools.trimlast(newBirth);
                final String  newSchoolTime2= MyTools.trimlast(newSchoolTime);


                MyDialog.showBottomLoadingDialog(UpdatePersonInfoActivity.this);
                         SendToServer.ChPerIn(newNickName2,newSex2,newfrom2,newPlace2,newSchool2,newPart2,newMajor2,newBirth2,Integer.parseInt(newSchoolTime2),newIntroduce2);


                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        UpdatePersonInfoActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyDialog.dismissBottomLoadingDialog();
                                Toast.makeText(UpdatePersonInfoActivity.this,"服务器繁忙,请稍后再试",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                },50000);

                break;
            case R.id.update_person_info_sex:
                showSelectDialog();
                break;
            case R.id.update_person_info_birth:
                isBirth=true;
                showDate();

                break;
            case R.id.update_person_info_school_time:
                isBirth=false;
                showDate2();

                break;

                default:
                    break;

        }
    }


    public void showDate() {
        // 出生日期
        DateScrollerDialog dialog = new DateScrollerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setTitleStringId("选择出生日期")
                .setMinMilliseconds(System.currentTimeMillis() - HUNDRED_YEARS)
                .setMaxMilliseconds(System.currentTimeMillis())
                .setCurMilliseconds(mLastTime)
                .setCallback(mOnDateSetListener)
                .build();

        if (dialog != null) {
            if (!dialog.isAdded()) {
                dialog.show(getSupportFragmentManager(), "year_month_day");
            }
        }
    }
    public void showDate2() {
        // 出生日期
        DateScrollerDialog dialog = new DateScrollerDialog.Builder()
                .setType(Type.YEAR)
                .setTitleStringId("选择入学年份")
                .setMinMilliseconds(System.currentTimeMillis() - HUNDRED_YEARS)
                .setMaxMilliseconds(System.currentTimeMillis())
                .setCurMilliseconds(mLastTime)
                .setCallback(mOnDateSetListener)
                .build();

        if (dialog != null) {
            if (!dialog.isAdded()) {
                dialog.show(getSupportFragmentManager(), "year_month_day");
            }
        }
    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }


    private void getConstellations(String time) {
        String[] data = time.split("-");
        int day = date[Integer.parseInt(data[1]) - 1];
        String[] cl1 = constellations[Integer.parseInt(data[1]) - 1];
        if (Integer.parseInt(data[2]) >= day) {
            startText.setText(cl1[1]);
        } else {
            startText.setText(cl1[0]);
        }
    }

    protected void onResume(){

        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MY);
        registerReceiver(broadcastReceiver,intentFilter);


    }
    public void onDestroy(){
        super.onDestroy();

        //unbindService(connection);

        //stopService(bindIntent);
        unregisterReceiver(broadcastReceiver);
        if(timer!=null){
            timer.cancel();
        }
        MyDialog.dismissBottomLoadingDialog();
    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MY)){

                if(timer!=null){
                    timer.cancel();
                }

                MyDialog.dismissBottomLoadingDialog();
               String rs = intent.getStringExtra("rs");
                if(rs.equals("ok")){
                    Toast.makeText(UpdatePersonInfoActivity.this,"修改成功!",Toast.LENGTH_SHORT).show();


                    ObjectService.personalInfo.setNickname(MyTools.trimlast(nickName.getText().toString()));
                    ObjectService.personalInfo.setAddress(MyTools.trimlast(nowWhere.getText().toString()));
                    ObjectService.personalInfo.setSex(MyTools.trimlast(sexText.getText().toString()));
                    ObjectService.personalInfo.setSchoolname(MyTools.trimlast(schoolName.getText().toString()));
                    ObjectService.personalInfo.setDepartmentname(MyTools.trimlast(partName.getText().toString()));
                    ObjectService.personalInfo.setMajorname(MyTools.trimlast(majorName.getText().toString()));
                    ObjectService.personalInfo.setBirthday(MyTools.trimlast(birthText.getText().toString()));
                    ObjectService.personalInfo.setRuxueyear(Integer.parseInt(MyTools.trimlast(schoolTime.getText().toString())));
                    ObjectService.personalInfo.setFrom(MyTools.trimlast(fromWhere.getText().toString()));
                    ObjectService.personalInfo.setIntroduce(MyTools.trimlast(introduceEdit.getText().toString()));


                    SharedPreferences sh = getSharedPreferences( Config.sharedPreferences_personal_info,Context.MODE_PRIVATE);

                    Gson gson = new Gson();
                    String json = gson.toJson(ObjectService.personalInfo);
                    SharedPreferences.Editor editor = sh.edit() ;
                    editor.putString(Config.sharedPreferences_personal_info, json) ; //存入json串
                    editor.commit() ; //提交
                    finish();

                }else{
                    Toast.makeText(UpdatePersonInfoActivity.this,"服务器繁忙,请稍后再试!!",Toast.LENGTH_SHORT).show();
                }

            }

        }
    };


    private void showSelectDialog() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.select_sex, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtiltwo.dp2px(this, 16f);
        params.bottomMargin = DensityUtiltwo.dp2px(this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        TextView man=(TextView) contentView.findViewById(R.id.select_man);
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sexText.setText("男");
                bottomDialog.dismiss();
            }
        });
        TextView woman=(TextView) contentView.findViewById(R.id.select_woman);
        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sexText.setText("女");
                bottomDialog.dismiss();
            }
        });
    }


}