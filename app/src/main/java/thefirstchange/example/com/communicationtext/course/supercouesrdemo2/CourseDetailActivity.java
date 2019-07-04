package thefirstchange.example.com.communicationtext.course.supercouesrdemo2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.course.object.Course;


public class CourseDetailActivity extends BaseForCloseActivity implements View.OnClickListener{

    private TextView course_name_tv;
    private TextView course_college_tv;
    private TextView course_place_tv;
    private TextView course_zhoushu_tv;
    private TextView course_jieshu_tv;
    private TextView course_teachername_tv;
    private ImageView coursedetial_back_tv;


    private String name;
    private String college;
    private String place;
    private String zhoushu;
    private String jieshu;
    private String teachername;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursedetail);

        Intent intent = getIntent();
        int index = intent.getIntExtra("index",-1);
        if(index==-1){
            finish();
        }
        Course course = CourseAndScore.courses.get(index);
        name = course.getCN();
        college = course.getCC();
        place = course.getCP();
        zhoushu = course.getCT1();
        jieshu = course.getCT2()+" "+course.getCT3();
        teachername = course.getCTN();


        init();
        initevent();

    }

    public void init(){
       course_name_tv = this.findViewById(R.id.course_name_tv);
       course_college_tv  = this.findViewById(R.id.course_college_tv);
       course_place_tv  = this.findViewById(R.id.course_place_tv);
       course_zhoushu_tv  = this.findViewById(R.id.course_zhoushu_tv);
       course_jieshu_tv  = this.findViewById(R.id.course_jieshu_tv);
       course_teachername_tv  = this.findViewById(R.id.course_teachername_tv);
        coursedetial_back_tv = findViewById(R.id.coursedetial_back_tv);

       course_name_tv.setText(name);
       course_college_tv.setText("校区  " + college);
       course_place_tv.setText("地点  " +place);
       course_zhoushu_tv.setText("周数  " +zhoushu);
       course_jieshu_tv.setText("节数  " +jieshu);
       course_teachername_tv.setText("教师  " +teachername);


    }
    public void initevent(){
        coursedetial_back_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.coursedetial_back_tv:
                finish();
                break;
        }

    }
}
