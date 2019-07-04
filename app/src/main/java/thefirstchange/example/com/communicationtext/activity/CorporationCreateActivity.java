package thefirstchange.example.com.communicationtext.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.Timer;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.widget.dialog.WheelDialogFragment;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class CorporationCreateActivity extends BaseForCloseActivity implements View.OnClickListener{

    private static final String MY_2 = "thefirstchange.example.com.communicationtext.CORPORATIONCREATE";
    private Timer timer = new Timer();

    private ImageView cornameback_tv;
    private TextView cornamenext_tv;
    private EditText corname_et;
    private EditText cortype_et;
    private EditText corinfo_et;
    private NumberPicker numberPicker;

    private TextView choose_year_createCorp_tv;
    private TextView choose_xueqi_createCorp_tv;

    private int year ;
    private int xueqi ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corporatiocreate);

        init();
        initevent();
    }

    public void init(){

        cornameback_tv = this.findViewById(R.id.createcorcornameback_tv);
        cornamenext_tv = this.findViewById(R.id.createcorcornamenext_tv);
        corname_et = this.findViewById(R.id.corname_et);
        cortype_et = this.findViewById(R.id.cortype_et);
        corinfo_et = this.findViewById(R.id.corinfo_et);
        numberPicker=(NumberPicker)this.findViewById(R.id.number_picker);

        choose_year_createCorp_tv = (TextView)this.findViewById(R.id.choose_year_createCorp_tv);
        choose_xueqi_createCorp_tv = (TextView)this.findViewById(R.id.choose_xueqi_createCorp_tv);;

//        Calendar c = Calendar.getInstance();
//        year =  c.get(Calendar.YEAR) ;// 获取当前年
//        int month =  c.get(Calendar.MONTH)+1;// 获取当前月

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


        year = ObjectService.personalInfo.getRuxueyear()+(ObjectService.personalInfo.grade-1);
        xueqi = ObjectService.personalInfo.xueqi;

        choose_year_createCorp_tv.setText(year+"-"+(year+1)+" 大"+string);
        choose_xueqi_createCorp_tv.setText("第"+ObjectService.personalInfo.xueqi +"学期");

    }

    public void initevent(){
        cornameback_tv.setOnClickListener(this);
        cornamenext_tv.setOnClickListener(this);

        choose_year_createCorp_tv.setOnClickListener(this);
        choose_xueqi_createCorp_tv.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.createcorcornameback_tv:
                finish();
                break;

            case R.id.choose_year_createCorp_tv:
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
                        choose_year_createCorp_tv.setText(value);
                    }

                    @Override
                    public void onValueChanged(DialogFragment dialog, String value) {

                    }
                });

                dialogFragment.show(getSupportFragmentManager(), "");
                break;

            case R.id.choose_xueqi_createCorp_tv:
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
                        choose_xueqi_createCorp_tv.setText(value);
                    }

                    @Override
                    public void onValueChanged(DialogFragment dialog, String value) {

                    }
                });

                dialogFragment2.show(getSupportFragmentManager(), "");
                break;

            case R.id.createcorcornamenext_tv:

                if(!NetworkUtils.isConnected(CorporationCreateActivity.this)){
                    Toast.makeText(CorporationCreateActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String corname = corname_et.getText().toString().trim();
                final String cortype = cortype_et.getText().toString().trim();
                final String corinfo = corinfo_et.getText().toString().trim();
                int corNumebr=numberPicker.getValue();


                if(corname==null||corname.equals("")){

                    Toast.makeText(CorporationCreateActivity.this,"请填写社团名称!", Toast.LENGTH_SHORT).show();
                    corname_et.setText("");
                    corname_et.requestFocus();
                    return;
                }
                if(cortype==null||cortype.equals("")){

                    Toast.makeText(CorporationCreateActivity.this,"请填写社团类型!", Toast.LENGTH_SHORT).show();
                    cortype_et.setText("");
                    cortype_et.requestFocus();
                    return;
                }
                if(corinfo==null||corinfo.equals("")){

                    Toast.makeText(CorporationCreateActivity.this,"请填写社团介绍!", Toast.LENGTH_SHORT).show();
                    corinfo_et.setText("");
                    corinfo_et.requestFocus();
                    return;
                }
                if (corNumebr==0){
                    Toast.makeText(CorporationCreateActivity.this,"分部数量不能为零!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent=new Intent(CorporationCreateActivity.this, CreatePartActivity.class);
                intent.putExtra("corname",corname);
                intent.putExtra("cortype",cortype);
                intent.putExtra("corinfo",corinfo);
                intent.putExtra("cornumber",corNumebr);
                intent.putExtra("year",year);
                intent.putExtra("xueqi",xueqi);
                startActivity(intent);

//             new Thread(){
//                    public void run(){
//                        SendToServer.createCorp(PersonalInfo.getPhonenumber(),corname,cortype,corinfo);
//                    }
//             }.start();
//
//            timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    CorporationCreateActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(CorporationCreateActivity.this,"服务器繁忙,请稍后再试!",Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            },4000);

                break;

        }

    }

    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(MY_2);
        registerReceiver(MyMessageReceiver, filter);
        if(timer!=null){
            timer.cancel();
        }


    }


    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(MyMessageReceiver);
    }

    BroadcastReceiver MyMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MY_2)) {
                timer.cancel();
                String rs = intent.getStringExtra("rs");
                if (rs.equals("ok")) {
                    finish();
                }
            }
        }


    };


}
