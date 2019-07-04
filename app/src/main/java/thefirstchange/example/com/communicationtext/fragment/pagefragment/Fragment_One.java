package thefirstchange.example.com.communicationtext.fragment.pagefragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.PersonalHomePageActivity;
import thefirstchange.example.com.communicationtext.activity.UpdatePersonInfoActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.User;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.MyObjectTools;


public class Fragment_One extends Fragment implements View.OnClickListener{

    private static String MY = "thefirstchange.example.com.communicationtext.FRAGMENT_ONE";

    private String fromWhere;
    private String id;
    private User user;
    LinearLayout editLinear;

    private TextView useridText;
    private TextView nickNameText;
    private ImageView sexImag;
    private TextView fromWhereText;
    private TextView birthText;
    private TextView nowWhereText;
    private TextView schoolText;
    private TextView schoolTimeText;
    private TextView partNameText;
    private TextView majorNameText;
    private TextView starText;
    private TextView introduceText;

    private String[][] constellations = {{"摩羯座", "水瓶座"}, {"水瓶座", "双鱼座"}, {"双鱼座", "白羊座"}, {"白羊座", "金牛座"}, {"金牛座", "双子座"}, {"双子座", "巨蟹座"}, {"巨蟹座", "狮子座"},
            {"狮子座", "处女座"}, {"处女座", "天秤座"}, {"天秤座", "天蝎座"}, {"天蝎座", "射手座"}, {"射手座", "摩羯座"}};
    //星座分割时间
    private int[] date = {20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_viewpager_layout, null);
        PersonalHomePageActivity personalHomePageActivity=(PersonalHomePageActivity)getActivity();
        fromWhere=personalHomePageActivity.getFromWhere();
        id=personalHomePageActivity.getFromId();
        if(id.equals(ObjectService.personalInfo.getPhonenumber())){
            user =  MyObjectTools.personalInfoConvertToUser(ObjectService.personalInfo);

        }else if(!StaticAllList.usertemps.containsKey(id)&&StaticAllList.friendList.containsKey(id)){
            user = new User();
            user.setPhonenumber("");
            MyObjectTools.MyFriendConvertToUser(StaticAllList.friendList.get(id),user);
        }else{
            user = StaticAllList.usertemps.get(id);
        }

//        id=personalHomePageActivity.getFronId();
        initView(fromWhere,view);
        return view;
    }

    private void initView(String fromWhere,View view){
        editLinear=(LinearLayout)view.findViewById(R.id.edit_info);
        if (!id.equals(ObjectService.personalInfo.getPhonenumber())){
            editLinear.setVisibility(View.GONE);
        }else{
            editLinear.setOnClickListener(this);
        }


        useridText = (TextView)view.findViewById(R.id.home_page_userid_tv);
        useridText.setText(id+"");
        nickNameText=(TextView)view.findViewById(R.id.home_page_nickname);
        sexImag=(ImageView)view.findViewById(R.id.home_page_sex);
        if(ObjectService.personalInfo.getSex().equals("女")){
            sexImag.setImageResource(R.drawable.ic_woman);
        }else {
            sexImag.setImageResource(R.drawable.ic_man);
        }
        fromWhereText=(TextView)view.findViewById(R.id.home_page_from);
        birthText=(TextView)view.findViewById(R.id.home_page_birth);
        starText=(TextView)view.findViewById(R.id.home_page_star);
    if(ObjectService.personalInfo.getBirthday()!=null&&!ObjectService.personalInfo.getBirthday().trim().equals("")){
        getConstellations(ObjectService.personalInfo.getBirthday());
    }

        nowWhereText=(TextView)view.findViewById(R.id.home_page_place);
        schoolTimeText=(TextView)view.findViewById(R.id.home_page_schooltime);
        schoolText=(TextView)view.findViewById(R.id.home_page_school);
        partNameText=(TextView)view.findViewById(R.id.home_page_part);
        majorNameText=(TextView)view.findViewById(R.id.home_page_major);
        introduceText=(TextView)view.findViewById(R.id.home_page_instruce);

        if(id.equals(ObjectService.personalInfo.getPhonenumber())){
            nickNameText.setText(ObjectService.personalInfo.getNickname());
            fromWhereText.setText(ObjectService.personalInfo.getFrom());
            birthText.setText(ObjectService.personalInfo.getBirthday());
            nowWhereText.setText(ObjectService.personalInfo.getAddress());
            schoolText.setText(ObjectService.personalInfo.getSchoolname());
            schoolTimeText.setText(ObjectService.personalInfo.getRuxueyear()+"");
            partNameText.setText(ObjectService.personalInfo.getDepartmentname());
            majorNameText.setText(ObjectService.personalInfo.getMajorname());
            introduceText.setText(ObjectService.personalInfo.getIntroduce());
        }else{
                if(user==null||user.getPhonenumber().equals("")){
                    return;
                }
                if(user.getPhonenumber()!=null&&!user.getPhonenumber().equals("")&&user.getPhonenumber().equals(id)){
                    nickNameText.setText(user.getNickname());
                    fromWhereText.setText(user.getFrom());
                    birthText.setText(user.getBirthday());
                    nowWhereText.setText(user.getAddress());
                    schoolText.setText(user.getSchoolname());
                    schoolTimeText.setText(user.getRuxueyear()+"");
                    partNameText.setText(user.getDepartmentname());
                    majorNameText.setText(user.getMajorname());
                    introduceText.setText(user.getIntroduce());
                    if(user.getSex()==null){
                        sexImag.setVisibility(View.GONE);
                    }else{
                        if(user.getSex().equals("女")){
                            sexImag.setImageResource(R.drawable.ic_woman);
                        }else {
                            sexImag.setImageResource(R.drawable.ic_man);
                        }
                    }

                }

        }

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.edit_info:
                if(id.equals(ObjectService.personalInfo.getPhonenumber())){
                    Intent intent=new Intent(getContext(), UpdatePersonInfoActivity.class);
                    getActivity().startActivity(intent);
                }

                break;
                default:
                    break;
        }
    }

    private void getConstellations(String time) {
        String[] data = time.split("-");
        int day = date[Integer.parseInt(data[1]) - 1];
        String[] cl1 = constellations[Integer.parseInt(data[1]) - 1];
        if (Integer.parseInt(data[2]) >= day) {
            starText.setText(cl1[1]);
        } else {
            starText.setText(cl1[0]);
        }
    }


    public void onResume(){

        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MY);
         getContext().registerReceiver(broadcastReceiver,intentFilter);
        if(id.equals(ObjectService.personalInfo.getPhonenumber())){
            nickNameText.setText(ObjectService.personalInfo.getNickname());
            fromWhereText.setText(ObjectService.personalInfo.getFrom());
            birthText.setText(ObjectService.personalInfo.getBirthday());
            nowWhereText.setText(ObjectService.personalInfo.getAddress());
            schoolText.setText(ObjectService.personalInfo.getSchoolname());
            schoolTimeText.setText(ObjectService.personalInfo.getRuxueyear()+"");
            partNameText.setText(ObjectService.personalInfo.getDepartmentname());
            majorNameText.setText(ObjectService.personalInfo.getMajorname());
            introduceText.setText(ObjectService.personalInfo.getIntroduce());
        }


    }
    public void onDestroy(){
        super.onDestroy();

        //unbindService(connection);

        //stopService(bindIntent);
        getContext().unregisterReceiver(broadcastReceiver);
    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MY)){

                String type = intent.getStringExtra("type");
                if(type.equals("getIndexInfoByPhRs")){        //好友的个人资料
                    String ph = intent.getStringExtra("ph");
                    if (id.equals(ph)){
                        if(!StaticAllList.usertemps.containsKey(ph)){
                            return;
                        }
                        user = StaticAllList.usertemps.get(ph);
                        if(user.getSex().equals("女")){
                            sexImag.setImageResource(R.drawable.ic_woman);
                        }else {
                            sexImag.setImageResource(R.drawable.ic_man);
                        }
                        nickNameText.setText(user.getNickname());
                        fromWhereText.setText(user.getFrom());
                        birthText.setText(user.getBirthday());
                        nowWhereText.setText(user.getAddress());
                        schoolText.setText(user.getSchoolname());
                        schoolTimeText.setText(user.getRuxueyear()+"");
                        partNameText.setText(user.getDepartmentname());
                        majorNameText.setText(user.getMajorname());
                        introduceText.setText(user.getIntroduce());
                    }

                }


                }

        }
    };


}
