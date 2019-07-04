package thefirstchange.example.com.communicationtext.fragment.pagefragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.Vector;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.PersonalHomePageActivity;
import thefirstchange.example.com.communicationtext.adapter.HomePageLifeShowAdapter;
import thefirstchange.example.com.communicationtext.dongtai.Dongtai;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.listener.EndlessRecyclerOnScrollListener;
import thefirstchange.example.com.communicationtext.service.MyUserDtThread;


public class Fragment_Two extends Fragment {

    private static String MY = "thefirstchange.example.com.communicationtext.FRAGMENT_TWO";

    private RecyclerView mRecyclerView;
    private HomePageLifeShowAdapter homePageLifeShowAdapter;

    private String ph2;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_viewpager_layout, null);

        PersonalHomePageActivity personalHomePageActivity=(PersonalHomePageActivity)getActivity();
        final String ph = personalHomePageActivity.getFromId();
        ph2 = ph;
        MyUserDtThread myUserDtThread = new MyUserDtThread("gUsNDtIDs",ph,-1);
        myUserDtThread.start();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rec);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(!StaticAllList.personalDongtais.containsKey(ph)){
            StaticAllList.personalDongtais.put(personalHomePageActivity.getFromId(),new Vector<Dongtai>());
        }

        homePageLifeShowAdapter = new HomePageLifeShowAdapter(getContext(),StaticAllList.personalDongtais.get(ph),ph);
        mRecyclerView.setAdapter(homePageLifeShowAdapter);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                homePageLifeShowAdapter.setLoadState(homePageLifeShowAdapter.LOADING);

                int a = homePageLifeShowAdapter.getpos()-1;
                if(a>=StaticAllList.personalDongtais.get(ph).size()||a<0){
                    return;
                }
                int id = StaticAllList.personalDongtais.get(ph).get(a).getId();
                MyUserDtThread myUserDtThread = new MyUserDtThread("gUsODtIDs",ph,id);
                myUserDtThread.start();
            }
        });
        return view;
    }



    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MY)){

                String type = intent.getStringExtra("type");
                //请求动态    返回动态基本信息   不包括图片
                if(type.equals("gUsDtByDTIDRs")){

                    int size = StaticAllList.personalDongtais.get(ph2).size();
                    homePageLifeShowAdapter.notifyItemRangeChanged(0,homePageLifeShowAdapter.getItemCount()-1);
                }else if(type.equals("gUsDtByDTIDImRs")){     //返回动态的图片
                    homePageLifeShowAdapter.notifyItemRangeChanged(0,homePageLifeShowAdapter.getItemCount()-1);

                    //动态界面上拉刷新
                }else if(type.equals("gUsODtIDsRs")){

                    int size = intent.getIntExtra("size",0);
                    if(size==0||size==1){
                        homePageLifeShowAdapter.setLoadState(homePageLifeShowAdapter.LOADING_END);
                    }else {
                        homePageLifeShowAdapter.setLoadState(homePageLifeShowAdapter.LOADING_COMPLETE);
                    }


                }else if(type.equals("HomePageNOtify")){

                    homePageLifeShowAdapter.notifyItemRangeChanged(0,homePageLifeShowAdapter.getItemCount()-1);

                    //头像到来
                }else if(type.equals("getIndexIcInfoByPhRs")){
                    homePageLifeShowAdapter.notifyItemRangeChanged(0,homePageLifeShowAdapter.getItemCount()-1);

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

    }

}
