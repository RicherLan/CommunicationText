package thefirstchange.example.com.communicationtext.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.adapter.AdapterPage;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.fragment.IndexFragment;
import thefirstchange.example.com.communicationtext.fragment.pagefragment.Fragment_One;
import thefirstchange.example.com.communicationtext.fragment.pagefragment.Fragment_Two;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.User;
import thefirstchange.example.com.communicationtext.movingimage.MovingImageView;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.DensityUtiltwo;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.MyFileTools;
import thefirstchange.example.com.communicationtext.util.MyObjectTools;
import thefirstchange.example.com.communicationtext.util.MyViewTools;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;



public class PersonalHomePageActivity extends BaseActivity implements View.OnClickListener{


    private static String MY = "thefirstchange.example.com.communicationtext.PERSONALHOMEPAGE";

    private Timer changeIconTimer  =new Timer();

    boolean toggleState = true;
    boolean toggleCustomMovement = true;
    int[] imageList = {R.drawable.anotherworld, R.drawable.futurecity, R.drawable.spacecargo, R.drawable.city};
    int pos = 0;

    private MovingImageView movingImageView;
    private ViewPager viewpager;
    private ImageView iv_parallax,iv_back;
    private TextView iv_date;
    private CircleImageView iv_head;
    private SmartRefreshLayout refreshLayout;
    private ButtonBarLayout buttonBarLayout;
    private CollapsingToolbarLayout collapsing_toolbar;
    private AppBarLayout appbar;
    private Toolbar toolbar;
    private int mOffset = 0;
    boolean isblack = false;//状态栏字体是否是黑色
    boolean iswhite = true;//状态栏字体是否是亮色
    private TabLayout tabLayout;
    private String fromWhere="otherpeople";
    private String fromId;
    private ImageView editMyInfo;
    private LinearLayout twoTextLinear;
    private TextView nickName;
    private TextView introduceText;
    private TextView ivTitle;
    private TextView followText;
    private TextView toConnection;

    private Bitmap head;// 头像Bitmap
    private static String path = Config.usericonpath +"/"+ ObjectService.personalInfo.getPhonenumber()+".jpg";// sd路径

    private User user = null;
    boolean isWrong = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_home_page);
        Intent intent=getIntent();
       fromWhere=intent.getStringExtra("from");
       fromId=intent.getStringExtra("id");
       if(fromId.equals(ObjectService.personalInfo.getPhonenumber())){
           user =  MyObjectTools.personalInfoConvertToUser(ObjectService.personalInfo);

       }else{
           if(StaticAllList.friendList.containsKey(fromId)){
               user = new User();
               user.setPhonenumber("");
               MyObjectTools.MyFriendConvertToUser(StaticAllList.friendList.get(fromId),user);
           }else{
               user = StaticAllList.usertemps.get(fromId);
           }
       }


//        if(user==null||user.getPhonenumber().equals("")){
//           Toast.makeText(PersonalHomePageActivity.this,"系统错误,请稍后再试!",Toast.LENGTH_SHORT).show();
//            isWrong=true;
//            finish();
//        }else{

           SendToServer.getIndexInfoByPh(fromId+"");

            initView();
            initViewPager();
            initListener();
//        }

    }
    private void initView() {
        movingImageView=(MovingImageView)findViewById(R.id.moving_image);
//        movingImageView.getMovingAnimator().setInterpolator(new BounceInterpolator());
//        movingImageView.getMovingAnimator().setSpeed(100);
//        movingImageView.getMovingAnimator().addCustomMovement().
//                addDiagonalMoveToDownRight().
//                addHorizontalMoveToLeft().
//                addDiagonalMoveToUpRight().
//                addVerticalMoveToDown().
//                addHorizontalMoveToLeft().
//                addVerticalMoveToUp().
//                start();
        followText=(TextView)findViewById(R.id.follow_text);
        followText.setOnClickListener(this);
        toConnection=(TextView)findViewById(R.id.to_connection_text);
        toConnection.setOnClickListener(this);
        movingImageView.setOnClickListener(this);
        nickName=(TextView)findViewById(R.id.nickname);
        iv_head = (CircleImageView) findViewById(R.id.iv_head);
        introduceText=(TextView)findViewById(R.id.info_introduce);
        ivTitle=(TextView)findViewById(R.id.tv_title);
        if (fromId.equals(ObjectService.personalInfo.getPhonenumber())){
            nickName.setText(ObjectService.personalInfo.getNickname());
            introduceText.setText(ObjectService.personalInfo.getIntroduce());
            ivTitle.setText(ObjectService.personalInfo.getNickname());
//            Glide.with(this).load(ObjectService.personalInfo.getIcon())
//                    .into(iv_head);
            setUserImage();


        }else {
            if(user!=null){
                nickName.setText(user.getNickname());
                ivTitle.setText(user.getNickname());

                introduceText.setText(user.getIntroduce());
            }

            String icpath = MyFileTools.getUserIconPath(fromId);
            MyViewTools.setCircleImage(iv_head,icpath);

        }

        //动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.img_animation);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);
        iv_head.startAnimation(animation);


        nickName.setOnClickListener(this);

        twoTextLinear=(LinearLayout)findViewById(R.id.two_textbu_lay);
        editMyInfo=(ImageView)findViewById(R.id.edit_my_info);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        viewpager = (ViewPager) findViewById(R.id.view_pager);
       // iv_parallax = (ImageView) findViewById(R.id.iv_parallax);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        buttonBarLayout = (ButtonBarLayout) findViewById(R.id.buttonBarLayout);
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_date = (TextView)findViewById(R.id.iv_date);
        if (!fromId.equals(ObjectService.personalInfo.getPhonenumber())){
            iv_date.setVisibility(View.GONE);
        }else{
            iv_date.setOnClickListener(this);
        }

       // toolbar_avatar = (ImageView) findViewById(R.id.toolbar_avatar);


        //初始化沉浸式
        mImmersionBar.titleBar(toolbar).init();
     //   RequestOptions options = new RequestOptions();


//        Glide.with(this).load(R.drawable.user_image1)
//                .apply(options.circleCrop())
//                .into(toolbar_avatar);
        iv_back.setOnClickListener(this);
        if (!fromId.equals(ObjectService.personalInfo.getPhonenumber())){
            iv_date.setVisibility(View.INVISIBLE);
            editMyInfo.setVisibility(View.GONE);
            iv_head.setClickable(false);

        }else {
            iv_date.setVisibility(View.VISIBLE);
            editMyInfo.setVisibility(View.VISIBLE);
            twoTextLinear.setVisibility(View.GONE);
            iv_head.setClickable(true);
            iv_head.setOnClickListener(this);
        }


    }

    /**
     * 设置item
     *
     */
    private void initViewPager() {
        viewpager = (ViewPager) findViewById(R.id.view_pager);
        AdapterPage adapter = new AdapterPage(getSupportFragmentManager());
        adapter.addFragment(new Fragment_One(), "资料");
        adapter.addFragment(new Fragment_Two(), "动态");
      //  adapter.addFragment(new Fragment_Three(), "相册");
        viewpager.setAdapter(adapter);
        //设置tablayout，viewpager上的标题
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        //tabLayout.setTabGravity(10);
        viewpager.setCurrentItem(1);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout,30,30);
            }
        });

        Intent intent = new Intent("thefirstchange.example.com.communicationtext.FRAGMENT_TWO");
        intent.putExtra("type","HomePageNOtify");
        sendBroadcast(intent);
    }

    private void initListener() {
        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {


            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
                movingImageView.setTranslationY(mOffset);
            }
            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
                movingImageView.setTranslationY(mOffset);
            }

        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                Log.e("fsdf","jskdfj舒心");
            }
        });

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                movingImageView.setTranslationY(verticalOffset);
                //200是appbar的高度
                if (Math.abs(verticalOffset) == DensityUtil.dp2px(300) - toolbar.getHeight()) {//关闭
                    if (iswhite) {//变黑
                        if (ImmersionBar.isSupportStatusBarDarkFont()) {
                            mImmersionBar.statusBarDarkFont(true).init();
                            isblack = true;
                            iswhite = false;
                        }
                    }
                    buttonBarLayout.setVisibility(View.VISIBLE);
                    collapsing_toolbar.setContentScrimResource(R.color.white);
                    iv_back.setBackgroundResource(R.drawable.ic_back_black_30);
                    if (fromId.equals(ObjectService.personalInfo.getPhonenumber())){
                        iv_date.setTextColor(getResources().getColor(R.color.black));
                    }

//                    toolbar.setVisibility(View.VISIBLE);
                } else {  //展开
                    if (isblack) { //变白
                        mImmersionBar.statusBarDarkFont(false).init();
                        isblack = false;
                        iswhite = true;
                    }
                    buttonBarLayout.setVisibility(View.INVISIBLE);
                    collapsing_toolbar.setContentScrimResource(R.color.transparent);
                    iv_back.setBackgroundResource(R.drawable.ic_back_write_30);
                    iv_date.setTextColor(getResources().getColor(R.color.white));
//                    toolbar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    /**
     * 通过反射修改踏遍layout的宽，其实相当于margin
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public void setIndicator (TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip=null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);

        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_head:
                showChooseIvDialog();
                break;
            case R.id.moving_image:
                clickTitle(v);
                break;
            case R.id.nickname:
                clickTitle(v);
                break;
            case R.id.iv_date:
                if(fromId.equals(ObjectService.personalInfo.getPhonenumber())){
                    Intent intent=new Intent(PersonalHomePageActivity.this,UpdatePersonInfoActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.follow_text:
                Intent intent1=new Intent(PersonalHomePageActivity.this,FindFriendOrGroupActivity.class);
                intent1.putExtra("number",fromId+"");
                intent1.putExtra("is","nogroup");
                startActivity(intent1);
                break;
            case R.id.to_connection_text:
                if(fromId.equals(ObjectService.personalInfo.getPhonenumber())){
                    return;
                }
                Intent intent2=new Intent(PersonalHomePageActivity.this,ConnectOtherActivity.class);
                intent2.putExtra("senderId",fromId);
                intent2.putExtra("receiver_nickname",user.getNickname());
                startActivity(intent2);
                finish();

                break;
                default:
        }
    }

    public String getFromWhere(){
        return fromWhere;
    }

    public String getFromId(){
        return fromId;
    }


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

                if(!NetworkUtils.isConnected(PersonalHomePageActivity.this)){
                    Toast.makeText(PersonalHomePageActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
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


                        MyDialog.showBottomLoadingDialog(PersonalHomePageActivity.this);


                        SendToServer.ChPerIc(head);


                        changeIconTimer = new Timer();
                        changeIconTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                PersonalHomePageActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyDialog.dismissBottomLoadingDialog();
                                        Toast.makeText(PersonalHomePageActivity.this,"服务器繁忙,请稍后再试",Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        },40000);

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

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
//        File file = new File(path);
//        file.mkdirs();// 创建文件夹
        String fileName = path;// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            b.flush();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        SharedPreferences sh = getSharedPreferences( Config.sharedPreferences_personal_info,Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = gson.toJson(ObjectService.personalInfo);
        SharedPreferences.Editor editor = sh.edit() ;
        editor.putString(Config.sharedPreferences_personal_info, json) ; //存入json串
        editor.commit() ; //提交


    }

    public void clickImage(View v) {
        if (toggleState) {
            movingImageView.getMovingAnimator().pause();
            //Toast.makeText(this, "Pause", Toast.LENGTH_SHORT).show();
        } else {
            movingImageView.getMovingAnimator().resume();
           // Toast.makeText(this, "Resume", Toast.LENGTH_SHORT).show();
        }


        toggleState = !toggleState;
    }

    public void clickTitle(View v) {
        pos = (pos + 1) >= imageList.length ? 0 : pos + 1;
        movingImageView.setImageResource(imageList[pos]);
        toggleCustomMovement = true;
       // Toast.makeText(this, "Next picture", Toast.LENGTH_SHORT).show();
    }

    public void clickText(View v) {
        if(toggleCustomMovement) {
            movingImageView.getMovingAnimator().addCustomMovement().addDiagonalMoveToDownRight().addHorizontalMoveToLeft().addDiagonalMoveToUpRight()
                    .addVerticalMoveToDown().addHorizontalMoveToLeft().addVerticalMoveToUp().start();
          //  Toast.makeText(this, "Custom movement", Toast.LENGTH_SHORT).show();
        } else {
            movingImageView.getMovingAnimator().clearCustomMovement();
            //Toast.makeText(this, "Default movement", Toast.LENGTH_SHORT).show();
        }
        toggleCustomMovement = !toggleCustomMovement;
    }


    private void setUserImage(){

//        Bitmap bitmap  = UserUtil.getPersonalIcon();
        if(ObjectService.personalIcon!=null) {
            iv_head.setImageBitmap(ObjectService.personalIcon);
        }else{
            iv_head.setImageDrawable(getResources().getDrawable(R.drawable.user_image1));
        }

    }



    protected void onResume(){

        super.onResume();

        if (fromId==null||fromId.equals(ObjectService.personalInfo.getPhonenumber())){
            nickName.setText(ObjectService.personalInfo.getNickname());
            introduceText.setText(ObjectService.personalInfo.getIntroduce());
            ivTitle.setText(ObjectService.personalInfo.getNickname());
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MY);
        registerReceiver(broadcastReceiver,intentFilter);


    }
    public void onDestroy(){
        super.onDestroy();

        if(!isWrong){
            unregisterReceiver(broadcastReceiver);

        }

        if(changeIconTimer!=null){
            changeIconTimer.cancel();
        }

    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MY)){

                String type = intent.getStringExtra("type");
                if(type.equals("ChPerIcRs")){
                    if(changeIconTimer!=null){
                        changeIconTimer.cancel();
                    }
                    MyDialog.dismissBottomLoadingDialog();

                    String rs = intent.getStringExtra("rs");
                    if(rs.equals("ok")){
                        if(head!=null){

                            iv_head.setImageBitmap(head);// 用ImageButton显示出来

                            setPicToView(head);// 保存在SD卡中
                            ObjectService.personalIcon=head;
                            ObjectService.personalInfo.setIcon(path);

                            if(IndexFragment.head_icon!=null){
                                IndexFragment.head_icon.setImageBitmap(ObjectService.personalIcon);
                            }

                        }

                        Toast.makeText(PersonalHomePageActivity.this,"修改成功!",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(PersonalHomePageActivity.this,"服务器繁忙,请稍后再试！",Toast.LENGTH_SHORT).show();
                    }

                }else if(type.equals("getIndexInfoByPhRs")){           //资料

                    String ph = intent.getStringExtra("ph");
                    if(ph.equals(fromId)){
                        user = StaticAllList.usertemps.get(ph);
                        nickName.setText(user.getNickname());
                    }
                    //获得基本信息后请求头像

                    SendToServer.getIndexICByPh(user.getPhonenumber());


                }else if(type.equals("getIndexIcInfoByPhRs")){       //头像

                    String ph = intent.getStringExtra("ph");
                    if(fromId.equals(ph)){
                        if(user!=null&&user.getPhonenumber().equals(fromId)){
                            MyViewTools.setCircleImage(iv_head,user.getIcon());
                        }else{
                            MyViewTools.setCircleImage(iv_head,"");
                        }
                    }

                }else if(type.equals("getPerIc")) {
                    if(ObjectService.personalIcon!=null){
                        iv_head.setImageBitmap(ObjectService.personalIcon);
                    }

                }

            }

        }
    };


}
