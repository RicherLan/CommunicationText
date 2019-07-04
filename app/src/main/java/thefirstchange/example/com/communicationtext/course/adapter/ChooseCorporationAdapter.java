package thefirstchange.example.com.communicationtext.course.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;


public class ChooseCorporationAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<Integer> list;
    private LayoutInflater layoutInflater;
    public Dialog dialog;

    public ChooseCorporationAdapter(Context mContext, List<Integer> list, Dialog dialog){
        this.mContext=mContext;
        this.list=list;
        layoutInflater=LayoutInflater.from(mContext);
        this.dialog = dialog;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChooseView(layoutInflater.inflate(R.layout.item_choose_corporation,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ChooseView chooseView=(ChooseView)holder;


        final int groupid = list.get(position);


        String name = "";
        if(StaticAllList.groupList.containsKey(groupid)){
            name = StaticAllList.groupList.get(groupid).getGroupname();
        }

        chooseView.corpName.setText(name);
        chooseView.groupid.setText(groupid+"");


        if(mlistener!=null){
            chooseView.toNext.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
//
                   mlistener.onItemClick(position);
//                    if(position<0||position==list.size()){
//                        return;
//                    }
//                    dialog.dismiss();
//                    CourseAndScore.duty_groupid = groupid;
//
//                    SharedPreferences mSharedPreferences = mContext.getSharedPreferences(Config.sharedPreferences_duty_group, Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = mSharedPreferences.edit();
//                    editor.putInt("duty_groupid", groupid);
//                    editor.commit();
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ChooseView extends RecyclerView.ViewHolder{
        public  LinearLayout toNext;
        TextView corpName;
        TextView groupid;

        public ChooseView(View itemView) {
            super(itemView);
            toNext=(LinearLayout)itemView.findViewById(R.id.item_choose_corporation_layout);
            corpName=(TextView)itemView.findViewById(R.id.item_choose_corp_name);
            groupid=(TextView)itemView.findViewById(R.id.item_choose_corp_groupid);
        }
    }

    public interface ItemClickListener{

        void onItemClick(int position);
    }

    public ItemClickListener mlistener;

    public void setOnItemClickListener(ItemClickListener listener){
        this.mlistener = listener;
    }


}
