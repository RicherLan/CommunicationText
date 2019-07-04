package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.gson.MyFriendEasy;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetAllFriendInfo_RESPONSE;

/*
 * 获得自己的所有好友信息    一般是刚登陆
 */
public class GetAllFriendInfoResponsePacket extends Packet {
	Vector<MyFriendEasy> users;
	
    @Override
    public int getCommand() {

        return GetAllFriendInfo_RESPONSE;
    }

	public Vector<MyFriendEasy> getUsers() {
		return users;
	}

	public void setUsers(Vector<MyFriendEasy> users) {
		this.users = users;
	}

}
