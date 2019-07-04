package thefirstchange.example.com.communicationtext.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.DateUtils;
import thefirstchange.example.com.communicationtext.util.MyFileTools;
import thefirstchange.example.com.communicationtext.util.MyViewTools;

/*
    别人搜索某群时   进入这个群的介绍界面
 */
public class SearchGroupHomeActivity extends BaseForCloseActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView groupIcon;

    TextView grouphome_gid_tv;
    TextView grouphome_createtime;
    TextView grouphome_grouptype_tv;
    TextView grouphome_groupuser_tv;
    TextView grouphome_addgroup_tv;

    int groupid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchgrouphome);
        Intent intent = getIntent();
        groupid = intent.getIntExtra("groupid",-1);
        if(groupid==-1||ObjectService.groupBasicInfo==null||ObjectService.groupBasicInfo.getGroupid()!=groupid){
            Toast.makeText(SearchGroupHomeActivity.this,"系统错误,请稍后再试!",Toast.LENGTH_SHORT).show();
            finish();
        }else{

            groupIcon = (ImageView)findViewById(R.id.grouphome_page_iv);
            groupIcon.setOnClickListener(this);
            //groupIcon.setImageBitmap(ObjectService.personalIcon);
            //groupIcon.setImageResource((R.drawable.user_image1));
            String groupicpath = MyFileTools.getGroupIconPath(groupid);
            MyViewTools.setGroupIcon(groupIcon,groupicpath);

            toolbar=(Toolbar)findViewById(R.id.grouphome_page_toolbar);
            collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.grouphome_collapsing_toolbar);

            collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.black));


            setSupportActionBar(toolbar);
            ActionBar actionBar=getSupportActionBar();
            if (actionBar!=null){
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_back_gray);
            }
            collapsingToolbarLayout.setTitle(ObjectService.groupBasicInfo.getGroupname());
            initView();
        }

    }

    private void initView(){
        grouphome_gid_tv = (TextView)findViewById(R.id.grouphome_gid_tv);
         grouphome_createtime = (TextView)findViewById(R.id.grouphome_createtime);
         grouphome_grouptype_tv = (TextView)findViewById(R.id.grouphome_grouptype_tv);
         grouphome_groupuser_tv = (TextView)findViewById(R.id.grouphome_groupuser_tv);
         grouphome_addgroup_tv = (TextView)findViewById(R.id.grouphome_addgroup_tv);

        grouphome_gid_tv.setText("群号      "+groupid);
        String timestr = DateUtils.longToStringTime(ObjectService.groupBasicInfo.getCreatetime());
        grouphome_createtime.setText("创建时间  "+timestr);
        if(ObjectService.groupBasicInfo.getGrouptype().contains("普通")){
            grouphome_grouptype_tv.setText("普通群");
        }else{
            grouphome_grouptype_tv.setText("社团组织群");
        }

        grouphome_groupuser_tv.setText(ObjectService.groupBasicInfo.getUsernum()+"人");
        grouphome_addgroup_tv.setVisibility(View.GONE);
    }

    private void initEvent(){

    }

    @Override
    public void onClick(View v) {

    }

}
