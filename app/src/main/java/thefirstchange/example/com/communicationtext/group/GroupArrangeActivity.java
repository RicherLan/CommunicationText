package thefirstchange.example.com.communicationtext.group;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.activity.CheckCourseInputActivity;
import thefirstchange.example.com.communicationtext.corpration.AddAlterCorpPartActivity;
import thefirstchange.example.com.communicationtext.corpration.AlterCorpTermActivity;
import thefirstchange.example.com.communicationtext.corpration.appointPos.AppointPosActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class GroupArrangeActivity extends BaseForCloseActivity implements View.OnClickListener{

    private static final String MY="thefirstchange.example.com.communicationtext.GROUPARRANGEA";
    private Timer checkLoadCourseTimer = new Timer();


    private ImageView finishSelf;
    private RelativeLayout toAlterPart;
    private RelativeLayout toAlterPos;
    private RelativeLayout toAlterTerm;
    private RelativeLayout deleteGroup;
    private RelativeLayout lookFinishPeople;

    int groupid;
    boolean iswrong = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_arrange);
        Intent intent = getIntent();
        groupid = intent.getIntExtra("groupid",-1);
        if(groupid==-1){
            Toast.makeText(GroupArrangeActivity.this,"系统错误,请稍后再试!",Toast.LENGTH_SHORT).show();
            iswrong = true;
            finish();
        }

        initView();
    }

    private void initView() {
        finishSelf=(ImageView)findViewById(R.id.finish_group_arrange1);
        finishSelf.setOnClickListener(this);
        toAlterPos = (RelativeLayout)findViewById(R.id.to_alter_pos_layout);
        toAlterPos.setOnClickListener(this);
        toAlterPart=(RelativeLayout)findViewById(R.id.to_alter_part);
        toAlterPart.setOnClickListener(this);
        toAlterTerm=(RelativeLayout)findViewById(R.id.to_alter_term);
        toAlterTerm.setOnClickListener(this);
        deleteGroup=(RelativeLayout)findViewById(R.id.delete_group);
        deleteGroup.setOnClickListener(this);
        lookFinishPeople=(RelativeLayout)findViewById(R.id.to_look_load);
        lookFinishPeople.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.finish_group_arrange1:
                finish();
                break;
            case R.id.to_alter_part:
                if(!(StaticAllList.groupList.get(groupid).getAuthid()+"").equals(ObjectService.personalInfo.getPhonenumber())){
                    Toast.makeText(GroupArrangeActivity.this,"抱歉,您的权限不够!",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent2 = new Intent(GroupArrangeActivity.this, AddAlterCorpPartActivity.class);
                intent2.putExtra("groupid",groupid);
                startActivity(intent2);

                break;
            case R.id.to_alter_pos_layout:
                if(!(StaticAllList.groupList.get(groupid).getAuthid()+"").equals(ObjectService.personalInfo.getPhonenumber())){
                    Toast.makeText(GroupArrangeActivity.this,"抱歉,您的权限不够!",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent3 = new Intent(GroupArrangeActivity.this, AppointPosActivity.class);
                intent3.putExtra("groupid",groupid);
                startActivity(intent3);
                break;
            case R.id.to_alter_term:

                if(!(StaticAllList.groupList.get(groupid).getAuthid()+"").equals(ObjectService.personalInfo.getPhonenumber())){
                    Toast.makeText(GroupArrangeActivity.this,"抱歉,您的权限不够!",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(GroupArrangeActivity.this, AlterCorpTermActivity.class);
                intent.putExtra("groupid",groupid);
                startActivity(intent);
                break;
            case R.id.delete_group:

                Toast.makeText(GroupArrangeActivity.this, "该功能尚未开放!", Toast.LENGTH_SHORT).show();


//                if(!NetworkUtils.isConnected(GroupArrangeActivity.this)){
//                    Toast.makeText(GroupArrangeActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//
//                if(!StaticAllList.groupList.get(groupid).getUserauthority().equals("群主")){
//                    Toast.makeText(GroupArrangeActivity.this,"抱歉,您的权限不够!",Toast.LENGTH_SHORT).show();
//                    return;
//                }

                break;
            case R.id.to_look_load:

                if(!NetworkUtils.isConnected(GroupArrangeActivity.this)){
                    Toast.makeText(GroupArrangeActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

                MyDialog.showBottomLoadingDialog(GroupArrangeActivity.this);
                SendToServer.corpLoadCourseRs(groupid);

                checkLoadCourseTimer = new Timer();
                checkLoadCourseTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                checkLoadCourseTimer.cancel();
                                MyDialog.dismissBottomLoadingDialog();
                                Toast.makeText(GroupArrangeActivity.this,"服务器繁忙,请稍后再试",Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                },60000);


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

        if(checkLoadCourseTimer!=null){
            checkLoadCourseTimer.cancel();
        }
    }

    BroadcastReceiver MyMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MY)){
                String type = intent.getStringExtra("type");

                if(type.equals("corpLoadCourseRs")) {
                    int gid = intent.getIntExtra("groupid",-1);
                    if(gid!=groupid){
                        return;
                    }
                    if (checkLoadCourseTimer != null) {
                        checkLoadCourseTimer.cancel();
                    }
                    MyDialog.dismissBottomLoadingDialog();
                    String rs = intent.getStringExtra("rs");
                    if(rs.equals("ok")){
                        Intent intent2=new Intent(GroupArrangeActivity.this,CheckCourseInputActivity.class);
                        intent2.putExtra("groupid",groupid);
                        startActivity(intent2);
                    }else{
                        Toast.makeText(GroupArrangeActivity.this,"服务器繁忙,请稍后再试!",Toast.LENGTH_SHORT).show();
                    }

                }

            }

        }
    };




}
