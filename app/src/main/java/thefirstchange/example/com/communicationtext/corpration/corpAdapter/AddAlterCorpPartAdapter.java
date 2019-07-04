package thefirstchange.example.com.communicationtext.corpration.corpAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Inflater;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CorpSendToServer;
import thefirstchange.example.com.communicationtext.util.DensityUtil;
import thefirstchange.example.com.communicationtext.util.DensityUtiltwo;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class AddAlterCorpPartAdapter extends RecyclerView.Adapter<AddAlterCorpPartAdapter.ViewHolder>{

    public Timer deletetimer;
    public Timer altertimer;

    private EditText alter_partname_dialog_et;

    private Context mContext;
    private Inflater inflater;
    String[] partName;
    int groupId;


    public AddAlterCorpPartAdapter(Context context, int groupId, String[] partName){
        this.mContext=context;
        this.partName=partName;
        this.groupId=groupId;

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
                ChooseOption(name);
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


    //type的类型    add  delete alter    name为社团的名称
    private void showChoosePartDialog(final String type , final String oldname,final String newname){
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
        if(type.equals("add")){
            infoText.setText("确定添加?");
        }else if(type.equals("delete")){
            infoText.setText("确定删除?");
        }else if(type.equals("alter")){
            infoText.setText("确定修改?");
        }

        yesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                if(!NetworkUtils.isConnected(mContext)){

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                            bottomDialog.dismiss();
                        }
                    });
                    return;
                }


                if(type.equals("delete")){

                    MyDialog.showBottomLoadingDialog(mContext);

                    CorpSendToServer.deleteCorpPart(groupId,oldname);



                    deletetimer = new Timer();
                    deletetimer.schedule(new TimerTask() {
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
                    },10000);

                    bottomDialog.dismiss();
                }else if(type.equals("alter")){

                    MyDialog.showBottomLoadingDialog(mContext);

                    CorpSendToServer.alterCorpPart(groupId,oldname,newname);


                    altertimer = new Timer();
                    altertimer.schedule(new TimerTask() {
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
                    },10000);

                    bottomDialog.dismiss();
                }


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


    private void ChooseOption(final String partname){
        final Dialog bottomDialog = new Dialog(mContext, R.style.BottomDialog);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_addalter_corppart_choice_options, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = mContext.getResources().getDisplayMetrics().widthPixels - DensityUtiltwo.dp2px(mContext, 180f);
        params.bottomMargin = DensityUtiltwo.dp2px(mContext, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        LinearLayout delete=(LinearLayout) contentView.findViewById(R.id.addalter_delete_corppart_layout);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoosePartDialog("delete",partname,"");
                bottomDialog.dismiss();
            }
        });
        ImageView deleteiv = (ImageView) contentView.findViewById(R.id.addalter_delete_corppart_iv);
        TextView deletetv = (TextView)contentView.findViewById(R.id.addalter_delete_corppart_tv);
        deleteiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoosePartDialog("delete",partname,"");
                bottomDialog.dismiss();
            }
        });
        deletetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoosePartDialog("delete",partname,"");
                bottomDialog.dismiss();
            }
        });



        LinearLayout alter=(LinearLayout) contentView.findViewById(R.id.addalter_alter_corppart_layout);
        alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterCorpPartNameDialog(partname);
                bottomDialog.dismiss();
            }
        });

        ImageView alteriv = (ImageView) contentView.findViewById(R.id.addalter_alter_corppart_iv);
        TextView altertv = (TextView)contentView.findViewById(R.id.addalter_alter_corppart_tv);
        alteriv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterCorpPartNameDialog(partname);
                bottomDialog.dismiss();
            }
        });
        altertv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterCorpPartNameDialog(partname);
                bottomDialog.dismiss();
            }
        });
    }

    private void alterCorpPartNameDialog(final String oldname){

        TextView title = new TextView(mContext);
        title.setText("更改部室名称");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
         title.setTextColor(mContext.getResources().getColor(R.color.black));
        title.setTextSize(21);
//        title.setTextColor(mContext.getResources().getColor(R.color.DeepSkyBlue1));

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.AlertDialog);
        builder.setCustomTitle(title);
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View v = LayoutInflater.from(mContext).inflate(R.layout.altercorppartname_dialog, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(v);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String newname = alter_partname_dialog_et.getText().toString().trim();
                if(newname.equals("")){
                    Toast.makeText(mContext,"部室名称不允许为空!",Toast.LENGTH_SHORT).show();
                }else{

                    for(String part:StaticAllList.groupList.get(groupId).getCorppart()){
                        if(newname.equals(part)){
                            Toast.makeText(mContext,"该部室名已经存在,修改失败!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    showChoosePartDialog("alter" ,  oldname, newname);
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_corner);
        //设置大小
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = DensityUtil.dip2px(mContext,300) ;
        layoutParams.height =  DensityUtil.dip2px(mContext,155);
        dialog.getWindow().setAttributes(layoutParams);

        alter_partname_dialog_et = (EditText) dialog.getWindow().findViewById(R.id.alter_partname_dialog_et);
        alter_partname_dialog_et.setText(oldname);

    }


}
