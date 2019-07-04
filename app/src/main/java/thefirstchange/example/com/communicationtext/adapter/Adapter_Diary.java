package thefirstchange.example.com.communicationtext.adapter;

/**
 * Created by WZJSB-01 on 2017/12/5.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import thefirstchange.example.com.communicationtext.R;

class Adapter_Diary extends RecyclerView.Adapter<Adapter_Diary.ViewHolder>{
    private ArrayList<String> datas= null;
    Adapter_Diary(ArrayList<String> datas) {
        this.datas = datas;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(datas.get(position));
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public  ViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text);
        }
    }
}
