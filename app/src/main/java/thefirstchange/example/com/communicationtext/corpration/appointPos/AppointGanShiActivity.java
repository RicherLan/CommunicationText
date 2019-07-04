package thefirstchange.example.com.communicationtext.corpration.appointPos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.corpration.appointPos.adapter.AppointPeopleAdapter;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.UserGroup;
import thefirstchange.example.com.communicationtext.gson.UserInGroupInfo;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

/*
    任命干事
 */
public class AppointGanShiActivity extends BaseForCloseActivity implements View.OnClickListener,TextWatcher {
    private final static String MY="thefirstchange.example.com.communicationtext.APPOINTGANSHIACTIVITY";
    UserGroup userGroup = null;
    int groupId;
    Vector<UserInGroupInfo> peoplelist;

    private ImageView finish_iv;
    private EditText search_people;
    private RecyclerView recyclerView;
    private AppointPeopleAdapter adapter;




    private Timer getAllUserInfoTimer = new Timer();
    boolean iswrong = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_ganshi);
        Intent intent=getIntent();
        groupId=intent.getIntExtra("groupid",-1);

        if(groupId==-1|| !StaticAllList.groupList.containsKey(groupId)){
            Toast.makeText(this,"系统错误，请稍后再试!",Toast.LENGTH_SHORT).show();
            iswrong=true;
            finish();
        }
        initview();
        initevent();
        userGroup = StaticAllList.groupList.get(groupId);

        getAllUser();

    }

    private void getAllUser(){


        if(!NetworkUtils.isConnected(AppointGanShiActivity.this)){
            Toast.makeText(AppointGanShiActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
            return;
        }else{


            MyDialog.showBottomLoadingDialog(AppointGanShiActivity.this);

                    SendToServer.getGroupAllU(groupId,"appoint_ganshi");


            getAllUserInfoTimer = new Timer();
            getAllUserInfoTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    AppointGanShiActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyDialog.dismissBottomLoadingDialog();
                            Toast.makeText(AppointGanShiActivity.this,"加载失败!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            },50000);
        }

    }

    private void initview(){

        finish_iv = (ImageView)findViewById(R.id.finish_appoint_ganshi_iv);
        search_people = (EditText)findViewById(R.id.appoint_ganshi_search_people_et);

        recyclerView=(RecyclerView)findViewById(R.id.appoint_ganshi_allpeople_recycleview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void initevent(){
        finish_iv.setOnClickListener(this);
        search_people.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.finish_appoint_ganshi_iv:
                finish();
                break;

            default:
                break;
        }

    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String text = charSequence.toString();
        peoplelist.clear();
        for(UserInGroupInfo userInGroupInfo :StaticAllList.groupUsersInfo.get(groupId) ){

            if(userInGroupInfo.getPh().contains(text)
                    ||userInGroupInfo.getGroupnickname().contains(text)
                    ||(userInGroupInfo.getCorpos()!=null&&userInGroupInfo.getCorpos().contains(text))
                    ||(userInGroupInfo.getCorppart()!=null&&userInGroupInfo.getCorppart().contains(text))){
                peoplelist.add(userInGroupInfo);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable editable) {

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

        if(adapter!=null){
            if(adapter.appointTimer!=null){
                adapter.appointTimer.cancel();
            }
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
                if(StaticAllList.groupUsersInfo.get(groupId)==null){
                    return;
                }


                String type2 ="普通群";
                if(StaticAllList.groupList.containsKey(groupId)){
                    type2 =StaticAllList.groupList.get(groupId).getGrouptype();
                }
                peoplelist=(Vector<UserInGroupInfo>) StaticAllList.groupUsersInfo.get(groupId).clone();

                adapter=new AppointPeopleAdapter(AppointGanShiActivity.this,peoplelist,type2,groupId,"","干事");
                recyclerView.setAdapter(adapter);
                SendToServer.okGroupAllU(groupId);

                //群成员列表的  成员的头像到了
            }else if(type.equals("getGroupUserIconRs")){

                String text = search_people.getText().toString();
                if(text.isEmpty()){
                    peoplelist=(Vector<UserInGroupInfo>) StaticAllList.groupUsersInfo.get(groupId).clone();
                    adapter.notifyDataSetChanged();
                }

                //任命职位
            }else if(type.equals("appointPos")){
                int gid = intent.getIntExtra("groupid",-1);
                if(gid!=groupId){
                    return;
                }
                String rs = intent.getStringExtra("rs");
                if(adapter.appointTimer!=null){
                    adapter.appointTimer.cancel();
                }
                MyDialog.dismissBottomLoadingDialog();

                String ph = intent.getStringExtra("ph");
                String oldph = intent.getStringExtra("oldph");
                for(UserInGroupInfo userInGroupInfo : StaticAllList.groupUsersInfo.get(groupId)){
                    if(userInGroupInfo.getPh().equals(ph)){
                        userInGroupInfo.setCorpos("干事");
                    }
                }

                if(rs.equals("ok")){
                    Toast.makeText(AppointGanShiActivity.this,"任职成功!",Toast.LENGTH_SHORT).show();
                    String text = search_people.getText().toString();
                    if(!text.isEmpty()){
                        search_people.setText("");
                        peoplelist=(Vector<UserInGroupInfo>) StaticAllList.groupUsersInfo.get(groupId).clone();
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(AppointGanShiActivity.this,"任职失败!",Toast.LENGTH_SHORT).show();
                }

            }
        }
    };


}
