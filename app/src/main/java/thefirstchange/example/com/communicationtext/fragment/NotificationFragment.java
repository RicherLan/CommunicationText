package thefirstchange.example.com.communicationtext.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.Vector;

import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.ContactsActivity;
import thefirstchange.example.com.communicationtext.activity.FindFriendOrGroupActivity;
import thefirstchange.example.com.communicationtext.adapter.AdapterNotifaction;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.view.drop.CoverManager;

import static android.content.Context.NOTIFICATION_SERVICE;


public class NotificationFragment extends Fragment implements View.OnClickListener{

    private Timer freshNotiFicationTimer = new Timer();

    private static boolean hasloadFrameFromDb = false;

    private static RecyclerView recyclerView;
    private  static AdapterNotifaction adapterNotifaction;
    private ImageView toContacts;
    private SmartRefreshLayout smartRefreshLayout;
    private static Context mContext;
    private ImageView addFriendImg;
    private static final String MY="thefirstchange.example.com.communicationtext.NOTIFICATION";
    static ArrayList<ChatMsg> arrayList=new ArrayList<>();
    View view=null;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if(view==null){
            view=inflater.inflate(R.layout.notification_fragment,container,false);
            addFriendImg=(ImageView)view.findViewById(R.id.add_friend);
            addFriendImg.setOnClickListener(this);
            mContext=getContext();
            smartRefreshLayout=view.findViewById(R.id.refreshLayout);
            smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {

                    /*
                    if(!NetworkUtils.isConnected(getContext())){
                        Toast.makeText(getContext(), "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Vector<String> phs = new Vector<>();
                    for(String count:MyMessageQueue.chatframes.keySet()){
                        String type = MyMessageQueue.chatframes.get(count);
                        if(type.equals("single")){
                            phs.add(count);
                        }
                    }
                    if(phs.size()!=0){
                        SendToServer.freshNotification(phs);
                    }


                    freshNotiFicationTimer = new Timer();
                    freshNotiFicationTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            smartRefreshLayout.finishRefresh();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(),"刷新失败",Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    },10000);
                    */
                    loadFrameFromDb();
                    initList();
                    //  smartRefreshLayout.setEnableRefresh(false);
                   smartRefreshLayout.finishRefresh();
                }
            });

            recyclerView=(RecyclerView)view.findViewById(R.id.notification_recycler);
            toContacts=(ImageView)view.findViewById(R.id.to_contacts);
            toContacts.setOnClickListener(this);
            RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);

            adapterNotifaction=new AdapterNotifaction(mContext,arrayList);
            recyclerView.setAdapter(adapterNotifaction);

//        initList();
            CoverManager.getInstance().init(getActivity());
            CoverManager.getInstance().setMaxDragDistance(300);//250
            CoverManager.getInstance().setEffectDuration(300);//150

            loadFrameFromDb();
        }

        return view;
    }

    //项目启动时   把窗口恢复
    public void loadFrameFromDb(){

        if(hasloadFrameFromDb){
            return;
        }
        hasloadFrameFromDb = true;
        if(StaticAllList.chatRecordDao!=null){
            StaticAllList.chatRecordDao.getNoticeFrameList();
            initList();
        }else{
            MainActivity.initChatRecorddb();
            StaticAllList.chatRecordDao.getNoticeFrameList();
            initList();
        }
    }


    public static void initList(){
         arrayList .clear();   //存在未读消息  那么拿出最后一条   不存在拿出已读的最后一条
        ArrayList<Integer> msgNumsArrayList=new ArrayList<>();    //某用户给你的未读消息的数量


        for(int i=0;i<MyMessageQueue.dutyframes.size();++i){
            int id = MyMessageQueue.dutyframes.get(i);
            //存在未读消息
            if(MyMessageQueue.dutyQueueNotRead.containsKey(id)&&MyMessageQueue.dutyQueueNotRead.get(id).size()!=0){

                Vector<ChatMsg> singleChatMsgs =  MyMessageQueue.dutyQueueNotRead.get(id);
                int msgNums = singleChatMsgs.size();             //该用户给你发的未读的消息数量
                ChatMsg singleChatMsg = singleChatMsgs.get(msgNums-1);               //理论上最后的是最大的
                arrayList.add(singleChatMsg);
                msgNumsArrayList.add(msgNums);


                //拿到已读消息的最后一条
            }else{
                if(MyMessageQueue.dutyQueueHadRead.containsKey(id)&&MyMessageQueue.dutyQueueHadRead.get(id).size()!=0){
                    Vector<ChatMsg> singleChatMsgs =  MyMessageQueue.dutyQueueHadRead.get(id);
                    int msgNums = singleChatMsgs.size();
                    ChatMsg singleChatMsg = singleChatMsgs.get(msgNums-1);
                    arrayList.add(singleChatMsg);
                    msgNumsArrayList.add(msgNums);
                }

            }
        }

        for(String id:MyMessageQueue.chatframes.keySet()){

            //String type = MyMessageQueue.chatframes.get(id);
           // if(type.equals("user")){
                //存在未读消息
                if(MyMessageQueue.chatQueueNotRead.containsKey(id)&&MyMessageQueue.chatQueueNotRead.get(id).size()!=0){

                    Vector<ChatMsg> singleChatMsgs =  MyMessageQueue.chatQueueNotRead.get(id);
                    int msgNums = singleChatMsgs.size();             //该用户给你发的未读的消息数量
                    ChatMsg singleChatMsg = singleChatMsgs.get(msgNums-1);               //理论上最后的是最大的
                    arrayList.add(singleChatMsg);
                    msgNumsArrayList.add(msgNums);


                    //拿到已读消息的最后一条
                }else{
                    if(MyMessageQueue.chatQueueHadRead.containsKey(id)&&MyMessageQueue.chatQueueHadRead.get(id).size()!=0){
                        Vector<ChatMsg> singleChatMsgs =  MyMessageQueue.chatQueueHadRead.get(id);
                        int msgNums = singleChatMsgs.size();
                        ChatMsg singleChatMsg = singleChatMsgs.get(msgNums-1);
                        arrayList.add(singleChatMsg);
                        msgNumsArrayList.add(msgNums);
                    }

                }
               //群消息
//            }else {
//
//            }

        }

        if(MyMessageQueue.requestQueueNotHandle!=null&&MyMessageQueue.requestQueueNotHandle.size()!=0){
            int num = MyMessageQueue.requestQueueNotHandle.size();
            msgNumsArrayList.add(num);
            arrayList.add(MyMessageQueue.requestQueueNotHandle.get(0));
        }else if(MyMessageQueue.requestQueueHadHandled!=null&&MyMessageQueue.requestQueueHadHandled.size()!=0){
            int num = MyMessageQueue.requestQueueHadHandled.size();
            msgNumsArrayList.add(num);
            arrayList.add(MyMessageQueue.requestQueueHadHandled.get(0));
        }


        //按照消息来到的时间进行排序
        Collections.sort(arrayList, new Comparator<ChatMsg>(){

            public int compare(ChatMsg p1, ChatMsg p2) {

                if(p1.getMsgtime() < p2.getMsgtime()){
                    return 1;
                }
                if(p1.getMsgtime() == p2.getMsgtime()){
                    return 0;
                }
                return -1;
            }
        });

       adapterNotifaction.notifyDataSetChanged();
        changemsgnotreadnum();

    }

    public void onResume(){
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MY);
        getActivity().registerReceiver(receiver,intentFilter);

        initList();
        changemsgnotreadnum();

        //Toast.makeText(getContext(),MyMessageQueue.requestQueueNotHandle.size()+"  啊实打实的",Toast.LENGTH_SHORT).show();
    }

    public void onDestroy(){
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);

        if(freshNotiFicationTimer!=null){
            freshNotiFicationTimer.cancel();
        }

    }

    public void onStart(){
        super.onStart();
//        initList();
//        changemsgnotreadnum();

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.to_contacts:
                Intent intent=new Intent(getContext(), ContactsActivity.class);
                startActivity(intent);
                break;
            case R.id.add_friend:
                Intent intent1=new Intent(getContext(), FindFriendOrGroupActivity.class);
                intent1.putExtra("is","people");
                startActivity(intent1);
                default:
                    break;
        }
    }

    public static void changemsgnotreadnum(){

        int msgsum = 0;
        for(String id: MyMessageQueue.chatQueueNotRead.keySet()){

            msgsum+=MyMessageQueue.chatQueueNotRead.get(id).size();
        }
        if(MyMessageQueue.requestQueueNotHandle!=null&&MyMessageQueue.requestQueueNotHandle.size()!=0) {
            for(int i=0;i<MyMessageQueue.requestQueueNotHandle.size();++i){
                if(!MyMessageQueue.requestQueueNotHandle.get(i).getHandleRs().equals("hadread")){
                    ++msgsum;
                }
            }
        }
        msgsum+=MyMessageQueue.dutyQueueNotRead.size();

        if(msgsum==0){
            MainActivity.bar.disMissNum(1);
        }else{
            MainActivity.bar.showNum(1,msgsum);
        }

    }
    BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(MY)){
                String type = intent.getStringExtra("type");
                if(type.equals("freNotiUserIc")){
                    if(freshNotiFicationTimer!=null){
                        freshNotiFicationTimer.cancel();
                    }
                    smartRefreshLayout.finishRefresh();
                    adapterNotifaction.notifyDataSetChanged();
                    //initList();
                    //changemsgnotreadnum();

                }else if(type.equals("freshNotificationRs")){
                    if(freshNotiFicationTimer!=null){
                        freshNotiFicationTimer.cancel();
                    }
                    smartRefreshLayout.finishRefresh();

                    //获得某群头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
                }else if(type.equals("GetNotiIconOfGroup")){
                    adapterNotifaction.notifyDataSetChanged();

                   //获得某用户头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求 //
                }else if(type.equals("GetNotiIconOfUser")){
                    adapterNotifaction.notifyDataSetChanged();

                //以经有管理员对加群请求处理了
                }else if(type.equals("someoneHasHandledAddGroup")){
                    initList();
                    changemsgnotreadnum();
                    adapterNotifaction.notifyDataSetChanged();

                    //获得所有的请求信息
                }else if(type.equals("getRequestRs")){
                    initList();
                    changemsgnotreadnum();

                    //删除好友  刷新通知界面（删除聊天框）
                }else if(type.equals("deleteFriendResult")){
                    initList();
                    adapterNotifaction.notifyDataSetChanged();
                    changemsgnotreadnum();

                    //退群
                }else if(type.equals("exitGroupResult")){
                    initList();
                    adapterNotifaction.notifyDataSetChanged();
                    changemsgnotreadnum();
                }

                else{
//                    smartRefreshLayout.finishRefresh();
                    initList();
                    adapterNotifaction.notifyDataSetChanged();
                    changemsgnotreadnum();
                }


            }


        }
    };

    public void sendChatMsg(View view,String msg) {
        NotificationManager manager = (NotificationManager)getActivity(). getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(getContext(), "chat")
                .setContentTitle("收到一条聊天消息")
                .setContentText(msg)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setAutoCancel(true)
                .build();
        manager.notify(1, notification);
    }




}
