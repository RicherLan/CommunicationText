package thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetUserIcOfAddMeAsFriend_RESPONSE;

/*
 * 	添加好友  被添加方要获得对方的头像
 */
public class GetUserIcOfAddMeAsFriendResponsePacket extends Packet {

	String ph;
	byte[] icon;
	@Override
	public int getCommand() {
		
		return GetUserIcOfAddMeAsFriend_RESPONSE;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public byte[] getIcon() {
		return icon;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

}
