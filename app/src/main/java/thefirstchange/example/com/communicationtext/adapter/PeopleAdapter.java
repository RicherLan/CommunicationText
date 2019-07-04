package thefirstchange.example.com.communicationtext.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.PersonalHomePageActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.User;
import thefirstchange.example.com.communicationtext.gson.UserInGroupInfo;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.StaticAllListOperator;
import thefirstchange.example.com.communicationtext.util.DateUtils;
import thefirstchange.example.com.communicationtext.util.MyFileTools;
import thefirstchange.example.com.communicationtext.util.MyViewTools;

public class PeopleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    //0加载普通群列表布局
    private int choositem=0;
    private LayoutInflater layoutInflater;
    private List<UserInGroupInfo> list;
    private String type="普通群";
    private int groupid;

    public PeopleAdapter(Context mContext, List<UserInGroupInfo> list,String type,int groupid){
        this.mContext=mContext;
        this.list=list;
        this.type=type;
        layoutInflater=LayoutInflater.from(mContext);
        this.groupid = groupid;
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
            PeopleAdapter.OrGroupPeopleView orGroupPeopleView=(PeopleAdapter.OrGroupPeopleView)holder;
            orGroupPeopleView.peopleName.setText(list.get(position).getGroupnickname());
            orGroupPeopleView.auth.setText(list.get(position).getAuth());


            String userph = list.get(position).getPh();
            String icpath = MyFileTools.getUserIconPath(userph);
            if(icpath==null||icpath.equals("")){
                StaticAllList.groupUserIconLastRequestTime.put(groupid+userph+"",System.currentTimeMillis());
                SendToServer.getUserIcOfGroupUsers(groupid,userph,"getGroupAllURs");
            }

            MyViewTools.setCircleImage(orGroupPeopleView.peopleImage,icpath);


            if(StaticAllList.groupUserIconLastRequestTime.containsKey(groupid+userph+"")){
                long lasttime = StaticAllList.groupUserIconLastRequestTime.get(groupid+userph+"");
                long nowtime = System.currentTimeMillis();
                //上次请求在10分钟之前  那么重新请求
                if(!DateUtils.isTwoTimeDifferLessAppointTime(lasttime,nowtime,600)){
                    SendToServer.getUserIcOfGroupUsers(groupid,userph,"getGroupAllURs");
                }

            }else{
                StaticAllList.groupUserIconLastRequestTime.put(groupid+userph+"",System.currentTimeMillis());
                SendToServer.getUserIcOfGroupUsers(groupid,userph,"getGroupAllURs");
            }


            orGroupPeopleView.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent=new Intent(mContext, ConnectOtherActivity.class);
//                    intent.putExtra("senderId",list.get(position).getPh()+"");
//                    intent.putExtra("receiver_nickname",list.get(position).getGroupnickname());
//                //    intent.putExtra("receiver_nickname",list.get(position).get);
//                    mContext.startActivity(intent);

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


                    Intent intent = new Intent(mContext, PersonalHomePageActivity.class);
                    intent.putExtra("from","otherpeople");
                    intent.putExtra("id",list.get(position).getPh()+"");
                    mContext.startActivity(intent);

                }
            });
//            AppointPeopleAdapter.UnionGroupView unionGroupView=(AppointPeopleAdapter.UnionGroupView)holder;
        }else if(holder instanceof UnionGroupView){       //社团群

            PeopleAdapter.UnionGroupView orGroupPeopleView=(PeopleAdapter.UnionGroupView)holder;
            orGroupPeopleView.peopleName.setText(list.get(position).getGroupnickname());
            orGroupPeopleView.peoplePart.setText(list.get(position).getCorppart());
            orGroupPeopleView.peoplePartPos.setText(list.get(position).getCorpos());

            String userph = list.get(position).getPh();
            String icpath = MyFileTools.getUserIconPath(userph);
            if(icpath==null||icpath.equals("")){
                StaticAllList.groupUserIconLastRequestTime.put(groupid+userph+"",System.currentTimeMillis());
                SendToServer.getUserIcOfGroupUsers(groupid,userph,"getGroupAllURs");
            }

            MyViewTools.setCircleImage(orGroupPeopleView.peopleImage,icpath);


            if(StaticAllList.groupUserIconLastRequestTime.containsKey(groupid+userph+"")){
                long lasttime = StaticAllList.groupUserIconLastRequestTime.get(groupid+userph+"");
                long nowtime = System.currentTimeMillis();
                //上次请求在10分钟之前  那么重新请求
                if(!DateUtils.isTwoTimeDifferLessAppointTime(lasttime,nowtime,600)){
                    SendToServer.getUserIcOfGroupUsers(groupid,userph,"getGroupAllURs");
                }

            }else{
                StaticAllList.groupUserIconLastRequestTime.put(groupid+userph+"",System.currentTimeMillis());
                SendToServer.getUserIcOfGroupUsers(groupid,userph,"getGroupAllURs");
            }

            orGroupPeopleView.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    Intent intent=new Intent(mContext, ConnectOtherActivity.class);
//                    intent.putExtra("senderId",list.get(position).getPh()+"");
//                    intent.putExtra("receiver_nickname",list.get(position).getGroupnickname());
//                    //    intent.putExtra("receiver_nickname",list.get(position).get);
//                    mContext.startActivity(intent);

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

                    Intent intent = new Intent(mContext, PersonalHomePageActivity.class);
                    intent.putExtra("from","otherpeople");
                    intent.putExtra("id",ph);
                    mContext.startActivity(intent);

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
}
