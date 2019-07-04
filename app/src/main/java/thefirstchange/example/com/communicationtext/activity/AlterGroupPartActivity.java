package thefirstchange.example.com.communicationtext.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.adapter.AterGroupPartAdapter;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.util.MyDialog;

public class AlterGroupPartActivity extends BaseForCloseActivity implements View.OnClickListener{


    private ImageView finishSelf;
    private EditText myGroupPart;
    private TextView savePart;
    private TextView alter_part_corppart_tv;

    private int groupId;
    private String partname;   //自己现在是在哪一个部室

    private RecyclerView recyclerView;
    private AterGroupPartAdapter aterGroupPartAdapter;
    private LinearLayoutManager linearLayoutManager;

    public Timer timer = new Timer();
    private static final String MY="thefirstchange.example.com.communicationtext.ALTERGROUPPART";

    private boolean iswrong = false;    //是不是由 系统错误，请稍后再试 造成

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_group_part);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);

        Intent intent=getIntent();
        groupId=intent.getIntExtra("groupid",-1);
        partname = intent.getStringExtra("partname");

        if(groupId==-1){
            Toast.makeText(AlterGroupPartActivity.this,"系统错误，请稍后再试!",Toast.LENGTH_SHORT).show();
            iswrong=true;
            finish();
        }else{
            initView();
        }

    }

    private void initView(){
        finishSelf=(ImageView)findViewById(R.id.finish_alter_my_group_part);
        finishSelf.setOnClickListener(this);
        recyclerView=(RecyclerView)findViewById(R.id.alter_group_part_recycler);
        alter_part_corppart_tv = (TextView)this.findViewById(R.id.alter_part_corppart_tv);
        if(partname.equals("")){
            alter_part_corppart_tv.setText("当前所属部室:  无");
        }else{
            alter_part_corppart_tv.setText("当前所属部室:  "+partname);
        }


        String[] strings=null;
        if(StaticAllList.groupList.containsKey(groupId)){
            strings = StaticAllList.groupList.get(groupId).getCorppart();
        }

        if(strings==null){
            Toast.makeText(AlterGroupPartActivity.this,"该社团组织暂无部门!",Toast.LENGTH_SHORT).show();
        }


        aterGroupPartAdapter=new AterGroupPartAdapter(this,groupId,strings,partname);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(aterGroupPartAdapter);


//        myGroupPart =(EditText)findViewById(R.id.write_new_my_group_part);
//        savePart =(TextView)findViewById(R.id.save_my_new_group__part);
//        savePart.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.finish_alter_my_group_part:
                finish();
                break;
//            case R.id.save_my_new_group__part:
//                if(!NetworkUtils.isConnected(AlterGroupPartActivity.this)){
//                    Toast.makeText(AlterGroupPartActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                final String name= myGroupPart.getText().toString().trim();
//                if (name.isEmpty()){
//                    Toast.makeText(AlterGroupPartActivity.this,"部室不能为空",Toast.LENGTH_SHORT).show();
//                }else {
//                    new Thread(){
//                        public void run(){
//                            SendToServer.upGroupPart(PersonalInfo.getPhonenumber(),groupId,name);
//                        }
//                    }.start();
//
//                    timer = new Timer();
//                    timer.schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//
//                            Toast.makeText(AlterGroupPartActivity.this,"服务器繁忙",Toast.LENGTH_SHORT).show();
//                            timer.cancel();
//                        }
//                    },3000,10000);
//
//                }
//
//                break;
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
            if(timer!=null){
                timer.cancel();
            }
        }

        if(aterGroupPartAdapter!=null){
            if(aterGroupPartAdapter.timer!=null){
                aterGroupPartAdapter.timer.cancel();
            }
        }



    }

    BroadcastReceiver MyMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String rs = intent.getStringExtra("rs");
            if(aterGroupPartAdapter!=null){
                if(aterGroupPartAdapter.timer!=null){
                    aterGroupPartAdapter.timer.cancel();
                }
                MyDialog.dismissBottomLoadingDialog();
            }
//            timer.cancel();
            if(rs.equals("ok")){

                Toast.makeText(AlterGroupPartActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(AlterGroupPartActivity.this,"服务器繁忙",Toast.LENGTH_SHORT).show();
            }
        }
    };

}
