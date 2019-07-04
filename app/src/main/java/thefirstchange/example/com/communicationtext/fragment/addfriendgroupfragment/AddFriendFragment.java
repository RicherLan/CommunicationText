package thefirstchange.example.com.communicationtext.fragment.addfriendgroupfragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.FindFriendOrGroupActivity;
import thefirstchange.example.com.communicationtext.activity.PersonalHomePageActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.MyFileTools;
import thefirstchange.example.com.communicationtext.util.MyViewTools;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;
import thefirstchange.example.com.communicationtext.util.TextUtil;

public class AddFriendFragment extends Fragment implements View.OnClickListener{

    private TextView findFriend;
    private RelativeLayout findedFriend;
    private TextView newPeopleName;
    private TextView newPeopleNumber;
    private TextView addThisPeople;
    private CircleImageView newPeopleIv;
    private EditText findPeopleNumber;
    private EditText checkInfoEdit;
    private TextView sendAddInfo;
    private RelativeLayout checkLay;
    private String friendNumber;
    private TextView hadFriend;
    private static final String MYACTION="thefirstchange.example.com.communicationtext.ADD_FRIEND";
    private String phoneNu;


    Timer timer = new Timer();
    Timer searchTimer = new Timer();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add_friend,container,false);
        FindFriendOrGroupActivity findFriendOrGroupActivity=(FindFriendOrGroupActivity)getActivity();
        phoneNu=findFriendOrGroupActivity.getPhone();

        initView(view);
        return view;
    }

    private void initView(View view){
        findFriend=(TextView)view.findViewById(R.id.find_friend_text);
        findedFriend=(RelativeLayout)view.findViewById(R.id.finded_friend);
        findedFriend.setOnClickListener(this);
        newPeopleIv=(CircleImageView)view.findViewById(R.id.add_friend_iv);
        newPeopleName=(TextView)view.findViewById(R.id.add_friend_name);
        newPeopleNumber=(TextView)view.findViewById(R.id.add_friend_number);
        addThisPeople=(TextView)view.findViewById(R.id.add_this_people);
        findPeopleNumber=(EditText)view.findViewById(R.id.find_friend_number);
        findPeopleNumber.setHint("请输入对方账号");
        if (!phoneNu.equals("-1")){
            findPeopleNumber.setText(phoneNu);
        }
        findPeopleNumber.addTextChangedListener(textWatcher);
        checkInfoEdit=(EditText)view.findViewById(R.id.check_info);
        sendAddInfo=(TextView)view.findViewById(R.id.send_add_info);
        checkLay=(RelativeLayout)view.findViewById(R.id.send_add_info_lay);
        hadFriend=(TextView)view.findViewById( R.id.had_friend);
        sendAddInfo.setOnClickListener(this);
        addThisPeople.setOnClickListener(this);
        findFriend.setOnClickListener(this);

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.find_friend_text:

                String peopleNumber=findPeopleNumber.getText().toString();
                boolean isInt= TextUtil.isInteger(peopleNumber);
                if (peopleNumber.equals("")){
                    Toast.makeText(getContext(),"账号不能为空",Toast.LENGTH_SHORT).show();

                    break;

                }
                if (isInt){
                    for(String ph : StaticAllList.friendList.keySet()){
                        String s = ph;
                        int a =0;
                    }

                    if(StaticAllList.friendList.containsKey(peopleNumber)){
                        friendNumber = peopleNumber;
                        addThisPeople.setVisibility(View.GONE);
                        hadFriend.setVisibility(View.VISIBLE);
                        findedFriend.setVisibility(View.VISIBLE);

                        newPeopleName.setText(StaticAllList.friendList.get(peopleNumber).getRemark());
                        newPeopleNumber.setText(peopleNumber);

                        String icpath = MyFileTools.getUserIconPath(peopleNumber);
                        if(icpath==null||icpath.equals("")){
                            SendToServer.getUICOfSearchAddUser(peopleNumber);
                        }else{
                            MyViewTools.setCircleImage(newPeopleIv,icpath);
                        }
                    }else if(peopleNumber.equals(ObjectService.personalInfo.getPhonenumber())){
                        friendNumber = peopleNumber;
                        addThisPeople.setVisibility(View.GONE);
                        hadFriend.setVisibility(View.VISIBLE);
                        findedFriend.setVisibility(View.VISIBLE);

                        newPeopleName.setText(ObjectService.personalInfo.getNickname());
                        newPeopleNumber.setText(peopleNumber);

                        MyViewTools.setCircleImage(newPeopleIv,ObjectService.personalInfo.getIcon());

                    }else{

                        if(!NetworkUtils.isConnected(getContext())){
                            Toast.makeText(getContext(), "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        MyDialog.showBottomLoadingDialog(getActivity());
                        SendToServer.searchAddUser(peopleNumber);
                        searchTimer = new Timer();
                        searchTimer.schedule(new TimerTask(){

                            @Override
                            public void run() {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyDialog.dismissBottomLoadingDialog();
                                        Toast.makeText(getContext(),"搜索失败!",Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }, 30000);

                    }

                }else {
                    Toast.makeText(getContext(),"请正确输入!",Toast.LENGTH_SHORT).show();

                }



                break;
            case R.id.add_this_people:
                checkLay.setVisibility(View.VISIBLE);
                addThisPeople.setVisibility(View.INVISIBLE);
                break;
            case R.id.send_add_info:

                if(!NetworkUtils.isConnected(getContext())){
                    Toast.makeText(getContext(), "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String info=checkInfoEdit.getText().toString();
                SendToServer.addFriend(ObjectService.personalInfo.getPhonenumber(),friendNumber,info);

                timer = new Timer();

                timer.schedule(new TimerTask(){

                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(),"发送失败",Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }, 3000);

                break;

                //点击查找出来的
                case R.id.finded_friend:
                    Intent intent = new Intent(getActivity(), PersonalHomePageActivity.class);
                    intent.putExtra("from","");
                    intent.putExtra("id",friendNumber);
                    getActivity().startActivity(intent);
                    break;
            default:
                    break;

        }
    }





    BroadcastReceiver addFriendBrodcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
          String type=intent.getStringExtra("type");
          if (type.equals("searchUInfoRs")){
              if(searchTimer!=null){
                  searchTimer.cancel();
              }
              MyDialog.dismissBottomLoadingDialog();
              String rs = intent.getStringExtra("rs");
              if(rs.equals("notexist")){

                  Toast.makeText(getContext(), "该用户不存在!", Toast.LENGTH_SHORT).show();
                  return;
              }

              friendNumber=intent.getStringExtra("phonenumber");

              if(StaticAllList.friendList.containsKey(friendNumber)){

                  addThisPeople.setVisibility(View.GONE);
                  hadFriend.setVisibility(View.VISIBLE);

              }else {
                  addThisPeople.setVisibility(View.VISIBLE);
                  hadFriend.setVisibility(View.GONE);
              }

              newPeopleName.setText(intent.getStringExtra("nickname"));
              newPeopleNumber.setText(friendNumber);
            //  newPeopleIv.setImageBitmap(BitmapFactory.decodeByteArray(intent.getStringExtra("usericonpath").getBytes(),0,intent.getStringExtra("usericonpath").getBytes().length));
              findedFriend.setVisibility(View.VISIBLE);
          }else if(type.equals("getUICOfsearchUInfoRs")){
              String ph = intent.getStringExtra("phonenumber");
              String icpath = intent.getStringExtra("icpath");

              if(ph.equals(friendNumber)){
                  MyViewTools.setCircleImage(newPeopleIv,icpath);
              }
          }
          else if (type.equals("requestAddFriendResult")){

              timer.cancel();
              String rs = intent.getStringExtra("rs");
              if(rs.equals("ok")){
                  Toast.makeText(getContext(), "请求已发送", Toast.LENGTH_SHORT).show();
                    if(FindFriendOrGroupActivity.findFriendOrGroupActivity!=null){
                        FindFriendOrGroupActivity.findFriendOrGroupActivity.finish();
                    }
              }else if(rs.equals("hasFriend")){   //已经是好友了
                  Toast.makeText(getContext(), "请求失败,该用户已经是您的好友!", Toast.LENGTH_SHORT).show();
              }else{
                  Toast.makeText(getContext(), "服务器繁忙,请稍后再试!", Toast.LENGTH_SHORT).show();
              }

          }

        }
    };

    TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length()<11){
                findedFriend.setVisibility(View.GONE);
                checkLay.setVisibility(View.GONE);

            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void onResume(){
        super.onResume();

        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MYACTION);
        getActivity().registerReceiver(addFriendBrodcastReceiver,intentFilter);
    }

    public void onDestroy(){
        super.onDestroy();
        getActivity().unregisterReceiver(addFriendBrodcastReceiver);

        if(timer!=null){
            timer.cancel();
        }

        if(searchTimer!=null){
            searchTimer.cancel();
        }
    }
}
