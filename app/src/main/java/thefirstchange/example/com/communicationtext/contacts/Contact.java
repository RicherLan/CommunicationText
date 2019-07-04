package thefirstchange.example.com.communicationtext.contacts;

import java.io.Serializable;

import thefirstchange.example.com.communicationtext.gson.MyFriend;

/**
 * Created by xin on 2017/11/27.
 */

public class Contact implements Serializable {
    private MyFriend user;
    private String name;
    private int mType;

    public Contact(MyFriend user, int type) {
        this.user = user;
        mType = type;
        name=user.getRemark();
        if(user.getRemark().isEmpty()){
            name=user.getNickname();
        }

    }
    public Contact(String name,int mType){
        this.name=name;
        this.mType=mType;
    }

    public MyFriend getUser() {
        return user;
    }
    public String getmName(){
        return name;
    }

    public int getmType() {
        return mType;
    }

}