package thefirstchange.example.com.communicationtext.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Vector;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.dongtai.DongtaiMsg;
import thefirstchange.example.com.communicationtext.util.TimeUtil;

public class LifeShowNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private Vector<DongtaiMsg> dongtaiMsgs;
    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;
    public LifeShowNotificationAdapter(Context mContext, Vector<DongtaiMsg> dongtaiMsgs){
        this.mContext=mContext;
        this.dongtaiMsgs=dongtaiMsgs;
        layoutInflater=LayoutInflater.from(mContext);
    }

    public static enum ITEM_TYPE{
        ITEM_OR,
        ITEM_FOOT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==ITEM_TYPE.ITEM_FOOT.ordinal()){
            return new FootViewHolder(layoutInflater.inflate(R.layout.layout_refresh_footer,parent,false));
        }else {
            return new LifeShowMsgView(layoutInflater.inflate(R.layout.item_life_show_notification,parent,false));

        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LifeShowMsgView){
            LifeShowNotificationAdapter.LifeShowMsgView lifeShowMsgView=(LifeShowNotificationAdapter.LifeShowMsgView)holder;
            DongtaiMsg dongtaiMsg=dongtaiMsgs.get(position);
            String type=dongtaiMsg.getType();
            lifeShowMsgView.notNickName.setText(dongtaiMsg.getUsername());
            lifeShowMsgView.timeText.setText(TimeUtil.getChatTime(dongtaiMsg.getTime()));
            int dongtaiid = dongtaiMsg.getDongtaiid();
            for(int i=0;i< StaticAllList.dongtaisMsgDongtai.size();++i){
                if(dongtaiid== StaticAllList.dongtaisMsgDongtai.get(i).getId()){
                    String dongtaiUsername = StaticAllList.dongtaisMsgDongtai.get(i).getSdname();     //动态的主人昵称
                    String content = StaticAllList.dongtaisMsgDongtai.get(i).getContent();                 //动态的内容
                    if( StaticAllList.dongtaisMsgDongtai.get(i).getImph().size()!=0){
                        String filepath = StaticAllList.dongtaisMsgDongtai.get(i).getImph().get(0);   //第一张图片的位置
                    }

                }
            }
           // lifeShowMsgView.fromNickName.setText(dongtaiMsg.get);
            if (type.equals("praise")){
                lifeShowMsgView.goodLay.setVisibility(View.VISIBLE);
                lifeShowMsgView.resendLay.setVisibility(View.GONE);
                lifeShowMsgView.commentText.setVisibility(View.GONE);
            }else if (type.equals("tocomment")){
                lifeShowMsgView.goodLay.setVisibility(View.GONE);
                lifeShowMsgView.resendLay.setVisibility(View.GONE);
                lifeShowMsgView.commentText.setVisibility(View.VISIBLE);
                lifeShowMsgView.commentText.setText(dongtaiMsg.getMsg());
            }




        }else {
            LifeShowNotificationAdapter.FootViewHolder footViewHolder = (LifeShowNotificationAdapter.FootViewHolder) holder;
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
        }


    }

    @Override
    public int getItemCount() {
        return dongtaiMsgs.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
       if (position+1==getItemCount()){
           return ITEM_TYPE.ITEM_FOOT.ordinal();
       }else {
           return ITEM_TYPE.ITEM_OR.ordinal();
       }
    }

    class LifeShowMsgView extends RecyclerView.ViewHolder{

        CircleImageView ivHead;
        TextView notNickName;
        TextView timeText;
        TextView commentText;
        LinearLayout goodLay;
        LinearLayout resendLay;
        LinearLayout showLay;
        ImageView fistImage;
        TextView fromNickName;
        TextView showContent;
        TextView replyText;


        public LifeShowMsgView(View itemView) {
            super(itemView);
            ivHead=(CircleImageView)itemView.findViewById(R.id.item_life_show_notify_iv);
            notNickName=(TextView)itemView.findViewById(R.id.life_notification_nickname);
            timeText=(TextView)itemView.findViewById(R.id.item_life_show_notify_time);
            commentText=(TextView)itemView.findViewById(R.id.item_life_show_notify_comment);
            goodLay=(LinearLayout)itemView.findViewById(R.id.item_life_show_notify_good_lay);
            resendLay=(LinearLayout)itemView.findViewById(R.id.item_life_show_notify_resend_lay);
            showLay=(LinearLayout)itemView.findViewById(R.id.item_life_show_notify_show_lay);
            fistImage=(ImageView)itemView.findViewById(R.id.item_life_show_notify_first_ima);
            fromNickName=(TextView)itemView.findViewById(R.id.item_life_show_notify_from);
            showContent=(TextView)itemView.findViewById(R.id.item_life_show_notify_content);
            replyText=(TextView)itemView.findViewById(R.id.item_life_show_notify_reply_to_other);
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
