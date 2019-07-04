package thefirstchange.example.com.communicationtext.corpration;

/*
        社团组织的群的最高执行者增加或修改部室名字
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.corpration.corpAdapter.AddAlterCorpPartAdapter;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CorpSendToServer;
import thefirstchange.example.com.communicationtext.util.DensityUtil;
import thefirstchange.example.com.communicationtext.util.DensityUtiltwo;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

/*
    部室管理   增删改部室
 */
public class AddAlterCorpPartActivity extends BaseForCloseActivity implements View.OnClickListener{

    private static final String MY="thefirstchange.example.com.communicationtext.ADDALTERCORPPART";

    private ImageView finish_addalter_corppart;
    private TextView add_corppart_tv;
    private RecyclerView recyclerView;
    private AddAlterCorpPartAdapter addAlterCorpPartAdapter;
    private LinearLayoutManager linearLayoutManager;

    private EditText add_partname_dialog_et;

    private int groupId;
    String[] strings=null;       //部门的名字

    private boolean iswrong = false;

    private Timer addCorppartTimer = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addalter_corppart);

        Intent intent=getIntent();
        groupId=intent.getIntExtra("groupid",-1);
        if(groupId==-1){
            Toast.makeText(AddAlterCorpPartActivity.this,"系统错误，请稍后再试!",Toast.LENGTH_SHORT).show();
            iswrong=true;
            finish();
        }

        initview();
        initevent();
    }

    public void initview(){
        finish_addalter_corppart = (ImageView)this.findViewById(R.id.finish_addalter_corppart_iv);
        add_corppart_tv = (TextView)this.findViewById(R.id.addalter_add_corppart_tv);
        recyclerView = (RecyclerView)this.findViewById(R.id.addalter_corppart_recycler);


       getCorpPart();
//        if(strings==null){
//            Toast.makeText(AddAlterCorpPartActivity.this,"系统错误,请稍后重试!",Toast.LENGTH_SHORT).show();
//            iswrong=true;
//            finish();
//        }

        addAlterCorpPartAdapter=new AddAlterCorpPartAdapter(this,groupId,strings);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(addAlterCorpPartAdapter);

    }

    public void initevent(){
        finish_addalter_corppart.setOnClickListener(this);
        add_corppart_tv.setOnClickListener(this);

    }

    private void getCorpPart(){
        if(StaticAllList.groupList.containsKey(groupId)){
            strings = StaticAllList.groupList.get(groupId).getCorppart();
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.finish_addalter_corppart_iv:
                finish();
                break;
            case R.id.addalter_add_corppart_tv:
                addCorpPartNameDialog();
                break;

        }

    }

    protected void onResume(){
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(MY);
        registerReceiver(MyMessageReceiver, filter);
//        Intent intent=new Intent("thefirstchange.example.com.communicationtext.MAIN_LOOK");
//        sendBroadcast(intent);
    }

    protected void onDestroy(){

        super.onDestroy();
        if(!iswrong){
            unregisterReceiver(MyMessageReceiver);
        }

        if(addCorppartTimer!=null){
            addCorppartTimer.cancel();
        }

        if(addAlterCorpPartAdapter!=null){
            if(addAlterCorpPartAdapter.deletetimer!=null){
                addAlterCorpPartAdapter.deletetimer.cancel();
            }
            if(addAlterCorpPartAdapter.altertimer!=null){
                addAlterCorpPartAdapter.altertimer.cancel();
            }
        }


    }

    BroadcastReceiver MyMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

           String type = intent.getStringExtra("type");
            if(type.equals("addCorpPartRs")){   //添加部门

                String rs = intent.getStringExtra("rs");
                int gid = intent.getIntExtra("gid",-1);
                if(gid==groupId){
                    if(addCorppartTimer!=null){
                        addCorppartTimer.cancel();
                    }
                  MyDialog.dismissBottomLoadingDialog();

                    if(rs.equals("ok")){
                      getCorpPart();
                        addAlterCorpPartAdapter=new AddAlterCorpPartAdapter(AddAlterCorpPartActivity.this,groupId,strings);
                        recyclerView.setAdapter(addAlterCorpPartAdapter);
                        Toast.makeText(AddAlterCorpPartActivity.this,"添加成功!",Toast.LENGTH_SHORT).show();

                        //finish();
                    }else if(rs.equals("hasexist")){
                        Toast.makeText(AddAlterCorpPartActivity.this,"添加失败,该部门已存在!",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AddAlterCorpPartActivity.this,"服务器繁忙,请稍后再试!",Toast.LENGTH_SHORT).show();
                    }
                }

            }else if(type.equals("deleteCorpPartRs")){   //删除部门

                String rs = intent.getStringExtra("rs");
                int gid = intent.getIntExtra("gid",-1);
                if(gid==groupId){
                    if(addAlterCorpPartAdapter!=null){
                        if(addAlterCorpPartAdapter.deletetimer!=null){
                            addAlterCorpPartAdapter.deletetimer.cancel();
                        }
                       MyDialog.dismissBottomLoadingDialog();
                    }
                    MyDialog.dismissBottomLoadingDialog();

                    if(rs.equals("ok")){
                        getCorpPart();
                        addAlterCorpPartAdapter=new AddAlterCorpPartAdapter(AddAlterCorpPartActivity.this,groupId,strings);
                        recyclerView.setAdapter(addAlterCorpPartAdapter);
                        Toast.makeText(AddAlterCorpPartActivity.this,"删除成功!",Toast.LENGTH_SHORT).show();

                       // finish();
                    }else if(rs.equals("notexist")){
                        Toast.makeText(AddAlterCorpPartActivity.this,"删除失败,该部门不存在!",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AddAlterCorpPartActivity.this,"服务器繁忙,请稍后再试!",Toast.LENGTH_SHORT).show();
                    }
                }

            }else if(type.equals("alterCorpPartRs")){   //修改部门名称
                String rs = intent.getStringExtra("rs");
                int gid = intent.getIntExtra("gid",-1);
                if(gid==groupId){
                    if(addAlterCorpPartAdapter!=null){
                        if(addAlterCorpPartAdapter.altertimer!=null){
                            addAlterCorpPartAdapter.altertimer.cancel();
                        }
                        MyDialog.dismissBottomLoadingDialog();
                    }
                    MyDialog.dismissBottomLoadingDialog();

                    if(rs.equals("ok")){
                        getCorpPart();
                        addAlterCorpPartAdapter=new AddAlterCorpPartAdapter(AddAlterCorpPartActivity.this,groupId,strings);
                        recyclerView.setAdapter(addAlterCorpPartAdapter);
                        Toast.makeText(AddAlterCorpPartActivity.this,"修改成功!",Toast.LENGTH_SHORT).show();

                       // finish();
                    }else if(rs.equals("notexist")){
                        Toast.makeText(AddAlterCorpPartActivity.this,"修改失败,该部门不存在!",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AddAlterCorpPartActivity.this,"服务器繁忙,请稍后再试!",Toast.LENGTH_SHORT).show();
                    }
                }
            }


//            if(rs.equals("ok")){
//
//                Toast.makeText(AlterGroupPartActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
//                finish();
//            }else{
//                Toast.makeText(AlterGroupPartActivity.this,"服务器繁忙",Toast.LENGTH_SHORT).show();
//            }
        }
    };


    private void addCorpPartNameDialog(){

        TextView title = new TextView(AddAlterCorpPartActivity.this);
        title.setText("请输入新部室名称");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(AddAlterCorpPartActivity.this.getResources().getColor(R.color.black));
        title.setTextSize(21);
//        title.setTextColor(mContext.getResources().getColor(R.color.DeepSkyBlue1));

        AlertDialog.Builder builder = new AlertDialog.Builder(AddAlterCorpPartActivity.this,R.style.AlertDialog);
        builder.setCustomTitle(title);
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View v = LayoutInflater.from(AddAlterCorpPartActivity.this).inflate(R.layout.altercorppartname_dialog, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(v);



        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String newname = add_partname_dialog_et.getText().toString().trim();
                if(newname.equals("")){
                    Toast.makeText(AddAlterCorpPartActivity.this,"部室名称不允许为空!",Toast.LENGTH_SHORT).show();
                }else{

                    if(StaticAllList.groupList.get(groupId).getCorppart()!=null){
                        for(String part:StaticAllList.groupList.get(groupId).getCorppart()){
                            if(newname.equals(part)){
                                Toast.makeText(AddAlterCorpPartActivity.this,"该部室名已经存在,添加失败!",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }

                    showAddPartDialog(newname);
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_corner);
        //设置大小
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = DensityUtil.dip2px(AddAlterCorpPartActivity.this,300) ;
        layoutParams.height =  DensityUtil.dip2px(AddAlterCorpPartActivity.this,155);
        dialog.getWindow().setAttributes(layoutParams);
        add_partname_dialog_et = (EditText) dialog.getWindow().findViewById(R.id.alter_partname_dialog_et);


    }

    private void showAddPartDialog(final String name){
        final Dialog bottomDialog = new Dialog(AddAlterCorpPartActivity.this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(AddAlterCorpPartActivity.this).inflate(R.layout.dialog_delete_alarm, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = AddAlterCorpPartActivity.this.getResources().getDisplayMetrics().widthPixels - DensityUtiltwo.dp2px(AddAlterCorpPartActivity.this, 90f);
        params.bottomMargin = DensityUtiltwo.dp2px(AddAlterCorpPartActivity.this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        TextView yesText=contentView.findViewById(R.id.yes_delete);
        TextView infoText=contentView.findViewById(R.id.delete_alarm_info);
        infoText.setText("确定添加?");

        yesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                if(!NetworkUtils.isConnected(AddAlterCorpPartActivity.this)){

//                    Handler handler = new Handler(Looper.getMainLooper());
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
                            Toast.makeText(AddAlterCorpPartActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
// }
//                    });
                    return;
                }

                MyDialog.showBottomLoadingDialog(AddAlterCorpPartActivity.this);

                CorpSendToServer.addCorpPart(groupId,name);


                addCorppartTimer = new Timer();
                addCorppartTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    MyDialog.dismissBottomLoadingDialog();
                                    Toast.makeText(AddAlterCorpPartActivity.this, "服务器繁忙,请稍后再试!", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    },50000);

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


}
