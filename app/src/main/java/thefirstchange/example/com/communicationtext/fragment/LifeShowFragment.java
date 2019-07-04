package thefirstchange.example.com.communicationtext.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import byc.imagewatcher.ImageWatcher;
import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.EditLifeShowActivity;
import thefirstchange.example.com.communicationtext.activity.LifeShowNotificationActivity;
import thefirstchange.example.com.communicationtext.adapter.LifeShowAdapter;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.imagewarker.MessagePicturesLayout;
import thefirstchange.example.com.communicationtext.imagewarker.SpaceItemDecoration;
import thefirstchange.example.com.communicationtext.imagewarker.Utils;
import thefirstchange.example.com.communicationtext.listener.EndlessRecyclerOnScrollListener;
import thefirstchange.example.com.communicationtext.service.DongtaiUploadThread;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public  class LifeShowFragment extends Fragment implements View.OnClickListener,MessagePicturesLayout.Callback, ImageWatcher.OnPictureLongPressListener{

    private static String MY = "thefirstchange.example.com.communicationtext.LIFESHOWFRAGMENT";

    private ImageView personlShowChoose;
    private ImageView unionShowChoose;
    private RecyclerView mRecyclerView;
    private LifeShowAdapter mListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView editShowBu;
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton floatingActionButton;
    private CircleImageView lastshowImage;
    private TextView showMsgNu;

    public static ImageWatcher vImageWatcher;
    boolean isTranslucentStatus;

    View view=null;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if(view==null){
            view=inflater.inflate(R.layout.life_show_fragmnet,container,false);
            initView(view);
//            DongtaiUploadThread dongtaiUploadThread = new DongtaiUploadThread("getnewDongtaiIds",new Vector<Integer>());
//            dongtaiUploadThread.start();

        }else{

        }

        return view;
    }

    private void initView(View view){
        editShowBu=(ImageView)view.findViewById(R.id.edit_show);
        editShowBu.setOnClickListener(this);
        personlShowChoose=(ImageView)view.findViewById(R.id.personal_show_choose_imag);
        personlShowChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personlShowChoose.setImageResource(R.drawable.ic_show_choose_press);
                PopupMenu popupMenu=new PopupMenu(getActivity(),personlShowChoose);
                popupMenu.getMenuInflater().inflate(R.menu.pop_personal_show_choose,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.all_show:
                                personlShowChoose.setImageResource(R.drawable.ic_show_choose);
                                break;
                            case R.id.hot_show:
                                personlShowChoose.setImageResource(R.drawable.ic_show_choose);
                                break;
                            case R.id.school_show:
                                personlShowChoose.setImageResource(R.drawable.ic_show_choose);
                                break;
                            case R.id.followers_show:
                                personlShowChoose.setImageResource(R.drawable.ic_show_choose);
                                break;
                                default:
                                    break;

                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        unionShowChoose=(ImageView)view.findViewById(R.id.union_show_choose_imag);
        unionShowChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unionShowChoose.setImageResource(R.drawable.ic_show_choose_press);
                PopupMenu popupMenu=new PopupMenu(getActivity(),unionShowChoose);
                popupMenu.getMenuInflater().inflate(R.menu.pop_union_show_choose,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.school_all_show:
                                unionShowChoose.setImageResource(R.drawable.ic_show_choose_press);
                                break;
                            case  R.id.school_hot_show:
                                unionShowChoose.setImageResource(R.drawable.ic_show_choose_press);
                                break;
                            case R.id.school_self_show:
                                unionShowChoose.setImageResource(R.drawable.ic_show_choose_press);
                                break;
                                default:
                                    break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        floatingActionButton=(FloatingActionButton)view.findViewById(R.id.edit_show_floatingBu);
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        floatingActionButton.setOnClickListener(this);

        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.life_show_swipw);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.life_show_recycler);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {                //下拉刷新动态界面


                if(!NetworkUtils.isConnected(getContext())){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(getContext(), "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                DongtaiUploadThread dongtaiUploadThread = new DongtaiUploadThread("getnewDongtaiIds",new Vector<Integer>());
                dongtaiUploadThread.start();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });

                    }
                },30000);

            }
        });
        mListAdapter=new LifeShowAdapter(getActivity(), StaticAllList.dongtais);
//        mListAdapter.setHasStableIds(true);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(getActivity()).setSpace(14).setSpaceColor(0xFFECECEC));

        mRecyclerView.setAdapter(mListAdapter.setPictureClickCallback(this));

        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {                            //上拉刷新动态界面
                mListAdapter.setLoadState(mListAdapter.LOADING);
                Vector<Integer> integers = new Vector<>();
                int a = mListAdapter.getpos()-1;
                if(a>=0){
                    if(a>=StaticAllList.dongtais.size()||a<0){
                        return;
                    }
                    int id = StaticAllList.dongtais.get(a).getId();
                    integers.add(id);
                    DongtaiUploadThread dongtaiUploadThread = new DongtaiUploadThread("getoldDongtaiIds",integers);
                    dongtaiUploadThread.start();
                }

            }
        });

        lastshowImage=(CircleImageView)view.findViewById(R.id.last_mag_iv);
        lastshowImage.setOnClickListener(this);
        showMsgNu=(TextView)view.findViewById(R.id.life_show_msg_nu);

        vImageWatcher = ImageWatcher.Helper.with(getActivity()) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setTranslucentStatus(!isTranslucentStatus ? Utils.calcStatusBarHeight(getActivity()) : 0) // 如果是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
                .setErrorImageRes(R.mipmap.error_picture) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setHintMode(ImageWatcher.POINT)//设置指示器（默认小白点）
                .setHintColor(getResources().getColor(R.color.red), getResources().getColor(R.color.white))//设置指示器颜色
                .setOnPictureLongPressListener(this) // 长按图片的回调，你可以显示一个框继续提供一些复制，发送等功能
                .setLoader(new ImageWatcher.Loader() {//调用show方法前，请先调用setLoader 给ImageWatcher提供加载图片的实现
                    @Override
                    public void load(Context context, String url, final ImageWatcher.LoadCallback lc) {
                        Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                lc.onResourceReady(resource);
                            }

                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                lc.onLoadStarted(placeholder);
                            }

                            @Override
                            public void onLoadFailed(Drawable errorDrawable) {
                                lc.onLoadFailed(errorDrawable);
                            }
                        });
                    }
                })
                .create();

        //Utils.fitsSystemWindows(isTranslucentStatus, view.findViewById(R.id.v_fit));
        initMsg();



    }

    private void initMsg(){
        if(MyMessageQueue.dongtaiMsgNotRead.size()!=0){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int sum=0;
            String ph1 = "";
            for(String ph: MyMessageQueue.dongtaiMsgNotRead.keySet()){
                ++sum;
                if(sum==MyMessageQueue.dongtaiMsgNotRead.size()){
                    ph1=ph;
                }
            }

            MyMessageQueue.dongtaiMsgNotRead.get(ph1).compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes=baos.toByteArray();
//                  String icon = MyMessageQueue.dongtaiMsgNotRead.get(ph);        //对方的头像
            Glide.with(getActivity()).load(bytes).into(lastshowImage);
            showMsgNu.setText( StaticAllList.dongtaiMsgNotReadNum+"");
        }

    }


    @Override
    public void onThumbPictureClick(ImageView i, List<ImageView> imageGroupList, List<String> urlList) {
        vImageWatcher.show(i, imageGroupList, urlList);
    }

    @Override
    public void onPictureLongPress(ImageView v, String url, int pos) {
      //  Toast.makeText(v.getContext().getApplicationContext(), "长按了第" + (pos + 1) + "张图片", Toast.LENGTH_SHORT).show();
    }

//    //记得重写返回键哦
//    public void onBackPressed() {
//        if (!vImageWatcher.handleBackPressed()) {
//            super.onBackPressed();
//        }
//    }



    public void onClick(View view){
        switch (view.getId()){
            case R.id.edit_show_floatingBu:
                Intent intent=new Intent(getActivity(), EditLifeShowActivity.class);
                startActivity(intent);
            break;
            case R.id.edit_show:
                Intent intent1=new Intent(getActivity(), EditLifeShowActivity.class);
                startActivity(intent1);
                break;
            case R.id.last_mag_iv:
                Intent intent2=new Intent(getContext(), LifeShowNotificationActivity.class);
                startActivity(intent2);
                break;
                default:
                    break;

        }


    }



    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           if(intent.getAction().equals(MY)){

               String type = intent.getStringExtra("type");
               //请求动态    返回动态基本信息   不包括图片
                if(type.equals("getDtByDTIDRs")){

                    swipeRefreshLayout.setRefreshing(false);
                    mListAdapter.notifyDataSetChanged();

                    //请求动态    返回动态的主人的头像
                }else if(type.equals("getDtByDTIDIcRs")){
                    swipeRefreshLayout.setRefreshing(false);
                    mListAdapter.notifyDataSetChanged();

                    //请求动态    返回动态的图片
                } else if(type.equals("getDtByDTIDImRs")){
                    swipeRefreshLayout.setRefreshing(false);
                    mListAdapter.notifyDataSetChanged();

                    //动态页面下拉刷新
                }else if(type.equals("getnewDongtaiIDsResult")){
                    swipeRefreshLayout.setRefreshing(false);

                    //动态界面上拉刷新
                }else if(type.equals("getoldDongtaiIDsResult")){


                    int size = intent.getIntExtra("size",0);
                    if(size==0||size==1){
                        mListAdapter.setLoadState(mListAdapter.LOADING_END);
                    }else {
                        mListAdapter.setLoadState(mListAdapter.LOADING_COMPLETE);
                    }


                }else if(type.equals("userPraiseForyou")){           //某人给自己点赞
                    StaticAllList.dongtaiMsgNotReadNum++;
                    String ph = intent.getStringExtra("ph");
//                    String icon = MyMessageQueue.dongtaiMsgNotRead.get(ph);        //对方的头像

                }else if(type.equals("userCommentForyou")){           //某人给自己的动态评论

                    StaticAllList.dongtaiMsgNotReadNum++;
                    String ph = intent.getStringExtra("ph");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    MyMessageQueue.dongtaiMsgNotRead.get(ph).compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] bytes=baos.toByteArray();
//                  String icon = MyMessageQueue.dongtaiMsgNotRead.get(ph);        //对方的头像
                    Glide.with(getActivity()).load(bytes).into(lastshowImage);
                    showMsgNu.setText( StaticAllList.dongtaiMsgNotReadNum+"");



                }else if(type.equals("UCmtForYCmtOfDT")){           //某人给自己对某动态的评论评论
                    StaticAllList.dongtaiMsgNotReadNum++;
                    String ph = intent.getStringExtra("ph");
//                  String icon = MyMessageQueue.dongtaiMsgNotRead.get(ph);        //对方的头像

                }else if(type.equals("UCmtForYDTCmt")){           //某人给自己的动态的某一条动态评论
                    StaticAllList.dongtaiMsgNotReadNum++;
                    String ph = intent.getStringExtra("ph");
//                  String icon = MyMessageQueue.dongtaiMsgNotRead.get(ph);        //对方的头像

                }



                else if(type.equals("dongtaicommentCommentResult")){             //给动态的评论 评论 是否成功
                    String rs = intent.getStringExtra("rs");
                    if(rs.equals("ok")){

                    }else{

                    }

                }else if(type.equals("dongtaiCommentResult")){             //给动态评论是否成功
                    String rs = intent.getStringExtra("rs");
                    if(rs.equals("ok")){

                    }else{

                    }
                }

           }

        }
    };

    public  void onDestroy(){
        super.onDestroy();

        getContext().unregisterReceiver(broadcastReceiver);

    }

    public  void onResume() {

        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MY);
        getContext().registerReceiver(broadcastReceiver,intentFilter);
        //刷新动态
        DongtaiUploadThread dongtaiUploadThread = new DongtaiUploadThread("getnewDongtaiIds",new Vector<Integer>());
        dongtaiUploadThread.start();
    }




    public static boolean clickBack(int keycode,KeyEvent event){

        if(!vImageWatcher.handleBackPressed()){
            return true;

        }else {
            return false;
        }



    }

}
