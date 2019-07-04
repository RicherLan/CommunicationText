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

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.adapter.LifeShowNotificationAdapter;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.listener.EndlessRecyclerOnScrollListener;

public class LifeShowNotificationActivity extends BaseForCloseActivity implements View.OnClickListener{

    private static String MY = "thefirstchange.example.com.communicationtext.LIFESHOWNOTIFICATION";
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private LifeShowNotificationAdapter adapter;
    private ImageView finishSelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_show_notification);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);
        initView();
    }
    public void initView(){

        finishSelf=(ImageView)findViewById(R.id.finish_life_show_notification);
        finishSelf.setOnClickListener(this);



        recyclerView=(RecyclerView)findViewById(R.id.life_show_notification_recycler);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new LifeShowNotificationAdapter(this,StaticAllList.dongtaiMsgs);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                adapter.setLoadState(adapter.LOADING);
            }
        });


    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.finish_life_show_notification:
                finish();
                break;
                default:
                    break;
        }
    }


    protected  void onResume(){
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MY);
        registerReceiver(broadcastReceiver,intentFilter);

        /*  先注释   还没做这个功能
        //刷新动态消息
        Vector<Integer> ids = new Vector<>();
        DongtaiUploadThread dongtaiUploadThread = new DongtaiUploadThread("getnewDongtaiMsgIDs",ids);
        dongtaiUploadThread.start();
        */
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MY)){

                String type = intent.getStringExtra("type");
                //请求动态消息    返回动态消息基本信息   不包括图片
                if(type.equals("getDTMsgByIdResult")){



                    //请求动态消息      返回动态消息的动态的第一张图片和内容
                }else if(type.equals("getDTMsgUserIcRs")){


                    //请求动态消息      返回动态消息的制造者的头像
                } else if(type.equals("getDTFirstImAndtextById")){



                    //用户上拉刷新动态消息的页面  就是请求旧的动态消息
                }else if(type.equals("getoldDongtaiMsgIDsResult")){


                }

            }

        }
    };

    public  void onDestroy(){
        super.onDestroy();

        unregisterReceiver(broadcastReceiver);

    }

}
