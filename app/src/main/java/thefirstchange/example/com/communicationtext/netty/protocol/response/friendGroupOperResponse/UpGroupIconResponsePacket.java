package thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.UpGroupIcon_RESPONSE;

/*
 * 	更改群头像
 */
public class UpGroupIconResponsePacket extends Packet{

	int groupid;
	String rs;
	@Override
	public int getCommand() {
		
		return UpGroupIcon_RESPONSE;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public String getRs() {
		return rs;
	}
	public void setRs(String rs) {
		this.rs = rs;
	}

}
