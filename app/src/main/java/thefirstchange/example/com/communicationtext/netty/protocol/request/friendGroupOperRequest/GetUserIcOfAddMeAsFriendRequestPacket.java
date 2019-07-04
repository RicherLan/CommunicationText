package thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetUserIcOfAddMeAsFriend_REQUEST;

/*
 * 添加好友  被添加方要获得对方的头像
 */
public class GetUserIcOfAddMeAsFriendRequestPacket extends Packet{
	
	String ph;
	public GetUserIcOfAddMeAsFriendRequestPacket() {
		
	}
	public GetUserIcOfAddMeAsFriendRequestPacket(String ph) {
		this.ph = ph;
	}
	@Override
	public int getCommand() {
		
		return GetUserIcOfAddMeAsFriend_REQUEST;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}

}
