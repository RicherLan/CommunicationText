package thefirstchange.example.com.communicationtext.fragment.addfriendgroupfragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import thefirstchange.example.com.communicationtext.group.SearchGroupHomeActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.MyFileTools;
import thefirstchange.example.com.communicationtext.util.MyViewTools;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;
import thefirstchange.example.com.communicationtext.util.TextUtil;


public class AddGroupFragment extends Fragment implements View.OnClickListener{
    private TextView findGroupText;
    private EditText groupNumberEdit;
    private TextView addGroup;
    private RelativeLayout newGroupLay;
    private EditText editInfo;
    private TextView sendInfo;
    private RelativeLayout sendInfoLay;
    private CircleImageView groupIv;
    private TextView groupName_tv;
    private TextView groupid_tv;
    private TextView hadGroup;

    private int groupid = -1;

    Timer requestAddGroupTimer = new Timer();
    Timer searchTimer = new Timer();

    private static final String MYACTION="thefirstchange.example.com.communicationtext.ADD_GROUP";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_add_group,container,false);
        initView(view);
       return view;

    }

    private void initView(View view){
        findGroupText=(TextView)view.findViewById(R.id.find_group_text);
        groupNumberEdit=(EditText)view.findViewById(R.id.group_number_edit);
        groupNumberEdit.addTextChangedListener(textWatcher);
        groupNumberEdit.setHint("请输入群号");
        addGroup=(TextView)view.findViewById(R.id.add_this_group);
        newGroupLay=(RelativeLayout)view.findViewById(R.id.finded_group);
        editInfo=(EditText)view.findViewById(R.id.group_check_info);
        sendInfo=(TextView)view.findViewById(R.id.group_send_add_info);
        sendInfoLay=(RelativeLayout)view.findViewById(R.id.group_send_add_info_lay);
        groupIv=(CircleImageView)view.findViewById(R.id.add_group_iv);
        groupName_tv =(TextView)view .findViewById(R.id.add_group_name);
        groupid_tv=(TextView)view.findViewById(R.id.add_group_number);
        hadGroup=(TextView)view.findViewById(R.id.group_had_friend);

        newGroupLay.setOnClickListener(this);
        addGroup.setOnClickListener(this);
        findGroupText.setOnClickListener(this);
        sendInfo.setOnClickListener(this);


    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.find_group_text:

                String groupNumber=groupNumberEdit.getText().toString().trim();
                boolean isInt= TextUtil.isInteger(groupNumber);
                if (isInt){

                    groupid = Integer.parseInt(groupNumber);

                        if(StaticAllList.groupList.containsKey(groupid)){

                            addGroup.setVisibility(View.GONE);
                            hadGroup.setVisibility(View.VISIBLE);
                            newGroupLay.setVisibility(View.VISIBLE);

                            groupName_tv.setText(StaticAllList.groupList.get(groupid).getGroupname());
                            groupid_tv.setText(StaticAllList.groupList.get(groupid).groupid+"");

                            String icpath = MyFileTools.getGroupIconPath(groupid);
                            if(icpath==null||icpath.equals("")){
                                SendToServer.getGroupICOfSearchAddGroup(groupid);
                            }else{
                                MyViewTools.setCircleImage(groupIv,icpath);
                            }

                        }else{


                            if(!NetworkUtils.isConnected(getContext())){
                                Toast.makeText(getContext(), "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            MyDialog.showBottomLoadingDialog(getActivity());

                                    SendToServer.searchAddGroup(groupid);


                            searchTimer = new Timer();
                            searchTimer.schedule(new TimerTask(){

                                @Override
                                public void run() {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            MyDialog.dismissBottomLoadingDialog();
                                            Toast.makeText(getContext(),"查找失败!",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }, 30000);
                        }


                }else {
                    Toast.makeText(getContext(),"请正确输入!",Toast.LENGTH_SHORT).show();
                    groupNumberEdit.setText("");
                    groupNumberEdit.requestFocus();
                }
                break;
            case R.id.add_this_group:


                sendInfoLay.setVisibility(View.VISIBLE);
                break;
            case R.id.group_send_add_info:

                if(!NetworkUtils.isConnected(getContext())){
                    Toast.makeText(getContext(), "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

                MyDialog.showBottomLoadingDialog(getActivity());
                final  String info=editInfo.getText().toString();

                        SendToServer.requestAddGroup( groupid,info);


                requestAddGroupTimer = new Timer();
                requestAddGroupTimer.schedule(new TimerTask(){

                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyDialog.dismissBottomLoadingDialog();
                                Toast.makeText(getContext(),"发送失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }, 5000);

                break;


            case R.id.finded_group:     //点击查找出来的
                Intent intent = new Intent(getActivity(), SearchGroupHomeActivity.class);
                intent.putExtra("groupid",groupid);
                getActivity().startActivity(intent);
                break;

        }
    }




    BroadcastReceiver addFriendBrodcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type=intent.getStringExtra("type");
            if (type.equals("searchGInfoRs")){
                int s =intent.getIntExtra("groupid",-2);
                if(s!=groupid){
                    return;
                }
                if(searchTimer!=null){
                    searchTimer.cancel();
                }
                MyDialog.dismissBottomLoadingDialog();
                String rs = intent.getStringExtra("rs");

                if(rs.equals("notexist")){

                    Toast.makeText(getContext(), "该群聊不存在!", Toast.LENGTH_SHORT).show();
                    return;
                }


                newGroupLay.setVisibility(View.VISIBLE);
                addGroup.setVisibility(View.VISIBLE);
                hadGroup.setVisibility(View.GONE);
                if(ObjectService.groupBasicInfo!=null&&ObjectService.groupBasicInfo.getGroupid()==groupid){
                    groupName_tv.setText(ObjectService.groupBasicInfo.getGroupname());
                    groupid_tv.setText(groupid+"");

                    String icpath = MyFileTools.getGroupIconPath(groupid);
                    MyViewTools.setCircleGroupIcon(groupIv,icpath);

                }


            }else if(type.equals("getGroupICOfsearchGroup")){
               int gidtemp = intent.getIntExtra("groupid",-2);
                String icpath = intent.getStringExtra("icpath");

                if(groupid==gidtemp){
                    MyViewTools.setCircleImage(groupIv,icpath);
                }
            }

            else if (type.equals("requestAddGroupResult")){
                if(requestAddGroupTimer !=null){
                    requestAddGroupTimer.cancel();
                }
               MyDialog.dismissBottomLoadingDialog();
                String rs = intent.getStringExtra("rs");

                if(rs.equals("ok")){
                    Toast.makeText(getContext(), "请求已发送", Toast.LENGTH_SHORT).show();
                    if(FindFriendOrGroupActivity.findFriendOrGroupActivity!=null){
                        FindFriendOrGroupActivity.findFriendOrGroupActivity.finish();
                    }
                }else if(rs.equals("hasjoin")){
                    Toast.makeText(getContext(), "请求失败,您已经是该群的成员", Toast.LENGTH_SHORT).show();
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
            newGroupLay.setVisibility(View.GONE);
            sendInfoLay.setVisibility(View.GONE);

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
        if(requestAddGroupTimer!=null){
            requestAddGroupTimer.cancel();
        }

        if(searchTimer!=null){
            searchTimer.cancel();
        }
    }

}
