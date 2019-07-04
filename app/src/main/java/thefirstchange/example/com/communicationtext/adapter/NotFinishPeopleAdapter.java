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

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.ConnectOtherActivity;
import thefirstchange.example.com.communicationtext.gson.CorpUserNotLoadCourse;

public class NotFinishPeopleAdapter extends RecyclerView.Adapter<NotFinishPeopleAdapter.PeopleView>{
    private Context mContext;
    List<CorpUserNotLoadCourse> list;


   public NotFinishPeopleAdapter(Context context,List<CorpUserNotLoadCourse> list){
       this.mContext=context;
       this.list=list;
   }


    @NonNull
    @Override
    public PeopleView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PeopleView(LayoutInflater.from(mContext).inflate(R.layout.item_not_finish_people,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleView holder, int position) {

        CorpUserNotLoadCourse corpUserNotLoadCourse = list.get(position);
        final String ph = corpUserNotLoadCourse.getPh();
        final String groupname = corpUserNotLoadCourse.getGroupnickname();
       holder.nameText.setText(groupname);
       holder.partText.setText(corpUserNotLoadCourse.getPart());
       holder.posText.setText(corpUserNotLoadCourse.getPos());

       holder.linearLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               Intent intent=new Intent(mContext, ConnectOtherActivity.class);
               intent.putExtra("senderId",ph);
               intent.putExtra("receiver_nickname",groupname);
               mContext.startActivity(intent);
           }
       });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PeopleView extends RecyclerView.ViewHolder{
       TextView nameText;
       TextView partText;
       TextView posText;
       LinearLayout linearLayout;

        public PeopleView(View itemView) {
            super(itemView);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.not_people_linear);
            nameText=(TextView)itemView.findViewById(R.id.not_people_name);
            partText=(TextView)itemView.findViewById(R.id.not_people_part);
            posText=(TextView)itemView.findViewById(R.id.not_people_pos);
        }
    }
}
