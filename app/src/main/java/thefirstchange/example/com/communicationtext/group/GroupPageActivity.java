package thefirstchange.example.com.communicationtext.group;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.AlterGroupNameActivity;
import thefirstchange.example.com.communicationtext.activity.AlterGroupPartActivity;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.activity.MoreGroupInfoOne;
import thefirstchange.example.com.communicationtext.activity.SearchEmptyCourePeopleActivity;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.CorpLoadCourseActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.UserGroup;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.DensityUtiltwo;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.MyFileTools;
import thefirstchange.example.com.communicationtext.util.MyViewTools;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class GroupPageActivity extends BaseForCloseActivity implements View.OnClickListener{
    private static final String MY="thefirstchange.example.com.communicationtext.GROUPPAGE";
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView groupIcon;

    private RelativeLayout toAlterMyName;
    private RelativeLayout toAlterMyPart;

    private TextView thisGroupId;
    private RelativeLayout toEmptyCourseList;
    private RelativeLayout toPeopleList;
    private RelativeLayout inputCourseRel;
    private RelativeLayout toArrangeGroup;
    private RelativeLayout toAlterMyPartPos;
    private RelativeLayout group_page_moreinfo_layout;

    private TextView groupidTV;
    private TextView myGroupName;
    private TextView exitGroup;
    private TextView corpname;
    private TextView corppartpos;

    private int groupId;
    private String grouptype;    //群类型
    String groupname;
    String groupnickname;
    String grouppos;           //群主或者管理员，普通用户

    Timer exitTimer = new Timer();

    boolean iswrong = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);
        Intent intent=getIntent();
        groupId=intent.getIntExtra("groupid",-1);
        if(groupId==-1 ||!StaticAllList.groupList.containsKey(groupId)){
            iswrong=true;
            Toast.makeText(GroupPageActivity.this, "系统错误,请稍后再试!", Toast.LENGTH_SHORT).show();
            finish();
        }

        grouptype = StaticAllList.groupList.get(groupId).getGrouptype();

        initView();
        UserGroup userGroup = StaticAllList.groupList.get(groupId);
        String groupicpath = userGroup.getGroupicon();

        groupIcon = (ImageView)findViewById(R.id.group_page_iv);
        groupIcon.setOnClickListener(this);
        //groupIcon.setImageBitmap(ObjectService.personalIcon);
        //groupIcon.setImageResource((R.drawable.user_image1));

        MyViewTools.setGroupIcon(groupIcon,groupicpath);

        toolbar=(Toolbar)findViewById(R.id.group_page_toolbar);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.group_collapsing_toolbar);

      collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.black));


        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_gray);
        }
        collapsingToolbarLayout.setTitle(groupname);

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);

    }

    private void initView(){

        if(groupId==-1){
            return;
        }
        groupidTV = (TextView)findViewById(R.id.group_page_gid_tv);
        groupidTV.setText("群号        "+groupId);

        groupname = StaticAllList.groupList.get(groupId).getGroupname();
        groupnickname = StaticAllList.groupList.get(groupId).getGroupnickname();

        grouppos = StaticAllList.groupList.get(groupId).getUserauthority();

        toEmptyCourseList=(RelativeLayout)findViewById(R.id.to_empty_course_list);
        toEmptyCourseList.setOnClickListener(this);
        corpname=(TextView)this.findViewById(R.id.group_part_name_tv);
        corppartpos=(TextView)this.findViewById(R.id.group_part_pos_tv);


        if(grouptype.contains("社团")){
            if(StaticAllList.groupList.containsKey(groupId)){
                if(StaticAllList.groupList.get(groupId).getCorppos().contains("主席")){
                    corpname.setText("主席");
                }else if(StaticAllList.groupList.get(groupId).getCorppos().contains("官方账号")){
                    corpname.setText("官方账号");
                }else{
                    String part = StaticAllList.groupList.get(groupId).getPart();
                    if(part==null){
                        part="";
                    }
                    corpname.setText(part);
                }
                corppartpos.setText(StaticAllList.groupList.get(groupId).getCorppos());
            }

        }


        group_page_moreinfo_layout = (RelativeLayout)this.findViewById(R.id.group_page_moreinfo_layout);
        group_page_moreinfo_layout.setOnClickListener(this);
        toAlterMyName=(RelativeLayout)findViewById(R.id.to_alter_my_group_name);
        exitGroup = (TextView)findViewById(R.id.exitGroup);
        exitGroup.setOnClickListener(this);
        if(grouptype.contains("普通")){
            if(StaticAllList.groupList.get(groupId).getCreatorid().equals(ObjectService.personalInfo.getPhonenumber())){
                exitGroup.setText("解散群");
            }
        }else if(grouppos.equals("群主")){
            exitGroup.setText("解散群");
        }
        thisGroupId=(TextView)findViewById(R.id.this_group_id);
        thisGroupId.setText(groupId+"");
        toAlterMyName.setOnClickListener(this);
        myGroupName=(TextView)findViewById(R.id.my_group_name);
        myGroupName.setText(groupnickname);
        toAlterMyPart=(RelativeLayout)findViewById(R.id.to_alter_my_group_part);
        toAlterMyPart.setOnClickListener(this);
        toPeopleList=(RelativeLayout)findViewById(R.id.to_people_list);
        toPeopleList.setOnClickListener(this);
        inputCourseRel=(RelativeLayout)findViewById(R.id.input_courese_rel);
        inputCourseRel.setOnClickListener(this);
        toArrangeGroup=(RelativeLayout)findViewById(R.id.to_arrange_group);
        toArrangeGroup.setOnClickListener(this);
        toAlterMyPartPos = (RelativeLayout)this.findViewById(R.id.to_alter_my_group_part_pos);
        toAlterMyPartPos.setOnClickListener(this);

        // 社团群还是普通群    普通群的话   有的内容不显示
        String type = StaticAllList.groupList.get(groupId).getGrouptype();
        if(type.equals("普通群")){
            toAlterMyPart.setVisibility(View.GONE);
            toAlterMyPartPos  .setVisibility(View.GONE);
            toArrangeGroup .setVisibility(View.GONE);
            inputCourseRel .setVisibility(View.GONE);
            toEmptyCourseList.setVisibility(View.GONE);
        }

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.to_alter_my_group_name:

                Intent intent=new Intent(GroupPageActivity.this,AlterGroupNameActivity.class);
                intent.putExtra("groupid",groupId+"");
                intent.putExtra("name",groupnickname);
                startActivity(intent);
                break;
            case R.id.to_alter_my_group_part:
                Intent intent1=new Intent(GroupPageActivity.this,AlterGroupPartActivity.class);
                intent1.putExtra("groupid",groupId);
                intent1.putExtra("partname",corpname.getText().toString().trim());
                startActivity(intent1);
                break;
            case R.id.to_alter_my_group_part_pos:
//                Intent intent6=new Intent(GroupPageActivity.this,AlterCorpPosActivity.class);
//                intent6.putExtra("groupid",groupId);
//                intent6.putExtra("partpos",corppartpos.getText().toString().trim());
//                startActivity(intent6);
                break;
            case R.id.to_empty_course_list:

                Intent intent2=new Intent(GroupPageActivity.this,SearchEmptyCourePeopleActivity.class);
                intent2.putExtra("groupid",groupId);
                startActivity(intent2);
                break;
            case R.id.to_people_list:
                Intent intent3=new Intent(this,AllPeopleShowActivity.class);
                intent3.putExtra("groupid",groupId);
                startActivity(intent3);
                break;
            case R.id.exitGroup:             //退群

                showExitGroupDialog();

                break;
            case R.id.input_courese_rel:

                if(StaticAllList.groupList.containsKey(groupId)){
                    int year = StaticAllList.groupList.get(groupId).year;
                    int xueqi = StaticAllList.groupList.get(groupId).xueqi;
                    Intent intent4=new Intent(getApplicationContext(), CorpLoadCourseActivity.class);

                    intent4.putExtra("year",year);
                    intent4.putExtra("xueqi",xueqi);
                    startActivity(intent4);
                }else{
                    Toast.makeText(GroupPageActivity.this,"系统错误,请稍后再试!",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.to_arrange_group:    //社团管理

                Intent intent4=new Intent(GroupPageActivity.this, GroupArrangeActivity.class);
                intent4.putExtra("groupid",groupId);
                startActivity(intent4);

                break;
            case R.id.group_page_moreinfo_layout:
                Intent intent5 = new Intent(GroupPageActivity.this,MoreGroupInfoOne.class);
                intent5.putExtra("groupid",groupId);
                startActivity(intent5);

                break;


            case R.id.group_page_iv:          //更换群头像
                UserGroup userGroup = StaticAllList.groupList.get(groupId);
                if(userGroup.getCreatorid().equals(ObjectService.personalInfo.getPhonenumber())||userGroup.getCorppos().equals("主席")||userGroup.getCorppos().equals("官方账号")){
                    showChooseIvDialog();
                }

                break;

                default:
                    break;

        }
    }

    protected void onResume(){
        super.onResume();
        groupnickname = StaticAllList.groupList.get(groupId).getGroupnickname();
        myGroupName.setText(groupnickname);

        if(StaticAllList.groupList.containsKey(groupId)&&StaticAllList.groupList.get(groupId).getGrouptype().contains("社团")){
            if(StaticAllList.groupList.get(groupId).getCorppos().equals("主席")){
                corpname.setText("主席");
            }else if(StaticAllList.groupList.get(groupId).getCorppos().contains("官方账号")){
                corpname.setText("官方账号");
            }else{
                corpname.setText(StaticAllList.groupList.get(groupId).getPart());
            }
            corppartpos.setText(StaticAllList.groupList.get(groupId).getCorppos());
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(MY);
        registerReceiver(MyMessageReceiver, filter);
    }

    protected void onDestroy(){

        super.onDestroy();
        if(!iswrong){
            unregisterReceiver(MyMessageReceiver);
        }

        if(exitTimer!=null){
            exitTimer.cancel();
        }

    }

    BroadcastReceiver MyMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MY)) {
                String type = intent.getStringExtra("type");

                if (type.equals("exitGroupRs")) {               //退群   服务器的回执
                    if (exitTimer != null) {
                        exitTimer.cancel();
                    }
                    MyDialog.dismissBottomLoadingDialog();

                    String rs = intent.getStringExtra("rs");
                    int groupid = intent.getIntExtra("gid",-1);
                    if(rs.equals("ok")&&groupId==groupid){

                        Toast.makeText(GroupPageActivity.this, "退群成功!", Toast.LENGTH_SHORT).show();
                       StaticAllList.groupList.remove(groupid);
                       finish();
                    }else{
                        Toast.makeText(GroupPageActivity.this, "服务器繁忙,请稍后再试!", Toast.LENGTH_SHORT).show();

                    }

                }else if(type.equals("ChGroupIcRs")){           //更改群头像

                    int gid = intent.getIntExtra("groupid",-1);
                    if(gid!=groupId){
                        return;
                    }

                    if(changeIconTimer!=null){
                        changeIconTimer.cancel();
                    }
                    MyDialog.dismissBottomLoadingDialog();

                    String rs = intent.getStringExtra("rs");
                    if(rs.equals("ok")){
                        if(head!=null){
                            groupIcon.setImageBitmap(head);// 用ImageButton显示出来

                            setPicToView(head);// 保存在SD卡中

                        }

                        Toast.makeText(GroupPageActivity.this,"修改成功!",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(GroupPageActivity.this,"服务器繁忙,请稍后再试！",Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
    };



    private void showExitGroupDialog(){
        final Dialog bottomDialog = new Dialog(GroupPageActivity.this, R.style.BottomDialog);
        View contentView = LayoutInflater.from((GroupPageActivity.this)).inflate(R.layout.dialog_delete_alarm, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getApplicationContext().getResources().getDisplayMetrics().widthPixels - DensityUtiltwo.dp2px(GroupPageActivity.this, 90f);
        params.bottomMargin = DensityUtiltwo.dp2px(GroupPageActivity.this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        TextView yesText=contentView.findViewById(R.id.yes_delete);
        TextView infoText=contentView.findViewById(R.id.delete_alarm_info);

        if(grouppos.equals("群主")){
            infoText.setText("确认解散该群?");
            bottomDialog.dismiss();
            Toast.makeText(this,"该功能暂未开放!",Toast.LENGTH_SHORT).show();

            return;
        }else{
            infoText.setText("确认退出该群?");
        }
        yesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                if(!NetworkUtils.isConnected(getApplicationContext())){

                    Toast.makeText(GroupPageActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

              MyDialog.showBottomLoadingDialog(GroupPageActivity.this);

                SendToServer.exitGroup(groupId);

                exitTimer = new Timer();
                exitTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        GroupPageActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText( GroupPageActivity.this,"服务器繁忙,请稍后重试!",Toast.LENGTH_SHORT).show();
                                MyDialog.dismissBottomLoadingDialog();
                            }
                        });
                    }
                },30000);

                bottomDialog.dismiss();
            }
        });
        TextView noText=contentView.findViewById(R.id.no_delete);
        noText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });

    }


    private Bitmap head;           // 头像Bitmap
    private Timer changeIconTimer;

    //更换头像
    private void showChooseIvDialog() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_choose_photo, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtiltwo.dp2px(this, 16f);
        params.bottomMargin = DensityUtiltwo.dp2px(this, 16f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        TextView nextToPhotos=(TextView)contentView.findViewById(R.id.next_choose_photo);
        TextView nextToCamera=(TextView)contentView.findViewById(R.id.next_to_camera);
        TextView cancel=(TextView)contentView.findViewById(R.id.cancel_choose_photo);
        nextToPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                //打开文件
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                bottomDialog.dismiss();


            }
        });
        nextToCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                bottomDialog.dismiss();


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:

                if(!NetworkUtils.isConnected(GroupPageActivity.this)){
                    Toast.makeText(GroupPageActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    int c=head.getByteCount();
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        MyDialog.showBottomLoadingDialog(GroupPageActivity.this);

                        SendToServer.ChGroupIc(groupId,head);


                        changeIconTimer = new Timer();
                        changeIconTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                GroupPageActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(GroupPageActivity.this,"服务器繁忙,请稍后再试",Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        },10000);

//                        setPicToView(head);// 保存在SD卡中
//                        iv_head.setImageBitmap(head);// 用ImageButton显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    //保存图像
    private void setPicToView(Bitmap mBitmap) {
        MyFileTools.saveGroupIconAndUpdate(groupId,mBitmap);
    }

}
