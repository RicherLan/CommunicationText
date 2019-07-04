package thefirstchange.example.com.communicationtext.corpration;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.widget.dialog.WheelDialogFragment;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class AlterCorpTermActivity extends BaseForCloseActivity implements View.OnClickListener{

    private static final String MY="thefirstchange.example.com.communicationtext.ALTERCORPTERM";
    private Timer alterCorpTremTimer = new Timer();



    private ImageView alter_corpterm_Back_tv;
    private TextView alter_corp_term_Grade_tv;
    private TextView alter_corp_term_Xueqi_tv;
    private TextView alter_corp_term_zhou_tv;
    private Button alter_corp_term_login;

    int groupId;

    private int year ;
    private int xueqi ;
    private int zhou;
    boolean iswrong = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_corp_term);
        Intent intent = getIntent();
        groupId=intent.getIntExtra("groupid",-1);
        if(groupId==-1){
            Toast.makeText(AlterCorpTermActivity.this,"系统错误,请稍后再试!",Toast.LENGTH_SHORT).show();
            iswrong=false;
            finish();
        }

        if(StaticAllList.groupList.containsKey(groupId)){
            year = StaticAllList.groupList.get(groupId).year;
            xueqi = StaticAllList.groupList.get(groupId).xueqi;
            zhou = StaticAllList.groupList.get(groupId).zhou;
        }else{
            Toast.makeText(AlterCorpTermActivity.this,"系统错误,请稍后再试!",Toast.LENGTH_SHORT).show();
            iswrong=false;
            finish();
        }


        initview();
        initevent();

    }

    public void initview(){
        alter_corpterm_Back_tv = (ImageView)this.findViewById(R.id.alter_corpterm_Back_tv);
        alter_corp_term_Grade_tv = (TextView)this.findViewById(R.id.alter_corp_term_Grade_tv);
        alter_corp_term_Xueqi_tv = (TextView)this.findViewById(R.id.alter_corp_term_Xueqi_tv);
        alter_corp_term_login = (Button)this.findViewById(R.id.alter_corp_term_login);
        alter_corp_term_zhou_tv = (TextView)this.findViewById(R.id.alter_corp_term_zhou_tv);

        alter_corp_term_Grade_tv.setText(year+"-"+(year+1)+" 学年");
        alter_corp_term_Xueqi_tv.setText("第"+xueqi +"学期");
        alter_corp_term_zhou_tv.setText("第"+zhou+"周");
    }

    public void initevent(){
        alter_corpterm_Back_tv.setOnClickListener(this);
        alter_corp_term_Grade_tv.setOnClickListener(this);
        alter_corp_term_Xueqi_tv.setOnClickListener(this);
        alter_corp_term_login.setOnClickListener(this);
        alter_corp_term_zhou_tv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.alter_corpterm_Back_tv:
                finish();
                break;
            case R.id.alter_corp_term_Grade_tv:
                Bundle bundle = new Bundle();
                bundle.putBoolean(WheelDialogFragment.DIALOG_BACK, false);
                bundle.putBoolean(WheelDialogFragment.DIALOG_CANCELABLE, false);
                bundle.putBoolean(WheelDialogFragment.DIALOG_CANCELABLE_TOUCH_OUT_SIDE, false);
                bundle.putString(WheelDialogFragment.DIALOG_LEFT, "取消");
                bundle.putString(WheelDialogFragment.DIALOG_RIGHT, "确定");
                String[] grades = new String[15];
                int index = 0;
                for(int i=2012;i<=2016+10;++i){
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
                        alter_corp_term_Grade_tv.setText(value+" 学年");
                    }

                    @Override
                    public void onValueChanged(DialogFragment dialog, String value) {

                    }
                });

                dialogFragment.show(getSupportFragmentManager(), "");
                break;
            case R.id.alter_corp_term_Xueqi_tv:
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
                        alter_corp_term_Xueqi_tv.setText(value);
                    }

                    @Override
                    public void onValueChanged(DialogFragment dialog, String value) {

                    }
                });

                dialogFragment2.show(getSupportFragmentManager(), "");
                break;
            case R.id.alter_corp_term_zhou_tv:

                Bundle bundle3 = new Bundle();
                bundle3.putBoolean(WheelDialogFragment.DIALOG_BACK, false);
                bundle3.putBoolean(WheelDialogFragment.DIALOG_CANCELABLE, false);
                bundle3.putBoolean(WheelDialogFragment.DIALOG_CANCELABLE_TOUCH_OUT_SIDE, false);
                bundle3.putString(WheelDialogFragment.DIALOG_LEFT, "取消");
                bundle3.putString(WheelDialogFragment.DIALOG_RIGHT, "确定");
                //bundle.putStringArray(WheelDialogFragment.DIALOG_WHEEL, ResUtil.getStringArray(R.array.main_home_menu));
                String[] zhoustrings = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27"};
                bundle3.putStringArray(WheelDialogFragment.DIALOG_WHEEL,zhoustrings);

                WheelDialogFragment dialogFragment3 = WheelDialogFragment.newInstance(WheelDialogFragment.class, bundle3);

                dialogFragment3.setWheelDialogListener(new WheelDialogFragment.OnWheelDialogListener() {
                    @Override
                    public void onClickLeft(DialogFragment dialog, String value) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onClickRight(DialogFragment dialog, String value) {
                        dialog.dismiss();
                        zhou = Integer.parseInt(value);

                        alter_corp_term_zhou_tv.setText("第"+zhou+"周");

                    }

                    @Override
                    public void onValueChanged(DialogFragment dialog, String value) {

                    }
                });

                dialogFragment3.show(getSupportFragmentManager(), "");
                break;
            case R.id.alter_corp_term_login:

                if(!NetworkUtils.isConnected(AlterCorpTermActivity.this)){
                    Toast.makeText(AlterCorpTermActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }


                SendToServer.alterCorpTerm(groupId,year,xueqi,zhou);

                MyDialog.showBottomLoadingDialog(this);
                alterCorpTremTimer = new Timer();
                alterCorpTremTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyDialog.dismissBottomLoadingDialog();
                                Toast.makeText(AlterCorpTermActivity.this,"服务器繁忙,请稍后再试!",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                },50000);

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

        if(alterCorpTremTimer!=null){
            alterCorpTremTimer.cancel();
        }
        MyDialog.dismissBottomLoadingDialog();
    }
    BroadcastReceiver MyMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MY)) {

                String type = intent.getStringExtra("type");
                if(type.equals("alterCorpTermRs")){
                    int groupid = intent.getIntExtra("gid",-1);
                    if(groupid==-1){
                        if(alterCorpTremTimer!=null){
                            alterCorpTremTimer.cancel();
                        }
                        MyDialog.dismissBottomLoadingDialog();
                        Toast.makeText(AlterCorpTermActivity.this,"系统错误,请稍后再试!",Toast.LENGTH_SHORT).show();
                        return;

                    }else if(groupid!=groupId){
                        return;
                    }
                    if(alterCorpTremTimer!=null){
                        alterCorpTremTimer.cancel();
                    }
                    MyDialog.dismissBottomLoadingDialog();
                    Toast.makeText(AlterCorpTermActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
                   // finish();
                }

            }

        }
    };



}
