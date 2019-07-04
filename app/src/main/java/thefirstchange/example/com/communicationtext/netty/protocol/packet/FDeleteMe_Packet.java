package thefirstchange.example.com.communicationtext.netty.protocol.packet;


import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.FDeleteMe_Packet;

/*
 * 好友删除了自己
 */
public class FDeleteMe_Packet extends Packet{
	
	int msgid;
	String phonenumber;
	@Override
	public int getCommand() {
		
		return FDeleteMe_Packet;
	}
	public int getMsgid() {
		return msgid;
	}
	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	

}
