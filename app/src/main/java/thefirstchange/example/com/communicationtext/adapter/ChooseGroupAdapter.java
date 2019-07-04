package thefirstchange.example.com.communicationtext.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.contacts.ContactAdapter;
import thefirstchange.example.com.communicationtext.contacts.ContactComparator;
import thefirstchange.example.com.communicationtext.contacts.GroupContact;
import thefirstchange.example.com.communicationtext.contacts.Utils;
import thefirstchange.example.com.communicationtext.group.GroupChatActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.UserGroup;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.util.DateUtils;
import thefirstchange.example.com.communicationtext.util.MyFileTools;
import thefirstchange.example.com.communicationtext.util.MyViewTools;

public class ChooseGroupAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<String> mContactList; // 联系人名称List（转换成拼音）
    private List<GroupContact> resultList; // 最终结果（包含分组的字母）
    private List<String> characterList; // 字母List
    private Activity activity;

    public enum ITEM_TYPE {
        ITEM_TYPE_CHARACTER,
        ITEM_TYPE_CONTACT
    }

    public ChooseGroupAdapter(Context context,Activity activity){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.activity=activity;

        handleContact();
    }

    private void handleContact() {
        mContactList = new ArrayList<>();
        Map<String, UserGroup> map = new HashMap<>();

        for (int groupid: StaticAllList.groupList.keySet()) {
            UserGroup userGroup =  StaticAllList.groupList.get(groupid);
            String pinyin = Utils.getPingYin(userGroup.getGroupname())+groupid+"";
            map.put(pinyin, userGroup);
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
                    resultList.add(new GroupContact(character, ChooseGroupAdapter.ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                } else {
                    if (!characterList.contains("#")) {
                        characterList.add("#");
                        resultList.add(new GroupContact("#", ChooseGroupAdapter.ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                    }
                }
            }

            resultList.add(new GroupContact(map.get(name), ChooseGroupAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ContactAdapter.ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()) {
            return new ChooseGroupAdapter.CharacterHolder(mLayoutInflater.inflate(R.layout.item_character, parent, false));
        } else {
            return new ChooseGroupAdapter.ContactHolder(mLayoutInflater.inflate(R.layout.item_contact, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ChooseGroupAdapter.CharacterHolder) {
            ((ChooseGroupAdapter.CharacterHolder) holder).mTextView.setText(resultList.get(position).getmName());
        } else if (holder instanceof ChooseGroupAdapter.ContactHolder) {
            ((ChooseGroupAdapter.ContactHolder) holder).mTextView.setText(resultList.get(position).getmName());

            int groupid = resultList.get(position).getUserGroup().getGroupid();
            String path = path = MyFileTools.getGroupIconPath(groupid);

            if(path==null||path.equals("")){

                //之前没有请求过该群的头像
                if(!StaticAllList.groupIconLastRequest.containsKey(groupid)){
                    StaticAllList.groupIconLastRequest.put(groupid,System.currentTimeMillis());
                    //请求头像
                    SendToServer.getGroupIcByGid(groupid);
                }else{
                    long lasttime = StaticAllList.groupIconLastRequest.get(groupid);
                    long nowtime = System.currentTimeMillis();

                    if(!DateUtils.isTwoTimeDifferLessAppointTime(lasttime,nowtime,300)){
                        StaticAllList.groupIconLastRequest.put(groupid,nowtime);
                        //请求头像
                        SendToServer.getGroupIcByGid(groupid);
                    }
                }


            }else {
                resultList.get(position).getUserGroup().setGroupicon(path);
            }


            MyViewTools.setCircleGroupIcon(((ContactHolder) holder).groupIcView,path);
            ((ChooseGroupAdapter.ContactHolder)holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, GroupChatActivity.class);
                    intent.putExtra("groupId",groupid);
                    intent.putExtra("groupname",resultList.get(position).getUserGroup().getGroupname());
                    mContext.startActivity(intent);

                    //更新下该群的信息

                    SendToServer.getGroupInfoByGid(groupid);


                    activity.finish();

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
        CircleImageView groupIcView;
        TextView mTextView;
        LinearLayout linearLayout;


        ContactHolder(View view) {
            super(view);
            groupIcView = (CircleImageView)view.findViewById(R.id.contact_list_icon);
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

}
