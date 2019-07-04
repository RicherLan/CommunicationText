package thefirstchange.example.com.communicationtext.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.gson.EmptyCoursePeople;

public class EmptyCoursePeopleAdapter extends ArrayAdapter<EmptyCoursePeople> {

    private Context mContext;
    private int resourceId;


    public EmptyCoursePeopleAdapter(Context mContext, int viewResourceId, List<EmptyCoursePeople> objects){
        super(mContext,viewResourceId,objects);
        this.mContext=mContext;
        this.resourceId=viewResourceId;


    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        EmptyCoursePeople emptyCoursePeople=getItem(position);
        View view= LayoutInflater.from(mContext).inflate(resourceId,parent,false);
        TextView emptyPeopleName=(TextView)view.findViewById(R.id.empty_course_people_name);
        TextView emptyPeoplePart=(TextView)view.findViewById(R.id.empty_course_people_part);
        TextView emptyPeoplePhone=(TextView)view.findViewById(R.id.empty_course_people_phone);
        LinearLayout emptyCoureLay=(LinearLayout)view.findViewById(R.id.empty_course_people_lay);
        return view;
    }
}
