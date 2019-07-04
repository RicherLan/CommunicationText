package thefirstchange.example.com.communicationtext.course.adapter;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.ConnectOtherActivity;
import thefirstchange.example.com.communicationtext.course.object.ListViewSchDutyUserInfo;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.DensityUtiltwo;


public class SchDutyUserInfoAdapter extends RecyclerView.Adapter<SchDutyUserInfoAdapter.MyHolder> {

    private Context mcontext;
    private int resourceid;
    List<ListViewSchDutyUserInfo> zhoushus;


    public SchDutyUserInfoAdapter(Context context, List<ListViewSchDutyUserInfo> listviewZhoushus){
        this.zhoushus = listviewZhoushus;
        mcontext = context;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schduty_userinfo_item,parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final ListViewSchDutyUserInfo listviewZhoushu = zhoushus.get(position);
        final String ph = listviewZhoushu.getPh();
        holder.account.setText(listviewZhoushu.getPh());
        holder.name.setText(listviewZhoushu.getName());
        holder.pos.setText(listviewZhoushu.getPos());
        holder.corppart.setText(listviewZhoushu.getCorppart());

        holder.schduty_userinfo_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ChooseOption(listviewZhoushu.getPh(),listviewZhoushu.getName());
//                Intent intent = new Intent(mcontext, PersonalHomePageActivity.class);
//                intent.putExtra("from","otherpeople");
//                intent.putExtra("id",ph);
//                mcontext.startActivity(intent);
            }
        });

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
        LinearLayout schduty_userinfo_layout;
        TextView account;
        TextView name;
        TextView pos;
        TextView corppart;

        public MyHolder(View itemView) {
            super(itemView);
            schduty_userinfo_layout = (LinearLayout)itemView.findViewById(R.id.schduty_userinfo_layout);
            account = (TextView)itemView.findViewById(R.id.schduty_user_account);
            name = (TextView)itemView.findViewById(R.id.schduty_userinfo_name);
            pos = (TextView)itemView.findViewById(R.id.schduty_userinfo_pos);
            corppart = (TextView)itemView.findViewById(R.id.schduty_userinfo_corppart);
        }
    }


//    public interface ItemClickListener{
//
//        void onItemClick(int position);
//    }
//    public ItemClickListener mlistener;
//    public void setOnItemClickListener(ItemClickListener listener){
//        this.mlistener = listener;
//    }

    private void ChooseOption(final String phoneNumber, final String nickName){
        final Dialog bottomDialog = new Dialog(mcontext, R.style.BottomDialog);
        View contentView = LayoutInflater.from(mcontext).inflate(R.layout.dialog_schuser_choice_options, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = mcontext.getResources().getDisplayMetrics().widthPixels - DensityUtiltwo.dp2px(mcontext, 180f);
        params.bottomMargin = DensityUtiltwo.dp2px(mcontext, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        ImageView chat=(ImageView) contentView.findViewById(R.id.schduty_send_chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneNumber.equals(ObjectService.personalInfo.getPhonenumber())){
                    return;
                }
                Intent intent=new Intent(mcontext, ConnectOtherActivity.class);
                intent.putExtra("senderId",phoneNumber);
                intent.putExtra("receiver_nickname",nickName);
                mcontext.startActivity(intent);
                bottomDialog.dismiss();

            }
        });
        ImageView call=(ImageView) contentView.findViewById(R.id.schduty_send_call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + phoneNumber);
                intent.setData(data);
                mcontext.startActivity(intent);

                bottomDialog.dismiss();
            }
        });
    }


}
