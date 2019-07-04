package thefirstchange.example.com.communicationtext.activity.myselfactivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.advice.AdviceActivity;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.util.ActivityCollector;
import thefirstchange.example.com.communicationtext.util.DensityUtiltwo;

public class SoftSettingActivity extends BaseForCloseActivity implements View.OnClickListener{
    private ImageView finishSelf;
    private RelativeLayout finishSoft;
    private RelativeLayout toAdvice;
    private RelativeLayout toAlterIp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_setting);
        //StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);
        initView();
    }

    private void initView(){
        finishSelf=(ImageView)findViewById(R.id.finish_setting);
        finishSelf.setOnClickListener(this);
        finishSoft=(RelativeLayout)findViewById(R.id.finish_soft_lay);
        finishSoft.setOnClickListener(this);
        toAdvice=(RelativeLayout)findViewById(R.id.to_advice_and_help);
        toAdvice.setOnClickListener(this);


    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.finish_setting:
                finish();
                break;
            case R.id.finish_soft_lay:
              showFinishDialog();
                break;
            case R.id.to_advice_and_help:
                Intent intent=new Intent(SoftSettingActivity.this, AdviceActivity.class);
                startActivity(intent);
                break;
                default:
                    break;

        }
    }


    private void showFinishDialog() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_delete_alarm, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtiltwo.dp2px(this, 30f);
        params.bottomMargin = DensityUtiltwo.dp2px(this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

        TextView info=(TextView)contentView.findViewById(R.id.delete_alarm_info);
        info.setText("退出将不再接收消息");
        TextView yes=(TextView)contentView.findViewById(R.id.yes_delete);
        TextView no=(TextView)contentView.findViewById(R.id.no_delete);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendToServer.loginout();
                NettyService.disconnect();

                //关闭服务
                final Intent intent = new Intent();
                intent.setAction("ITOP.MOBILE.SIMPLE.SERVICE.NETTYSERVICE");
                stopService(intent);
                NettyService.nettyService=null;
                Config.isExitSoft = true;

                StaticAllList.removeAll();
                bottomDialog.dismiss();

                ActivityCollector.finishAll();
            }
        });

    }
}
