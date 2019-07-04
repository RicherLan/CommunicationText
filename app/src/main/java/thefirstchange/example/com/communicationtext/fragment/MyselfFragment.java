package thefirstchange.example.com.communicationtext.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.PersonalHomePageActivity;
import thefirstchange.example.com.communicationtext.activity.myselfactivity.HelpServiceListActivity;
import thefirstchange.example.com.communicationtext.activity.myselfactivity.PasswordAndSafeActivity;
import thefirstchange.example.com.communicationtext.activity.myselfactivity.SoftSettingActivity;
import thefirstchange.example.com.communicationtext.corpration.CorpHelpActivity;
import thefirstchange.example.com.communicationtext.service.ObjectService;

public class MyselfFragment extends Fragment implements View.OnClickListener{

    private static String MY = "thefirstchange.example.com.communicationtext.MYSELFFRAGMENT";

    private LinearLayout showPersonalInfo;
    private LinearLayout toPasswordSafe;
    private LinearLayout toSoftSetting;
    private LinearLayout toHelpCenter;
    private CircleImageView ivHead;
    private TextView nickName;
    private ImageView mySex;

    View view=null;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(view==null){
            view=inflater.inflate(R.layout.myself_fragmnet,container,false);
            initView(view);
        }

        return view;
    }

    private void initView(View view){
        showPersonalInfo=(LinearLayout)view.findViewById(R.id.show_personal_info);
        showPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PersonalHomePageActivity.class);
                intent.putExtra("from","myself");
                intent.putExtra("id", ObjectService.personalInfo.getPhonenumber());
                startActivity(intent);

            }
        });

        toPasswordSafe=(LinearLayout)view.findViewById(R.id.to_password_and_safe);
        toPasswordSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), PasswordAndSafeActivity.class);
                getActivity().startActivity(intent);
            }
        });

        toHelpCenter = (LinearLayout)view.findViewById(R.id.to_help_center_layout);
        toHelpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), HelpServiceListActivity.class);
                getActivity().startActivity(intent);
            }
        });


        toSoftSetting=(LinearLayout)view.findViewById(R.id.to_soft_setting);
        toSoftSetting.setOnClickListener(this);

        ivHead=(CircleImageView)view.findViewById(R.id.myself_iv);

        nickName=(TextView)view.findViewById(R.id.myself_nickname);

        nickName.setText(ObjectService.personalInfo.getNickname());

        mySex=(ImageView)view.findViewById(R.id.myself_sex);
        if (ObjectService.personalInfo.getSex().equals("女")){
            mySex.setImageResource(R.drawable.ic_woman);
        }else {
            mySex.setImageResource(R.drawable.ic_man);
        }

        setUserImage();

    }

    private void setUserImage(){


//        Bitmap bitmap  = UserUtil.getPersonalIcon();
        if(ObjectService.personalIcon!=null) {
            ivHead.setImageBitmap(ObjectService.personalIcon);
        }

//        else{
//            ivHead.setImageDrawable(getResources().getDrawable(R.drawable.user_image1));
//        }
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.to_soft_setting:
                Intent intent=new Intent(getContext(), SoftSettingActivity.class);
                startActivity(intent);
                break;

        }
    }

    public void onResume(){
        super.onResume();
        nickName.setText(ObjectService.personalInfo.getNickname());

        if (ObjectService.personalInfo.getSex().equals("女")){
            mySex.setImageResource(R.drawable.ic_woman);
        }else {
            mySex.setImageResource(R.drawable.ic_man);
        }
        setUserImage();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MY);
        getActivity().registerReceiver(broadcastReceiver,intentFilter);
    }



    public void onDestroy(){
        super.onDestroy();

        getActivity().unregisterReceiver(broadcastReceiver);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MY)){

                String type = intent.getStringExtra("type");
                if(type.equals("getPerIc")) {
                    if(ObjectService.personalIcon!=null){
                        ivHead.setImageBitmap(ObjectService.personalIcon);
                    }

                }
            }

        }
    };


}
