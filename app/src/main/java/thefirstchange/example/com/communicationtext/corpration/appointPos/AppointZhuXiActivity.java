package thefirstchange.example.com.communicationtext.corpration.appointPos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.corpration.appointPos.adapter.AppointPeopleAdapter;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.UserGroup;
import thefirstchange.example.com.communicationtext.gson.UserInGroupInfo;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.MyFileTools;
import thefirstchange.example.com.communicationtext.util.MyViewTools;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

/*
    任命主席
 */
public class AppointZhuXiActivity extends BaseForCloseActivity implements View.OnClickListener,TextWatcher {

    private final static String MY="thefirstchange.example.com.communicationtext.APPOINTZHUXIACTIVITY";
    UserGroup userGroup = null;
    int groupId;
    Vector<UserInGroupInfo> peoplelist;

    private ImageView finish_iv;
    private EditText search_people;
    private TextView appoint_current_people_tv;     //当前的主席是谁
    private RecyclerView recyclerView;
    private AppointPeopleAdapter adapter;

    //当前职位的是谁
    CircleImageView appoint_current_head_iv;
    TextView appoint_current_name;
    TextView appoint_current_part;
    TextView appoint_current_pos;

    private String currentUserPh;


    private Timer getAllUserInfoTimer = new Timer();
    boolean iswrong = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_zhuxi);
        Intent intent=getIntent();
        groupId=intent.getIntExtra("groupid",-1);

        if(groupId==-1|| !StaticAllList.groupList.containsKey(groupId)){
            Toast.makeText(this,"系统错误，请稍后再试!",Toast.LENGTH_SHORT).show();
            iswrong=true;
            finish();
        }
        initview();
        initevent();
        userGroup = StaticAllList.groupList.get(groupId);

        getAllUser();

    }

    private void getAllUser(){


        if(!NetworkUtils.isConnected(AppointZhuXiActivity.this)){
            Toast.makeText(AppointZhuXiActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
            return;
        }else{


            MyDialog.showBottomLoadingDialog(AppointZhuXiActivity.this);

                    SendToServer.getGroupAllU(groupId,"appoint_zhuxi");


            getAllUserInfoTimer = new Timer();
            getAllUserInfoTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    AppointZhuXiActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyDialog.dismissBottomLoadingDialog();
                            Toast.makeText(AppointZhuXiActivity.this,"加载失败!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            },50000);
        }

    }

    private void initview(){

        finish_iv = (ImageView)findViewById(R.id.finish_appoint_zhuxi_iv);
        search_people = (EditText)findViewById(R.id.appoint_search_people_et);
        appoint_current_people_tv = (TextView)findViewById(R.id.appoint_current_people_tv);
        appoint_current_people_tv.setText("当前主席");

        recyclerView=(RecyclerView)findViewById(R.id.appoint_allpeople_recycleview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


         appoint_current_head_iv = (CircleImageView)findViewById(R.id.appoint_current_head_iv);
         appoint_current_name = (TextView)findViewById(R.id.appoint_current_name);
         appoint_current_part = (TextView)findViewById(R.id.appoint_current_part);
         appoint_current_pos = (TextView)findViewById(R.id.appoint_current_pos);
    }

    private void initevent(){
        finish_iv.setOnClickListener(this);
        search_people.addTextChangedListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.finish_appoint_zhuxi_iv:
                finish();
                break;

                default:
                    break;
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String text = charSequence.toString();
        peoplelist.clear();
        for(UserInGroupInfo userInGroupInfo :StaticAllList.groupUsersInfo.get(groupId) ){

                    if(userInGroupInfo.getPh().contains(text)
                            ||userInGroupInfo.getGroupnickname().contains(text)
                            ||(userInGroupInfo.getCorpos()!=null&&userInGroupInfo.getCorpos().contains(text))
                            ||(userInGroupInfo.getCorppart()!=null&&userInGroupInfo.getCorppart().contains(text))){
                        peoplelist.add(userInGroupInfo);
                    }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    protected void onResume(){
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MY);
        registerReceiver(broadcastReceiver,intentFilter);
    }

    protected void onDestroy(){
        super.onDestroy();
        if(!iswrong){
            unregisterReceiver(broadcastReceiver);
        }

        if(getAllUserInfoTimer!=null){
            getAllUserInfoTimer.cancel();
        }

        if(adapter!=null){
            if(adapter.appointTimer!=null){
                adapter.appointTimer.cancel();
            }
        }

    }

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String type = intent.getStringExtra("type");
            if (type.equals("getGroupAllURs")) {

                int gid = intent.getIntExtra("groupid",-1);
                if(gid!=groupId){
                    return;
                }
                if(getAllUserInfoTimer!=null){
                    getAllUserInfoTimer.cancel();
                }
                MyDialog.dismissBottomLoadingDialog();
                if(StaticAllList.groupUsersInfo.get(groupId)==null){
                    return;
                }

                boolean hasCurrentPos = false;
                for(UserInGroupInfo userInGroupInfo : StaticAllList.groupUsersInfo.get(groupId)){
                    if(userInGroupInfo.getCorpos().equals("主席")){
                        hasCurrentPos=true;
                        currentUserPh = userInGroupInfo.getPh();
                        setHeadImage(userInGroupInfo.getIcon());
                        appoint_current_name.setText(userInGroupInfo.getGroupnickname());
                        appoint_current_part.setText(userInGroupInfo.getCorppart());
                        appoint_current_pos.setText(userInGroupInfo.getCorpos());
                        break;
                    }
                }
                if(!hasCurrentPos){
                    setHeadImage("");
                     appoint_current_name.setText("当前未任命主席");
                    appoint_current_part.setText("");
                    appoint_current_pos.setText("");
                }


                    String type2 ="普通群";
                    if(StaticAllList.groupList.containsKey(groupId)){
                        type2 =StaticAllList.groupList.get(groupId).getGrouptype();
                    }
                    peoplelist=(Vector<UserInGroupInfo>) StaticAllList.groupUsersInfo.get(groupId).clone();
                    String ph = "";
                    for(UserInGroupInfo userInGroupInfo:peoplelist){
                        if(userInGroupInfo.getCorpos().equals("主席")){
                            ph = userInGroupInfo.getPh();
                        }
                    }

                    adapter=new AppointPeopleAdapter(AppointZhuXiActivity.this,peoplelist,type2,groupId,ph,"主席");
                    recyclerView.setAdapter(adapter);

                    SendToServer.okGroupAllU(groupId);

                //群成员列表的  成员的头像到了
            }else if(type.equals("getGroupUserIconRs")){
                String text = search_people.getText().toString();
                if(text.isEmpty()){
                    peoplelist=(Vector<UserInGroupInfo>) StaticAllList.groupUsersInfo.get(groupId).clone();
                    adapter.notifyDataSetChanged();
                }

                String userph = intent.getStringExtra("userph");
                if(userph.equals(currentUserPh)){
                   String path = MyFileTools.getUserIconPath(userph);
                    setHeadImage(path);
                }

                //任命职位
            }else if(type.equals("appointPos")){
                int gid = intent.getIntExtra("groupid",-1);
                if(gid!=groupId){
                    return;
                }
                String rs = intent.getStringExtra("rs");
                if(adapter.appointTimer!=null){
                    adapter.appointTimer.cancel();
                }
                MyDialog.dismissBottomLoadingDialog();

                 String ph = intent.getStringExtra("ph");
                String oldph = intent.getStringExtra("oldph");
                for(UserInGroupInfo userInGroupInfo : StaticAllList.groupUsersInfo.get(groupId)){
                    if(userInGroupInfo.getPh().equals(ph)){
                        userInGroupInfo.setCorpos("主席");
                        userInGroupInfo.setCorppart("");
                    }
                    if(userInGroupInfo.getPh().equals(oldph)){
                        userInGroupInfo.setCorpos("干事");
                    }
                }

                if(rs.equals("ok")){
                    Toast.makeText(AppointZhuXiActivity.this,"任职成功!",Toast.LENGTH_SHORT).show();
                    boolean hasCurrentPos = false;
                    for(UserInGroupInfo userInGroupInfo : StaticAllList.groupUsersInfo.get(groupId)){
                        if(userInGroupInfo.getCorpos().equals("主席")){
                            hasCurrentPos=true;
                            setHeadImage(userInGroupInfo.getIcon());
                            appoint_current_name.setText(userInGroupInfo.getGroupnickname());
                            appoint_current_part.setText(userInGroupInfo.getCorppart());
                            appoint_current_pos.setText(userInGroupInfo.getCorpos());
                            break;
                        }
                    }
                    if(!hasCurrentPos){
                        setHeadImage("");
                        appoint_current_name.setText("当前未任命主席");
                        appoint_current_part.setText("");
                        appoint_current_pos.setText("");
                    }
                    adapter.currentPosPh = ph;
                    String text = search_people.getText().toString();
                    if(!text.isEmpty()){
                        search_people.setText("");
                        peoplelist=(Vector<UserInGroupInfo>) StaticAllList.groupUsersInfo.get(groupId).clone();
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(AppointZhuXiActivity.this,"任职失败!",Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

    private void setHeadImage(String path){

        MyViewTools.setCircleImage(appoint_current_head_iv,path);
    }



}
