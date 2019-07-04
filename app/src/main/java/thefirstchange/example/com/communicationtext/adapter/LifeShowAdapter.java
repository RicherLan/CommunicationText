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

import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.CommentShowActivity;
import thefirstchange.example.com.communicationtext.activity.PersonalHomePageActivity;
import thefirstchange.example.com.communicationtext.dongtai.Dongtai;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.User;
import thefirstchange.example.com.communicationtext.imagewarker.MessagePicturesLayout;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.DongtaiUploadThread;
import thefirstchange.example.com.communicationtext.service.StaticAllListOperator;
import thefirstchange.example.com.communicationtext.util.DateUtils;
import thefirstchange.example.com.communicationtext.util.MyTools;
import thefirstchange.example.com.communicationtext.util.MyViewTools;
import thefirstchange.example.com.communicationtext.util.TimeUtil;



public class LifeShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MessagePicturesLayout.Callback mCallback;
    private int pos=0;

    private Vector<Dongtai> dongtais;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;

    //上次请求该动态的该图片的时间
    Map<String,Long> dongtaiIcRequest = new HashMap<>();

    //上次请求该动态的主人头像的时间
    Map<String,Long> dongtaiUserIcRequest = new HashMap<>();


    public LifeShowAdapter(Context mContext, Vector<Dongtai> dongtais) {
        this.mContext = mContext;
        this.dongtais=dongtais;
        layoutInflater=LayoutInflater.from(mContext);
    }

    public static enum ITEM_TYPE{
        ITEM_TYPE_USER,
        ITEM_TYPE_AUTH,
        ITEM_TYPE_AD,
        TYPE_FOOT;
    }

    public LifeShowAdapter setPictureClickCallback(MessagePicturesLayout.Callback callback) {
        mCallback = callback;
        return this;
    }

    public int getpos(){
        return pos;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==ITEM_TYPE.ITEM_TYPE_USER.ordinal()){
            return new UserViewHolder(layoutInflater.inflate(R.layout.person_show_item,parent,false));
        }else if (viewType==ITEM_TYPE.TYPE_FOOT.ordinal()){
            return new FootViewHolder(layoutInflater.inflate(R.layout.layout_refresh_footer,parent,false));
        }
            return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder){
//            ((UserViewHolder)holder).mImageNice9Layout.setVisibility(View.GONE);
            RequestOptions options = new RequestOptions();
            options.placeholder(R.drawable.default_picture);
            options.centerCrop();


            int pos=position;
            if(pos<0){
                return;
            }

            final Dongtai dongtai= dongtais.get(pos);
            ((UserViewHolder)holder).lifeShowNickName.setText(dongtai.getSdname());
            ((UserViewHolder)holder).lifeShowSendTime.setText(TimeUtil.getChatTime(dongtai.getTime()));
                List<String> ls=new ArrayList<String>();
                for (int i=0;i<dongtai.getImph().size();i++){
                    ls.add(dongtai.getImph().get(i));


                    //如果该图片没有  拿到  那么向服务器请求
                    if(MyTools.isInteger(dongtai.getImph().get(i))){
                        int fileid = Integer.parseInt(dongtai.getImph().get(i));
                        //从未请求过该图片
                        if(!dongtaiIcRequest.containsKey(dongtai.getId()+fileid+"")){
                            dongtaiIcRequest.put(dongtai.getId()+fileid+"",System.currentTimeMillis());

                            SendToServer.getDongtaiIcByIcid(dongtai.getId(),fileid);


                        }else {
                            //上次请求该动态的该图片的时间
                            long oldtime = dongtaiIcRequest.get(dongtai.getId()+fileid+"");
                            long nowtime = System.currentTimeMillis();
                            //如果上次的请求是在8秒前  那么再次请求

                            if(!DateUtils.isTwoTimeDifferLessAppointTime(oldtime, nowtime,8)){
                                dongtaiIcRequest.put(dongtai.getId()+fileid+"",nowtime);

                                SendToServer.getDongtaiIcByIcid(dongtai.getId(),fileid);

                            }
                        }

                    }

                }
              //  int a = ls.size();
                ((UserViewHolder)holder).lPictures.set(ls,ls);
//                ((UserViewHolder)holder).mImageNice9Layout.setVisibility(View.VISIBLE);
//                ((UserViewHolder)holder).mImageNice9Layout.bindData(dongtai.getImagepath());
//                ((UserViewHolder)holder).mImageNice9Layout.setItemDelegate(new ImageNice9Layout.ItemDelegate() {
//                    @Override
//                    public void onItemClick(int position) {
//                        Toast.makeText(mContext, "位置"+ position, Toast.LENGTH_SHORT).show();
//                    }
//                });


            if (!dongtai.getContent().isEmpty()){
                ((UserViewHolder)holder).lifeShowText.setVisibility(View.VISIBLE);
                ((UserViewHolder)holder).lifeShowText.setText(dongtai.getContent());
            }else {
                ((UserViewHolder)holder).lifeShowText.setVisibility(View.GONE);
            }

            ((UserViewHolder)holder).commentLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, CommentShowActivity.class);
                    intent.putExtra("showid",dongtai.getId());
                    mContext.startActivity(intent);
                }
            });
            ((UserViewHolder)holder).sendFavourLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Vector<Integer> ids = new Vector<>();
                    ids.add(dongtai.getId());
                    DongtaiUploadThread dongtaiUploadThread  = new DongtaiUploadThread("dongtaipraise",ids);
                    dongtaiUploadThread.start();


                }
            });
            ((UserViewHolder)holder).lifeShowIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String ph = dongtai.getSdid()+"";
                    if(!StaticAllList.usertemps.containsKey(ph)){
                        User user = new User();
                        user.setPhonenumber(ph);
                        StaticAllListOperator.add2UserTemps(user);
                    }


                    Intent intent=new Intent(mContext, PersonalHomePageActivity.class);
                    intent.putExtra("from","otherpeople");
                    intent.putExtra("id",ph);
                    mContext.startActivity(intent);
                }
            });


//            if(dongtai.getSdid().equals(ObjectService.personalInfo.getPhonenumber())){
//                ((UserViewHolder)holder).lifeShowIv.setImageBitmap(ObjectService.personalIcon);
//            }else
                {
                String icpath = dongtai.getSdic();
                MyViewTools.setCircleImage(((UserViewHolder)holder).lifeShowIv,icpath);

                //请求头像
                if(icpath==null||icpath.equals("")){
                    //从未请求过该图片
                    if(!dongtaiUserIcRequest.containsKey(dongtai.getId()+dongtai.getSdid())){
                        dongtaiUserIcRequest.put(dongtai.getId()+dongtai.getSdid(),System.currentTimeMillis());

                        SendToServer.getDongtaiUICByDTID(dongtai.getSdid(),dongtai.getId());


                    }else {
                        //上次请求该动态的该图片的时间
                        long oldtime = dongtaiUserIcRequest.get(dongtai.getId()+dongtai.getSdid());
                        long nowtime = System.currentTimeMillis();
                        //如果上次的请求是在8秒前  那么再次请求

                        if(!DateUtils.isTwoTimeDifferLessAppointTime(oldtime, nowtime,8)){
                            dongtaiUserIcRequest.put(dongtai.getId()+dongtai.getSdid(),nowtime);

                            SendToServer.getDongtaiUICByDTID(dongtai.getSdid(),dongtai.getId());
                        }
                    }

                }

//            else{
//                orGroupPeopleView.peopleImage .setImageDrawable(mContext.getResources().getDrawable(R.drawable.user_logo));
//            }
            }


            ((UserViewHolder)holder).lifeShowHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent=new Intent(mContext, CommentShowActivity.class);
//                    mContext.startActivity(intent);


                }
            });
            if (dongtai.getPranum()>0){
                ((UserViewHolder)holder).lifeShowFavourNu.setText(dongtai.getPranum());
            }
            if (dongtai.getComnum()>0){
                ((UserViewHolder)holder).lifeShowCommemtNu.setText(dongtai.getComnum()+"");
            }

        }else if (holder instanceof FootViewHolder){
            FootViewHolder footViewHolder = (FootViewHolder) holder;
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
        return dongtais.size()+1;
    }

    public int getItemViewType(int position) {

        if (position+1==getItemCount()){
            pos = position;
            return ITEM_TYPE.TYPE_FOOT.ordinal();

        }
        if (dongtais.get(position).getType().equals("user")){
            return ITEM_TYPE.ITEM_TYPE_USER.ordinal();
        }else if (dongtais.get(position).getType().equals("auth")){
            return ITEM_TYPE.ITEM_TYPE_AUTH.ordinal();
        }else  {
            return ITEM_TYPE.ITEM_TYPE_AD.ordinal();
        }
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
//        ImageNice9Layout mImageNice9Layout;
        MessagePicturesLayout lPictures;
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
        public UserViewHolder(View itemView) {
            super(itemView);
            commentLinearLayout=(LinearLayout)itemView.findViewById(R.id.comment_linear);
            //mImageNice9Layout = (ImageNice9Layout) itemView.findViewById(R.id.person_show_nice9);
            lifeShowText=(TextView)itemView.findViewById(R.id.person_show_text);
            sendFavourLay=(LinearLayout)itemView.findViewById(R.id.send_favour_lay);
            lifeShowFavourNu=(TextView)itemView.findViewById(R.id.life_show_favour_nu);
            lifeShowSendTime=(TextView)itemView.findViewById(R.id.life_show_send_time);
            lifeShowNickName=(TextView)itemView.findViewById(R.id.life_show_personal_nickname);
            lifeShowResendNu=(TextView)itemView.findViewById(R.id.life_show_resend_nu);
            lifeShowCommemtNu=(TextView)itemView.findViewById(R.id.life_show_comment_nu);
            lifeShowIv=(CircleImageView) itemView.findViewById(R.id.life_show_personal_iv);
            lifeShowHead=(RelativeLayout)itemView.findViewById(R.id.person_show_head);
            lPictures = (MessagePicturesLayout) itemView.findViewById(R.id.l_picture);
            lPictures.setCallback(mCallback);
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


//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if (manager instanceof GridLayoutManager) {
//            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
//            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
//                    return getItemViewType(position) == ITEM_TYPE.TYPE_FOOT.ordinal()? gridManager.getSpanCount() : 1;
//                }
//            });
//        }
//    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
}
