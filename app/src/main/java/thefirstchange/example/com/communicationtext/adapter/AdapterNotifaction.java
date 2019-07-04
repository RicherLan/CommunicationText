package thefirstchange.example.com.communicationtext.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import cn.andy.qpopuwindow.QPopuWindow;
import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.ConnectOtherActivity;
import thefirstchange.example.com.communicationtext.activity.DutyNotiRecyViewActivity;
import thefirstchange.example.com.communicationtext.group.GroupChatActivity;
import thefirstchange.example.com.communicationtext.activity.OtherPeopleApplicationActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.MyFileTools;
import thefirstchange.example.com.communicationtext.util.TimeUtil;
import thefirstchange.example.com.communicationtext.view.drop.WaterDropNoAction;

public class AdapterNotifaction extends RecyclerView.Adapter<AdapterNotifaction.ViewHolder>{

    List<ChatMsg> mList;
    Context  mContext;

    public AdapterNotifaction(Context context, List<ChatMsg> list){
        mContext=context;
        mList=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_notification_item,parent,false));
    }


    @Override
    public void onBindViewHolder(AdapterNotifaction.ViewHolder holder, final int position) {
        final ViewHolder viewHolder=(ViewHolder)holder;
       // holder.mTextView.setText(.get(position));
        ChatMsg notification =mList.get(position);
        String type = notification.getType();
        if(type.equals("yourfrienddeleteyou")){
            return;
        }
        viewHolder.notificationText.setText(notification.getMsgbody());

        if(type.equals("schDuty")){
            String mystate = notification.getMsgbody();
            String isDuty = "我被安排值班";
            if(mystate.equals("no")){    //本次值班我被安排进来
                isDuty = "我未被安排值班";
            }
            viewHolder. notificationText.setText("值班表更新  "+isDuty);
        }else if(type.equals("addgroup")){
            viewHolder.notificationText.setText("请求加群");
        }else if(type.equals("addfriend")){
            viewHolder. notificationText.setText("请求添加你为好友");
        }else if(type.equals("agreeYourAddFriend")){
            viewHolder.notificationText.setText("同意了你的好友请求");
        }else if(type.equals("disagreeYourAddFriend")){
            viewHolder. notificationText.setText("不同意你的好友请求");
        }else if(type.equals("agreeAddGroup")){
            viewHolder.notificationText.setText("同意了你的加群请求");
        }else if(type.equals("disagreeAddGroup")){
            viewHolder.notificationText.setText("不同意你的加群请求");
        }else if(type.equals("someoneHasHandledAddGroup")){
            String handlername = notification.getReceivername();
            viewHolder. notificationText.setText("请求加群 已被"+handlername+"处理");
        }else if(type.equals("exitgroup")){
            String groupname = notification.getMsgbody();
            viewHolder.notificationText.setText("退出 "+groupname+" 群聊");
        }
        else if(notification.getMsgtype().equals("voice")){
            viewHolder. notificationText.setText("语音消息");
        }else if(notification.getMsgtype().equals("image")){
            viewHolder. notificationText.setText("图片");
        }

        viewHolder.senderNickName.setText(notification.getSendername());


        String id = "";
        String name = "";
        if(type.equals("group")){
            id = notification.getGroupid()+"";
            for(int groupid2 : StaticAllList.groupList.keySet()){
                int groupid = StaticAllList.groupList.get(groupid2).getGroupid();
                if(id.equals(groupid+"")){
                    name = StaticAllList.groupList.get(groupid2).getGroupname();
                }
            }
        }else if(type.equals("single")){
            id = notification.getSenderid();
            name = notification.getSendername();
        }


        if(type.equals("group")){
            viewHolder.senderNickName.setText(name);
        }
        else if(!type.equals("schDuty")&&notification.getSenderid().equals(ObjectService.personalInfo.getPhonenumber())){
            viewHolder.senderNickName.setText(notification.getReceivername());
        }else{
            viewHolder.senderNickName.setText(notification.getSendername());
        }

        viewHolder. notificationTime.setText(TimeUtil.getChatTime(notification.getMsgtime()));



        /*      设置未读消息数量   */
        int msgnum=0;

        //单人聊天
        if(type.equals("single")){
            if(MyMessageQueue.chatQueueNotRead!=null){
                String uid = mList.get(position).getSenderid();
                if(uid.equals(ObjectService.personalInfo.getPhonenumber())){
                    uid=mList.get(position).getReceiverid();
                }

                for(String keyid: MyMessageQueue.chatQueueNotRead.keySet()){
                    if(keyid.equals(uid)){
                        msgnum+=MyMessageQueue.chatQueueNotRead.get(id).size();
                        break;
                    }
                }
            }

            //群聊
        }else if(type.equals("group")){

            if(MyMessageQueue.chatQueueNotRead!=null){
                int groupid = mList.get(position).getGroupid();
                for(String keyid: MyMessageQueue.chatQueueNotRead.keySet()){
                    if(keyid.equals(groupid+"")){
                        msgnum+=MyMessageQueue.chatQueueNotRead.get(id).size();
                        break;
                    }
                }
            }

            //值班通知
        }else if(type.equals("schDuty")){
            int groupid = mList.get(position).getGroupid();
            if(MyMessageQueue.dutyQueueNotRead!=null){
                for(int gid:MyMessageQueue.dutyQueueNotRead.keySet()){
                    if(gid==groupid){
                        msgnum+=MyMessageQueue.dutyQueueNotRead.get(gid).size();
                    }
                }
            }


            //好友或群请求应答通知
        }else{
            if(MyMessageQueue.requestQueueNotHandle!=null&&MyMessageQueue.requestQueueNotHandle.size()!=0) {
                for(int i=0;i<MyMessageQueue.requestQueueNotHandle.size();++i){
                    if(!MyMessageQueue.requestQueueNotHandle.get(i).getHandleRs().equals("hadread")){
                        ++msgnum;
                    }
                }
            }
        }


        if(msgnum<=0){
            viewHolder.waterDrop.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.waterDrop.setVisibility(View.VISIBLE);
            if(msgnum>99){
                msgnum=99;
            }
            viewHolder.waterDrop.setText(msgnum+"");
        }

        String icpath = null;
        if(type.equals("group")||type.equals("schDuty")){ //群消息或值班通知
             int gid = mList.get(position).getGroupid();
            icpath = MyFileTools.getGroupIconPath(gid);
            if(icpath==null||icpath.equals("")){

                SendToServer.getNotiIconOfGroup(gid);

            }
        }else{
            icpath = mList.get(position).getSendericon();
            if(icpath==null||icpath.equals("")){
                String ph = mList.get(position).getSenderid();
                if(ph.equals(ObjectService.personalInfo.getPhonenumber())){
                    ph = mList.get(position).getReceiverid();
                }
                icpath = MyFileTools.getUserIconPath(ph);
                if(icpath==null||icpath.equals("")){
                    final String phtemp = ph;

                            SendToServer.getNotiIconOfUser(phtemp);

                }
            }
        }

        if(icpath!=null&&!icpath.equals("")) {
            File file = new File(icpath);
            if (file.exists()) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(icpath);
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    viewHolder.nitification_icon.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
//                        nitification_icon .setImageDrawable(mContext.getResources().getDrawable(R.drawable.user_image1));
                }
            }
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = mList.get(position).getSenderid();
                int groupid= mList.get(position).getGroupid();
                String name = mList.get(position).getSendername();
                //如果是自己的Id  那么就传递好友deid
                String type = mList.get(position).getType();
                if(type.equals("single")){
                    if(mList.get(position).getSenderid().equals(ObjectService.personalInfo.getPhonenumber())){
                        id=mList.get(position).getReceiverid();
                        name = mList.get(position).getReceivername();
                    }
                }else{
                    id=mList.get(position).getGroupid()+"";
                    for(int groupid2 : StaticAllList.groupList.keySet()){
                        int groupid3 = StaticAllList.groupList.get(groupid2).getGroupid();
                        if(id.equals(groupid3+"")){
                            name = StaticAllList.groupList.get(groupid2).getGroupname();
                        }
                    }
                }

                Intent intent;

                if(type.equals("single")){
                    intent=new Intent(mContext, ConnectOtherActivity.class);
                    intent.putExtra("senderId",id);
                    intent.putExtra("receiver_nickname",name);

                }else if(type.equals("group")){
                    intent=new Intent(mContext, GroupChatActivity.class);
                    intent.putExtra("groupId",groupid);
                    intent.putExtra("groupname",name);

                }else if(type.equals("schDuty")){
                    intent=new Intent(mContext, DutyNotiRecyViewActivity.class);
                    intent.putExtra("groupid",groupid);
                }else{
                    intent=new Intent(mContext, OtherPeopleApplicationActivity.class);
                   // intent.putExtra("msgtype",type);
                }
                mContext.startActivity(intent);

            }
        });




   }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return mList.size();
    }



    class GroupHolder extends RecyclerView.ViewHolder{
        public GroupHolder(View view){
            super(view);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout notification_layout;
        private TextView notificationText;
        private TextView senderNickName;
        private TextView notificationTime;
        private CircleImageView nitification_icon;
        private WaterDropNoAction waterDrop;
        MotionEvent event;

        public ViewHolder(View view) {
            super(view);
            notification_layout = (LinearLayout)view.findViewById(R.id.notification_layout);
            notificationText=(TextView)view.findViewById(R.id.notification_text);
            senderNickName=(TextView)view.findViewById(R.id.notification_sender_nickname);
            notificationTime=(TextView)view.findViewById(R.id.notification_time);
            nitification_icon=(CircleImageView)view.findViewById(R.id.nitification_icon);
            waterDrop=(WaterDropNoAction)view.findViewById(R.id.notification_water_drop);


            //长按  弹窗
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    QPopuWindow.getInstance(MainActivity.mainactivityContext).builder
                            .bindView(v,ViewHolder.this.getAdapterPosition())
                            .setPopupItemList(new String[]{"置顶","删除","标记","更多"})
                            .setRadius(30)
                            .setTextSize(15)
                            .setPointers(MainActivity.mainactivity.rawX,MainActivity.mainactivity.rawY)
                            .setOnPopuListItemClickListener(new QPopuWindow.OnPopuListItemClickListener() {
                                //position  为点击的是哪一个选项 ： 删除、更多等
                                //anchorViewPosition  是recyclerview的第几个item
                                //两者下表都是从0开始
                                @Override
                                public void onPopuListItemClick(View anchorView, int anchorViewPosition, int position) {
//                                   Toast.makeText(MainActivity.mainactivityContext,anchorViewPosition+"  "+position,Toast.LENGTH_SHORT).show();
                                    switch (position){
                                        case 1:     //删除

                                            String id = mList.get(anchorViewPosition).getSenderid();
                                            int groupid= mList.get(anchorViewPosition).getGroupid();
//                                            String name = mList.get(anchorViewPosition).getSendername();
                                            //如果是自己的Id  那么就传递好友deid
                                            String type = mList.get(anchorViewPosition).getType();
                                            if(type.equals("single")){
                                                if(mList.get(anchorViewPosition).getSenderid().equals(ObjectService.personalInfo.getPhonenumber())){
                                                    id=mList.get(anchorViewPosition).getReceiverid();
                                                   // name = mList.get(position).getReceivername();
                                                }
                                            }else{
                                                id=mList.get(anchorViewPosition).getGroupid()+"";
                                            }

                                            if(type.contains("friend")||type.contains("Friend")||type.contains("group")||type.contains("Group")){
                                                type="request";
                                            }

                                            if(StaticAllList.chatRecordDao!=null){
                                                StaticAllList.chatRecordDao.deleteNoticeFrame(type,id,groupid);
                                            }else{
                                                MainActivity.initChatRecorddb();
                                                StaticAllList.chatRecordDao.deleteNoticeFrame(type,id,groupid);
                                            }

                                            /*
                                            //单人聊天
                                            if(type.equals("single")){

                                            //群聊
                                            }else if(type.equals("group")){

                                            //值班通知
                                            }else if(type.equals("schDuty")){


                                                //好友或群请求应答通知
                                            }else{

                                            }*/

                                            removeData(anchorViewPosition);
                                            break;
                                        default:
                                            Toast.makeText(MainActivity.mainactivityContext,"该功能暂未上线!",Toast.LENGTH_SHORT).show();
                                            break;

                                    }
                                }
                            }).show();
                    return true;
                }
            });


        }

    }

    //  删除数据
    public void removeData(int position) {
        mList.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


}
