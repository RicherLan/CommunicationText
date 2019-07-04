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
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.CourseAndScore;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.SchDutyActivity;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.MyFileTools;
import thefirstchange.example.com.communicationtext.util.MyViewTools;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class DutyNotiRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<ChatMsg> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public static Timer timer = new Timer();


    public DutyNotiRecyclerAdapter(Context context, List<ChatMsg> list){
        this.mList=list;
        this.mContext=context;
        mLayoutInflater=LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new DutyNotiView(mLayoutInflater.inflate(R.layout.item_duty_noti_recucler,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        DutyNotiView dutyNotiView = (DutyNotiView)holder;

        ChatMsg chatMsg = mList.get(position);
        final String mystate = chatMsg.getMsgbody();
        final String dutystate = chatMsg.getHandleRs();
        final int groupid = chatMsg.getGroupid();
        final long time = chatMsg.getMsgtime();
        final int dnid = chatMsg.getMsgid();

        if(mystate.equals("yes")){    //本次值班我被安排进来
            dutyNotiView.duty_noti_ry_myduty_tv.setText(" 我被安排值班");
        }else{
            dutyNotiView.duty_noti_ry_myduty_tv.setText(" 我未被安排值班");
        }

        if(dutystate.equals("new")){   //该值班是新的
            dutyNotiView.duty_noti_ry_state_tv.setText("未读");
        }else if(dutystate.equals("hasread")){
            dutyNotiView.duty_noti_ry_state_tv.setText("已读");
        }else{
            dutyNotiView.duty_noti_ry_state_tv.setText("过期");
        }

        String icpath = MyFileTools.getGroupIconPath(groupid);
        MyViewTools.setCircleGroupIcon(dutyNotiView.group_iv,icpath);

        dutyNotiView.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dutystate.equals("old")){
                    return;
                }else if(dutystate.equals("hasread")){
                    CourseAndScore.duty_groupid = groupid;
                    Intent intent = new Intent(mContext, SchDutyActivity.class);
                    mContext.startActivity(intent);
                    return;
                }

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

                MyDialog.showBottomLoadingDialog(mContext);

                        SendToServer.getSDByGid(groupid,dnid);


                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                MyDialog.dismissBottomLoadingDialog();
                                Toast.makeText(mContext.getApplicationContext(),"服务器繁忙,请稍后再试!",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                },20000);

            }
        });


    }




    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public class DutyNotiView extends RecyclerView.ViewHolder{

        private CircleImageView group_iv;
        private TextView duty_noti_ry_myduty_tv;
        private TextView duty_noti_ry_state_tv;
        private RelativeLayout relativeLayout;


        public DutyNotiView(View view){
            super(view);
            group_iv=(CircleImageView)view.findViewById(R.id.duty_noti_ry_iv);
            duty_noti_ry_myduty_tv=(TextView)view.findViewById(R.id.duty_noti_ry_myduty_tv);
            duty_noti_ry_state_tv=(TextView)view.findViewById(R.id.duty_noti_ry_state_tv);

            relativeLayout=(RelativeLayout)view.findViewById(R.id.duty_noti_ry_item);

        }

    }


}
