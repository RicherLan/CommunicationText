package thefirstchange.example.com.communicationtext.service;

/*
 * 请求信息    添加删除等
 */
public class RequestFriendOrGroupMsg {

	private int msgid;
	private String senderid;          //发送者的账号
	private String sendernickname;
	private String sendersex;
	private String sendericon;
	private String type;                  //类型 比如退群等 exitgroup  addfriend   addgroup
	private String recivierid;               //对方的账号  也就是接收者的账号
	private long msgtime;
	private String msg;                     //添加时的验证信息之类的
	private String groupid;                  //加群或退群时的群号

	public String getSendersex() {
		return sendersex;
	}

	public void setSendersex(String sendersex) {
		this.sendersex = sendersex;
	}

	public String getSendericon() {
		return sendericon;
	}

	public void setSendericon(String sendericon) {
		this.sendericon = sendericon;
	}

	public long getMsgtime() {
		return msgtime;
	}

	public void setMsgtime(long msgtime) {
		this.msgtime = msgtime;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSendernickname() {
		return sendernickname;
	}

	public void setSendernickname(String sendernickname) {
		this.sendernickname = sendernickname;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRecivierid() {
		return recivierid;
	}

	public void setRecivierid(String recivierid) {
		this.recivierid = recivierid;
	}




	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
}
