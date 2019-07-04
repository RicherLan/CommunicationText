package thefirstchange.example.com.communicationtext.gson;

public class Notifications {
    private static final int TYPE_SEND=0;
    private static final int TYPE_RECIVE=1;
    private static String senderPhone;
    private  String info;
    private  String reciveTime;
    private int type;
    private String belongId;
    private String belongAvatar;
    private float time;
    private String filePath;
    private long msgTime;
    private String belongUserName;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static int getTypeSend() {
        return TYPE_SEND;
    }

    public static int getTypeRecive() {
        return TYPE_RECIVE;
    }

    public static String getSenderPhone() {
        return senderPhone;
    }

    public static void setSenderPhone(String senderPhone) {
        Notifications.senderPhone = senderPhone;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getReciveTime() {
        return reciveTime;
    }

    public void setReciveTime(String reciveTime) {
        this.reciveTime = reciveTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }

    public String getBelongAvatar() {
        return belongAvatar;
    }

    public void setBelongAvatar(String belongAvatar) {
        this.belongAvatar = belongAvatar;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(long msgTime) {
        this.msgTime = msgTime;
    }

    public String getBelongUserName() {
        return belongUserName;
    }

    public void setBelongUserName(String belongUserName) {
        this.belongUserName = belongUserName;
    }
}
