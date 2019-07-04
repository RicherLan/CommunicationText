package thefirstchange.example.com.communicationtext.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.group.GroupListActivity;
import thefirstchange.example.com.communicationtext.activity.CorporationCreateActivity;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.CSSActivity;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.SchDutyActivity;
import thefirstchange.example.com.communicationtext.service.ObjectService;


public class IndexFragment extends Fragment {

    private static String MY = "thefirstchange.example.com.communicationtext.INDEXFRAGMENT";


    private CardView toCommunication;
    private CardView toCouseList;
    private CardView toSchedule;
    private CardView createUnion;

    public static CircleImageView head_icon;
    View view=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(view==null){
            view=inflater.inflate(R.layout.index_fragment,container,false);
            toCommunication=(CardView)view.findViewById(R.id.to_all_people_communication);
            toCommunication.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(), GroupListActivity.class);
                    startActivity(intent);
                }
            });
            toCouseList=(CardView)view.findViewById(R.id.to_course_list);
            toCouseList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), CSSActivity.class);
                    startActivity(intent);
                }
            });
            toSchedule=(CardView)view.findViewById(R.id.to_schedule);
            toSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), SchDutyActivity.class);

                    startActivity(intent);
                }
            });
            createUnion=(CardView)view.findViewById(R.id.create_union);
            createUnion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), CorporationCreateActivity.class);
                    startActivity(intent);

                }
            });

            head_icon = (CircleImageView)view.findViewById(R.id.index_fragment_icon);

            setUserImage();

        }else{

        }

        //动画
        Animation animation = AnimationUtils.loadAnimation(MainActivity.mainactivityContext, R.anim.img_animation);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);
        head_icon.startAnimation(animation);

        return view;
    }


    private void setUserImage(){


//        Bitmap bitmap  = UserUtil.getPersonalIcon();
        if(ObjectService.personalIcon!=null) {
            head_icon.setImageBitmap(ObjectService.personalIcon);
        }

//        else{
//            ivHead.setImageDrawable(getResources().getDrawable(R.drawable.user_image1));
//        }
    }

    public void onResume(){

        super.onResume();

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
                        head_icon.setImageBitmap(ObjectService.personalIcon);
                    }

                }else if(type.equals("ChPerIcRs")){
                    head_icon.setImageBitmap(ObjectService.personalIcon);
                }
            }

        }
    };


}
