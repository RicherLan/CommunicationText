package thefirstchange.example.com.communicationtext.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.ImageBrowserActivity;
import thefirstchange.example.com.communicationtext.activity.LocationActivity;
import thefirstchange.example.com.communicationtext.adapter.base.ViewHolder;
import thefirstchange.example.com.communicationtext.config.NotificationMsgConfig;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.FaceTextUtils;
import thefirstchange.example.com.communicationtext.util.MyFileTools;
import thefirstchange.example.com.communicationtext.util.MyViewTools;
import thefirstchange.example.com.communicationtext.util.TimeUtil;
import thefirstchange.example.com.communicationtext.widget.MediaManager;

public class NotificationMsgsAdapter extends BaseListAdapter<ChatMsg>{

    private final int TYPE_RECEIVER_TXT = 0;
    private final int TYPE_SEND_TXT = 1;

    private final int TYPE_SEND_IMAGE = 2;
    private final int TYPE_RECEIVER_IMAGE = 3;

    private final int TYPE_SEND_LOCATION = 4;
    private final int TYPE_RECEIVER_LOCATION = 5;

    private final int TYPE_SEND_VOICE =6;
    private final int TYPE_RECEIVER_VOICE = 7;

    DisplayImageOptions options;
    String currentId="";
    
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public NotificationMsgsAdapter(Context context, List<ChatMsg> notificationsList){
        super(context, notificationsList);

        currentId= ObjectService.personalInfo.getPhonenumber();
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .resetViewBeforeLoading(true)
                .cacheOnDisc(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }

    public int getItemViewType(int position) {
        ChatMsg notifications = list.get(position);


        if(notifications.getMsgtype().equals(NotificationMsgConfig.TYPE_IMAGE) ){
            return notifications.getSenderid().equals(currentId) ? TYPE_SEND_IMAGE: TYPE_RECEIVER_IMAGE;
        }else if(notifications.getMsgtype().equals(NotificationMsgConfig.TYPE_LOCATION)){
            return notifications.getSenderid().equals(currentId) ? TYPE_SEND_LOCATION: TYPE_RECEIVER_LOCATION;
        }else if(notifications.getMsgtype().equals(NotificationMsgConfig.TYPE_VOICE)){
            return notifications.getSenderid().equals(currentId) ? TYPE_SEND_VOICE: TYPE_RECEIVER_VOICE;
        }else if(notifications.getMsgtype().equals(NotificationMsgConfig.TYPE_TEXT)){
            return notifications.getSenderid().equals(currentId) ? TYPE_SEND_TXT: TYPE_RECEIVER_TXT;
        }else {
            return -1;
        }
    }
    public int getViewTypeCount() {
        return 8;
    }

    private View createViewByType(ChatMsg notifications, int position) {
        String type = notifications.getMsgtype();
        if(type.equals(NotificationMsgConfig.TYPE_IMAGE)){
            return getItemViewType(position) == TYPE_RECEIVER_IMAGE ?
                    mInflater.inflate(R.layout.item_chat_receive_image, null)
                    :
                    mInflater.inflate(R.layout.item_chat_send_image, null);
        }else if(type.equals(NotificationMsgConfig.TYPE_LOCATION)){
            return getItemViewType(position) == TYPE_RECEIVER_LOCATION ?
                    mInflater.inflate(R.layout.item_chat_receive_location, null)
                    :
                    mInflater.inflate(R.layout.item_chat_send_location, null);
        }else if(type.equals(NotificationMsgConfig.TYPE_VOICE)){
            return getItemViewType(position) == TYPE_RECEIVER_VOICE ?
                    mInflater.inflate(R.layout.item_chat_receive_voice, null)
                    :
                    mInflater.inflate(R.layout.item_chat_send_voice, null);
        }else if(type.equals(NotificationMsgConfig.TYPE_TEXT)){
            return getItemViewType(position) == TYPE_RECEIVER_TXT ?
                    mInflater.inflate(R.layout.item_chat_receive_msg, null)
                    :
                    mInflater.inflate(R.layout.item_chat_send_msg,  null);
        }
        else {
            return null;
        }
    }

    public View bindView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ChatMsg item = list.get(position);
        if (convertView == null) {
            convertView = createViewByType(item, position);
        }

        CircleImageView iv_avatar = ViewHolder.get(convertView, R.id.iv_avatar);
        final ImageView iv_fail_resend = ViewHolder.get(convertView, R.id.iv_fail_resend);
        final TextView tv_send_status = ViewHolder.get(convertView, R.id.tv_send_status);
        TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
        TextView tv_message = ViewHolder.get(convertView, R.id.tv_message);

        ImageView iv_picture = ViewHolder.get(convertView, R.id. iv_picture);
        final ProgressBar progress_load  = ViewHolder.get(convertView, R.id.progress_load);

        TextView tv_location = ViewHolder.get(convertView, R.id.tv_location);

        final ImageView iv_voice = ViewHolder.get(convertView, R.id.iv_voice);

        final TextView tv_voice_length = ViewHolder.get(convertView, R.id.tv_voice_length);



//        String avatar = item.getBelongAvatar();
//        if(avatar!=null && !avatar.equals("")){
//            ImageLoader.getInstance().displayImage(avatar, iv_avatar, ImageLoadOptions.getOptions(),animateFirstListener);
//        }else{
//            iv_avatar.setImageResource(R.drawable.user_image1);
//        }
//
//        iv_avatar.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                Intent intent =new Intent(mContext,PersonalHomePageActivity.class);
//                if(getItemViewType(position) == TYPE_RECEIVER_TXT
//                        ||getItemViewType(position) == TYPE_RECEIVER_IMAGE
//                        ||getItemViewType(position)==TYPE_RECEIVER_LOCATION
//                        ||getItemViewType(position)==TYPE_RECEIVER_VOICE){
//                    intent.putExtra("from", "other");
//                    intent.putExtra("username", item.getBelongUserName());
//                }else{
//                    intent.putExtra("from", "me");
//                }
//                mContext.startActivity(intent);
//            }
//        });

        //自己发的消息   头像设为自己的
        if(item.getSenderid().equals(currentId)){
            if(ObjectService.personalIcon!=null){
                iv_avatar.setImageBitmap(ObjectService.personalIcon);
            }

        }else{
            String icpath = MyFileTools.getUserIconPath(item.getSenderid());
            if(icpath==null||icpath.equals("")){

                        SendToServer.getUserIcByPh(item.getSenderid());

            }else{
                MyViewTools.setCircleImage(iv_avatar,icpath);
            }

        }
        tv_time.setText(TimeUtil.getChatTime(item.getMsgtime()));
       // TimeUtil.getChatTime(Long.parseLong(item.getMsgTime()))

        if(getItemViewType(position)==TYPE_SEND_TXT
//				||getItemViewType(position)==TYPE_SEND_IMAGE//
                ||getItemViewType(position)==TYPE_SEND_LOCATION
                ||getItemViewType(position)==TYPE_SEND_VOICE){//ֻ

//            if(item.getStatus()==NotificationMsgConfig.STATUS_SEND_SUCCESS){//
                progress_load.setVisibility(View.INVISIBLE);
                iv_fail_resend.setVisibility(View.INVISIBLE);
                if(item.getMsgtype().equals(NotificationMsgConfig.TYPE_VOICE)){
                    tv_send_status.setVisibility(View.GONE);
                    tv_voice_length.setVisibility(View.VISIBLE);
                }else{
                    tv_send_status.setVisibility(View.VISIBLE);
                    tv_send_status.setText("已发送");
                }
//            }
// else if(item.getStatus()==NotificationMsgConfig.STATUS_SEND_FAIL){
//                progress_load.setVisibility(View.INVISIBLE);
//                iv_fail_resend.setVisibility(View.VISIBLE);
//                tv_send_status.setVisibility(View.INVISIBLE);
//                if(item.getType()==NotificationMsgConfig.TYPE_VOICE){
//                    tv_voice_length.setVisibility(View.GONE);
//                }
//            }else if(item.getStatus()==NotificationMsgConfig.STATUS_SEND_RECEIVERED){
//                progress_load.setVisibility(View.INVISIBLE);
//                iv_fail_resend.setVisibility(View.INVISIBLE);
//                if(item.getType()==NotificationMsgConfig.TYPE_VOICE){
//                    tv_send_status.setVisibility(View.GONE);
//                    tv_voice_length.setVisibility(View.VISIBLE);
//                }else{
//                    tv_send_status.setVisibility(View.VISIBLE);
//                    tv_send_status.setText("已阅读");
//                }
//            }else if(item.getStatus()==NotificationMsgConfig.STATUS_SEND_START){
//                progress_load.setVisibility(View.VISIBLE);
//                iv_fail_resend.setVisibility(View.INVISIBLE);
//                tv_send_status.setVisibility(View.INVISIBLE);
//                if(item.getType()==NotificationMsgConfig.TYPE_VOICE){
//                    tv_voice_length.setVisibility(View.GONE);
//                }
//            }
        }

        final String text = item.getMsgbody();
        switch (item.getMsgtype()) {
            case NotificationMsgConfig.TYPE_TEXT:
                try {
                    SpannableString spannableString = FaceTextUtils
                            .toSpannableString(mContext, text);
                    tv_message.setText(spannableString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case NotificationMsgConfig.TYPE_IMAGE:
                try {
                    if (text != null && !text.equals("")) {
                        dealWithImage(position, progress_load, iv_fail_resend, tv_send_status, iv_picture, item);
                    }
                    iv_picture.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent =new Intent(mContext,ImageBrowserActivity.class);
                            ArrayList<String> photos = new ArrayList<String>();
                            photos.add(getImageUrl(item));
                            intent.putStringArrayListExtra("photos", photos);
                            intent.putExtra("position", 0);
                            mContext.startActivity(intent);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case NotificationMsgConfig.TYPE_LOCATION:
                try {
                    if (text != null && !text.equals("")) {
                        String address  = text.split("&")[0];
                        final String latitude = text.split("&")[1];
                        final String longtitude = text.split("&")[2];
                        tv_location.setText(address);
                        tv_location.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                // TODO Auto-generated method stub
                                Intent intent = new Intent(mContext, LocationActivity.class);
                                intent.putExtra("type", "scan");
                                intent.putExtra("latitude", Double.parseDouble(latitude));
                                intent.putExtra("longtitude", Double.parseDouble(longtitude));
                                mContext.startActivity(intent);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
                break;
            case NotificationMsgConfig.TYPE_VOICE:
                try {
                    double seconds=item.getVoicetime();
                    DecimalFormat decimalFormat=new DecimalFormat(".0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                    String length=decimalFormat.format(seconds);


                    if (length!=null&&!length.equals("")) {
                        tv_voice_length.setVisibility(View.VISIBLE);
                        String content = item.getMsgbody();
                        tv_voice_length.setVisibility(View.VISIBLE);
                        tv_voice_length.setText(length);


//                        if (item.getSenderid().equals(currentId)) {
////                            if(item.getStatus()==NotificationMsgConfig.STATUS_SEND_RECEIVERED
////                                    ||item.getStatus()==NotificationMsgConfig.STATUS_SEND_SUCCESS){
//                                tv_voice_length.setVisibility(View.VISIBLE);
//                                tv_voice_length.setText(length);
//
//                            //    tv_voice_length.setText(length+"\''");
//                            }else{
//                             //  tv_voice_length.setVisibility(View.INVISIBLE);
//                            }
                        } else {
//                            boolean isExists = BmobDownloadManager.checkTargetPathExist(currentId,item);
//                            if(!isExists){
//                                String netUrl = content.split("&")[0];
//                                final String length = content.split("&")[1];
//                                BmobDownloadManager downloadTask = new BmobDownloadManager(mContext,item,new DownloadListener() {
//
//                                    @Override
//                                    public void onStart() {
//                                        progress_load.setVisibility(View.VISIBLE);
//                                        tv_voice_length.setVisibility(View.GONE);
//                                        iv_voice.setVisibility(View.INVISIBLE);
//                                    }
//
//                                    @Override
//                                    public void onSuccess() {
//                                        // TODO Auto-generated method stub
//                                        progress_load.setVisibility(View.GONE);
//                                        tv_voice_length.setVisibility(View.VISIBLE);
//                                        tv_voice_length.setText(length+"\''");
//                                        iv_voice.setVisibility(View.VISIBLE);
//                                    }
//                                    @Override
//                                    public void onError(String error) {
//                                        // TODO Auto-generated method stub
//                                        progress_load.setVisibility(View.GONE);
//                                        tv_voice_length.setVisibility(View.GONE);
//                                        iv_voice.setVisibility(View.INVISIBLE);
//                                    }
//                                });
//                                downloadTask.execute(netUrl);
//                            }else{
//                                String length = content.split("&")[2];
//                                tv_voice_length.setText(length+"\''");
//                            }
                        }


                    iv_voice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startRecordAnimation(item,iv_voice);
                            String ge=item.getMsgbody();
                            MediaManager.playSound(item.getMsgbody(), new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                    //mAnimView.setBackgroundResource(R.drawable.voice_left3);
                                    stopRecordAnimation(item,iv_voice);
                                }
                            });

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
        return convertView;
    }



    private String getImageUrl(ChatMsg item){
        String showUrl = "";
        showUrl="file://"+item.getMsgbody();
//        String text = item.getInfo();
//        if(item.getBelongId().equals(currentId)){//
//            if(text.contains("&")){
//                showUrl = text.split("&")[0];
//            }else{
//                showUrl = text;
//            }
//        }else{
//            showUrl = text;
//        }
        return showUrl;
    }


    private void dealWithImage(int position,final ProgressBar progress_load,ImageView iv_fail_resend,TextView tv_send_status,ImageView iv_picture,ChatMsg item){
        String text = "file://"+item.getMsgbody();
        if(getItemViewType(position)==TYPE_SEND_IMAGE){
            //Log.i("smile", position+",״状态"+item.getStatus());
//            if(item.getStatus()==NotificationMsgConfig.STATUS_SEND_START){
//                progress_load.setVisibility(View.VISIBLE);
//                iv_fail_resend.setVisibility(View.INVISIBLE);
//                tv_send_status.setVisibility(View.INVISIBLE);
//            }else if(item.getStatus()==NotificationMsgConfig.STATUS_SEND_SUCCESS){
                progress_load.setVisibility(View.INVISIBLE);
                iv_fail_resend.setVisibility(View.INVISIBLE);
                tv_send_status.setVisibility(View.VISIBLE);
                tv_send_status.setText("已发送");
//            }else if(item.getStatus()==NotificationMsgConfig.STATUS_SEND_FAIL){
//                progress_load.setVisibility(View.INVISIBLE);
//                iv_fail_resend.setVisibility(View.VISIBLE);
//                tv_send_status.setVisibility(View.INVISIBLE);
//            }else if(item.getStatus()==NotificationMsgConfig.STATUS_SEND_RECEIVERED){
//                progress_load.setVisibility(View.INVISIBLE);
//                iv_fail_resend.setVisibility(View.INVISIBLE);
//                tv_send_status.setVisibility(View.VISIBLE);
//                tv_send_status.setText("已阅读");
//            }
//
            String showUrl = "";
            showUrl=item.getMsgbody();
            showUrl="file://"+showUrl;
//            if(text.contains("&")){
//                showUrl = text.split("&")[0];
//            }else{
//                showUrl = text;
//            }
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(mContext));
            ImageLoader.getInstance().displayImage(showUrl, iv_picture);

        }else{
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(mContext));
            ImageLoader.getInstance().displayImage(text, iv_picture,options,new ImageLoadingListener() {

                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    // TODO Auto-generated method stub
                    progress_load.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view,
                                            FailReason failReason) {
                    // TODO Auto-generated method stub
                    progress_load.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    // TODO Auto-generated method stub
                    progress_load.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    // TODO Auto-generated method stub
                    progress_load.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
    private AnimationDrawable anim = null;

    private void startRecordAnimation(ChatMsg notifications, ImageView iv_voice) {
        if (notifications.getSenderid().equals(currentId)) {
            iv_voice.setImageResource(R.drawable.anim_chat_voice_right);
        } else {
            iv_voice.setImageResource(R.drawable.anim_chat_voice_left);
        }
        anim = (AnimationDrawable) iv_voice.getDrawable();
        anim.start();
    }

    private void stopRecordAnimation(ChatMsg notifications, ImageView iv_voice) {
        if (notifications.getSenderid().equals(currentId)) {
            iv_voice.setImageResource(R.drawable.voice_left3);
        } else {
            iv_voice.setImageResource(R.drawable.voice_right3);
        }
        if (anim != null) {
            anim.stop();
        }
    }

}
