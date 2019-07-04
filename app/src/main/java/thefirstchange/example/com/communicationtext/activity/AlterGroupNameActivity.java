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

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class AlterGroupNameActivity extends BaseForCloseActivity implements View.OnClickListener{

    private ImageView finishSelf;
    private EditText myGroupName;
    private TextView saveName;
    private String groupId;
    private String groupname;

    public Timer timer = new Timer();
    private static final String MY="thefirstchange.example.com.communicationtext.ALTERGROUPNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_group_name);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);

        Intent intent=getIntent();
        groupId=intent.getStringExtra("groupid");
        groupname=intent.getStringExtra("name");
        initView();
    }

    private void initView(){
        finishSelf=(ImageView)findViewById(R.id.finish_alter_my_group_name);
        finishSelf.setOnClickListener(this);
        myGroupName=(EditText)findViewById(R.id.write_new_my_group_name);
        myGroupName.setText(groupname);
        saveName=(TextView)findViewById(R.id.save_my_new_group__name);
        saveName.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.finish_alter_my_group_name:
                finish();
                break;
            case R.id.save_my_new_group__name:

                if(!NetworkUtils.isConnected(AlterGroupNameActivity.this)){
                    Toast.makeText(AlterGroupNameActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String name=myGroupName.getText().toString().trim();
                if (name.isEmpty()){
                    Toast.makeText(AlterGroupNameActivity.this,"备注不能为空",Toast.LENGTH_SHORT).show();
                }else {

                    MyDialog.showBottomLoadingDialog(AlterGroupNameActivity.this);
                    SendToServer.upGroupRemark(Integer.parseInt(groupId),name);


                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            AlterGroupNameActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MyDialog.dismissBottomLoadingDialog();
                                    Toast.makeText(AlterGroupNameActivity.this,"服务器繁忙,请稍后再试！",Toast.LENGTH_SHORT).show();

                                }
                            });
                             }
                    },30000);

                }

                break;
        }
    }


    protected void onResume(){
        super.onResume();
//        Log.e("ConnectionOtherActivity","onResume");

        IntentFilter filter = new IntentFilter();

        filter.addAction(MY);

        registerReceiver(MyMessageReceiver, filter);
//        Intent intent=new Intent("thefirstchange.example.com.communicationtext.MAIN_LOOK");
//        sendBroadcast(intent);
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

            String rs = intent.getStringExtra("rs");
            if(timer!=null){
                timer.cancel();
            }
            MyDialog.dismissBottomLoadingDialog();
            if(rs.equals("ok")){
                Toast.makeText(AlterGroupNameActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(AlterGroupNameActivity.this,"服务器繁忙,请稍后再试!",Toast.LENGTH_SHORT).show();
            }
        }
    };

}
