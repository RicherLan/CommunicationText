package thefirstchange.example.com.communicationtext.course.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.gson.ClassRoom;

public class ClassRoomAdapter extends RecyclerView.Adapter<ClassRoomAdapter.ViewHolder>{

    private Context mContext;
    Vector<ClassRoom> classRooms;


    public ClassRoomAdapter(Context context,Vector<ClassRoom> classRooms ){
        this.mContext=context;
        this.classRooms = classRooms;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.emptyclassroom_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final ViewHolder viewHolder=(ViewHolder)holder;
        ClassRoom classRoom = classRooms.get(position);
        viewHolder.classroom_name_tv.setText(classRoom.getRoomName());
        viewHolder.classroom_type_tv.setText(classRoom.getRoomType());
        viewHolder.classroom_seatnum_tv.setText("座位数:"+classRoom.getSeatNum());


    }

    @Override
    public int getItemCount() {
        if(classRooms==null){
            return 0;
        }
        return classRooms.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView classroom_name_tv;
        TextView classroom_type_tv;
        TextView classroom_seatnum_tv;


        public ViewHolder(View itemView) {
            super(itemView);
            classroom_name_tv=(TextView) itemView.findViewById(R.id.classroom_name_tv);
            classroom_type_tv=(TextView) itemView.findViewById(R.id.classroom_type_tv);
            classroom_seatnum_tv=(TextView) itemView.findViewById(R.id.classroom_seatnum_tv);

        }
    }





}
