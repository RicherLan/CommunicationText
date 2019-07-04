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
import thefirstchange.example.com.communicationtext.course.object.ListviewZhoushu;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.CourseAndScore;


public class ZhoushuAdapter extends RecyclerView.Adapter<ZhoushuAdapter.MyHolder> {

    private Context mcontext;
    private int resourceid;
    List<ListviewZhoushu> zhoushus;

    private View nowView=null;
    private int nowpos= CourseAndScore.nowzhoushu-1;

    public ZhoushuAdapter(Context context, List<ListviewZhoushu> listviewZhoushus){
        this.zhoushus = listviewZhoushus;
        mcontext = context;
    }

    //重新加载
    public void refresh(){
        if(nowpos!=CourseAndScore.nowzhoushu-1){
            nowpos = CourseAndScore.nowzhoushu-1;
            nowView.setBackgroundColor(mcontext.getResources().getColor(R.color.FloralWhite));
            nowView=null;
        }

    }

    @NonNull
    @Override
    public ZhoushuAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zhoushu_item,parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ZhoushuAdapter.MyHolder holder, final int position) {
        ListviewZhoushu listviewZhoushu = zhoushus.get(position);
        holder.zhoushu.setText(listviewZhoushu.getZhoushu());
        if(listviewZhoushu.getBenzhou()){
            holder.benzhou.setText("本周");
            holder.linearLayout.setBackgroundColor(mcontext.getResources().getColor(R.color.LightSkyBlue));
        }

        if(mlistener!=null){
            holder.linearLayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(position==nowpos){
                        return;
                    }
                    if(nowpos==CourseAndScore.nowzhoushu-1){
                        v.setBackgroundColor(mcontext.getResources().getColor(R.color.DarkSeaGreen1));
                        nowView=v;
                        nowpos=position;
                    }else{
                        nowView.setBackgroundColor(mcontext.getResources().getColor(R.color.FloralWhite));
                        if(position+1!=CourseAndScore.nowzhoushu){
                            v.setBackgroundColor(mcontext.getResources().getColor(R.color.DarkSeaGreen1));
                        }
                        nowView=v;
                        nowpos=position;
                    }

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
        TextView benzhou;
        public LinearLayout linearLayout;
        public MyHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.zhoushu_item_layout);
            zhoushu = (TextView)itemView.findViewById(R.id.zhoushu_item_zhou);
            benzhou = (TextView)itemView.findViewById(R.id.zhoushu_item_benzhou);
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
