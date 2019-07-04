package thefirstchange.example.com.communicationtext;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.wakehao.bar.BottomNavigationBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.activity.EditLifeShowActivity;
import thefirstchange.example.com.communicationtext.adapter.WePagerAdapter;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.CourseAndScore;
import thefirstchange.example.com.communicationtext.db.dao.BasicDataDao;
import thefirstchange.example.com.communicationtext.db.dao.ChatRecordDao;
import thefirstchange.example.com.communicationtext.fragment.IndexFragment;
import thefirstchange.example.com.communicationtext.fragment.LifeShowFragment;
import thefirstchange.example.com.communicationtext.fragment.MyselfFragment;
import thefirstchange.example.com.communicationtext.fragment.NotificationFragment;
import thefirstchange.example.com.communicationtext.gson.MyFriend;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.UserGroup;
import thefirstchange.example.com.communicationtext.login.LoginActivity;
import thefirstchange.example.com.communicationtext.netty.NettyListener;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.ActivityCollector;
import thefirstchange.example.com.communicationtext.util.DateUtils;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;


public class MainActivity extends BaseForCloseActivity {


  public static Context mainactivityContext;
  public static MainActivity mainactivity;

    private LifeShowFragment mBackHandedFragment;
    private boolean hadIntercept;
    private static String MY = "thefirstchange.example.com.communicationtext.MAIN";

     private long firstTime=0;

     private static Context context;

    public  static BottomNavigationBar bar;
    private WePagerAdapter wePagerAdapter;
    private ViewPager mViewPager;
    private List<Fragment> list;
   // public static int chatMsgNum=0;

    //Intent bindIntent=new Intent(this,NotifyService.class);

    private Timer loginTimer;

    String isneedlogin = "no";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainactivityContext = getApplicationContext();
        mainactivity = this;
        initChatRecorddb();
        initBasicDataDb();

        context=getApplicationContext();
        initView();


        bar = (BottomNavigationBar) findViewById(R.id.bar);
        bar.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                return true;
            }
        });
        //可动态改变item的标题
//        bar.setTitle(0,"home(99)");
        bar.showNum(0,0);
        bar.showNum(1,100);
        bar.showNum(2,-2);
        bar.disMissNum(3);
        bar.getViewPager();


        mViewPager.setOnPageChangeListener(new MyPagerChangeListener());
        list=new ArrayList<>();
        list.add(new IndexFragment());
        list.add(new NotificationFragment());
        list.add(new LifeShowFragment());
        list.add(new MyselfFragment());
        wePagerAdapter=new WePagerAdapter(getSupportFragmentManager(),list);
        mViewPager.setAdapter(wePagerAdapter);
        mViewPager.setCurrentItem(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }


        if(!NetworkUtils.isConnected(MainActivity.this)){
            Toast.makeText(MainActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
            offlineDataFromDb();
            return;
        }else{

            if(NettyService.connetstatuscode!= NettyListener.STATUS_CONNECT_SUCCESS){
                offlineDataFromDb();
            }

            Intent intent = getIntent();
             isneedlogin = intent.getStringExtra("isneedlogin");

            if(isneedlogin!=null&&isneedlogin.equals("yes")){

                loginTimer = new Timer();
                loginTimer.schedule(new TimerTask(){

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                SendToServer.login(ObjectService.personalInfo.getPhonenumber(),ObjectService.personalInfo.getPassword(),"MainUI");

                            }
                        });
                    }
                }, 0, Config.loginIntervalTime);

            }
        }

        /*
            解决android7.0设备  打开相机死掉问题
         */
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();


    }



    public static void initChatRecorddb(){
        StaticAllList.chatRecordDao = ChatRecordDao.getInstance(mainactivityContext);
    }
    public static void initBasicDataDb(){
        StaticAllList.basicDataDao = BasicDataDao.getInstance(mainactivityContext);
    }

    public  void offlineDataFromDb(){
        //从数据库获得自己的群列表     给离线做的

        if(StaticAllList.groupList==null||StaticAllList.groupList.size()==0){
            if(StaticAllList.basicDataDao==null){
                MainActivity.initBasicDataDb();
            }
            Vector<UserGroup> userGroups = StaticAllList.basicDataDao.getUserGroupList(ObjectService.personalInfo.getPhonenumber());
            int sum=0;
            for(UserGroup userGroup : userGroups){
                //说明服务器已经发来群信息
                if(sum==0&&StaticAllList.groupList!=null&&StaticAllList.groupList.size()>0){
                    break;
                }
                ++sum;
                if(StaticAllList.groupList.containsKey(userGroup.getGroupid())){
                    continue;
                }
                StaticAllList.groupList.put(userGroup.getGroupid(),userGroup);

            }
        }

        if(StaticAllList.friendList==null||StaticAllList.friendList.size()==0){

            if(StaticAllList.basicDataDao==null){
                MainActivity.initBasicDataDb();
            }
            Vector<MyFriend> myFriends = StaticAllList.basicDataDao.getUserFriendList(ObjectService.personalInfo.getPhonenumber());

            int sum=0;
            for(MyFriend myFriend: myFriends){
                //说明服务器已经发来了 好友信息
                if(sum==0&&StaticAllList.friendList!=null&&StaticAllList.friendList.size()!=0){
                    break;
                }
                sum++;
                if(StaticAllList.friendList.containsKey(myFriend.getPhonenumber())){
                    continue;
                }
                StaticAllList.friendList.put(myFriend.getPhonenumber(),myFriend);

            }

        }

    }


    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setShowBadge(false);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=RESULT_OK||requestCode!=1)return;
        //不带动画的切换item
        bar.setItemSelected(3,false);
        super.onActivityResult(requestCode, resultCode, data);
    }


    public BottomNavigationBar getBar(){
        return bar;
    }
       // android.support.v7.widget.Toolbar toolbar=findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);


        /*ClientFragment clientFragment=new ClientFragment();
        android.app.FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(clientFragment,"1");
        transaction.commit();*/


       // bindService(bindIntent,connection,BIND_AUTO_CREATE);
     //   startService(bindIntent);


    public void onDestroy(){
        super.onDestroy();

        if(loginTimer!=null){
            loginTimer.cancel();
        }
        //unbindService(connection);

        //stopService(bindIntent);
        unregisterReceiver(broadcastReceiver);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (LifeShowFragment.vImageWatcher!=null&&!LifeShowFragment.vImageWatcher.handleBackPressed()) {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                return true;
            }

            return false;
        }
        return super.onKeyDown(keyCode, event);
    }



    private void initView(){
        mViewPager=(ViewPager)findViewById(R.id.view_pager);
    }

    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener{

        public void onPageScrollStateChanged(int arg0) {

        }



        @Override

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }



        @Override

        public void onPageSelected(int arg0) {
        }

    }

    public static Context finishSelf(){
        return context;

    }

    protected void onResume(){

        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MY);
        registerReceiver(broadcastReceiver,intentFilter);



    }

    protected void onPause(){
        super.onPause();

    }

    protected void onStop(){
        super.onStop();
//        Intent intent=new Intent("thefirstchange.example.com.communicationtext.MAIN_LOOK");
//        sendBroadcast(intent);

    }



    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           if(intent.getAction().equals(MY)){

               String type = intent.getStringExtra("type");
               //动态的基本信息是否发表成功  收到该消息就是成功
               if(type.equals("addDongtaiResult")){

                    if(EditLifeShowActivity.addDongtaiTimer!=null){
                        EditLifeShowActivity.addDongtaiTimer.cancel();
                    }
                    MyDialog.dismissBottomLoadingDialog();
                    if(EditLifeShowActivity.editLifeShowActivity!=null){
                        EditLifeShowActivity.editLifeShowActivity.finish();
                    }


                    String rs = intent.getStringExtra("rs");
                    int dongtaiid = intent.getIntExtra("dongtaiid",-1);
                    if(rs.equals("error")){
                        Toast.makeText(MainActivity.this, "动态发表失败", Toast.LENGTH_SHORT).show();

                    }else {
                        //该动态没有图片  且发表成功
                        if(StaticAllList.nowDongtai.getImnum()==0){
                            Toast.makeText(context, "动态发表成功", Toast.LENGTH_SHORT).show();
                        }
                    }
               }else if(type.equals("adddongtaiimageResult")){

                   if(EditLifeShowActivity.addDongtaiTimer!=null){
                       EditLifeShowActivity.addDongtaiTimer.cancel();
                   }
                   MyDialog.dismissBottomLoadingDialog();
                   if(EditLifeShowActivity.editLifeShowActivity!=null){
                       EditLifeShowActivity.editLifeShowActivity.finish();
                   }

                   String rs = intent.getStringExtra("rs");
                   int dongtaiid = intent.getIntExtra("dongtaiid",-1);
                    if(rs.equals("error")){
                        Toast.makeText(context, "动态发表失败", Toast.LENGTH_SHORT).show();

                    }else{
                        //该动态有图片  且都发表成功
                        if(StaticAllList.nowDongtai.getImnum()==StaticAllList.dongtaiImageHasnotUpload){
                            Toast.makeText(context, "动态发表成功", Toast.LENGTH_SHORT).show();

                        }
                    }

                    ////用户下拉刷新动态的页面  就是请求新的动态      服务器收到回返回ok  否则客户端超时  就提示刷新失败
               }else if(type.equals("getnewDongtaiIDsResult")){
                    String rs = intent.getStringExtra("rs");
                    if(rs.equals("error")){
                        Toast.makeText(context, "刷新失败", Toast.LENGTH_SHORT).show();
                    }

                   //用户上拉刷新动态的页面  就是请求旧的动态      服务器收到回返回ok  否则客户端超时  就提示刷新失败
               }else if(type.equals("getoldDongtaiIDsResult")){
                   String rs = intent.getStringExtra("rs");
                   if(rs.equals("error")){
                       Toast.makeText(context, "刷新失败", Toast.LENGTH_SHORT).show();
                   }
               }

               else if(type.equals("loginRs")){  //登陆的结果
                   if(loginTimer!=null){
                       loginTimer.cancel();
                   }

                   String rs=intent.getStringExtra("login_info");
                   if (rs.equals("ok")){


                   }else {
                       Config.isLogined = false;
                       //Toast.makeText(MainActivity.this,"身份已过期,请重新登录!",Toast.LENGTH_SHORT).show();
                       showLoginFailedDialog(ActivityCollector.getLastActivity());
                   }
               }

               //自己登陆   有人异地登陆  顶替自己
               else if(type.equals("loginByOther")){
                   if(loginTimer!=null){
                       loginTimer.cancel();
                   }
                   Config.isLogined = false;
                    long time = intent.getLongExtra("time",-1);
                    if(time==-1){
                        time = System.currentTimeMillis();
                    }
                    String timestr = DateUtils.longToStringTime(time);

                   showLoginByOtherDialog(ActivityCollector.getLastActivity());
               }
           }

        }
    };


    private void showLoginByOtherDialog(Context context){
        ObjectService.personalInfo.setPassword("");
        AlertDialog alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("提示");
        alertDialog.setMessage("您的账户在异地登陆,您被迫下线!");
        alertDialog.setCancelable(false);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                CourseAndScore.removeAll();
                MyMessageQueue.removeAll();
                StaticAllList.removeAll();
                ActivityCollector.finishAll();
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.context.startActivity(intent);

            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }


    private void showLoginFailedDialog(Context context){

        ObjectService.personalInfo.setPassword("");
        AlertDialog alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("提示");
        alertDialog.setMessage("身份已过期,请重新登录!");
        alertDialog.setCancelable(false);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                CourseAndScore.removeAll();
                MyMessageQueue.removeAll();
                StaticAllList.removeAll();
                ActivityCollector.finishAll();
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.context.startActivity(intent);

            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }






    public static void changemsgnotreadnum(){

        int msgsum = 0;
        for(String id: MyMessageQueue.chatQueueNotRead.keySet()){
            msgsum+=MyMessageQueue.chatQueueNotRead.get(id).size();
        }
        if(MyMessageQueue.requestQueueNotHandle!=null&&MyMessageQueue.requestQueueNotHandle.size()!=0) {
            for(int i=0;i<MyMessageQueue.requestQueueNotHandle.size();++i){
                if(!MyMessageQueue.requestQueueNotHandle.get(i).getHandleRs().equals("hadread")){
                    ++msgsum;
                }
            }
        }
        msgsum+=MyMessageQueue.dutyQueueNotRead.size();
        int a = msgsum;
        if(msgsum==0){
            MainActivity.bar.disMissNum(1);
        }else{
            MainActivity.bar.showNum(1,msgsum);
        }

    }

    public int rawX;
    public int rawY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        rawX= (int) ev.getRawX();
        rawY= (int) ev.getRawY();
        return super.dispatchTouchEvent(ev);
    }



}
