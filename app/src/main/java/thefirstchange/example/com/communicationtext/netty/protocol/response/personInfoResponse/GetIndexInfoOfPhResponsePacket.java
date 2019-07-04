package thefirstchange.example.com.communicationtext.netty.protocol.response.personInfoResponse;

import thefirstchange.example.com.communicationtext.gson.User;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetIndexInfoOfPh_RESPONSE;

/*
 * 	进入某人的个人页面时  获得其基本信息
 */
public class GetIndexInfoOfPhResponsePacket extends Packet {

	User user;
	@Override
	public int getCommand() {

		return GetIndexInfoOfPh_RESPONSE;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
