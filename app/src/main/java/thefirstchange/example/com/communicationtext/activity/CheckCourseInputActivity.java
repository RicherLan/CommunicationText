package thefirstchange.example.com.communicationtext.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.adapter.NotFinishPeopleAdapter;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.service.MessageTemp;

public class CheckCourseInputActivity extends BaseForCloseActivity {
    private TextView notFinishPeopleTex;
    private TextView finishPeopleTex;
    private RecyclerView notFinishPeopleRecycler;
    private ImageView finishSelf;
    private LinearLayoutManager linearLayoutManager;
    private NotFinishPeopleAdapter notFinishPeopleAdapter;

    int groupid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_course_input);
        Intent intent = getIntent();
        groupid=intent.getIntExtra("groupid",-1);
        if(groupid==-1||!StaticAllList.groupList.containsKey(groupid)){
            Toast.makeText(CheckCourseInputActivity.this,"系统错误,请稍后再试!",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            initView();
        }

    }

    private void initView() {
        notFinishPeopleTex=(TextView)findViewById(R.id.not_finish_people_text);
        finishPeopleTex=(TextView)findViewById(R.id.finish_people_text);

        int sumpeople = StaticAllList.groupList.get(groupid).getUsernum();
        if(MessageTemp.corpUserNotLoadCourses!=null){
            notFinishPeopleTex.setText(MessageTemp.corpUserNotLoadCourses.size()+"");
            finishPeopleTex.setText(sumpeople-MessageTemp.corpUserNotLoadCourses.size()+"");
        }else{
            finishPeopleTex.setText(sumpeople+"");
        }

        notFinishPeopleRecycler=(RecyclerView)findViewById(R.id.not_finish_people_recycler);
        finishSelf=(ImageView)findViewById(R.id.finish_group_arrange);
        finishSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        linearLayoutManager=new LinearLayoutManager(this);
        notFinishPeopleAdapter=new NotFinishPeopleAdapter(this,MessageTemp.corpUserNotLoadCourses);
        notFinishPeopleRecycler.setLayoutManager(linearLayoutManager);
        notFinishPeopleRecycler.setAdapter(notFinishPeopleAdapter);

    }
}
