package thefirstchange.example.com.communicationtext.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.adapter.SearchEmptyCoursePeopleAdapter;
import thefirstchange.example.com.communicationtext.service.MessageTemp;

/*
    社团组织获得   获得某几节课都有空的人
 */
public class SearchEmptyCoursePeopleListActivity extends BaseForCloseActivity implements View.OnClickListener{

    private final static String MY="thefirstchange.example.com.communicationtext.SEARCHEMPTYCOURSEPEOPLELIST";

    private LinearLayoutManager linearLayoutManager;
    private SearchEmptyCoursePeopleAdapter schDutyUserInfoAdapter;
    private RecyclerView search_ecp_list_recyclerview;

    private ImageView search_ecp_list_back_tv;
    private int groupid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchemptycourse_people_list);

        Intent intent = getIntent();
        groupid = intent.getIntExtra("groupid",-1);
        if(groupid==-1){
            Toast.makeText(SearchEmptyCoursePeopleListActivity.this,"系统错误,请稍后重试!",Toast.LENGTH_SHORT).show();
            finish();
        }

        initview();
        initevent();
    }

    public void initview(){

        search_ecp_list_back_tv = (ImageView)findViewById(R.id.search_ecp_list_back_tv) ;

        search_ecp_list_recyclerview = (RecyclerView)findViewById(R.id.search_ecp_list_recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        search_ecp_list_recyclerview.setLayoutManager(linearLayoutManager);

        schDutyUserInfoAdapter = new SearchEmptyCoursePeopleAdapter(SearchEmptyCoursePeopleListActivity.this,groupid, MessageTemp.userCorps);
        search_ecp_list_recyclerview.setAdapter(schDutyUserInfoAdapter);
        //添加Android自带的分割线
        search_ecp_list_recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    public void initevent(){
        search_ecp_list_back_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.search_ecp_list_back_tv:
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
//        if(!iswrong){
            unregisterReceiver(broadcastReceiver);
//        }

    }

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type = intent.getStringExtra("type");

                //群成员列表的  成员的头像到了
            if(type.equals("getGroupUserIconRs")){
                schDutyUserInfoAdapter.notifyDataSetChanged();
            }
        }

    };

}
