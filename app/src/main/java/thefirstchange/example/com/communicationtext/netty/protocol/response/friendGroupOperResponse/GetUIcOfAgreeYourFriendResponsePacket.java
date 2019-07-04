package thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetUIcOfAgreeYourFriend_RESPONSE;



/*
 * 	对方同意了你的好友请求    获得对方的头像
 */
public class GetUIcOfAgreeYourFriendResponsePacket extends Packet{

	String ph;
	byte[] icon;
	@Override
	public int getCommand() {
		// TODO Auto-generated method stub
		return GetUIcOfAgreeYourFriend_RESPONSE;
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
