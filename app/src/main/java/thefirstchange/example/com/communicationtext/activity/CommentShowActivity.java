package thefirstchange.example.com.communicationtext.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import byc.imagewatcher.ImageWatcher;
import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.dongtai.DTComRoot;
import thefirstchange.example.com.communicationtext.dongtai.Dongtai;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.imagewarker.MessagePicturesLayout;
import thefirstchange.example.com.communicationtext.imagewarker.Utils;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.nice9layout.ImageNice9Layout;
import thefirstchange.example.com.communicationtext.showcomment.adapter.CommentExpandAdapter;
import thefirstchange.example.com.communicationtext.showcomment.bean.CommentBean;
import thefirstchange.example.com.communicationtext.showcomment.view.CommentExpandableListView;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;
import thefirstchange.example.com.communicationtext.util.TimeUtil;

public class CommentShowActivity extends BaseForCloseActivity implements View.OnClickListener,MessagePicturesLayout.Callback, ImageWatcher.OnPictureLongPressListener{
    private static final String TAG = "CommentShowActivity";
    private static String MY = "thefirstchange.example.com.communicationtext.COMMENTSHOW";
    private Timer timer = new Timer();

    private CircleImageView fromIv;
    private TextView fromNickName;
    private TextView showSendTime;
    private TextView fromSchool;
    private TextView showContent;
    private ImageNice9Layout imageNice9Layout;
    MessagePicturesLayout llPictures;
    private LinearLayout resendLay;
    private LinearLayout commentLay;
    private LinearLayout favLay;

    private ImageView finishSelf;


    private TextView bt_comment;
    private CommentExpandableListView expandableListView;
    private CommentExpandAdapter adapter;
    private CommentBean commentBean;
    private List<DTComRoot> commentsList;
    private BottomSheetDialog dialog;

    private MessagePicturesLayout.Callback mCallback;
    private static ImageWatcher vImageWatcher;
    boolean isTranslucentStatus;

    private int showId;
    private String testJson = "{\n" +
            "\t\"code\": 1000,\n" +
            "\t\"message\": \"查看评论成功\",\n" +
            "\t\"data\": {\n" +
            "\t\t\"total\": 3,\n" +
            "\t\t\"list\": [{\n" +
            "\t\t\t\t\"id\": 42,\n" +
            "\t\t\t\t\"nickName\": \"程序猿\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"时间是一切财富中最宝贵的财富。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 1,\n" +
            "\t\t\t\t\"createDate\": \"三分钟前\",\n" +
            "\t\t\t\t\"replyList\": [{\n" +
            "\t\t\t\t\t\"nickName\": \"沐風\",\n" +
            "\t\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\t\"id\": 40,\n" +
            "\t\t\t\t\t\"commentId\": \"42\",\n" +
            "\t\t\t\t\t\"content\": \"时间总是在不经意中擦肩而过,不留一点痕迹.\",\n" +
            "\t\t\t\t\t\"status\": \"01\",\n" +
            "\t\t\t\t\t\"createDate\": \"一个小时前\"\n" +
            "\t\t\t\t}]\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": 41,\n" +
            "\t\t\t\t\"nickName\": \"设计狗\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"这世界要是没有爱情，它在我们心中还会有什么意义！这就如一盏没有亮光的走马灯。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 1,\n" +
            "\t\t\t\t\"createDate\": \"一天前\",\n" +
            "\t\t\t\t\"replyList\": [{\n" +
            "\t\t\t\t\t\"nickName\": \"留话\",\n" +
            "\t\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\t\"commentId\": \"41\",\n" +
            "\t\t\t\t\t\"content\": \"时间总是在不经意中擦肩而过,不留一点痕迹.\",\n" +
            "\t\t\t\t\t\"status\": \"01\",\n" +
            "\t\t\t\t\t\"createDate\": \"三小时前\"\n" +
            "\t\t\t\t}]\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": 40,\n" +
            "\t\t\t\t\"nickName\": \"产品喵\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"笨蛋自以为聪明，聪明人才知道自己是笨蛋。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 0,\n" +
            "\t\t\t\t\"createDate\": \"三天前\",\n" +
            "\t\t\t\t\"replyList\": []\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t}\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_show);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);
        Intent intent=getIntent();
        showId=intent.getIntExtra("showid",-1);
        initView();
    }

    private void initView() {

        Dongtai dongtai = new Dongtai();
        for(int i=0;i< StaticAllList.dongtais.size();++i){
            if(StaticAllList.dongtais.get(i).getId()==showId){
                dongtai= StaticAllList.dongtais.get(i);
                break;
            }
        }

        expandableListView = (CommentExpandableListView) findViewById(R.id.detail_page_lv_comment);
        bt_comment = (TextView) findViewById(R.id.detail_page_do_comment);
        bt_comment.setOnClickListener(this);
        generateTestData();
        initExpandableListView();
        fromIv=(CircleImageView)findViewById(R.id.comment_show_iv);
        fromNickName=(TextView)findViewById(R.id.comment_show_nickname);
        fromNickName.setText(dongtai.getSdname());
        showSendTime=(TextView)findViewById(R.id.comment_show_send_time);
        showSendTime.setText(TimeUtil.getChatTime(dongtai.getTime()));

        fromSchool=(TextView)findViewById(R.id.comment_show_school);
       // fromSchool.setText(dongtai.get);
        showContent=(TextView)findViewById(R.id.comment_show_text);
        if (dongtai.getContent()==null||dongtai.getContent().isEmpty()){
            showContent.setVisibility(View.GONE);
        }else {
            showContent.setText(dongtai.getContent());
        }
//        imageNice9Layout=(ImageNice9Layout)findViewById(R.id.comment_show_nice9);
        setPictureClickCallback(mCallback);
        llPictures = (MessagePicturesLayout) findViewById(R.id.ll_picture);
        llPictures.setCallback(mCallback);
        llPictures.set(dongtai.getImph(),dongtai.getImph());

        resendLay=(LinearLayout)findViewById(R.id.comment_show_resend_lay);
        resendLay.setOnClickListener(this);
        commentLay=(LinearLayout)findViewById(R.id.comment_show_comment_lay);
        commentLay.setOnClickListener(this);
        favLay=(LinearLayout)findViewById(R.id.comment_show_fav_lay);
        favLay.setOnClickListener(this);
        finishSelf=(ImageView)findViewById(R.id.finish_show_datil);
        finishSelf.setOnClickListener(this);
        expandableListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (i+i1==i2&&i2>0){
                   // adapter.setLoadState(adapter.LOADING);

                }

            }
        });



        vImageWatcher = ImageWatcher.Helper.with(this) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setTranslucentStatus(!isTranslucentStatus ? Utils.calcStatusBarHeight(this) : 0) // 如果是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
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
    }

    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(){
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        if(!StaticAllList.dTComRootMap.containsKey(showId)){
            StaticAllList.dTComRootMap.put(showId,new Vector<DTComRoot>());
        }
        adapter = new CommentExpandAdapter(this, StaticAllList.dTComRootMap.get(showId));
        expandableListView.setAdapter(adapter);
        for(int i = 0; i<StaticAllList.dTComRootMap.get(showId).size(); i++){
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
              //  Log.e(TAG, "onGroupClick: 当前的评论id>>>"+commentList.get(groupPosition).getId());
//                if(isExpanded){
//                    expandableListView.collapseGroup(groupPosition);
//                }else {
//                    expandableListView.expandGroup(groupPosition, true);
//                }
                showReplyDialog(groupPosition);
                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Toast.makeText(CommentShowActivity.this,"点击了回复", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");

            }
        });

    }

    /**
     * by moos on 2018/04/20
     * func:生成测试数据
     * @return 评论数据
     */
    private void generateTestData(){


                SendToServer.getNewComByDongtaiId(showId);



//        Gson gson = new Gson();
//        commentBean = gson.fromJson(testJson, CommentBean.class);
//        List<CommentDetailBean> commentList = commentBean.getData().getList();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.detail_page_do_comment:
                showCommentDialog();
                break;
            case R.id.comment_show_resend_lay:
                break;
            case R.id.comment_show_comment_lay:
                break;
            case R.id.comment_show_fav_lay:
                break;
            case R.id.finish_show_datil:
                finish();
                break;
                default:
                    break;
        }
    }

    /**
     * by moos on 2018/04/20
     * func:弹出评论框
     */
    private void showCommentDialog(){
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(!NetworkUtils.isConnected(CommentShowActivity.this)){
                    Toast.makeText(CommentShowActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){

                    //commentOnWork(commentContent);

                    dialog.dismiss();

                    SendToServer.dongtaiComment(showId,commentContent);

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            CommentShowActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CommentShowActivity.this,"服务器繁忙,请稍后再试1",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    },3000);

//                    CommentDetailBean detailBean = new CommentDetailBean("小明", commentContent,"刚刚");
//                    adapter.addTheCommentData(detailBean);
                    Toast.makeText(CommentShowActivity.this,"评论成功",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(CommentShowActivity.this,"评论内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    /**
     * by moos on 2018/04/20
     * func:弹出回复框
     */
    private void showReplyDialog(final int position){
//        dialog = new BottomSheetDialog(this);
//        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
//        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
//        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
//       // commentText.setHint("回复 " + commentsList.get(position).getNickName() + " 的评论:");
//        dialog.setContentView(commentView);
//        bt_comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String replyContent = commentText.getText().toString().trim();
//                if(!TextUtils.isEmpty(replyContent)){
//
//                    dialog.dismiss();
//                    ReplyDetailBean detailBean = new ReplyDetailBean("小红",replyContent);
//                    adapter.addTheReplyData(detailBean, position);
//                    expandableListView.expandGroup(position);
//                    Toast.makeText(CommentShowActivity.this,"回复成功",Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(CommentShowActivity.this,"回复内容不能为空",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        commentText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
//                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
//                }else {
//                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        dialog.show();
    }


//    public LifeShowAdapter setPictureClickCallback(MessagePicturesLayout.Callback callback) {
//        mCallback = callback;
//        return this;
//    }

    @Override
    public void onThumbPictureClick(ImageView i, List<ImageView> imageGroupList, List<String> urlList) {
        vImageWatcher.show(i, imageGroupList, urlList);
    }

    @Override
    public void onPictureLongPress(ImageView v, String url, int pos) {
        Toast.makeText(v.getContext().getApplicationContext(), "长按了第" + (pos + 1) + "张图片", Toast.LENGTH_SHORT).show();
    }

    //记得重写返回键哦

    public void onBackPressed() {
        if (!vImageWatcher.handleBackPressed()) {
            super.onBackPressed();
        }
    }

    public void setPictureClickCallback(MessagePicturesLayout.Callback callback) {
        mCallback = callback;
    }


    protected void onResume(){

        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MY);
        registerReceiver(broadcastReceiver,intentFilter);


    }

    public void onDestroy(){
        super.onDestroy();

        //unbindService(connection);

        //stopService(bindIntent);
        unregisterReceiver(broadcastReceiver);
        if(timer!=null){
            timer.cancel();
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MY)){

                String type = intent.getStringExtra("type");
                if(type.equals("dtComRs")){
                    timer.cancel();
                    String rs = intent.getStringExtra("rs");
                    if(rs.equals("ok")){



                    }else{
                        Toast.makeText(CommentShowActivity.this,"服务器繁忙,请稍后再试!",Toast.LENGTH_SHORT).show();

                    }

                    //进入某动态的所有评论界面    刚开始要刷新   返回根评论总共10条   大的评论下最多回执3条
                }else if(type.equals("gNComByDTidRS")){
                    adapter.notifyDataSetChanged();

                    //进入某动态的所有评论界面    刚开始要刷新   返回根评论总共10条   大的评论下最多回执3条  获得头像
                }else if(type.equals("getRtComUIC")){
                    adapter.notifyDataSetChanged();

                }

            }

        }
    };

}
