package thefirstchange.example.com.communicationtext.course.supercouesrdemo2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.course.adapter.ClassRoomAdapter;

/*
    空教室查询结果
 */
public class SearchEmptyClassroomResultActivity extends BaseForCloseActivity implements View.OnClickListener{

    private ImageView back;
    private TextView emptyclassroom_buildname;
    private TextView emptyclassroom_weekandday;
    private TextView emptyclassroom_jieshu;

    private ClassRoomAdapter adapter;
    private RecyclerView recyclerView;

    int week;
    int day;
    String jieshu;
    String buildname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchemptyclassroom_result);

        Intent intent = getIntent();
        week = intent.getIntExtra("week",-1);
        day = intent.getIntExtra("day",-1);
        jieshu = intent.getStringExtra("jieshu");
        buildname = intent.getStringExtra("buildname");

        initview();
        initEvent();

    }

    private void initview(){
          back = (ImageView)findViewById(R.id.emptyclassroom_back_iv);
          emptyclassroom_buildname = (TextView)findViewById(R.id.emptyclassroom_buildname);
          emptyclassroom_weekandday = (TextView)findViewById(R.id.emptyclassroom_weekandday);
          emptyclassroom_jieshu= (TextView)findViewById(R.id.emptyclassroom_jieshu);
        emptyclassroom_buildname.setText(buildname);
        emptyclassroom_weekandday.setText("第"+week+"周   星期"+day);
        emptyclassroom_jieshu.setText("节数:  "+jieshu);

        recyclerView=(RecyclerView)findViewById(R.id.emptyclassroom_recycle_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new ClassRoomAdapter(this, CourseAndScore.classRooms);
        recyclerView.setAdapter(adapter);
    }

    private void initEvent(){
        back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.emptyclassroom_back_iv:

                finish();
                break;
        }
    }
}
