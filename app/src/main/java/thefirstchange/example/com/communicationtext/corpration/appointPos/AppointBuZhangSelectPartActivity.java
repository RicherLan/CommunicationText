package thefirstchange.example.com.communicationtext.corpration.appointPos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.corpration.appointPos.adapter.AppointBuZhangSelectPartAdapter;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.UserGroup;

/*
    任命部长  首先选择部门
 */
public class AppointBuZhangSelectPartActivity extends BaseForCloseActivity implements View.OnClickListener{

    private ImageView finish_iv;

    private RecyclerView recyclerView;
    private AppointBuZhangSelectPartAdapter pArtListCorprationAdapter;
    private LinearLayoutManager linearLayoutManager;

    int groupid;
    UserGroup userGroup = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_buzhang_selectpart);
        Intent intent = getIntent();
        groupid = intent.getIntExtra("groupid",-1);

        if(StaticAllList.groupList.containsKey(groupid)){
            userGroup = StaticAllList.groupList.get(groupid);
        }
        if(groupid==-1||userGroup==null){
            Toast.makeText(AppointBuZhangSelectPartActivity.this,"系统错误,请稍后再试!",Toast.LENGTH_SHORT).show();
            finish();
        }

        initview();
        initevent();
    }

    public void initview(){

        finish_iv = (ImageView)this.findViewById(R.id.finish_appoint_buzhang_selectpart_iv);

        recyclerView = (RecyclerView)this.findViewById(R.id.appoint_buzhang_selectpart_recyclerview);
        pArtListCorprationAdapter=new AppointBuZhangSelectPartAdapter(this,groupid,userGroup.getCorppart());
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(pArtListCorprationAdapter);

    }
    public void initevent(){
        finish_iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.finish_appoint_buzhang_selectpart_iv:
                finish();
                break;
        }

    }
}
