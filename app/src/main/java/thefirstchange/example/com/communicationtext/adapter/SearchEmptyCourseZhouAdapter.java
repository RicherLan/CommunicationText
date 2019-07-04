package thefirstchange.example.com.communicationtext.adapter;

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

public class SearchEmptyCourseZhouAdapter extends RecyclerView.Adapter<SearchEmptyCourseZhouAdapter.MyHolder> {

    private Context mcontext;
    private int resourceid;
    List<Integer> zhoushus;

    public SearchEmptyCourseZhouAdapter(Context context, List<Integer> listviewZhoushus){
        this.zhoushus = listviewZhoushus;
        mcontext = context;
    }

    @NonNull
    @Override
    public SearchEmptyCourseZhouAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_empty_course_zhou_item,parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchEmptyCourseZhouAdapter.MyHolder holder, final int position) {
      int zhou = zhoushus.get(position);
        holder.zhoushu.setText("第"+zhou+"周");

        if(mlistener!=null){
            holder.linearLayout.setOnClickListener(new View.OnClickListener(){
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
        TextView zhoushu;
        public LinearLayout linearLayout;
        public MyHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.search_empty_course_zhoushu_item_layout);
            zhoushu = (TextView)itemView.findViewById(R.id.search_empty_course_zhoushu_item_zhou);
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
