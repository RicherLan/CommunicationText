package thefirstchange.example.com.communicationtext.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.CommentShowActivity;
import thefirstchange.example.com.communicationtext.dongtai.Dongtai;
import thefirstchange.example.com.communicationtext.imagewarker.MessagePicturesLayout;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.util.DateUtils;
import thefirstchange.example.com.communicationtext.util.MyFileTools;
import thefirstchange.example.com.communicationtext.util.MyTools;
import thefirstchange.example.com.communicationtext.util.MyViewTools;
import thefirstchange.example.com.communicationtext.util.TimeUtil;

public class HomePageLifeShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private int pos;

    private LayoutInflater layoutInflater;
    private Context mContext;
    private List<Dongtai> dongtais;

    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;

    private String ph;

    // //上次请求该动态的该图片的时间
    Map<String,Long> dongtaiIcRequest = new HashMap<>();

    public HomePageLifeShowAdapter(Context mContext, List<Dongtai> dongtais,String ph){
        this.mContext=mContext;
        this.dongtais=dongtais;
        layoutInflater=LayoutInflater.from(mContext);
        this.ph = ph;
    }

    public static enum ITEM_TYPE{
        ITEM_OR,
        ITEM_FOOT;
    }

    public int getpos(){
        return pos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==ITEM_TYPE.ITEM_FOOT.ordinal()){
            return new FootViewHolder(layoutInflater.inflate(R.layout.layout_refresh_footer,parent,false));
        }else {
            return new UserViewHolder(layoutInflater.inflate(R.layout.person_show_item,parent,false));

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof FootViewHolder){
            HomePageLifeShowAdapter.FootViewHolder footViewHolder = (HomePageLifeShowAdapter.FootViewHolder) holder;
            switch (loadState) {
                case LOADING: // 正在加载
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_COMPLETE: // 加载完成
                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_END: // 加载到底
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }

        }else {

            HomePageLifeShowAdapter.UserViewHolder userViewHolder=(HomePageLifeShowAdapter.UserViewHolder)holder;
           // userViewHolder.mImageNice9Layout.setVisibility(View.GONE);

            Dongtai dongtai= dongtais.get(position);

            String icpath = MyFileTools.getUserIconPath(ph);
//            if(icpath==null||icpath.equals("")){
//                new Thread(){
//                    public void run(){
//                        SendToServer.getIndexICByPh(ph);
//                    }
//                }.start();
//            }

            MyViewTools.setCircleImage(userViewHolder.lifeShowIv,icpath);
            userViewHolder.lifeShowNickName.setText(dongtai.getSdname());
            userViewHolder.lifeShowSendTime.setText(TimeUtil.getChatTime(dongtai.getTime()));
            List<String> ls=new ArrayList<String>();


            for (int i=0;i<dongtai.getImph().size();i++){
                ls.add(dongtai.getImph().get(i));

                if(MyTools.isInteger(dongtai.getImph().get(i))){


                    int fileid = Integer.parseInt(dongtai.getImph().get(i));
                    //从未请求过该图片
                    if(!dongtaiIcRequest.containsKey(dongtai.getId()+fileid+"")){
                        dongtaiIcRequest.put(dongtai.getId()+fileid+"",System.currentTimeMillis());

                        SendToServer.getUserDongtaiIconByIcId(dongtai.getId(),dongtai.getSdid(),fileid);
                        dongtaiIcRequest.put(dongtai.getId()+fileid+"",System.currentTimeMillis());
                    }else {
                        //上次请求该动态的该图片的时间
                        long oldtime = dongtaiIcRequest.get(dongtai.getId()+fileid+"");
                        long nowtime = System.currentTimeMillis();
                        //如果上次的请求是在8秒前  那么再次请求

                        if(!DateUtils.isTwoTimeDifferLessAppointTime(oldtime, nowtime,8)){
                            dongtaiIcRequest.put(dongtai.getId()+fileid+"",nowtime);

                            SendToServer.getUserDongtaiIconByIcId(dongtai.getId(),dongtai.getSdid(),fileid);

                        }
                    }

                }

            }
            ((UserViewHolder)holder).messagePicturesLayout.set(ls,ls);
//            if (dongtai.getImagenum()>0){
//                userViewHolder.mImageNice9Layout.setVisibility(View.VISIBLE);
//                userViewHolder.mImageNice9Layout.bindData(dongtai.getImagepath());
//                userViewHolder.mImageNice9Layout.setItemDelegate(new ImageNice9Layout.ItemDelegate() {
//                    @Override
//                    public void onItemClick(int position) {
//                        Toast.makeText(mContext, "位置"+ position, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
            if (!dongtai.getContent().isEmpty()){
                userViewHolder.lifeShowText.setVisibility(View.VISIBLE);
                userViewHolder.lifeShowText.setText(dongtai.getContent());
            }else {
                userViewHolder.lifeShowText.setVisibility(View.GONE);
            }



            userViewHolder.commentLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, CommentShowActivity.class);
                    mContext.startActivity(intent);
                }
            });

            userViewHolder.sendFavourLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            userViewHolder.lifeShowHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, CommentShowActivity.class);
                    mContext.startActivity(intent);
                }
            });

            userViewHolder.lifeshow_nameandtime_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, CommentShowActivity.class);
                    mContext.startActivity(intent);
                }
            });

            userViewHolder.person_show_body.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, CommentShowActivity.class);
                    mContext.startActivity(intent);
                }
            });

            if (dongtai.getPranum()>0){
                userViewHolder.lifeShowFavourNu.setText(dongtai.getPranum()+"");
            }
            if (dongtai.getComnum()>0){
                userViewHolder.lifeShowCommemtNu.setText(dongtai.getComnum()+"");
            }


        }


    }
    public int getItemViewType(int position){
        if (position+1==getItemCount()){
            pos = position;
            return ITEM_TYPE.ITEM_FOOT.ordinal();
        }else {
            return ITEM_TYPE.ITEM_OR.ordinal();
        }

    }

    @Override
    public int getItemCount() {
        return dongtais.size()+1;
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
       // ImageNice9Layout mImageNice9Layout;
        LinearLayout commentLinearLayout;
        TextView lifeShowText;
        LinearLayout sendFavourLay;
        TextView lifeShowFavourNu;
        TextView lifeShowSendTime;
        TextView lifeShowNickName;
        TextView lifeShowResendNu;
        TextView lifeShowCommemtNu;
        CircleImageView lifeShowIv;
        RelativeLayout lifeShowHead;
        MessagePicturesLayout messagePicturesLayout;
        LinearLayout person_show_body;
        LinearLayout lifeshow_nameandtime_layout;

        public UserViewHolder(View itemView) {
            super(itemView);
            lifeshow_nameandtime_layout = (LinearLayout)itemView.findViewById(R.id.lifeshow_nameandtime_layout);
            person_show_body = (LinearLayout)itemView.findViewById(R.id.person_show_body);
            commentLinearLayout=(LinearLayout)itemView.findViewById(R.id.comment_linear);
           // mImageNice9Layout = (ImageNice9Layout) itemView.findViewById(R.id.person_show_nice9);
            messagePicturesLayout=(MessagePicturesLayout)itemView.findViewById(R.id.l_picture);
            lifeShowText=(TextView)itemView.findViewById(R.id.person_show_text);
            sendFavourLay=(LinearLayout)itemView.findViewById(R.id.send_favour_lay);
            lifeShowFavourNu=(TextView)itemView.findViewById(R.id.life_show_favour_nu);
            lifeShowSendTime=(TextView)itemView.findViewById(R.id.life_show_send_time);
            lifeShowNickName=(TextView)itemView.findViewById(R.id.life_show_personal_nickname);
            lifeShowResendNu=(TextView)itemView.findViewById(R.id.life_show_resend_nu);
            lifeShowCommemtNu=(TextView)itemView.findViewById(R.id.life_show_comment_nu);
            lifeShowIv=(CircleImageView) itemView.findViewById(R.id.life_show_personal_iv);
            lifeShowHead=(RelativeLayout)itemView.findViewById(R.id.person_show_head);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;

        FootViewHolder(View itemView) {
            super(itemView);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
        }
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

}
