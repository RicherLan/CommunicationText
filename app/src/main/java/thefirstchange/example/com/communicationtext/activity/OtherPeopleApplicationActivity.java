package thefirstchange.example.com.communicationtext.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.adapter.OtherPeopleApplicationAdapter;
import thefirstchange.example.com.communicationtext.fragment.NotificationFragment;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.util.MyDialog;

public class OtherPeopleApplicationActivity extends BaseForCloseActivity implements View.OnClickListener{

    private ImageView finishSelf;
    private TextView toAddActivity;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private OtherPeopleApplicationAdapter applicationAdapter;
    private List<ChatMsg> list = new ArrayList<>();

    private final static String MY="thefirstchange.example.com.communicationtext.OTHERPEOPLEAPPLICATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_people_applaication);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);

        initView();
        loadFromDB();
        initlist();

    }

    public void loadFromDB(){
        if(StaticAllList.chatRecordDao!=null){
            StaticAllList.chatRecordDao.getRequestFromDB();
        }else{
            MainActivity.initChatRecorddb();
            StaticAllList.chatRecordDao.getRequestFromDB();
        }
    }

    private void initlist(){


        list.clear();
        Map<Integer,String> msgs = new HashMap<>();   //向服务器回执已读   key是消息的id，value是消息的类型

        //去掉已经处理的请求中  相同的人在未处理
        Vector<Integer> integers = new Vector<>();
        if(MyMessageQueue.requestQueueHadHandled!=null&&MyMessageQueue.requestQueueNotHandle!=null){
            for(int i=0;i<MyMessageQueue.requestQueueHadHandled.size();++i){
                ChatMsg chatMsg = MyMessageQueue.requestQueueHadHandled.get(i);

                for(ChatMsg chatMsg1:MyMessageQueue.requestQueueNotHandle){
                    if(chatMsg.getSenderid().equals(chatMsg1.getSenderid())&&
                            chatMsg.getType().equals(chatMsg1.getType())){
                        integers.add(i);
                        break;
                    }
                }
            }
        }
        if(MyMessageQueue.requestQueueHadHandled!=null){
            for(Integer integer:integers){
                MyMessageQueue.requestQueueHadHandled.removeElementAt(integer);
            }
        }

       Vector<ChatMsg> delete = new Vector<>();

        for (int i=0;i<MyMessageQueue.requestQueueNotHandle.size();i++){
            if(MyMessageQueue.requestQueueNotHandle.get(i).getHandleRs().equals("nothandle")){
                // 标记已读
                MyMessageQueue.requestQueueNotHandle.get(i).setHandleRs("hadread");
            }else if(MyMessageQueue.requestQueueNotHandle.get(i).getType().equals("someoneHasHandledAddGroup")){
                delete.add(MyMessageQueue.requestQueueNotHandle.get(i));
            }

        }

        for(ChatMsg chatMsg:delete){
            MyMessageQueue.requestQueueHadHandled.add(chatMsg);
            MyMessageQueue.requestQueueNotHandle.removeElement(chatMsg);
            msgs.put(chatMsg.getMsgid(),chatMsg.getType());
        }


        for (int i=0;i<MyMessageQueue.requestQueueNotHandle.size();i++){
            ChatMsg  chatMsg = MyMessageQueue.requestQueueNotHandle.get(i);
//            if(chatMsg.getType().equals("agreeYourAddFriend")||chatMsg.getType().equals("disagreeYourAddFriend")||chatMsg.getType().equals("agreeAddGroup")||chatMsg.getType().equals("disagreeAddGroup")){
            msgs.put(chatMsg.getMsgid(),chatMsg.getType());
//            }
            list.add(chatMsg);
        }
        for (int i=0;i<MyMessageQueue.requestQueueHadHandled.size();i++){
            list.add(MyMessageQueue.requestQueueHadHandled.get(i));
        }


//        int a = MyMessageQueue.requestQueueNotHandle.size();
//        int b = MyMessageQueue.requestQueueHadHandled.size();
        final Map<Integer,String> msgs2 = msgs;
//        new Thread(){
//            public void run(){
//                for(int id:msgs2.keySet()){
//                    String type = msgs2.get(id);
//                    if(type.contains("Friend")){
//                        SendToServer.AddFriendResult(id);
//                    }else{
//                        SendToServer.ReadaddGroupResult(id);
//                    }
//                }
//            }
//        }.start();




        NotificationFragment.changemsgnotreadnum();
//        MainActivity.changemsgnotreadnum();

        applicationAdapter.notifyDataSetChanged();

        for(int id:msgs2.keySet()){
            SendToServer.changeRequestMsgState(id);
        }

        for (int i=0;i<MyMessageQueue.requestQueueNotHandle.size();i++){
            // 标记已读
            // MyMessageQueue.requestQueueNotHandle.get(i).setHandleRs("hadread");
            ChatMsg chatMsg = MyMessageQueue.requestQueueNotHandle.get(i);
            if(StaticAllList.chatRecordDao!=null){
                StaticAllList.chatRecordDao.saveRequest(chatMsg);
            }else{
                MainActivity.initChatRecorddb();
                StaticAllList.chatRecordDao.saveRequest(chatMsg);
            }
        }




    }

    private void initView(){
        finishSelf=(ImageView)findViewById(R.id.finish_other_application);
        finishSelf.setOnClickListener(this);
        toAddActivity=(TextView)findViewById(R.id.application_add_text);
        toAddActivity.setOnClickListener(this);
        recyclerView=(RecyclerView)findViewById(R.id.other_people_application_recycler);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        applicationAdapter=new OtherPeopleApplicationAdapter(this,list);
        recyclerView.setAdapter(applicationAdapter);


    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.finish_other_application:
                finish();
                break;
            case R.id.application_add_text:
                Intent intent=new Intent(OtherPeopleApplicationActivity.this,FindFriendOrGroupActivity.class);
                intent.putExtra("is","group");
                startActivity(intent);
                finish();
                break;
        }
    }

    protected void onResume(){
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MY);
        registerReceiver(broadcastReceiver,intentFilter);
    }

    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);

        if(applicationAdapter!=null){
            if(applicationAdapter.addFriendTimer!=null){
                applicationAdapter.addFriendTimer.cancel();
            }
            if(applicationAdapter.addGroupTimer!=null){
                applicationAdapter.addGroupTimer.cancel();
            }
        }

    }

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type = intent.getStringExtra("type");
            if (type.equals("AddFriendReturnResult")) {

                if(applicationAdapter.addFriendTimer!=null){
                    applicationAdapter.addFriendTimer.cancel();
                }
                MyDialog.dismissBottomLoadingDialog();

                String rs = intent.getStringExtra("rs");
                String operator = intent.getStringExtra("operator");
                int msgid = intent.getIntExtra("msgid", -1);

                if (rs.equals("ok")) {

                    Toast.makeText(OtherPeopleApplicationActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    ChatMsg chatMsg = null;
                    int index = -1;


                    for (int i = 0; i < MyMessageQueue.requestQueueNotHandle.size(); ++i) {
                        if (MyMessageQueue.requestQueueNotHandle.get(i).getMsgid() == msgid) {
                            chatMsg = MyMessageQueue.requestQueueNotHandle.get(i);
                            index = i;
                            break;
                        }
                    }
                    if(index!=-1){
                        MyMessageQueue.requestQueueNotHandle.removeElementAt(index);
                    }


                    String ph = null;
                    if (chatMsg != null) {
                        chatMsg.setHandleRs(operator);
                        MyMessageQueue.requestQueueHadHandled.add(chatMsg);

                        initlist();
                        applicationAdapter.notifyDataSetChanged();


                        if(StaticAllList.chatRecordDao!=null){
                            StaticAllList.chatRecordDao.saveRequest(chatMsg);
                        }else{
                            MainActivity.initChatRecorddb();
                            StaticAllList.chatRecordDao.saveRequest(chatMsg);
                        }
                        ph = chatMsg.getSenderid();
//                        new Thread(){
//                            public void run(){

//                            }
//                        }.start();
                    }


                    if (ph != null) {
                        SendToServer.addUserToFriendList(ph);
                    }

//                    if(StaticAllList.chatRecordDao!=null){
//                        StaticAllList.chatRecordDao.changeRequeststate(msgid,operator);
//                    }else{
//                        MainActivity.initChatRecorddb();
//                        StaticAllList.chatRecordDao.changeRequeststate(msgid,operator);
//                    }


                } else {
                    Toast.makeText(OtherPeopleApplicationActivity.this, "服务器繁忙,请稍后再试!", Toast.LENGTH_SHORT).show();
                }

            } else if (type.equals("handlerAddGroupResult")) {

                if(applicationAdapter.addGroupTimer!=null){
                    applicationAdapter.addGroupTimer.cancel();
                }
                MyDialog.dismissBottomLoadingDialog();

                String rs = intent.getStringExtra("rs");
                String operator = intent.getStringExtra("operator");
                int msgid = intent.getIntExtra("msgid", -1);
                if (rs.equals("ok")) {

                    Toast.makeText(OtherPeopleApplicationActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    ChatMsg chatMsg = null;
                    int index = -1;
                    for (int i = 0; i < MyMessageQueue.requestQueueNotHandle.size(); ++i) {
                        if (MyMessageQueue.requestQueueNotHandle.get(i).getMsgid() == msgid) {
                            chatMsg = MyMessageQueue.requestQueueNotHandle.get(i);
                            index = i;
                            break;
                        }
                    }
                    if(index!=-1){
                        MyMessageQueue.requestQueueNotHandle.removeElementAt(index);
                    }

                    if (chatMsg != null) {

                        chatMsg.setHandleRs(operator);
                        MyMessageQueue.requestQueueHadHandled.add(chatMsg);
                        initlist();
                        applicationAdapter.notifyDataSetChanged();

                        if (StaticAllList.chatRecordDao != null) {
                            StaticAllList.chatRecordDao.saveRequest(chatMsg);
                        } else {
                            MainActivity.initChatRecorddb();
                            StaticAllList.chatRecordDao.saveRequest(chatMsg);
                        }
                    }


                    } else {
                        Toast.makeText(OtherPeopleApplicationActivity.this, "服务器繁忙,请稍后再试!", Toast.LENGTH_SHORT).show();
                    }

                //以经有管理员对加群请求处理了
                }else if(type.equals("someoneHasHandledAddGroup")){
                    initlist();
                    applicationAdapter.notifyDataSetChanged();
                }

                 //获得所有的请求信息
                else if(type.equals("getRequestRs")){
                    initlist();
                    applicationAdapter.notifyDataSetChanged();
                }

                else if (type.equals("other")) {

                    initlist();
                    applicationAdapter.notifyDataSetChanged();

                    //添加好友  被添加方要获得对方的头像
                }else if(type.equals("otherIcon")){
                    applicationAdapter.notifyDataSetChanged();
                }

            }

    };

}
