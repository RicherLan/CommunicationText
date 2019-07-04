package thefirstchange.example.com.communicationtext.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thefirstchange.example.com.communicationtext.R;

public class DialogManager {
    private Dialog mDialog;

    private ImageView mIcon;
    private ImageView mVoice;

    private TextView mLable;

    private Context mContext;

    public DialogManager(Context context){
        mContext=context;
    }

    public void showRecordingDialog(){
        mDialog=new Dialog(mContext, R.style.Theme_AudioDialog);
        LayoutInflater inflater= LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.dialog_recorder,null);
        mDialog.setContentView(view);
        mIcon=view.findViewById(R.id.id_recorder_dialog_icon);
        //mVoice=view.findViewById(R.id.id_recorder_dialog_voice);
        mLable=view.findViewById(R.id.id_recorder_dialog_label);

        mDialog.show();
    }

    //正在播放时的状态

    public void recording(){
        if(mDialog!=null&&mDialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
          //  mVoice.setVisibility(View.VISIBLE);
            mLable.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.chat_icon_voice1);
            mLable.setText("手指上划，取消发送");
        }
    }

    //想要取消

    public void wantToCancel(){
        if(mDialog!=null&&mDialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
          //  mVoice.setVisibility(View.GONE);
            mLable.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.chat_icon_voice1);
            mLable.setText("松开手指，取消发送");
        }
    }

    public void tooShort(){
        if(mDialog!=null&&mDialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);

            if(mVoice!=null){
                mVoice.setVisibility(View.GONE);
            }

            mLable.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.drawable.chat_icon_voice_short);
            mLable.setText("录音时间太短");
        }
    }

    //关闭Dialog
    public void dimissDailog(){
        if(mDialog!=null&&mDialog.isShowing()){
            mDialog.dismiss();
            mDialog=null;
        }
    }

    public void updateVoiceLevel(int level){
        if (mDialog!=null&&mDialog.isShowing()){
            int resId=mContext.getResources().getIdentifier("chat_icon_voice"+level,"drawable",mContext.getPackageName());
            mIcon.setImageResource(resId);
        }
    }
}
