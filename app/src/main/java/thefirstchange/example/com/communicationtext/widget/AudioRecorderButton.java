package thefirstchange.example.com.communicationtext.widget;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import thefirstchange.example.com.communicationtext.R;

public class AudioRecorderButton extends android.support.v7.widget.AppCompatButton implements AudioManager.AudioStateListener{


    //手指滑动 距离
    private static final int DISTANCE_Y_CANCEL=50;
    //状态
    private static final int STATE_NORMAL=1;
    private static final int STATE_RECORDING=2;
    private static final int STAT_WANT_TO_CANCEL=3;
    //当前状态
    private int mCurState=STATE_NORMAL;
    //开始录音
    private boolean isRecording=false;

    private DialogManager mDialogmanager;
    private AudioManager mAudioManager;

    private float mTime;
    //是否触发onlongclick
    private boolean mReady;

    public AudioRecorderButton(Context context){
        this(context,null);
    }

    public AudioRecorderButton(final Context context, AttributeSet attrs){
        super(context,attrs);
        mDialogmanager=new DialogManager(getContext());
        //判断是否可读

        String dir= Environment.getExternalStorageDirectory()+"/recorder_audios";

        mAudioManager=new AudioManager(dir);
        mAudioManager.setOnAudioStateListener(this);
        //长按按钮 准备录音 包括start
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                    mReady=true;
                    mAudioManager.prepareAudio();

                return false;
            }
        });


    }



    /**
     * 录音完成回调
     */
    public interface AudioFinishRecorderListener{
        //时长和文件
        void onFinish(float seconds, String filePath);
    }

    private AudioFinishRecorderListener mListener;

    public void setAudioFinishRecorderListener(AudioFinishRecorderListener listener){
        mListener=listener;
    }

    //获取音量大小的Runnable
    private Runnable mGetVioceLevelRunnable=new Runnable() {
        @Override
        public void run() {
            while (isRecording){
                try {
                    Thread.sleep(100);
                    mTime+=0.1;
                    mHandler.sendEmptyMessage(MSG_VOICE_CHANGED);

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    };

    private static final int MSG_AUDIO_PREPARED=0x110;
    private static final int MSG_VOICE_CHANGED=0x111;
    private static  final int MSG_DIALOG_DIMISS = 0x112;

    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case MSG_AUDIO_PREPARED :
                    mDialogmanager.showRecordingDialog();
                    isRecording=true;
                    new Thread(mGetVioceLevelRunnable).start();
                    break;
                case MSG_VOICE_CHANGED:
                    mDialogmanager.updateVoiceLevel(mAudioManager.getVoiceLevel(7));
                    break;
                case MSG_DIALOG_DIMISS :
                    mDialogmanager.dimissDailog();
                    break;
            }
        }
    };

    public void wellPrepared(){
        mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
    }

    public boolean onTouchEvent(MotionEvent event){
        int action=event.getAction();
        int x=(int)event.getX();
        int y=(int)event.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:

                isRecording=true;
                changeState(STATE_RECORDING);
                break;
            case MotionEvent.ACTION_MOVE :
                if(isRecording){
                    //根据x，y的坐标判断是否想要取消
                    if (wantToCancel(x,y)){
                        changeState(STAT_WANT_TO_CANCEL);
                    }else {
                        changeState(STATE_RECORDING);
                    }
                }
                break;
            case MotionEvent.ACTION_UP :
                //如果longclick 没触发
                if(!mReady){
                    reset();
                    return super.onTouchEvent(event);
                }

                //触发了onlongclick 没准备好但已经prepared 已经start
                //所以消除文件

                if ((!isRecording||mTime<0.6f)){
                    mDialogmanager.tooShort();
                    mAudioManager.cancel();
                    mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS,1300);
                }else if(mCurState==STATE_RECORDING){
                    //正常录制结束
                    mDialogmanager.dimissDailog();
                    mAudioManager.release();
                    if (mListener!=null){
                        mListener.onFinish(mTime,mAudioManager.getCurrentFilePath());

                    }
                }else if(mCurState==STATE_RECORDING){
                    mDialogmanager.dimissDailog();
                }else if (mCurState==STAT_WANT_TO_CANCEL){
                    mDialogmanager.dimissDailog();
                    mAudioManager.cancel();
                }

                reset();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 回复状态 标志位
     */
    private void reset(){
        isRecording=false;
        mReady=false;
        changeState(STATE_NORMAL);
        mTime=0;
    }

    private boolean wantToCancel(int x,int y){
        //如果左右画出button
        if(x<0||x>getWidth()){
            return false;
        }

        //如果上下画出button 加上自定义的距离
        if (y<-DISTANCE_Y_CANCEL||y>getHeight()+DISTANCE_Y_CANCEL){
            return true;
        }

        return false;
    }

    //改变状态
    private void changeState(int state){
        if(mCurState!=state){
            mCurState=state;
            switch (state){
                case STATE_NORMAL:
                    //setBackgroundResource();
                    setText(R.string.str_recorder_normal);
                    break;
                case STATE_RECORDING :
                    setText(R.string.str_recorder_recording);
                    if (isRecording){
                        mDialogmanager.recording();
                    }
                    break;
                case STAT_WANT_TO_CANCEL :
                    setText(R.string.str_recorder_want_cancel);
                    mDialogmanager.wantToCancel();
                    break;
            }
        }
    }
}
