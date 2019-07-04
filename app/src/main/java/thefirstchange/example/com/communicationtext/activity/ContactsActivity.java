package thefirstchange.example.com.communicationtext.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.contacts.ContactAdapter;
import thefirstchange.example.com.communicationtext.contacts.LetterView;
import thefirstchange.example.com.communicationtext.group.GroupListActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class ContactsActivity extends BaseForCloseActivity implements View.OnClickListener{

    private RecyclerView contactList;
    private String[] contactNames;
    private LinearLayoutManager layoutManager;
    private LetterView letterView;
    private ContactAdapter adapter;
    private TextView toGroupList;
    private ImageView finishSelf;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Timer timer = new Timer();

    private Timer freshFriendListInfoTimer = new Timer();

    private static final String MY="thefirstchange.example.com.communicationtext.CONTACTSWIPE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.contacts_list_swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

          /*
            刷新好友列表
           */
            @Override
            public void onRefresh() {
                if(!NetworkUtils.isConnected(ContactsActivity.this)){
                    Toast.makeText(ContactsActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }



                SendToServer.getAllFriendInfo();
                StaticAllList.friendsIconHadRequest.clear();

                freshFriendListInfoTimer = new Timer();
                freshFriendListInfoTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        ContactsActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"刷新失败",Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                },10000);
            }
        });
        contactList = (RecyclerView) findViewById(R.id.contact_list);
        letterView = (LetterView) findViewById(R.id.letter_view);
        toGroupList=(TextView)findViewById(R.id.to_group_list);
        finishSelf=(ImageView)findViewById(R.id.finish_people_list);
        finishSelf.setOnClickListener(this);
        toGroupList.setOnClickListener(this);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ContactAdapter(this,ContactsActivity.this);

        contactList.setLayoutManager(layoutManager);
        contactList.addItemDecoration(new DividerItemDecoration(this, thefirstchange.example.com.communicationtext.contacts.DividerItemDecoration.VERTICAL_LIST));
        contactList.setAdapter(adapter);

        letterView.setCharacterListener(new LetterView.CharacterClickListener() {
            @Override
            public void clickCharacter(String character) {
                layoutManager.scrollToPositionWithOffset(adapter.getScrollPosition(character),0);
            }

            @Override
            public void clickArrow() {
                layoutManager.scrollToPositionWithOffset(0,0);
            }
        });
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.to_group_list:
                Intent intent=new Intent(ContactsActivity.this, GroupListActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.finish_people_list:
                finish();
                break;
        }
    }

    protected void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MY);
        registerReceiver(broadcastReceiver,intentFilter);
    }

    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        if(timer!=null){
            timer.cancel();
        }
        if(freshFriendListInfoTimer!=null){
            freshFriendListInfoTimer.cancel();
        }
    }

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String type = intent.getStringExtra("type");
            if(type.equals("deleteFriendResult")){

                if(adapter.timer!=null){
                    adapter.timer.cancel();
                }
                MyDialog.dismissBottomLoadingDialog();

                String rs = intent.getStringExtra("rs");
                if(rs.equals("ok")){
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"服务器繁忙",Toast.LENGTH_SHORT).show();

                }




                //自己的某一个好友的头像  更新到来
            }else if(type.equals("GetFriendListUIcByPh")){

                adapter.notifyDataSetChanged();

                        //获得自己的所有好友的信息   没有头像
            }else if(type.equals("getAllFriendInfoResult")){

                if(freshFriendListInfoTimer !=null){
                    freshFriendListInfoTimer.cancel();
                }

//                layoutManager = new LinearLayoutManager(ContactsActivity.this);
//                adapter = new ContactAdapter(ContactsActivity.this,ContactsActivity.this);
//
//                contactList.setLayoutManager(layoutManager);
//                contactList.addItemDecoration(new DividerItemDecoration(ContactsActivity.this, thefirstchange.example.com.communicationtext.contacts.DividerItemDecoration.VERTICAL_LIST));
//                contactList.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();

                //刷新自己的好友列表
            }else if(type.equals("freshAllFriendInfoRs")){

                if(freshFriendListInfoTimer !=null){
                    freshFriendListInfoTimer.cancel();
                }

//                layoutManager = new LinearLayoutManager(ContactsActivity.this);
//                adapter = new ContactAdapter(ContactsActivity.this,ContactsActivity.this);
//
//                contactList.setLayoutManager(layoutManager);
//                contactList.addItemDecoration(new DividerItemDecoration(ContactsActivity.this, thefirstchange.example.com.communicationtext.contacts.DividerItemDecoration.VERTICAL_LIST));
//                contactList.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();

            }




        }
    };



}
