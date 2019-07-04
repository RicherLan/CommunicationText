package thefirstchange.example.com.communicationtext.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Inflater;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.util.DensityUtiltwo;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class AterGroupPartAdapter extends RecyclerView.Adapter<AterGroupPartAdapter.ViewHolder>{

    public Timer timer;

    private Context mContext;
    private Inflater inflater;
    String[] partName;
    int groupId;
    String partname;       //自己现在是在哪一个部室

    public AterGroupPartAdapter(Context context,int groupId, String[] partName,String partname){
        this.mContext=context;
        this.partName=partName;
        this.groupId=groupId;
        this.partname = partname;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_alter_group_part,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ViewHolder viewHolder=(ViewHolder)holder;
        viewHolder.editText.setText(partName[position]);
        viewHolder.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = partName[position];
                if(name.equals(partname)){

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "您已经是该部室的成员!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else if(partname.equals("官方账号")){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "官方账号不可更改!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(partname.equals("主席")){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "您是该组织的主席,不可更改!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    showChoosePartDialog(partName[position]);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        if(partName==null){
            return 0;
        }
        return partName.length;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView editText;

        public ViewHolder(View itemView) {
            super(itemView);
            editText=(TextView) itemView.findViewById(R.id.input_part_name);
        }
    }






    private void showChoosePartDialog(final String name){
        final Dialog bottomDialog = new Dialog(mContext, R.style.BottomDialog);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_delete_alarm, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = mContext.getResources().getDisplayMetrics().widthPixels - DensityUtiltwo.dp2px(mContext, 90f);
        params.bottomMargin = DensityUtiltwo.dp2px(mContext, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        TextView yesText=contentView.findViewById(R.id.yes_delete);
        TextView infoText=contentView.findViewById(R.id.delete_alarm_info);
        infoText.setText("确认修改为"+name);
        yesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                if(!NetworkUtils.isConnected(mContext)){

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                MyDialog.showBottomLoadingDialog(mContext);


                SendToServer.upGroupPart(groupId,name);


                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    MyDialog.dismissBottomLoadingDialog();
                                    Toast.makeText(mContext, "服务器繁忙,请稍后再试!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    },15000);

                bottomDialog.dismiss();
            }
        });
        TextView noText=contentView.findViewById(R.id.no_delete);
        noText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });

    }
}
