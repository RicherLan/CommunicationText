package thefirstchange.example.com.communicationtext.corpration.appointPos.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.corpration.appointPos.AppointBuZhangActivity;

/*
    任命部长时首先选择部门    这个adapter用来展示所有的部门
 */
public class AppointBuZhangSelectPartAdapter extends RecyclerView.Adapter<AppointBuZhangSelectPartAdapter.ViewHolder>{
    private Context mContext;
    private String[] partName;
    private int groupid;

    public AppointBuZhangSelectPartAdapter(Context context,int groupid, String[] partName){
        this.mContext=context;
        this.groupid = groupid;
        this.partName = partName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_alter_group_part,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ViewHolder viewHolder=(ViewHolder)holder;
        String partname = partName[position];
        viewHolder.name.setText(partname);
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AppointBuZhangActivity.class);
                intent.putExtra("groupid",groupid);
                intent.putExtra("part",partname);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        if(partName==null){
            return 0;
        }
        return partName.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.input_part_name);
        }
    }
}
