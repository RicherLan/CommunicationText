package thefirstchange.example.com.communicationtext.corpration;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CorpSendToServer;
import thefirstchange.example.com.communicationtext.util.DensityUtiltwo;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class AlterCorpPosActivity extends BaseForCloseActivity implements View.OnClickListener{

    private Dialog LoadingDialog = null;

    private CardView zhuxi;
    private CardView buzhang;
    private CardView ganshi;


    private ImageView finishSelf;

    private TextView alter_corppos_tv;

    private int groupId;
    private String partpos;


    public Timer timer = new Timer();
    private static final String MY="thefirstchange.example.com.communicationtext.ALTERCORPPOS";

    private boolean iswrong = false;    //是不是由 系统错误，请稍后再试 造成

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_corp_pos);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);

        Intent intent=getIntent();
        groupId=intent.getIntExtra("groupid",-1);
        partpos = intent.getStringExtra("partpos");
        if(groupId==-1){
            Toast.makeText(AlterCorpPosActivity.this,"系统错误，请稍后再试!",Toast.LENGTH_SHORT).show();
            iswrong=true;
            finish();
        }
        initView();
    }

    private void initView(){
        finishSelf=(ImageView)findViewById(R.id.finish_alter_my_corp_pos);
        finishSelf.setOnClickListener(this);

        alter_corppos_tv = (TextView)this.findViewById(R.id.alter_corppos_tv);
        alter_corppos_tv.setText("当前职位:  "+partpos);

        zhuxi = (CardView)this.findViewById(R.id.alter_corppos_zhuxi_cv);
        buzhang = (CardView)this.findViewById(R.id.alter_corppos_buzhang_cv);
        ganshi = (CardView)this.findViewById(R.id.alter_corppos_ganshi_cv);

        zhuxi.setOnClickListener(this);
        buzhang.setOnClickListener(this);
        ganshi.setOnClickListener(this);

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.finish_alter_my_group_part:
                finish();
                break;
            case R.id.alter_corppos_zhuxi_cv:
                if(partpos.equals("主席")){
                    Toast.makeText(AlterCorpPosActivity.this, "您已经是主席,无需修改!", Toast.LENGTH_SHORT).show();
                    return;
                }
                showAlterCorpPosDialog("主席");
                break;
            case R.id.alter_corppos_buzhang_cv:
                if(partpos.equals("部长")){
                    Toast.makeText(AlterCorpPosActivity.this, "您已经是部长,无需修改!", Toast.LENGTH_SHORT).show();
                    return;
                }
                showAlterCorpPosDialog("部长");
                break;
            case R.id.alter_corppos_ganshi_cv:
                if(partpos.equals("干事")){
                    Toast.makeText(AlterCorpPosActivity.this, "您已经是干事,无需修改!", Toast.LENGTH_SHORT).show();
                    return;
                }
                showAlterCorpPosDialog("干事");
                break;
        }
    }


    protected void onResume(){
        super.onResume();

        IntentFilter filter = new IntentFilter();

        filter.addAction(MY);

        registerReceiver(MyMessageReceiver, filter);
//        Intent intent=new Intent("thefirstchange.example.com.communicationtext.MAIN_LOOK");
//        sendBroadcast(intent);
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

            String rs = intent.getStringExtra("rs");


                if(timer!=null){
                    timer.cancel();
                }
                if(LoadingDialog!=null){
                    LoadingDialog.dismiss();
                }


            if(rs.equals("ok")){

                Toast.makeText(AlterCorpPosActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(AlterCorpPosActivity.this,"服务器繁忙",Toast.LENGTH_SHORT).show();
            }
        }
    };


    private void showAlterCorpPosDialog(final String name){
        final Dialog bottomDialog = new Dialog(AlterCorpPosActivity.this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(AlterCorpPosActivity.this).inflate(R.layout.dialog_delete_alarm, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = this.getResources().getDisplayMetrics().widthPixels - DensityUtiltwo.dp2px(AlterCorpPosActivity.this, 90f);
        params.bottomMargin = DensityUtiltwo.dp2px(AlterCorpPosActivity.this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        TextView yesText=contentView.findViewById(R.id.yes_delete);
        TextView infoText=contentView.findViewById(R.id.delete_alarm_info);
        infoText.setText("确认修改为"+name);
        yesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                if(!NetworkUtils.isConnected(AlterCorpPosActivity.this)){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AlterCorpPosActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                        }
                    });
                     return;
                }

                LoadingDialog = new Dialog(AlterCorpPosActivity.this, R.style.BottomDialog);
                showLoadingDialog();


                CorpSendToServer.alterCorpPos(groupId,name);


                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AlterCorpPosActivity.this, "服务器繁忙,请稍后再试!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        timer.cancel();
                    }
                },5000);

                bottomDialog.dismiss();
            }
        });
        TextView noText=contentView.findViewById(R.id.no_delete);
        noText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });

    }

    private void showLoadingDialog() {

        View contentView = LayoutInflater.from(AlterCorpPosActivity.this).inflate(R.layout.sr_loading_dialog, null);
        LoadingDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
//        params.width = 300;
//        params.height= 400;
        params.bottomMargin = DensityUtiltwo.dp2px(AlterCorpPosActivity.this, 8f);
        contentView.setLayoutParams(params);
        LoadingDialog.setCanceledOnTouchOutside(false);
        LoadingDialog.getWindow().setGravity(Gravity.CENTER);
        LoadingDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        LoadingDialog.show();
        ProgressBar progressBar = (ProgressBar)contentView.findViewById(R.id.sr_loading_loading);
        TextView sr_loading_tv = (TextView)contentView.findViewById(R.id.sr_loading_tv) ;

    }


}
