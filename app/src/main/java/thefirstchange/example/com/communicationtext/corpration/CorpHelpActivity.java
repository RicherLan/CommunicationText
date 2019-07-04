package thefirstchange.example.com.communicationtext.corpration;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;

public class CorpHelpActivity extends BaseForCloseActivity implements View.OnClickListener {

    ImageView corphelp_back_tv;
    TextView corphelp_text_view;

    String tip = "提示\n    账号为社团或组织的唯一账号，主要用于社团或组织的动态发表和安排值班\n   " +
            "群号为社团或组织的成员群，主要用于社团组织的管理以及成员交流\n    " +
            "社团或组织进行安排值班时,必须登陆社团组织的官方账号,官方账号安排值班前须初始化社团组织信息：登陆后，进入自己所属的群聊，进入最后的社团管理功能，" +
            "在此功能中进行社团或组织的部门添加修改删除、社团组织成员的职位任命(注意，主席不会被安排到值班,部长比较特殊:由排班者决定)、" +
            "修改学年和学期(注意，安排值班时，根据社团组织设定的学年和学期与社团组织成员导入到系统的课程表时间进行匹配)、导入统计功能查看那些成员" +
            "没有进行课程表的导入(注意没有导入课程表到系统的成员不会被安排到值班中,所有应督促成员导入课表到系统)\n    " +
            "社团或组织任意成员可以进入社团组织群界面，进行查看空课更能，该功能可以查看指定时间有空课的所有该社团组织的学生，便于社团组织进行活动安排人员";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corphelp);

        initView();
        initEvent();
    }

    private void initView(){
        corphelp_back_tv = (ImageView)findViewById(R.id.corphelp_back_tv);
        corphelp_text_view  = (TextView)findViewById(R.id.corphelp_text_view);
        corphelp_text_view.setText(tip);
    }

    private void initEvent(){
        corphelp_back_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.corphelp_back_tv:
                finish();
                break;
        }
    }
}
