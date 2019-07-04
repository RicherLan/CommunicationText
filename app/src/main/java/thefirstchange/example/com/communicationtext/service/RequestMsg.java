package thefirstchange.example.com.communicationtext.service;

/*
某人添加自己好友  某人退群   消息队列
 */
public class RequestMsg {

    int msgid;
    String senderid;           //对方的账号
    String sendernickname;       //对方的网名或群昵称
    String sex;                 //性别
    String icon;               //对方的头像
    String type;                //addfriend  addgroup   exitgroup   agreeYourAddFriend   agreeYourAddGroup  disagreeYourAddFriend
    String groupid;             //添加好友的话   直接为空就行
    String msgbody;            //对方说的话  比如添加好友时的验证信息
    String msgtime;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getSendernickname() {
        return sendernickname;
    }

    public void setSendernickname(String sendernickname) {
        this.sendernickname = sendernickname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsgbody() {
        return msgbody;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public void setMsgbody(String msgbody) {
        this.msgbody = msgbody;
    }

    public String getMsgtime() {
        return msgtime;
    }

    public void setMsgtime(String msgtime) {
        this.msgtime = msgtime;
    }
}
