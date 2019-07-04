package thefirstchange.example.com.communicationtext.showcomment.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.ShowMoreCommentActivity;
import thefirstchange.example.com.communicationtext.dongtai.DTComCh;
import thefirstchange.example.com.communicationtext.dongtai.DTComRoot;
import thefirstchange.example.com.communicationtext.showcomment.bean.CommentDetailBean;
import thefirstchange.example.com.communicationtext.showcomment.bean.ReplyDetailBean;
import thefirstchange.example.com.communicationtext.util.TimeUtil;

/**
 * Author: Moos
 * E-mail: moosphon@gmail.com
 * Date:  18/4/20.
 * Desc: 评论与回复列表的适配器
 */

public class CommentExpandAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CommentExpandAdapter";
    private List<DTComRoot> commentBeanList;
    private List<DTComCh> replyBeanList;
    private Context context;
    private int pageIndex = 1;


    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;

    public CommentExpandAdapter(Context context, List<DTComRoot> commentBeanList) {
        this.context = context;
        this.commentBeanList = commentBeanList;
    }

    @Override
    public int getGroupCount() {
        return commentBeanList.size()+1;
    }

    @Override
    public int getChildrenCount(int i) {
        if(commentBeanList.get(i).getDtComChs() == null){
            return 0;
        }else {
            if(commentBeanList.get(i).getDtComChs().size()>0){
                if(commentBeanList.get(i).getDtComChs().size()>3){
                    return 4;
                }else {
                    return commentBeanList.get(i).getDtComChs().size();
                }

            }else {
                return 0;
            }
           // return commentBeanList.get(i).getReplyList().size()>0 ? commentBeanList.get(i).getReplyList().size():0;
        }

    }

    @Override
    public Object getGroup(int i) {
        return commentBeanList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return commentBeanList.get(i).getDtComChs().get(i1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    boolean isLike = false;

    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        if(groupPosition+1==getGroupCount()){
            FootHolder footViewHolder;
            convertView=LayoutInflater.from(context).inflate(R.layout.layout_refresh_footer,viewGroup,false);
            footViewHolder=new FootHolder(convertView);
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
            final GroupHolder groupHolder;

//            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.comment_item_layout, viewGroup, false);
                groupHolder = new GroupHolder(convertView);
 //               convertView.setTag(groupHolder);
//            }else {
//                groupHolder = (GroupHolder) convertView.getTag();
//            }
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .error(R.mipmap.ic_launcher)
                    .centerCrop();
            Glide.with(context).load(R.drawable.user_other)
                    .apply(requestOptions)
                    .into(groupHolder.logo);
            groupHolder.tv_name.setText(commentBeanList.get(groupPosition).getRootuname());
            groupHolder.tv_time.setText(TimeUtil.getChatTime(commentBeanList.get(groupPosition).getTime()));
            groupHolder.tv_content.setText(commentBeanList.get(groupPosition).getMsg());
            groupHolder.iv_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isLike){
                        isLike = false;
                        groupHolder.iv_like.setColorFilter(Color.parseColor("#aaaaaa"));
                    }else {
                        isLike = true;
                        groupHolder.iv_like.setColorFilter(Color.parseColor("#FF5C5C"));
                    }
                }
            });

        }


        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        if (childPosition<3){
            final ChildHolder childHolder;
//            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.comment_reply_item_layout,viewGroup, false);
                childHolder = new ChildHolder(convertView);
                convertView.setTag(childHolder);
//            }
//            else {
//                childHolder = (ChildHolder) convertView.getTag();
//            }

            String replyUser = commentBeanList.get(groupPosition).getDtComChs().get(childPosition).getUname();
            if(!TextUtils.isEmpty(replyUser)){
                childHolder.tv_name.setText(replyUser + ":");
            }else {
                childHolder.tv_name.setText("无名"+":");
            }

            childHolder.tv_content.setText(commentBeanList.get(groupPosition).getDtComChs().get(childPosition).getMsg());
        }else {
            MoreHolder moreHolder;
//            if (convertView==null){
                convertView=LayoutInflater.from(context).inflate(R.layout.more_comment_reply_item,viewGroup,false);
                moreHolder=new MoreHolder(convertView);
                convertView.setTag(moreHolder);
//            }else {
//                moreHolder=(MoreHolder)convertView.getTag();
//            }

            moreHolder.to_more_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ShowMoreCommentActivity.class);
                    context.startActivity(intent);

                }
            });

        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private class GroupHolder{
        private CircleImageView logo;
        private TextView tv_name, tv_content, tv_time;
        private ImageView iv_like;
        public GroupHolder(View view) {
            logo = (CircleImageView) view.findViewById(R.id.comment_item_logo);
            tv_content = (TextView) view.findViewById(R.id.comment_item_content);
            tv_name = (TextView) view.findViewById(R.id.comment_item_userName);
            tv_time = (TextView) view.findViewById(R.id.comment_item_time);
            iv_like = (ImageView) view.findViewById(R.id.comment_item_like);
        }
    }

    private class ChildHolder{
        private TextView tv_name, tv_content;
        public ChildHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.reply_item_user);
            tv_content = (TextView) view.findViewById(R.id.reply_item_content);
        }
    }

    private class MoreHolder{
        private TextView to_more_comment;
        public MoreHolder(View view){
            to_more_comment=(TextView)view.findViewById(R.id.to_more_comment);
        }
    }



    private class FootHolder{
        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;
        FootHolder(View itemView){
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
        }
    }



//    /**
//     * by moos on 2018/04/20
//     * func:评论成功后插入一条数据
//     * @param commentDetailBean 新的评论数据
//     */
//    public void addTheCommentData(CommentDetailBean commentDetailBean){
//        if(commentDetailBean!=null){
//
//            commentBeanList.add(commentDetailBean);
//            notifyDataSetChanged();
//        }else {
//            throw new IllegalArgumentException("评论数据为空!");
//        }
//
//    }

//    /**
//     * by moos on 2018/04/20
//     * func:回复成功后插入一条数据
//     * @param replyDetailBean 新的回复数据
//     */
//    public void addTheReplyData(DTComCh replyDetailBean, int groupPosition){
//        if(replyDetailBean!=null){
//            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:"+replyDetailBean.toString() );
//            if(commentBeanList.get(groupPosition).getDtComChs() != null ){
//                commentBeanList.get(groupPosition).getDtComChs().add(replyDetailBean);
//            }else {
//                List<DTComCh> replyList = new ArrayList<>();
//                replyList.add(replyDetailBean);
//                commentBeanList.get(groupPosition).setReplyList(replyList);
//            }
//            notifyDataSetChanged();
//        }else {
//            throw new IllegalArgumentException("回复数据为空!");
//        }
//
//    }



//    /**
//     * by moos on 2018/04/20
//     * func:添加和展示所有回复
//     * @param replyBeanList 所有回复数据
//     * @param groupPosition 当前的评论
//     */
//    private void addReplyList(List<DTComCh> replyBeanList, int groupPosition){
//        if(commentBeanList.get(groupPosition).getDtComChs() != null ){
//            commentBeanList.get(groupPosition).getDtComChs().clear();
//            commentBeanList.get(groupPosition).getDtComChs().addAll(replyBeanList);
//        }else {
//
//          //  commentBeanList.get(groupPosition).setReplyList(replyBeanList);
//        }
//
//        notifyDataSetChanged();
//    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

}
