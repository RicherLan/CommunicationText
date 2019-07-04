package thefirstchange.example.com.communicationtext.usersignin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.List;

import thefirstchange.example.com.communicationtext.R;


public class SelectRuxueYearAdapter extends RecyclerView.Adapter<SelectRuxueYearAdapter.MyHolder> {

    private Context mcontext;
    private int resourceid;
    List<ListviewYear> zhoushus;


    public SelectRuxueYearAdapter(Context context, List<ListviewYear> listviewZhoushus){
        this.zhoushus = listviewZhoushus;
       mcontext = context;
    }


    @NonNull
    @Override
    public SelectRuxueYearAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ruxue_year_item,parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectRuxueYearAdapter.MyHolder holder, final int position) {
        ListviewYear listviewZhoushu = zhoushus.get(position);
        holder.year.setText(listviewZhoushu.getYear()+"年");

        if(mlistener!=null){
            holder.year.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    mlistener.onItemClick(position);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return zhoushus.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class MyHolder extends RecyclerView.ViewHolder{
        TextView year;

        public MyHolder(View itemView) {
            super(itemView);
            year = (TextView)itemView.findViewById(R.id.select_ruxue_year_item_tv);
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
