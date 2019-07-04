package thefirstchange.example.com.communicationtext.corpration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;

public class CreateCorpResActivity extends BaseForCloseActivity implements View.OnClickListener{


    private ImageView createcorpresback_tv;
    private TextView createcorpres_cunt_et;
    private TextView createcorpres_groupid_et;
    private TextView createcorpres_hint_et;


    private int count;
    private String password;
    private int groupid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createcorpres);

        Intent intent = getIntent();
        count = intent.getIntExtra("count",-1);
        password = intent.getStringExtra("password");
        groupid = intent.getIntExtra("groupid",-1);

        initview();
        initevent();
    }

    public void initview(){

        createcorpresback_tv=findViewById(R.id.createcorpresback_tv);
        createcorpres_cunt_et=findViewById(R.id.createcorpres_cunt_et);
        createcorpres_groupid_et=findViewById(R.id.createcorpres_groupid_et);
        createcorpres_hint_et= findViewById(R.id.createcorpres_hint_et);

        createcorpres_cunt_et.setText("账号： "+count+"       密码："+password);
        createcorpres_groupid_et.setText("群号： "+groupid);


        String tip = "提示\n    账号为社团或组织的唯一账号，主要用于社团或组织的动态发表和安排值班\n   " +
                "群号为社团或组织的成员群，主要用于社团组织的管理以及成员交流\n    " +
                "社团或组织进行安排值班时,必须登陆社团组织的官方账号,官方账号安排值班前须初始化社团组织信息：登陆后，进入自己所属的群聊，进入最后的社团管理功能，" +
                "在此功能中进行社团或组织的部门添加修改删除、社团组织成员的职位任命(注意，主席不会被安排到值班,部长比较特殊:由排班者决定)、" +
                "修改学年和学期(注意，安排值班时，根据社团组织设定的学年和学期与社团组织成员导入到系统的课程表时间进行匹配)、导入统计功能查看那些成员" +
                "没有进行课程表的导入(注意没有导入课程表到系统的成员不会被安排到值班中,所有应督促成员导入课表到系统)\n    " +
                "社团或组织任意成员可以进入社团组织群界面，进行查看空课更能，该功能可以查看指定时间有空课的所有该社团组织的学生，便于社团组织进行活动安排人员";

        createcorpres_hint_et.setText("");

    }

    public void initevent(){

        createcorpresback_tv.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.createcorpresback_tv:

                finish();
                break;
        }

    }
}
