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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.adapter.DutyNotiRecyclerAdapter;
import thefirstchange.example.com.communicationtext.course.database.dao.StuDao;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.CourseAndScore;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.SchDutyActivity;
import thefirstchange.example.com.communicationtext.fragment.NotificationFragment;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.util.MyDialog;

/*
    值班通知  点进去的显示某组织所有值班记录的活动
 */
public class DutyNotiRecyViewActivity  extends BaseForCloseActivity implements View.OnClickListener{

    private ImageView finishSelf;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DutyNotiRecyclerAdapter applicationAdapter;
    private List<ChatMsg> list = new ArrayList<>();
    private int groupid;

    private static final String MY="thefirstchange.example.com.communicationtext.DUTYNOTIRECYVIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dutynoti_recyview);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);

        Intent intent = getIntent();
        groupid = intent.getIntExtra("groupid",-1);
        if(groupid==-1){
            finish();
        }
        loadFromDB();
        initlist();
        initView();

    }

    public void loadFromDB(){
        if(StaticAllList.chatRecordDao!=null){
            StaticAllList.chatRecordDao.getSchDutyFromDB();
        }else{
            MainActivity.initChatRecorddb();
            StaticAllList.chatRecordDao.getSchDutyFromDB();
        }
    }

    private void initlist(){
        list.clear();

        if(MyMessageQueue.dutyQueueNotRead.containsKey(groupid)){
            for(int i=MyMessageQueue.dutyQueueNotRead.get(groupid).size()-1;i>=0;--i){
                list.add(MyMessageQueue.dutyQueueNotRead.get(groupid).get(i));
            }
        }

        if(MyMessageQueue.dutyQueueHadRead.containsKey(groupid)){
            for(int i=MyMessageQueue.dutyQueueHadRead.get(groupid).size()-1;i>=0;--i){
                list.add(MyMessageQueue.dutyQueueHadRead.get(groupid).get(i));
            }
        }

//        NotificationFragment.changemsgnotreadnum();

    }

    private void initView(){
        finishSelf=(ImageView)findViewById(R.id.finish_dutynotiRV);
        finishSelf.setOnClickListener(this);

        recyclerView=(RecyclerView)findViewById(R.id.duty_noti_recycler);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        applicationAdapter=new DutyNotiRecyclerAdapter(this,list);
        recyclerView.setAdapter(applicationAdapter);


    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.finish_dutynotiRV:
                finish();
                break;
        }
    }

    public void initGroupDuty(){
        for(int i=0;i<CourseAndScore.clientArrangements.size();++i){
            if(!CourseAndScore.groupDuty.contains(CourseAndScore.clientArrangements.get(i).groupid)){
                CourseAndScore.groupDuty.add(CourseAndScore.clientArrangements.get(i).groupid);
            }
        }
    }

    public void onResume(){
        super.onResume();
//        initlist();
        applicationAdapter.notifyDataSetChanged();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MY);
        registerReceiver(receiver,intentFilter);


    }



    public void onDestroy(){
        super.onDestroy();
     unregisterReceiver(receiver);
     if(applicationAdapter!=null) {

         if (applicationAdapter.timer != null) {
             applicationAdapter.timer.cancel();
         }
     }
    }

    BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MY)){

                String type = intent.getStringExtra("type");
                if(type.equals("DutySche")){
                    if(applicationAdapter!=null){
                        if( applicationAdapter.timer!=null){
                            applicationAdapter.timer.cancel();
                        }
                        MyDialog.dismissBottomLoadingDialog();

                        String rs = intent.getStringExtra("rs");
                        if(rs.equals("error")){
                            Toast.makeText(DutyNotiRecyViewActivity.this,"服务器繁忙,请稍后再试!",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        final int groupid = intent.getIntExtra("groupid",-1);
                        if(groupid!=-1){

                            final int dnid = intent.getIntExtra("dnid",-1);
                            if(dnid==-1){
                                return;
                            }

                            CourseAndScore.duty_groupid = groupid;
                            initGroupDuty();

                            NotificationFragment.changemsgnotreadnum();


                            Intent intent1 = new Intent(DutyNotiRecyViewActivity.this, SchDutyActivity.class);
                            intent1.putExtra("from","DutyNoti");
                            startActivity(intent1);

                            initlist();
                            applicationAdapter.notifyDataSetChanged();

                                    SendToServer.readDutyNoti(dnid,groupid);

                                    StuDao  stuDao = StuDao.getInstance(DutyNotiRecyViewActivity.this);
                                    stuDao.removeSchduleBygroupid(groupid);
                                    stuDao.saveStuSchList( CourseAndScore.clientArrangementsTemp);
//
                        }
                    }
                }

            }
        }
    };

}
