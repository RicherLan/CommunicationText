package thefirstchange.example.com.communicationtext.group;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.activity.LocationActivity;
import thefirstchange.example.com.communicationtext.adapter.EmoViewPagerAdapter;
import thefirstchange.example.com.communicationtext.adapter.EmoteAdapter;
import thefirstchange.example.com.communicationtext.adapter.GroupNotificationMsgsAdapter;
import thefirstchange.example.com.communicationtext.adapter.NotificationMsgsAdapter;
import thefirstchange.example.com.communicationtext.config.WithOtherConstants;
import thefirstchange.example.com.communicationtext.entity.FaceText;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.service.SingleChatMsg;
import thefirstchange.example.com.communicationtext.util.CommonUtils;
import thefirstchange.example.com.communicationtext.util.FaceTextUtils;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;
import thefirstchange.example.com.communicationtext.widget.AudioRecorderButton;
import thefirstchange.example.com.communicationtext.widget.EmoticonsEditText;
import thefirstchange.example.com.communicationtext.widget.xlistview.XListView;

public class GroupChatActivity extends BaseForCloseActivity implements View.OnClickListener,XListView.IXListViewListener{

    private Button btn_chat_emo, btn_chat_send, btn_chat_add,btn_chat_keyboard, btn_chat_voice;
    private AudioRecorderButton btn_speak;
    EmoticonsEditText edit_user_comment;
    private LinearLayout layout_more, layout_emo, layout_add;
    private ViewPager pager_emo;
    List<FaceText> emos;
    private TextView tv_picture, tv_camera, tv_location;
    XListView mListView;
    private static int MsgPagerNum;
    private Drawable[] drawable_Anims;

    private List<SingleChatMsg> mList=new ArrayList<>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText connectionEdit;
    private Button sendButton;
    private ImageView finishImage;
    private ImageView toHisHpmepage;
    private ImageView finishSelf;
    private ImageView toHisPage;
    private int groupId;
    private String groupName;
    private TextView receiverNameText;

    private  List<ChatMsg> list= new ArrayList<>();
    private static final String MYACTION="thefirstchange.example.com.communicationtext.GROUP_MSG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_contact);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);
        Intent intent=getIntent();
        groupId =intent.getIntExtra("groupId",-1);
        groupName =intent.getStringExtra("groupname");

        receiverNameText=(TextView)findViewById(R.id.group_name);
        receiverNameText.setText(groupName);


        initView();


    }

//    private void initMsg(){
//
//
//            Vector<SingleChatMsg> singleChatMsgs =  MyMessageQueue.SinglechatQueueNotRead.get(friendid);
//            int msgNums = singleChatMsgs.size();             //该用户给你发的未读的消息数量
//            SingleChatMsg singleChatMsg = singleChatMsgs.get(msgNums-1);               //理论上最后的是最大的
//            arrayList.add(singleChatMsg);
//            msgNumsArrayList.add(msgNums);
//        }
//    }

    private void
    initView(){
        finishSelf=(ImageView)findViewById(R.id.finish_group);
        finishSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toHisPage=(ImageView)findViewById(R.id.go_group_info);
        toHisPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GroupChatActivity.this,GroupPageActivity.class);
                intent.putExtra("groupid",groupId);
                //intent.putExtra("from","otherpeople");
                startActivity(intent);
            }
        });
        //mHeaderLayout = (HeaderLayout) findViewById(R.id.common_actionbar);
        mListView = (XListView) findViewById(R.id.mListView);
        //initTopBarForLeft("与" + "小兰" + "对话");

        //将聊天框加到聊天框队列
//        MyMessageQueue.chatframes.put(groupId+"","user");

        initBottomView();
        initXListView();
        loadMsgRecord("first");
        initRecorder();
        initMsgData();
    }



    private void initRecorder(){
        btn_speak = (AudioRecorderButton) findViewById(R.id.btn_speak);
        btn_speak.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {

                if(!NetworkUtils.isConnected(GroupChatActivity.this)){
                    Toast.makeText(GroupChatActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ChatMsg chatMsg = new ChatMsg();
                chatMsg.setType("group");
                chatMsg.setMsgid(-1);
                chatMsg.setSenderid(ObjectService.personalInfo.getPhonenumber());
                chatMsg.setSendername(ObjectService.personalInfo.getNickname());
                chatMsg.setGroupid(groupId);
                chatMsg.setMsgtype("voice");
                chatMsg.setMsgbody(filePath);
                chatMsg.setMsgtime(System.currentTimeMillis());
                chatMsg.setVoicetime(seconds);
                chatMsg.setHandleRs("hadread");

                for(int i: StaticAllList.groupList.keySet()){
                    if(groupId==StaticAllList.groupList.get(i).getGroupid()){
                        String groupname = StaticAllList.groupList.get(i).getGroupnickname();
                        chatMsg.setSendername(groupname);
                    }
                }

                boolean firstNotice = true;
                if( MyMessageQueue.chatframes.containsKey(groupId+"")){
                    firstNotice = false;
                }else{
                    //加入到聊天框队列
                    MyMessageQueue.chatframes.put(groupId+"","group");
                }

                if(MyMessageQueue.chatQueueHadRead.containsKey( groupId+"" ))  {
                    MyMessageQueue.chatQueueHadRead.get(groupId+"").add(chatMsg);
                }else{
                    MyMessageQueue.chatQueueHadRead.put(groupId+"",new Vector<ChatMsg>());
                    MyMessageQueue.chatQueueHadRead.get(groupId+"").add(chatMsg);
                }

                SendToServer.sendergroupchatfile(chatMsg);
                mAdapter.add(chatMsg);

                mListView.setSelection(mAdapter.getCount() - 1);

                if(StaticAllList.chatRecordDao!=null){
                    int id = StaticAllList.chatRecordDao.saveGroup(chatMsg);
                    chatMsg.setId(id);
                }else{
                    MainActivity.initChatRecorddb();
                    int id = StaticAllList.chatRecordDao.saveGroup(chatMsg);
                    chatMsg.setId(id);
                }

                if(firstNotice){
                    if(StaticAllList.chatRecordDao!=null){
                        StaticAllList.chatRecordDao.saveNoticeFrame("group","",groupId);
                    }else{
                        MainActivity.initChatRecorddb();
                        StaticAllList.chatRecordDao.saveNoticeFrame("group","",groupId);
                    }
                }

            }
        });

    }


    private void initBottomView() {

        btn_chat_add = (Button) findViewById(R.id.btn_chat_add);
        btn_chat_emo = (Button) findViewById(R.id.btn_chat_emo);
        btn_chat_add.setOnClickListener(this);
        btn_chat_emo.setOnClickListener(this);

        btn_chat_keyboard = (Button) findViewById(R.id.btn_chat_keyboard);
        btn_chat_voice = (Button) findViewById(R.id.btn_chat_voice);
        btn_chat_voice.setOnClickListener(this);
        btn_chat_keyboard.setOnClickListener(this);
        btn_chat_send = (Button) findViewById(R.id.btn_chat_send);
        btn_chat_send.setOnClickListener(this);

        layout_more = (LinearLayout) findViewById(R.id.layout_more);
        layout_emo = (LinearLayout) findViewById(R.id.layout_emo);
        layout_add = (LinearLayout) findViewById(R.id.layout_add);
        initAddView();
        initEmoView();




        edit_user_comment = (EmoticonsEditText) findViewById(R.id.edit_user_comment);
        edit_user_comment.setOnClickListener(this);
        edit_user_comment.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (!TextUtils.isEmpty(s)) {
                    btn_chat_send.setVisibility(View.VISIBLE);
                    btn_chat_keyboard.setVisibility(View.GONE);
                    btn_chat_add.setVisibility(View.GONE);
                }
                else {
                    btn_chat_add.setVisibility(View.VISIBLE);
                    btn_chat_send.setVisibility(View.GONE);

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

    }


    private void initEmoView() {
        pager_emo = (ViewPager) findViewById(R.id.pager_emo);
        emos = FaceTextUtils.faceTexts;

        List<View> views = new ArrayList<View>();
        for (int i = 0; i < 2; ++i) {
            views.add(getGridView(i));
        }
        pager_emo.setAdapter(new EmoViewPagerAdapter(views));
    }

    private View getGridView(final int i) {
        View view = View.inflate(this, R.layout.include_emo_gridview, null);
        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        List<FaceText> list = new ArrayList<FaceText>();
        if (i == 0) {
            list.addAll(emos.subList(0, 21));
        } else if (i == 1) {
            list.addAll(emos.subList(21, emos.size()));
        }
        final EmoteAdapter gridAdapter = new EmoteAdapter(GroupChatActivity.this,
                list);
        gridview.setAdapter(gridAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                FaceText name = (FaceText) gridAdapter.getItem(position);
                String key = name.text.toString();
                try {
                    if (edit_user_comment != null && !TextUtils.isEmpty(key)) {
                        int start = edit_user_comment.getSelectionStart();
                        CharSequence content = edit_user_comment.getText()
                                .insert(start, key);
                        edit_user_comment.setText(content);

                        CharSequence info = edit_user_comment.getText();
                        if (info instanceof Spannable) {
                            Spannable spanText = (Spannable) info;
                            Selection.setSelection(spanText,
                                    start + key.length());
                        }
                    }
                } catch (Exception e) {

                }

            }
        });
        return view;
    }

    GroupNotificationMsgsAdapter mAdapter;

    private void initAddView() {
        tv_picture = (TextView) findViewById(R.id.tv_picture);
        tv_camera = (TextView) findViewById(R.id.tv_camera);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_picture.setOnClickListener(this);
        tv_location.setOnClickListener(this);
        tv_camera.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.edit_user_comment:
                // mListView.setSelection(mListView.getCount() - 1);
                if (layout_more.getVisibility() == View.VISIBLE) {
                    layout_add.setVisibility(View.GONE);
                    layout_emo.setVisibility(View.GONE);
                    layout_more.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_chat_emo:
                if (layout_more.getVisibility() == View.GONE) {
                    showEditState(true);
                } else {
                    if (layout_add.getVisibility() == View.VISIBLE) {
                        layout_add.setVisibility(View.GONE);
                        layout_emo.setVisibility(View.VISIBLE);
                    } else {
                        layout_more.setVisibility(View.GONE);
                    }
                }

                break;


            case R.id.btn_chat_keyboard:
                showEditState(false);
                break;

            case R.id.btn_chat_send:

                if(!NetworkUtils.isConnected(GroupChatActivity.this)){
                    Toast.makeText(GroupChatActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String msg = edit_user_comment.getText().toString();
                if (msg.equals("")) {
                    Toast.makeText(GroupChatActivity.this,"消息不能为空!",Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
                if (!isNetConnected) {
                    Toast.makeText(GroupChatActivity.this,"当前网络不可用，请检查您的网络!",Toast.LENGTH_SHORT).show();
                    // return;
                }


                ChatMsg groupChatMsg=new ChatMsg();
                groupChatMsg.setType("group");
                groupChatMsg.setGroupid(groupId);
                groupChatMsg.setMsgbody(msg);
                groupChatMsg.setMsgid(0);
                groupChatMsg.setMsgtime(System.currentTimeMillis());
                groupChatMsg.setMsgtype("text");
                groupChatMsg.setSenderid(ObjectService.personalInfo.getPhonenumber());
                groupChatMsg.setHandleRs("hadread");

                for(int i: StaticAllList.groupList.keySet()){
                    if(groupId==StaticAllList.groupList.get(i).getGroupid()){
                        String groupname = StaticAllList.groupList.get(i).getGroupnickname();
                        groupChatMsg.setSendername(groupname);
                    }
                }

                boolean firstNotice = true;
                if(MyMessageQueue.chatframes.containsKey(groupId+"")){
                    firstNotice = false;
                }else{
                    //加入到聊天框队列
                    MyMessageQueue.chatframes.put(groupId+"","group");
                }

                if(MyMessageQueue.chatQueueHadRead.containsKey( groupId+""))  {
                    MyMessageQueue.chatQueueHadRead.get(groupId+"").add(groupChatMsg);
                }else{
                    MyMessageQueue.chatQueueHadRead.put(groupId+"",new Vector<ChatMsg>());
                    MyMessageQueue.chatQueueHadRead.get(groupId+"").add(groupChatMsg);
                }

                SendToServer.sendergroupchattext(groupChatMsg);
                refreshMessage(groupChatMsg);

                if(StaticAllList.chatRecordDao!=null){
                    int id = StaticAllList.chatRecordDao.saveGroup(groupChatMsg);
                    groupChatMsg.setId(id);
                }else{
                    MainActivity.initChatRecorddb();
                    int id = StaticAllList.chatRecordDao.saveGroup(groupChatMsg);
                    groupChatMsg.setId(id);
                }

                if(firstNotice){
                    if(StaticAllList.chatRecordDao!=null){
                        StaticAllList.chatRecordDao.saveNoticeFrame("group","",groupId);
                    }else{
                        MainActivity.initChatRecorddb();
                        StaticAllList.chatRecordDao.saveNoticeFrame("group","",groupId);
                    }
                }

                break;

            case R.id.btn_chat_add:
                if (layout_more.getVisibility() == View.GONE) {
                    layout_more.setVisibility(View.VISIBLE);
                    layout_add.setVisibility(View.VISIBLE);
                    layout_emo.setVisibility(View.GONE);
                    hideSoftInputView();
                } else {
                    if (layout_emo.getVisibility() == View.VISIBLE) {
                        layout_emo.setVisibility(View.GONE);
                        layout_add.setVisibility(View.VISIBLE);
                    } else {
                        layout_more.setVisibility(View.GONE);
                    }
                }

                break;
            case R.id.btn_chat_voice:
                edit_user_comment.setVisibility(View.GONE);
                layout_more.setVisibility(View.GONE);
                btn_chat_voice.setVisibility(View.GONE);
                btn_chat_keyboard.setVisibility(View.VISIBLE);
                btn_speak.setVisibility(View.VISIBLE);
                hideSoftInputView();
                break;
            case R.id.tv_camera:
                selectImageFromCamera();
                break;
            case R.id.tv_picture:
                selectImageFromLocal();
                break;
            case R.id.tv_location:
                selectLocationFromMap();
                break;
            default:
                break;

        }

    }

    private void selectLocationFromMap() {
        Intent intent = new Intent(this, LocationActivity.class);
        intent.putExtra("type", "select");
        startActivityForResult(intent, WithOtherConstants.REQUESTCODE_TAKE_LOCATION);
    }


    private void sendLocationMessage(String address, double latitude,
                                     double longtitude) {
        if (layout_more.getVisibility() == View.VISIBLE) {
            layout_more.setVisibility(View.GONE);
            layout_add.setVisibility(View.GONE);
            layout_emo.setVisibility(View.GONE);
        }

//        BmobMsg message = BmobMsg.createLocationSendMsg(this, targetId,
//                address, latitude, longtitude);
//
//        manager.sendTextMessage(targetUser, message);
//        Notifications notification=new Notifications();
//        notification.setMsgTime(System.currentTimeMillis()/1000);
//        notification.setType(NotificationMsgConfig.TYPE_LOCATION);
//        notification.setBelongId("1");
//        notification.setStatus(NotificationMsgConfig.STATUS_SEND_SUCCESS);
//        notification.setInfo(address);
//                mAdapter.add(notification);
//        mListView.setSelection(mAdapter.getCount() - 1);
    }

    private String localCameraPath = "";

    public void selectImageFromCamera() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dir = new File(WithOtherConstants.BMOB_PICTURE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, String.valueOf(System.currentTimeMillis())
                + ".jpg");
        localCameraPath = file.getPath();
        Uri imageUri = Uri.fromFile(file);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent,
                WithOtherConstants.REQUESTCODE_TAKE_CAMERA);
    }

    public void selectImageFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, WithOtherConstants.REQUESTCODE_TAKE_LOCAL);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case WithOtherConstants.REQUESTCODE_TAKE_CAMERA:
                    sendImageMessage(localCameraPath);
                    break;
                case WithOtherConstants.REQUESTCODE_TAKE_LOCAL:
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(
                                    selectedImage, null, null, null, null);
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex("_data");
                            String localSelectPath = cursor.getString(columnIndex);
                            cursor.close();
                            if (localSelectPath == null
                                    || localSelectPath.equals("null")) {
                                Toast.makeText(GroupChatActivity.this,"找不到图片",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            sendImageMessage(localSelectPath);
                        }
                    }
                    break;
                case WithOtherConstants.REQUESTCODE_TAKE_LOCATION:
                    double latitude = data.getDoubleExtra("x", 0);
                    double longtitude = data.getDoubleExtra("y", 0);
                    String address = data.getStringExtra("address");
                    if (address != null && !address.equals("")) {
                        sendLocationMessage(address, latitude, longtitude);
                    } else {
                        Toast.makeText(GroupChatActivity.this,"无法获取到您的位置信息!",Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    }


    private void sendImageMessage(String local) {
        if (layout_more.getVisibility() == View.VISIBLE) {
            layout_more.setVisibility(View.GONE);
            layout_add.setVisibility(View.GONE);
            layout_emo.setVisibility(View.GONE);
        }

        if(!NetworkUtils.isConnected(GroupChatActivity.this)){
            Toast.makeText(GroupChatActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setType("group");
        chatMsg.setMsgid(-1);
        chatMsg.setGroupid(groupId);
        chatMsg.setSenderid(ObjectService.personalInfo.getPhonenumber());
        chatMsg.setSendername(ObjectService.personalInfo.getPhonenumber());
        chatMsg.setMsgtype("image");
        chatMsg.setMsgbody(local);
        chatMsg.setMsgtime(System.currentTimeMillis());
        chatMsg.setVoicetime(-1);
        chatMsg.setHandleRs("hadread");

        for(int i: StaticAllList.groupList.keySet()){
            if(groupId==(StaticAllList.groupList.get(i).getGroupid())){
                String groupname = StaticAllList.groupList.get(i).getGroupnickname();
                chatMsg.setSendername(groupname);
            }
        }

        boolean firstNotice = true;
        if( MyMessageQueue.chatframes.containsKey(groupId+"")){
            firstNotice = false;
        }else{
            //加入到聊天框队列
            MyMessageQueue.chatframes.put(groupId+"","group");
        }

        if(MyMessageQueue.chatQueueHadRead.containsKey( groupId+"" ))  {
            MyMessageQueue.chatQueueHadRead.get(groupId+"").add(chatMsg);
        }else{
            MyMessageQueue.chatQueueHadRead.put(groupId+"",new Vector<ChatMsg>());
            MyMessageQueue.chatQueueHadRead.get(groupId+"").add(chatMsg);
        }

        SendToServer.sendergroupchatfile(chatMsg);
        mAdapter.add(chatMsg);
        mListView.setSelection(mAdapter.getCount() - 1);

        mAdapter.notifyDataSetChanged();

        if(StaticAllList.chatRecordDao!=null){
            int id = StaticAllList.chatRecordDao.saveGroup(chatMsg);
            chatMsg.setId(id);
        }else{
            MainActivity.initChatRecorddb();
            int id = StaticAllList.chatRecordDao.saveGroup(chatMsg);
            chatMsg.setId(id);
        }

        if(firstNotice){
            if(StaticAllList.chatRecordDao!=null){
                StaticAllList.chatRecordDao.saveNoticeFrame("group","",groupId);
            }else{
                MainActivity.initChatRecorddb();
                StaticAllList.chatRecordDao.saveNoticeFrame("group","",groupId);
            }
        }
    }

    private void showEditState(boolean isEmo) {
        edit_user_comment.setVisibility(View.VISIBLE);
        btn_chat_keyboard.setVisibility(View.GONE);
        btn_chat_voice.setVisibility(View.VISIBLE);
        btn_speak.setVisibility(View.GONE);
        edit_user_comment.requestFocus();
        if (isEmo) {
            layout_more.setVisibility(View.VISIBLE);
            layout_more.setVisibility(View.VISIBLE);
            layout_emo.setVisibility(View.VISIBLE);
            layout_add.setVisibility(View.GONE);
            hideSoftInputView();
        } else {
            layout_more.setVisibility(View.GONE);
            showSoftInputView();
        }
    }

    private void initXListView() {

        mListView.setPullLoadEnable(false);

        mListView.setPullRefreshEnable(true);

        mListView.setXListViewListener(this);
        mListView.pullRefreshing();
        mListView.setDividerHeight(0);

        initOrRefresh();
        mListView.setSelection(mAdapter.getCount() - 1);
        mListView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                hideSoftInputView();
                layout_more.setVisibility(View.GONE);
                layout_add.setVisibility(View.GONE);
                btn_chat_voice.setVisibility(View.VISIBLE);
                btn_chat_keyboard.setVisibility(View.GONE);
                btn_chat_send.setVisibility(View.GONE);
                return false;
            }
        });


//        mAdapter.setOnInViewClickListener(R.id.iv_fail_resend,
//                new MessageChatAdapter.onInternalClickListener() {
//
//                    @Override
//                    public void OnClickListener(View parentV, View v,
//                                                Integer position, Object values) {
//
//                        showResendDialog(parentV, v, values);
//                    }
//                });
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnInViewClickListener(R.id.iv_fail_resend,new NotificationMsgsAdapter.onInternalClickListener(){
            public void OnClickListener(View parentV, View v,
                                        Integer position, Object values) {

//                showResendDialog(parentV, v, values);
            }
        });
    }

//    public void showResendDialog(final View parentV, View v, final Object values) {
//        DialogTips dialog = new DialogTips(this, "重新发送该消息", "是", "不", "提示",
//                true);
//        // ���óɹ��¼�
//        dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialogInterface, int userId) {
//                if (((BmobMsg) values).getMsgType() == BmobConfig.TYPE_IMAGE
//                        || ((BmobMsg) values).getMsgType() == BmobConfig.TYPE_VOICE) {// ͼƬ���������͵Ĳ���
//                    resendFileMsg(parentV, values);
//                } else {
//                    resendTextMsg(parentV, values);
//                }
//                dialogInterface.dismiss();
//            }
//        });
//
//        dialog.show();
//        dialog = null;
//    }

    private void initMsgData() {
        list.clear();
        Vector<ChatMsg> chatMsgHadReadVector=MyMessageQueue.chatQueueHadRead.get(groupId+"");  //聊天的历史记录
        Vector<ChatMsg> chatMsgNotReadVector=MyMessageQueue.chatQueueNotRead.get(groupId+"");  //未读信息


        if(chatMsgHadReadVector!=null){
            for(int i=0;i<chatMsgHadReadVector.size();++i) {
                list.add(chatMsgHadReadVector.get(i));
            }
        }

        if(chatMsgNotReadVector!=null){
            for(int i=0;i<chatMsgNotReadVector.size();++i) {
                ChatMsg chatMsg = chatMsgNotReadVector.get(i);
                chatMsg.setHandleRs("hadread");
                list.add(chatMsg);

                //将未读消息加入到已读消息队列
                if(MyMessageQueue.chatQueueHadRead.containsKey(groupId+"")){
                    MyMessageQueue.chatQueueHadRead.get(groupId+"").add(chatMsg);
                }else {
                    MyMessageQueue.chatQueueHadRead.put(groupId+"", new Vector<ChatMsg>());
                    MyMessageQueue.chatQueueHadRead.get(groupId+"").add(chatMsg);
                }


            }

//            if(StaticAllList.chatRecordDao!=null){
//                StaticAllList.chatRecordDao.changeGroupsStateHadRead(chatMsgNotReadVector);
//            }else{
//                MainActivity.initChatRecorddb();
//                StaticAllList.chatRecordDao.changeGroupsStateHadRead(chatMsgNotReadVector);
//            }

            changeHasRead(chatMsgNotReadVector);

            //未读消息已读
            MyMessageQueue.chatQueueNotRead.remove(groupId+"");

        }

        mListView.setSelection(mAdapter.getCount() - 1);
        MainActivity.changemsgnotreadnum();

    }


    private void initOrRefresh() {
        if (mAdapter != null) {
//
        } else {
            mAdapter = new GroupNotificationMsgsAdapter(this, list,groupId);
            mListView.setAdapter(mAdapter);
        }
    }



    public void showSoftInputView() {
        if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .showSoftInput(edit_user_comment, 0);
        }
    }

    private void refreshMessage(ChatMsg msg) {

        mAdapter.add(msg);
        mListView.setSelection(mAdapter.getCount() - 1);
        edit_user_comment.setText("");
    }






    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == NEW_MESSAGE) {
                ChatMsg message = (ChatMsg) msg.obj;
                String uid = message.getSenderid();
                //BmobMsg m = BmobChatManager.getInstance(ChatActivity.this).getMessage(message.getConversationId(), message.getMsgTime());
//                if (!uid.equals(targetId))
//                    return;
                mAdapter.add(message);

                mListView.setSelection(mAdapter.getCount() - 1);


            }
        }
    };

    public void onLoadMore() {
        // TODO Auto-generated method stub
    }

    public static final int NEW_MESSAGE = 0x001;

    public void onRefresh() {
        // TODO Auto-generated method stub
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                loadMsgRecord("");
            }
        }, 1000);
    }

//    type是 刚进去  刷新 还是  进去后手动刷新
    private void loadMsgRecord(String type){
        int currents = list.size();

        int gid = -1;
        if(MyMessageQueue.chatQueueHadRead.containsKey(groupId+"")){
            gid = MyMessageQueue.chatQueueHadRead.get(groupId+"").get(0).getId();
        }
        if(MyMessageQueue.chatQueueNotRead.containsKey(groupId+"")){
            gid = MyMessageQueue.chatQueueNotRead.get(groupId+"").get(0).getId();
        }
        Vector<ChatMsg> chatMsgs = StaticAllList.chatRecordDao.getGroupListLimit(type,gid,groupId);
        if(chatMsgs==null||chatMsgs.size()==0){
            mListView.stopRefresh();
            return;
        }


        for(int i=0;i<chatMsgs.size();++i){
            boolean add = true;
            if(chatMsgs.get(i).getHandleRs().equals("hadread")){
                if(!MyMessageQueue.chatQueueHadRead.containsKey(groupId+"")){
                    MyMessageQueue.chatQueueHadRead.put(groupId+"",new Vector<ChatMsg>());
                }

                for(ChatMsg chatMsg:MyMessageQueue.chatQueueHadRead.get(groupId+"")){
                    if(chatMsg.getMsgid()==chatMsgs.get(i).getMsgid()){
                        add=false;
                        break;
                    }
                }
                if(add){
                    MyMessageQueue.chatQueueHadRead.get(groupId+"").add(0,chatMsgs.get(i));
                }

            }else{
                if(!MyMessageQueue.chatQueueNotRead.containsKey(groupId+"")){
                    MyMessageQueue.chatQueueNotRead.put(groupId+"",new Vector<ChatMsg>());
                }
                for(ChatMsg chatMsg:MyMessageQueue.chatQueueNotRead.get(groupId+"")){
                    if(chatMsg.getMsgid()==chatMsgs.get(i).getMsgid()){
                        add=false;
                        break;
                    }
                }
                if(add){
                    MyMessageQueue.chatQueueNotRead.get(groupId+"").add(0,chatMsgs.get(i));
                }

            }

            if(add&&!type.equals("first")){
                list.add(0,chatMsgs.get(i));
            }
        }

//                MsgPagerNum++;
//                int total =mAdapter.getCount();
//                //BmobLog.i("记录总数" + total);
//                int currents = mAdapter.getCount();
//                if (total <= currents) {
//                    Toast.makeText(GroupChatActivity.this,"聊天记录加载完成!",Toast.LENGTH_SHORT).show();
//                } else {
//                    mAdapter.setList(list);
        mAdapter.notifyDataSetChanged();
        mListView.setSelection(mAdapter.getCount() - currents - 1);
//                }
        mListView.stopRefresh();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (layout_more.getVisibility() == View.VISIBLE) {
                layout_more.setVisibility(View.GONE);
                return false;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void onResume(){
        super.onResume();
        Log.e("ConnectionOtherActivity","onResume");

        IntentFilter filter = new IntentFilter();

        filter.addAction(MYACTION);

        registerReceiver(MyMessageReceiver, filter);
//        Intent intent=new Intent("thefirstchange.example.com.communicationtext.MAIN_LOOK");
//        sendBroadcast(intent);
    }
    protected void onStop(){
        super.onStop();
        Log.e("ConnectionOtherActivity","onStop");
//        NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//        Notification notification=new NotificationCompat.Builder(this)
//                .setContentTitle("消息")
//                .setContentTitle("kjfdkjfkd")
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
//                .setAutoCancel(true)
//                .setDefaults(NotificationCompat.DEFAULT_ALL)
//                .setPriority(NotificationCompat.PRIORITY_MAX)
//                .build();
//        manager.notify(1,notification);
    }

    BroadcastReceiver MyMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MYACTION)){
                String type = intent.getStringExtra("type");
                if(type.equals("sendergroupchatfileResult")){
                    String rs = intent.getStringExtra("rs");
                    long msgtime = intent.getLongExtra("msgtime",-1);
                    if(rs.equals("error")){
                        for(int i=MyMessageQueue.chatQueueHadRead.get(groupId+"").size()-1;i>=0;--i){
                            if(msgtime==MyMessageQueue.chatQueueHadRead.get(groupId+"").get(i).getMsgtime()){
                                MyMessageQueue.chatQueueHadRead.get(groupId+"").get(i).setSendrs(false);
                            }
                        }
                    }
                }else if(type.equals("exitGroupRs")){      //退群
                    int groupid = intent.getIntExtra("gid",-1);
                    if(groupid==groupId){
                        finish();
                    }
                }
                else {
                    //每次来信息触发一次   所以只要拿到最后一条就行了  理论上只有一条
                    int size = MyMessageQueue.chatQueueNotRead.get(groupId+"").size();
                    ChatMsg singleChatMsg = MyMessageQueue.chatQueueNotRead.get(groupId+"").get(size-1);

                    // list.add(singleChatMsg);
                    mAdapter.add(singleChatMsg);
                    mAdapter.notifyDataSetChanged();


                    //将未读消息加入到已读消息队列
                    if(MyMessageQueue.chatQueueHadRead.containsKey(groupId+"")){
                        MyMessageQueue.chatQueueHadRead.get(groupId+"").add(singleChatMsg);
                    }else {
                        MyMessageQueue.chatQueueHadRead.put(groupId+"", new Vector<ChatMsg>());
                        MyMessageQueue.chatQueueHadRead.get(groupId+"").add(singleChatMsg);
                    }
                    MyMessageQueue.chatQueueNotRead.remove(groupId+"");

                    Vector<ChatMsg> chatMsgs = new Vector<>();
                    chatMsgs.add(singleChatMsg);
                    changeHasRead(chatMsgs);
                }

            }
//            abortBroadcast();
        }
    };


    protected void onDestroy(){

        super.onDestroy();
        unregisterReceiver(MyMessageReceiver);
//        Intent intent=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
//        sendBroadcast(intent);

    }


    //更改消息的状态为已读     两步  ：  向服务器回执消息已读    修改数据库消息的状态
    public void changeHasRead(Vector<ChatMsg> chatMsgs){
        if(StaticAllList.chatRecordDao!=null){
            StaticAllList.chatRecordDao.changeGroupsStateHadRead(chatMsgs);
        }else{
            MainActivity.initChatRecorddb();
            StaticAllList.chatRecordDao.changeGroupsStateHadRead(chatMsgs);
        }
        //回执已读
        //向服务器发送已读   只发送最大的消息编号即可
        //最大的消息编号
        int num = chatMsgs.get(chatMsgs.size()-1).getMsgid();
        SendToServer.reciviergroupchattext(num,groupId);
    }


}
