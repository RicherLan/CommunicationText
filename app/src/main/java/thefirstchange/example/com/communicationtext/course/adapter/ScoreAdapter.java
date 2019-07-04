package thefirstchange.example.com.communicationtext.course.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.course.object.ListViewScore;


public class ScoreAdapter extends ArrayAdapter<ListViewScore> {

    private Context mcontext;
    private int resourceid;


    public ScoreAdapter(@NonNull Context context, int resourceid, List<ListViewScore> list) {
        super(context, resourceid, list);
        mcontext = context;
        this.resourceid = resourceid;

    }

    @Override
    public View getView(int position, View convertview, ViewGroup viewGroup){
        ListViewScore listViewScore = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertview==null){
            view = LayoutInflater.from(getContext()).inflate(this.resourceid,null);
            viewHolder = new ViewHolder();
            viewHolder.coursename = (TextView)view.findViewById(R.id.score_item_coursename);
            viewHolder.coursescore = (TextView)view.findViewById(R.id.score_item_coursescore);
            viewHolder.coursecredit = (TextView)view.findViewById(R.id.score_item_coursecredit);
            view.setTag(viewHolder);
        }else{
            view = convertview;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.coursename.setText(listViewScore.courseName);
        viewHolder.coursescore.setText(listViewScore.courseScore+"");
        viewHolder.coursecredit.setText(listViewScore.courseCredit+"");
        return view;
    }

    class ViewHolder{
        TextView coursename;
        TextView coursescore;
        TextView coursecredit;
    }


}
