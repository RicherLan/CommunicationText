package thefirstchange.example.com.communicationtext.corpration.appointPos.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.User;
import thefirstchange.example.com.communicationtext.gson.UserInGroupInfo;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CorpSendToServer;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.StaticAllListOperator;
import thefirstchange.example.com.communicationtext.util.DateUtils;
import thefirstchange.example.com.communicationtext.util.DensityUtiltwo;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.MyFileTools;
import thefirstchange.example.com.communicationtext.util.MyViewTools;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

/*
    任职授衔  群的所有人  适配器
 */
public class AppointPeopleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    //0加载普通群列表布局
    private int choositem=0;
    private LayoutInflater layoutInflater;
    private List<UserInGroupInfo> list;
    private String type="普通群";
    private int groupid;

    public String currentPosPh;   //当前职位的拥有者账号   当前职位没有人的话 这个ph=""
    private String postype;       //任命  主席/XX部长/干事
    private String activityType;

    public Timer appointTimer = new Timer();

    public AppointPeopleAdapter(Context mContext, List<UserInGroupInfo> list, String type, int groupid,String currentPosPh,String postype){
        this.mContext=mContext;
        this.list=list;
        this.type=type;
        layoutInflater=LayoutInflater.from(mContext);
        this.groupid = groupid;
        this.currentPosPh = currentPosPh;
        this.postype = postype;
        if(postype.equals("主席")){
            activityType= "appoint_zhuxi";
        }else if(postype.contains("部长")){
            activityType= "appoint_buzhang";
        }else{
            activityType= "appoint_ganshi";
        }
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (type.equals("普通群")){
            return new OrGroupPeopleView(layoutInflater.inflate(R.layout.people_item,parent,false));
        }else if (type.equals("社团群")){
            return new UnionGroupView(layoutInflater.inflate(R.layout.item_union_group_list,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OrGroupPeopleView){
            AppointPeopleAdapter.OrGroupPeopleView orGroupPeopleView=(AppointPeopleAdapter.OrGroupPeopleView)holder;
            orGroupPeopleView.peopleName.setText(list.get(position).getGroupnickname());
            orGroupPeopleView.auth.setText(list.get(position).getAuth());

            String userph = list.get(position).getPh();
            String icpath = MyFileTools.getUserIconPath(userph);
            if(icpath==null||icpath.equals("")){
                StaticAllList.groupUserIconLastRequestTime.put(groupid+userph+"",System.currentTimeMillis());
                SendToServer.getUserIcOfGroupUsers(groupid,userph,activityType);
            }

            MyViewTools.setCircleImage(orGroupPeopleView.peopleImage,icpath);


            if(StaticAllList.groupUserIconLastRequestTime.containsKey(groupid+userph+"")){
                long lasttime = StaticAllList.groupUserIconLastRequestTime.get(groupid+userph+"");
                long nowtime = System.currentTimeMillis();
                //上次请求在10分钟之前  那么重新请求
                if(!DateUtils.isTwoTimeDifferLessAppointTime(lasttime,nowtime,600)){
                    SendToServer.getUserIcOfGroupUsers(groupid,userph,activityType);
                }

            }else{
                StaticAllList.groupUserIconLastRequestTime.put(groupid+userph+"",System.currentTimeMillis());
                SendToServer.getUserIcOfGroupUsers(groupid,userph,activityType);
            }

            orGroupPeopleView.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String ph = list.get(position).getPh()+"";
                    if(!StaticAllList.usertemps.containsKey(ph)){
                        User user = new User();
                        user.setPhonenumber(ph);
                        for(UserInGroupInfo userInGroupInfo:StaticAllList.groupUsersInfo.get(groupid)){
                            if(ph.equals(userInGroupInfo.getPh())){
                                user.setIcon(userInGroupInfo.getIcon());
                                user.setNickname(userInGroupInfo.getGroupnickname());
                                break;
                            }
                        }
                        StaticAllListOperator.add2UserTemps(user);
                    }



                }
            });
//            AppointPeopleAdapter.UnionGroupView unionGroupView=(AppointPeopleAdapter.UnionGroupView)holder;
        }else if(holder instanceof UnionGroupView){       //社团群

            AppointPeopleAdapter.UnionGroupView orGroupPeopleView=(AppointPeopleAdapter.UnionGroupView)holder;
            orGroupPeopleView.peopleName.setText(list.get(position).getGroupnickname());
            orGroupPeopleView.peoplePart.setText(list.get(position).getCorppart());
            orGroupPeopleView.peoplePartPos.setText(list.get(position).getCorpos());
            String userph = list.get(position).getPh();
            String icpath = MyFileTools.getUserIconPath(userph);
            if(icpath==null||icpath.equals("")){
                StaticAllList.groupUserIconLastRequestTime.put(groupid+userph+"",System.currentTimeMillis());
                SendToServer.getUserIcOfGroupUsers(groupid,userph,activityType);
            }

            MyViewTools.setCircleImage(orGroupPeopleView.peopleImage,icpath);


            if(StaticAllList.groupUserIconLastRequestTime.containsKey(groupid+userph+"")){
                long lasttime = StaticAllList.groupUserIconLastRequestTime.get(groupid+userph+"");
                long nowtime = System.currentTimeMillis();
                //上次请求在10分钟之前  那么重新请求
                if(!DateUtils.isTwoTimeDifferLessAppointTime(lasttime,nowtime,600)){
                    SendToServer.getUserIcOfGroupUsers(groupid,userph,activityType);
                }

            }else{
                StaticAllList.groupUserIconLastRequestTime.put(groupid+userph+"",System.currentTimeMillis());
                SendToServer.getUserIcOfGroupUsers(groupid,userph,activityType);
            }

            orGroupPeopleView.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String ph = list.get(position).getPh()+"";
                    String name = list.get(position).getGroupnickname();
                    if(!StaticAllList.usertemps.containsKey(ph)){
                        User user = new User();
                        user.setPhonenumber(ph);
                        for(UserInGroupInfo userInGroupInfo:StaticAllList.groupUsersInfo.get(groupid)){
                            if(ph.equals(userInGroupInfo.getPh())){
                                user.setIcon(userInGroupInfo.getIcon());
                                user.setNickname(userInGroupInfo.getGroupnickname());
                                break;
                            }
                        }
                        StaticAllListOperator.add2UserTemps(user);
                    }

                    //点击选择任命对象
                    showExitGroupDialog(list.get(position),name);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class OrGroupPeopleView extends RecyclerView.ViewHolder{
        CircleImageView peopleImage;
        TextView peopleName;
        TextView auth;
        LinearLayout linearLayout;

        public OrGroupPeopleView(View itemView) {
            super(itemView);
            peopleImage=(CircleImageView)itemView.findViewById(R.id.people_image);
            peopleName=(TextView)itemView.findViewById(R.id.people_name);
            auth=(TextView)itemView.findViewById(R.id.group_userlist_auth_tv);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.in_group_people_lay);

        }
    }

    class UnionGroupView extends RecyclerView.ViewHolder{
        CircleImageView peopleImage;
        TextView peopleName;
        TextView peoplePart;
        TextView peoplePartPos;
        LinearLayout layout;

        public UnionGroupView(View itemView) {
            super(itemView);
            peopleImage=(CircleImageView)itemView.findViewById(R.id.union_people_image);
            peopleName=(TextView)itemView.findViewById(R.id.union_people_name);
            peoplePart=(TextView)itemView.findViewById(R.id.union_people_part);
            peoplePartPos=(TextView)itemView.findViewById(R.id.union_people_part_pos);
            layout=(LinearLayout)itemView.findViewById(R.id.union_people_list_layout);
        }
    }

    private void showExitGroupDialog(UserInGroupInfo userInGroupInfo,String name){
        String ph = userInGroupInfo.getPh();
        final Dialog bottomDialog = new Dialog(mContext, R.style.BottomDialog);
        View contentView = LayoutInflater.from((mContext)).inflate(R.layout.dialog_delete_alarm, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = mContext.getResources().getDisplayMetrics().widthPixels - DensityUtiltwo.dp2px(mContext, 90f);
        params.bottomMargin = DensityUtiltwo.dp2px(mContext, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        TextView yesText=contentView.findViewById(R.id.yes_delete);
        TextView infoText=contentView.findViewById(R.id.delete_alarm_info);

        String postypetemp = postype;
        if(postypetemp.contains("部")){
            postypetemp+="部长";
        }
        if(currentPosPh.equals(ph)){
            infoText.setText("该成员已经是"+postypetemp+"!");
        }else if(userInGroupInfo.getCorpos().equals(postype)){
            infoText.setText("该成员已经是"+postypetemp+"!");
        }else if(userInGroupInfo.getCorpos().equals("官方账号")){
            infoText.setText("社团组织官方账号不可更改哦!");
        }else if(currentPosPh.equals("")){
            infoText.setText("确定任命 "+name+" 为"+postypetemp+"?");
        }else{
            infoText.setText("确定任命 "+name+" 为"+postypetemp+"?\t原来的"+postypetemp+"会变为干事!");
        }

        yesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String oldph = "";
                if(currentPosPh.equals(ph)){
                    bottomDialog.dismiss();
                    return ;
                }else if(userInGroupInfo.getCorpos().equals(postype)){
                    bottomDialog.dismiss();
                    return;
                }
                else if(userInGroupInfo.getCorpos().equals("官方账号")){
                    bottomDialog.dismiss();
                    return;
                }else if(currentPosPh.equals("")){

                }else{
                    oldph = currentPosPh;
                }

                if(!NetworkUtils.isConnected(mContext)){

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                            bottomDialog.dismiss();
                        }
                    });
                    return;
                }


                MyDialog.showBottomLoadingDialog(mContext);
                CorpSendToServer.appointCorpPos(groupid,ph,postype,oldph);

                appointTimer = new Timer();
                appointTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                MyDialog.dismissBottomLoadingDialog();
                                Toast.makeText(mContext,"服务器繁忙,请稍后重试!",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                },4000);

                bottomDialog.dismiss();
            }
        });
        TextView noText=contentView.findViewById(R.id.no_delete);
        noText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });

    }


}
