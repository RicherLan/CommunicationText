package thefirstchange.example.com.communicationtext.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.corpration.PartListOfCorprationActivity;
import thefirstchange.example.com.communicationtext.group.AllPeopleShowActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.UserGroup;
import thefirstchange.example.com.communicationtext.util.DateUtils;

public class MoreGroupInfoOne extends BaseForCloseActivity implements View.OnClickListener{


    private static final String MY="thefirstchange.example.com.communicationtext.MOREGROUPINFOONE";


    private TextView groupNameTe;
    private TextView groupIdTe;
    private TextView createGroupTimeTe;
    private TextView groupCreateTe;
    private TextView findPartTe;
    private TextView desTe;
    private TextView groupusernum;
    private ImageView finishSelf;
    private LinearLayout more_group_info_part_layout;

    int groupid;
    UserGroup userGroup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_group_info_one);
        Intent intent = getIntent();
        groupid = intent.getIntExtra("groupid",-1);
        if(StaticAllList.groupList.containsKey(groupid)){
            userGroup = StaticAllList.groupList.get(groupid);
        }
        if(groupid==1||userGroup==null){
            Toast.makeText(MoreGroupInfoOne.this,"系统错误,请稍后再试!",Toast.LENGTH_SHORT).show();
            finish();
        }
        initView();

    }

    private void initView() {
        finishSelf=(ImageView)findViewById(R.id.finish_more_group_info_one);
        finishSelf.setOnClickListener(this);
        groupNameTe=(TextView)findViewById(R.id.more_group_info_name);
        groupNameTe.setText(userGroup.getGroupname());
        groupIdTe=(TextView)findViewById(R.id.more_group_info_id);
        groupIdTe.setText(userGroup.getGroupid()+"");
        createGroupTimeTe=(TextView)findViewById(R.id.more_group_info_time);
        String time = DateUtils.longToStringTime(userGroup.getCreatetime());
        createGroupTimeTe.setText(time);

        groupusernum = (TextView)this.findViewById(R.id.more_group_info_usernum);
        groupusernum.setText(userGroup.getUsernum()+"人");
        groupusernum.setOnClickListener(this);
        groupCreateTe=(TextView)findViewById(R.id.more_group_info_create);
        groupCreateTe.setText(userGroup.getCreatorid());
        groupCreateTe.setOnClickListener(this);

        more_group_info_part_layout = (LinearLayout)this.findViewById(R.id.more_group_info_part_layout);
        if(!userGroup.getGrouptype().equals("社团群")){
            more_group_info_part_layout.setVisibility(View.GONE);
        }else{
            more_group_info_part_layout.setOnClickListener(this);
            findPartTe=(TextView)findViewById(R.id.more_group_info_part);
            findPartTe.setText(userGroup.getCorppart().length+"个部门(点击查看)");
        }

        desTe=(TextView)findViewById(R.id.more_group_info_de);
        if(userGroup.getGroupintro()==null){
            desTe.setText(" ");
        }else{
            desTe.setText(userGroup.getGroupintro());
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.finish_more_group_info_one:
                finish();
                break;
            case R.id.more_group_info_part_layout:
                Intent intent = new Intent(MoreGroupInfoOne.this, PartListOfCorprationActivity.class);
                intent.putExtra("groupid",groupid);
                startActivity(intent);
                break;

            case R.id.more_group_info_usernum:
                Intent intent3=new Intent(this,AllPeopleShowActivity.class);
                intent3.putExtra("groupid",groupid);
                startActivity(intent3);
                break;
            case R.id.more_group_info_create:

                Intent intent4=new Intent(MoreGroupInfoOne.this,PersonalHomePageActivity.class);
                intent4.putExtra("from","otherpeople");
                intent4.putExtra("id",userGroup.creatorid);
                startActivity(intent4);
                break;
        }
    }
}
