package thefirstchange.example.com.communicationtext.contacts;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.ConnectOtherActivity;
import thefirstchange.example.com.communicationtext.activity.myselfactivity.AlterHisNameActivity;
import thefirstchange.example.com.communicationtext.gson.MyFriend;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.util.DateUtils;
import thefirstchange.example.com.communicationtext.util.DensityUtiltwo;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.MyFileTools;
import thefirstchange.example.com.communicationtext.util.MyViewTools;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;


public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<String> mContactList; // 联系人名称List（转换成拼音）
    private List<Contact> resultList; // 最终结果（包含分组的字母）
    private List<String> characterList; // 字母List
    private Activity activity;

    public Timer timer = new Timer();

    public enum ITEM_TYPE {
        ITEM_TYPE_CHARACTER,
        ITEM_TYPE_CONTACT
    }

    public ContactAdapter(Context context, Activity activity) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.activity=activity;
        for (String ph: StaticAllList.friendList.keySet()) {
            String s = ph;
            int a = 0;
        }
        handleContact();
    }

    private void handleContact() {
        mContactList = new ArrayList<>();
        Map<String, MyFriend> map = new HashMap<>();

        for (String ph: StaticAllList.friendList.keySet()) {
            String pinyin = Utils.getPingYin(StaticAllList.friendList.get(ph).getRemark())+ph;
            map.put(pinyin, StaticAllList.friendList.get(ph));
            mContactList.add(pinyin);
        }
        Collections.sort(mContactList, new ContactComparator());

        resultList = new ArrayList<>();
        characterList = new ArrayList<>();

        for (int i = 0; i < mContactList.size(); i++) {
            String name = mContactList.get(i);
            String character = (name.charAt(0) + "").toUpperCase(Locale.ENGLISH);
            if (!characterList.contains(character)) {
                if (character.hashCode() >= "A".hashCode() && character.hashCode() <= "Z".hashCode()) { // 是字母
                    characterList.add(character);
                    resultList.add(new Contact(character, ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                } else {
                    if (!characterList.contains("#")) {
                        characterList.add("#");
                        resultList.add(new Contact("#", ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                    }
                }
            }

            resultList.add(new Contact(map.get(name), ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()) {
            return new CharacterHolder(mLayoutInflater.inflate(R.layout.item_character, parent, false));
        } else {
            return new ContactHolder(mLayoutInflater.inflate(R.layout.item_contact, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        handleContact();
        if (holder instanceof CharacterHolder&&resultList!=null&&resultList.size()!=0) {
            ((CharacterHolder) holder).mTextView.setText(resultList.get(position).getmName());
        } else if (holder instanceof ContactHolder&&resultList!=null&&resultList.size()!=0) {

            final String friendph = resultList.get(position).getUser().getPhonenumber();

            String path = MyFileTools.getUserIconPath(friendph);

            if(path==null||path.trim().equals("")){
                //之前没有请求过该人的头像
                if(!StaticAllList.friendsIconHadRequest.containsKey(friendph)){
                    StaticAllList.friendsIconHadRequest.put(friendph,System.currentTimeMillis());
                    //请求头像
                    SendToServer.getFriendListUIcByPh(friendph);
                }else{
                    long lasttime = StaticAllList.friendsIconHadRequest.get(friendph);
                    long nowtime = System.currentTimeMillis();

                    if(!DateUtils.isTwoTimeDifferLessAppointTime(lasttime,nowtime,300)){
                        StaticAllList.friendsIconHadRequest.put(friendph,nowtime);
                        //请求头像
                        SendToServer.getFriendListUIcByPh(friendph);
                    }
                }

            }else{
                resultList.get(position).getUser().setIcon(path);
            }

            {
                MyViewTools.setCircleImage(((ContactHolder) holder).icon,path);
            }



            ((ContactHolder) holder).mTextView.setText(resultList.get(position).getmName());
            ((ContactHolder)holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, ConnectOtherActivity.class);
                    intent.putExtra("senderId",resultList.get(position).getUser().getPhonenumber());
                    intent.putExtra("receiver_nickname",resultList.get(position).getUser().getRemark());
                    mContext.startActivity(intent);
                    activity.finish();

                }
            });
            ((ContactHolder)holder).linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    String number=resultList.get(position).getUser().getPhonenumber();
                    String hisname=resultList.get(position).getUser().getRemark();
                    showCircleDialog(number,hisname);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return resultList.get(position).getmType();
    }

    @Override
    public int getItemCount() {
        return resultList == null ? 0 : resultList.size();
    }

    public class CharacterHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        CharacterHolder(View view) {
            super(view);

            mTextView = (TextView) view.findViewById(R.id.character);
        }
    }

    public class ContactHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        LinearLayout linearLayout;
        CircleImageView icon;

        ContactHolder(View view) {
            super(view);

            icon = (CircleImageView)view.findViewById(R.id.contact_list_icon);
            mTextView = (TextView) view.findViewById(R.id.contact_name);
            linearLayout=(LinearLayout)view.findViewById(R.id.item_contact_lay);
        }
    }

    public int getScrollPosition(String character) {
        if (characterList.contains(character)) {
            for (int i = 0; i < resultList.size(); i++) {
                if (resultList.get(i).getmName().equals(character)) {
                    return i;
                }
            }
        }

        return -1; // -1不会滑动
    }

    private void showCircleDialog(final String number, final String name) {
        final Dialog bottomDialog = new Dialog(mContext, R.style.BottomDialog);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_content_circle, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = mContext.getResources().getDisplayMetrics().widthPixels - DensityUtiltwo.dp2px(mContext, 16f);
        params.bottomMargin = DensityUtiltwo.dp2px(mContext, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        TextView alterName=contentView.findViewById(R.id.alter_this_friend_name);
        alterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, AlterHisNameActivity.class);
                intent.putExtra("number",number);
                mContext.startActivity(intent);
                bottomDialog.dismiss();
            }
        });
        TextView deleteFriend=contentView.findViewById(R.id.delete_this_friend);
        deleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                showDeleteDialog(number,name);
                bottomDialog.dismiss();
            }
        });

        TextView backtv=contentView.findViewById(R.id.hide_dialog);
        backtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });

    }

    private void showDeleteDialog(final String number, String name){
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
        infoText.setText("确认删除"+name);
        yesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyDialog.showBottomLoadingDialog(mContext);
                SendToServer.deleteFriend(number);

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext,"服务器繁忙,请稍后再试!",Toast.LENGTH_SHORT).show();
                            }
                        });
                        timer.cancel();
                    }
                },10000);

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
