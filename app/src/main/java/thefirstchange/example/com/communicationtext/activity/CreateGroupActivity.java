package thefirstchange.example.com.communicationtext.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.group.GroupChatActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.UserGroup;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class CreateGroupActivity extends BaseForCloseActivity implements View.OnClickListener{

    private static String MY = "thefirstchange.example.com.communicationtext.CREATEGROUP";

    private CircleImageView createGroupIv;
    private EditText crateGroupName;
    private TextView submitCreate;
    private ImageView finishSelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);
        initView();
    }

    private void initView(){
        createGroupIv=(CircleImageView)findViewById(R.id.create_group_iv);
        submitCreate=(TextView)findViewById(R.id.submit_create_group_info);
        crateGroupName=(EditText)findViewById(R.id.edit_create_group_info);
        finishSelf=(ImageView)findViewById(R.id.finish_edit_group_info);
        finishSelf.setOnClickListener(this);
        submitCreate.setOnClickListener(this);
    }

    Timer timer = new Timer();

    public void onClick(View view){
        switch (view.getId()){
            case R.id.finish_edit_group_info:
                finish();
                break;
            case R.id.submit_create_group_info:
                if(!NetworkUtils.isConnected(CreateGroupActivity.this)){
                    Toast.makeText(CreateGroupActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

                MyDialog.showBottomLoadingDialog(CreateGroupActivity.this);

                SendToServer.createGroup(crateGroupName.getText().toString().trim());


                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyDialog.dismissBottomLoadingDialog();
                                Toast.makeText(CreateGroupActivity.this,"服务器繁忙,请稍后再试!",Toast.LENGTH_SHORT).show();
                            }
                        });

                        timer.cancel();
                    }
                },15000);


                break;

        }
    }

    protected  void onDestroy(){
        super.onDestroy();

        unregisterReceiver(broadcastReceiver);

        if(timer!=null){
            timer.cancel();
        }
    }

    protected  void onResume() {

        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MY);
        registerReceiver(broadcastReceiver,intentFilter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(timer!=null){
                timer.cancel();
            }
            MyDialog.dismissBottomLoadingDialog();
            String type = intent.getStringExtra("rs");
            int groupid = intent.getIntExtra("groupid",-1);
            if(type.equals("ok")){

                UserGroup userGroup = new UserGroup();
                userGroup.setGroupid(groupid);
                userGroup.setGroupname(crateGroupName.getText().toString());
                userGroup.setCreatorid(ObjectService.personalInfo.getPhonenumber());
                userGroup.setUserauthority("群主");
                long time = System.currentTimeMillis()/1000;
                userGroup.setCreatetime(time);
                userGroup.setGrouptype("普通群");
                userGroup.setGroupnickname(ObjectService.personalInfo.getNickname());
                StaticAllList.groupList.put(groupid,userGroup);

                Intent intent2 = new Intent(CreateGroupActivity.this,GroupChatActivity.class);
                intent2.putExtra("groupId",groupid);
                intent2.putExtra("groupname",crateGroupName.getText().toString());
                startActivity(intent2);
                finish();

            }else{
                Toast.makeText(CreateGroupActivity.this,"服务器繁忙",Toast.LENGTH_SHORT).show();

            }

        }
    };


}
