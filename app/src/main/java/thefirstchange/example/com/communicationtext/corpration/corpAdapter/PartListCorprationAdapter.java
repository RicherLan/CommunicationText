package thefirstchange.example.com.communicationtext.corpration.corpAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import thefirstchange.example.com.communicationtext.R;

public class PartListCorprationAdapter extends RecyclerView.Adapter<PartListCorprationAdapter.ViewHolder> {

    private Context mContext;
    private String[] partName;

    public PartListCorprationAdapter(Context context, String[] partName){
        this.mContext=context;
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
        viewHolder.name.setText(partName[position]);
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String name = partName[position];

            }
        });

    }

    @Override
    public int getItemCount() {
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
