package thefirstchange.example.com.communicationtext.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.dongtai.Dongtai;

public class MoreCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Dongtai> list;
    private Context context;
    private LayoutInflater inflater;


    public MoreCommentAdapter(Context context, List<Dongtai> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentItemView(inflater.inflate(R.layout.item_more_reply,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CommentItemView){

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class CommentItemView extends RecyclerView.ViewHolder{

        CircleImageView circleImageView;
        TextView nickName;
        TextView content;
        TextView time;
        TextView reply;

        public CommentItemView(View view1) {
            super(view1);
         circleImageView=(CircleImageView)view1.findViewById(R.id.item_more_comment_iv);
             nickName=(TextView)view1.findViewById(R.id.item_more_comment_nickname);
            content=(TextView)view1.findViewById(R.id.item_more_comment_content);
            time=(TextView)view1.findViewById(R.id.item_more_comment_time);
             reply=(TextView)view1.findViewById(R.id.item_more_comment_reply);
        }
    }
}
