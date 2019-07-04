package thefirstchange.example.com.communicationtext.course.adapter;

import android.app.Dialog;
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

import thefirstchange.example.com.communicationtext.course.object.Course;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.CourseAndScore;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.CourseDetailActivity;


public class ChooseSameCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Integer> list;
    private LayoutInflater layoutInflater;
    private Dialog dialog;

    public ChooseSameCourseAdapter(Context mContext, List<Integer> list, Dialog dialog){
        this.mContext=mContext;
        this.list=list;
        layoutInflater=LayoutInflater.from(mContext);
        this.dialog = dialog;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChooseView(layoutInflater.inflate(R.layout.item_choose_sametime_course,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ChooseView chooseView=(ChooseView)holder;
        if(position==0){
            chooseView.toNext.setBackgroundColor(mContext.getResources().getColor(R.color.DeepSkyBlue1));
        }
        Course course = CourseAndScore.courses.get(list.get(position));
        chooseView.courseName.setText(course.getCN());//课程名
        chooseView.courseLocation.setText(course.getCP());//课程地点

        chooseView.toNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(position<0||position==list.size()){
                    return;
                }
                Intent intent = new Intent(mContext,CourseDetailActivity.class);
                intent.putExtra("index",list.get(position));   //传课程在课程集合中的下标
                mContext.startActivity(intent);
                dialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ChooseView extends RecyclerView.ViewHolder{
       public  LinearLayout toNext;
        TextView courseName;
        TextView courseLocation;

        public ChooseView(View itemView) {
            super(itemView);
            toNext=(LinearLayout)itemView.findViewById(R.id.to_next);
            courseName=(TextView)itemView.findViewById(R.id.item_choose_course_course_name);
            courseLocation=(TextView)itemView.findViewById(R.id.item_choose_course_location);
        }
    }
}
