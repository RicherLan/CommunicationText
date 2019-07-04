package thefirstchange.example.com.communicationtext.course.supercouesrdemo2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.course.adapter.SchDutyUserInfoAdapter;
import thefirstchange.example.com.communicationtext.course.object.ListViewSchDutyUserInfo;


public class SchDutyUserInfoActivity extends BaseForCloseActivity implements View.OnClickListener{

    private LinearLayoutManager linearLayoutManager;
    private SchDutyUserInfoAdapter schDutyUserInfoAdapter;
    private RecyclerView ruxueyearlistview;

    private ImageView schduty_userinfo_back_tv;

    private Vector<ListViewSchDutyUserInfo> schuserlist = new Vector<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schduty_userinfo);
        Intent intent = getIntent();
        int index = intent.getIntExtra("index",-1);
        if(index!=-1&&index<CourseAndScore.clientArrangements.size()){
            Vector<String > phs = CourseAndScore.clientArrangements.get(index).getPhs();
            Vector<String > names = CourseAndScore.clientArrangements.get(index).getNames();
            Vector<String > poss = CourseAndScore.clientArrangements.get(index).getPoss();
            for(int i=0;i<phs.size();++i){
                ListViewSchDutyUserInfo listViewSchDutyUserInfo = new ListViewSchDutyUserInfo();
                listViewSchDutyUserInfo.setPh(phs.get(i));
                listViewSchDutyUserInfo.setName(names.get(i));
                listViewSchDutyUserInfo.setPos(poss.get(i));

                schuserlist.add(listViewSchDutyUserInfo);
            }
        }
        initview();
        initevent();
    }

    public void initview(){

        schduty_userinfo_back_tv = (ImageView)findViewById(R.id.schduty_userinfo_back_tv) ;

        ruxueyearlistview = (RecyclerView)findViewById(R.id.schduty_userinfo_recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ruxueyearlistview.setLayoutManager(linearLayoutManager);

        schDutyUserInfoAdapter = new SchDutyUserInfoAdapter(SchDutyUserInfoActivity.this,schuserlist);
        ruxueyearlistview.setAdapter(schDutyUserInfoAdapter);
//        //添加Android自带的分割线
//        ruxueyearlistview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    public void initevent(){
        schduty_userinfo_back_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.schduty_userinfo_back_tv:
                finish();
                break;

        }

    }
}
