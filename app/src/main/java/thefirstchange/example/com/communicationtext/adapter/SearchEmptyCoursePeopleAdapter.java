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
import thefirstchange.example.com.communicationtext.gson.UserCorp;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.util.DateUtils;
import thefirstchange.example.com.communicationtext.util.MyFileTools;
import thefirstchange.example.com.communicationtext.util.MyViewTools;

public class SearchEmptyCoursePeopleAdapter extends RecyclerView.Adapter<SearchEmptyCoursePeopleAdapter.MyHolder> {

    private Context mcontext;
    List<UserCorp> userCorpList;
    int groupid;
    public SearchEmptyCoursePeopleAdapter(Context context, int groupid,List<UserCorp> listviewZhoushus){
        this.userCorpList = listviewZhoushus;
        mcontext = context;
        this.groupid = groupid;
    }

    @NonNull
    @Override
    public SearchEmptyCoursePeopleAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_ecp_list_item,parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchEmptyCoursePeopleAdapter.MyHolder holder, final int position) {
        UserCorp userCorp = userCorpList.get(position);

        final String ph = userCorp.getPh();
//        holder.ph.setText(ph);
        holder.name.setText(userCorp.getGroupname());
        holder.pos.setText(userCorp.getCorppos());
        holder.corppart.setText(userCorp.getPart());

        String icpath = MyFileTools.getUserIconPath(ph);
        if(icpath==null||icpath.equals("")){
            StaticAllList.groupUserIconLastRequestTime.put(groupid+ph+"",System.currentTimeMillis());
            SendToServer.getUserIcOfGroupUsers(groupid,ph,"searchEmptyCourseUICRs");
        }

        MyViewTools.setCircleImage(holder.head_iv,icpath);


        if(StaticAllList.groupUserIconLastRequestTime.containsKey(groupid+ph+"")){
            long lasttime = StaticAllList.groupUserIconLastRequestTime.get(groupid+ph+"");
            long nowtime = System.currentTimeMillis();
            //上次请求在10分钟之前  那么重新请求
            if(!DateUtils.isTwoTimeDifferLessAppointTime(lasttime,nowtime,600)){
                SendToServer.getUserIcOfGroupUsers(groupid,ph,"searchEmptyCourseUICRs");
            }

        }else{
            StaticAllList.groupUserIconLastRequestTime.put(groupid+ph+"",System.currentTimeMillis());
            SendToServer.getUserIcOfGroupUsers(groupid,ph,"searchEmptyCourseUICRs");
        }


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mcontext, PersonalHomePageActivity.class);
                intent.putExtra("from","otherpeople");
                intent.putExtra("id",ph);
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userCorpList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class MyHolder extends RecyclerView.ViewHolder{
        LinearLayout layout;
//        TextView ph;
        TextView name;
        TextView pos;
        TextView corppart;
        CircleImageView head_iv;

        public MyHolder(View itemView) {
            super(itemView);
            head_iv=(CircleImageView) itemView.findViewById(R.id.search_empty_course_headic_iv);
            layout = (LinearLayout)itemView.findViewById(R.id.search_ecp_list_layout);
//            ph = (TextView)itemView.findViewById(R.id.search_ecp_list_ph);
            name = (TextView)itemView.findViewById(R.id.search_ecp_list_groupname);
            pos = (TextView)itemView.findViewById(R.id.search_ecp_list_pos);
            corppart = (TextView)itemView.findViewById(R.id.search_ecp_list_part);
        }
    }


}
