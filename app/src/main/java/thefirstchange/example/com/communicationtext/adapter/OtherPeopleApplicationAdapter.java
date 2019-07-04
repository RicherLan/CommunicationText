package thefirstchange.example.com.communicationtext.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.OtherApplicationDetailActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.MyFileTools;
import thefirstchange.example.com.communicationtext.util.MyViewTools;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class OtherPeopleApplicationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ChatMsg> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public  Timer addFriendTimer = new Timer();
    public  Timer addGroupTimer = new Timer();


    public OtherPeopleApplicationAdapter(Context context, List<ChatMsg> list){
        this.mList=list;
        this.mContext=context;
        mLayoutInflater=LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new OtherPeopleView(mLayoutInflater.inflate(R.layout.item_other_people_application,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

         ChatMsg chatMsg = mList.get(position);
        String handleRs = chatMsg.getHandleRs();
         boolean isgo=true;
         String groupname = "";
        int gid = chatMsg.getGroupid();
        if(StaticAllList.groupList.containsKey(gid)){
            groupname = StaticAllList.groupList.get(gid).getGroupname();
        }
        String type = mList.get(position).getType();
        if (handleRs.equals("nothandle")||handleRs.equals("hadread")){

            if(type.equals("addfriend")){
                ((OtherPeopleView)holder).agreedText.setVisibility(View.GONE);
                ((OtherPeopleView)holder).agreeText.setVisibility(View.VISIBLE);
                ((OtherPeopleView)holder).refusedText.setVisibility(View.GONE);
                ((OtherPeopleView)holder).checkType.setText("好友申请");
            }else  if(type.equals("addgroup")||type.equals("someoneHasHandledAddGroup")){
                ((OtherPeopleView)holder).agreedText.setVisibility(View.GONE);
                ((OtherPeopleView)holder).agreeText.setVisibility(View.VISIBLE);
                ((OtherPeopleView)holder).refusedText.setVisibility(View.GONE);
                ((OtherPeopleView)holder).checkType.setText("加群申请   "+groupname);
                isgo=false;
            }else  if(type.equals("agreeYourAddFriend")){
                ((OtherPeopleView)holder).agreedText.setVisibility(View.VISIBLE);
                ((OtherPeopleView)holder).agreeText.setVisibility(View.GONE);
                ((OtherPeopleView)holder).refusedText.setVisibility(View.GONE);
                ((OtherPeopleView)holder).checkType.setText("申请回复");
                isgo=false;

            }else  if(type.equals("disagreeYourAddFriend")){

                ((OtherPeopleView)holder).agreedText.setVisibility(View.GONE);
                ((OtherPeopleView)holder).agreeText.setVisibility(View.GONE);
                ((OtherPeopleView)holder).refusedText.setVisibility(View.VISIBLE);
                ((OtherPeopleView)holder).checkType.setText("申请回复");
                isgo=false;
            }else  if(type.equals("agreeAddGroup")){
                ((OtherPeopleView)holder).agreedText.setVisibility(View.VISIBLE);
                ((OtherPeopleView)holder).agreeText.setVisibility(View.GONE);
                ((OtherPeopleView)holder).refusedText.setVisibility(View.GONE);
                ((OtherPeopleView)holder).checkType.setText("申请回复   "+groupname);
                isgo=false;

            }else  if(type.equals("disagreeAddGroup")){
                ((OtherPeopleView)holder).agreedText.setVisibility(View.GONE);
                ((OtherPeopleView)holder).agreeText.setVisibility(View.GONE);
                ((OtherPeopleView)holder).refusedText.setVisibility(View.VISIBLE);
                ((OtherPeopleView)holder).checkType.setText("申请回复   "+groupname);
                isgo=false;

            }else if(type.equals("exitgroup")){

                ((OtherPeopleView)holder).agreedText.setVisibility(View.GONE);
                ((OtherPeopleView)holder).agreeText.setVisibility(View.GONE);
                ((OtherPeopleView)holder).refusedText.setVisibility(View.GONE);
                ((OtherPeopleView)holder).checkType.setText("退出群聊   "+chatMsg.getMsgbody());
                isgo=false;
            }


        }else if (handleRs.equals("agree")){

                if(type.equals("someoneHasHandledAddGroup")){
                 ((OtherPeopleView)holder).checkType.setText("加群申请   "+groupname);
                }
                ((OtherPeopleView)holder).agreedText.setVisibility(View.VISIBLE);
                ((OtherPeopleView)holder).agreeText.setVisibility(View.GONE);
                ((OtherPeopleView)holder).refusedText.setVisibility(View.GONE);

        }else if(handleRs.equals("disagree")){
                if(type.equals("someoneHasHandledAddGroup")){
                    ((OtherPeopleView)holder).checkType.setText("加群申请   "+groupname);
                }
                ((OtherPeopleView)holder).refusedText.setVisibility(View.VISIBLE);
                ((OtherPeopleView)holder).agreeText.setVisibility(View.GONE);
                ((OtherPeopleView)holder).agreedText.setVisibility(View.GONE);

        }

        String msg = mList.get(position).getMsgbody();
        if(type.contains("friend")||type.contains("Friend")){
            if(msg.equals("agree")){
                msg = "同意了你的好友请求";
            }else if(msg.equals("disagree")){
                msg = "拒绝了你的好友请求";
            }
        }else if(type.contains("group")||type.contains("Group")){
            if(type.equals("someoneHasHandledAddGroup")){
                String name = chatMsg.getReceivername();
                msg="已被"+name+"处理";
            }else if(type.equals("exitgroup")){
                msg = "退出群聊";
            }
            else{
                if(msg.equals("agree")){
                    msg = "同意了你的加群请求";
                }else if(msg.equals("disagree")){
                    msg = "拒绝了你的加群请求";
                }
            }

        }

        ((OtherPeopleView)holder).otherReason.setText(msg);


        ((OtherPeopleView)holder).otherNickName.setText(mList.get(position).getSendername());
        final int msgId=mList.get(position).getMsgid();
        final String otherph=mList.get(position).getSenderid();
        final String othername=mList.get(position).getSendername();
        final TextView textView=((OtherPeopleView)holder).agreedText;
        final TextView textView1=((OtherPeopleView)holder).agreeText;
        ((OtherPeopleView)holder).agreeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!NetworkUtils.isConnected(mContext)){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                String msgtype = mList.get(position).getType();
                int groupid =  mList.get(position).getGroupid();
                //有用户添加自己为好友
                if(msgtype.equals("addfriend")){
                    MyDialog.showBottomLoadingDialog(mContext);

                            SendToServer.AddFriendReturn(msgId, ObjectService.personalInfo.getNickname(),otherph,othername,"agree");


                    addFriendTimer = new Timer();
                    addFriendTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    MyDialog.dismissBottomLoadingDialog();
                                    Toast.makeText(mContext, "服务器繁忙,请稍后再试!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    },7000);


                //有用户加群
                }else if(msgtype.equals("addgroup")){

                    MyDialog.showBottomLoadingDialog(mContext);

                    SendToServer.requestAddGroupReturn(msgId,otherph,groupid,"agree");
                    addGroupTimer = new Timer();
                    addGroupTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    MyDialog.dismissBottomLoadingDialog();
                                    Toast.makeText(mContext, "服务器繁忙,请稍后再试!", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    },7000);

                }


//                textView.setVisibility(View.VISIBLE);
//                textView1.setVisibility(View.GONE);

            }
        });


        String icpath = mList.get(position).getSendericon();
        if(icpath==null||icpath.equals("")){
            if(mList.get(position).getSenderid()==null||mList.get(position).getSenderid().equals("")){
                icpath = MyFileTools.getGroupIconPath(mList.get(position).getGroupid());
            }else{
                icpath = MyFileTools.getUserIconPath(mList.get(position).getSenderid());
            }

        }
        MyViewTools.setCircleImage(((OtherPeopleView)holder).otherIv,icpath);

        final boolean finalIsgo = isgo;
        ((OtherPeopleView)holder).relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (finalIsgo){
                    Intent intent=new Intent(mContext, OtherApplicationDetailActivity.class);
                    intent.putExtra("nickname",mList.get(position).getSendername());
                    intent.putExtra("msgbody",mList.get(position).getMsgbody());
                    intent.putExtra("rs",mList.get(position).getHandleRs());
                    intent.putExtra("number",mList.get(position).getSenderid());
                    intent.putExtra("type",mList.get(position).getType());
                    mContext.startActivity(intent);

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public class OtherPeopleView extends RecyclerView.ViewHolder{

        private CircleImageView otherIv;
        private TextView otherNickName;
        private TextView otherReason;
        private TextView agreeText;
        private TextView agreedText;
        private RelativeLayout relativeLayout;
        private TextView refusedText;
        private TextView checkType;
        public OtherPeopleView(View view){
            super(view);
            otherIv=(CircleImageView)view.findViewById(R.id.other_people_iv);
            otherNickName=(TextView)view.findViewById(R.id.other_people_nickname);
            otherReason=(TextView)view.findViewById(R.id.other_people_reason);
            agreedText=(TextView)view.findViewById(R.id.agreed_text);
            agreeText=(TextView)view.findViewById(R.id.agree_text);
            relativeLayout=(RelativeLayout)view.findViewById(R.id.other_people_item);
            refusedText=(TextView)view.findViewById(R.id.application_refused_text);
            checkType=(TextView)view.findViewById(R.id.check_type);
        }

    }
}
