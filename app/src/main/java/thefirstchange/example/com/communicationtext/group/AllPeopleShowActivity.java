package thefirstchange.example.com.communicationtext.group;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.adapter.PeopleAdapter;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.UserInGroupInfo;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class AllPeopleShowActivity extends BaseForCloseActivity implements View.OnClickListener{

    private final static String MY="thefirstchange.example.com.communicationtext.ALLPEOPLESHOW";

//    private SwipeRefreshLayout swipeRefreshLayout;
    private PeopleAdapter adapter;
    private int groupId;
    private  RecyclerView recyclerView;
    private ImageView finish_all_people_show;
    boolean iswrong = false;

    private Timer getAllUserInfoTimer = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_people_show);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);
//        android.support.v7.widget.Toolbar toolbar=findViewById(R.id.people_list_toolbar);
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
//        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        groupId=intent.getIntExtra("groupid",-1);
        if(groupId==-1|| !StaticAllList.groupList.containsKey(groupId)){
            Toast.makeText(AllPeopleShowActivity.this, "系统错误,请稍后再试!", Toast.LENGTH_SHORT).show();
            iswrong = true;
            finish();
        }

        if(!NetworkUtils.isConnected(AllPeopleShowActivity.this)){
            Toast.makeText(AllPeopleShowActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
            return;
        }else{


            MyDialog.showBottomLoadingDialog(AllPeopleShowActivity.this);


            SendToServer.getGroupAllU(groupId,"getGroupAllURs");


            getAllUserInfoTimer = new Timer();
            getAllUserInfoTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    AllPeopleShowActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyDialog.dismissBottomLoadingDialog();
                            Toast.makeText(AllPeopleShowActivity.this,"加载失败!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            },60000);

        }


        finish_all_people_show = findViewById(R.id.finish_all_people_show);
        finish_all_people_show.setOnClickListener(this);

        recyclerView=(RecyclerView)findViewById(R.id.group_people_recycle_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(!StaticAllList.groupUsersInfo.containsKey(groupId)){
            adapter=new PeopleAdapter(this, new ArrayList<UserInGroupInfo>(),"普通群",groupId);
        }else{
            String type ="普通群";
            if(StaticAllList.groupList.containsKey(groupId)){
                type =StaticAllList.groupList.get(groupId).getGrouptype();
            }
            adapter=new PeopleAdapter(this, StaticAllList.groupUsersInfo.get(groupId),type,groupId);
        }

        recyclerView.setAdapter(adapter);
    }



    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


    protected void onResume(){
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MY);
        registerReceiver(broadcastReceiver,intentFilter);
    }

    protected void onDestroy(){
        super.onDestroy();
        if(!iswrong){
            unregisterReceiver(broadcastReceiver);
        }

        if(getAllUserInfoTimer!=null){
            getAllUserInfoTimer.cancel();
        }

    }

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type = intent.getStringExtra("type");
            if (type.equals("getGroupAllURs")) {
                int gid = intent.getIntExtra("groupid",-1);
                if(gid!=groupId){
                    return;
                }
                if(getAllUserInfoTimer!=null){
                    getAllUserInfoTimer.cancel();
                }
                MyDialog.dismissBottomLoadingDialog();

                if(!StaticAllList.groupUsersInfo.containsKey(groupId)){
                    adapter=new PeopleAdapter(AllPeopleShowActivity.this, new ArrayList<UserInGroupInfo>(),"普通群",groupId);
                }else{
                    String type2 ="普通群";
                    if(StaticAllList.groupList.containsKey(groupId)){
                        type2 =StaticAllList.groupList.get(groupId).getGrouptype();
                    }
                    adapter=new PeopleAdapter(AllPeopleShowActivity.this, StaticAllList.groupUsersInfo.get(groupId),type2,groupId);
                }
                recyclerView.setAdapter(adapter);
                SendToServer.okGroupAllU(groupId);

                //群成员列表的  成员的头像到了
            }else if(type.equals("getGroupUserIconRs")){
                adapter.notifyDataSetChanged();
            }
        }

    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.finish_all_people_show:
                finish();
                break;
        }
    }


}
