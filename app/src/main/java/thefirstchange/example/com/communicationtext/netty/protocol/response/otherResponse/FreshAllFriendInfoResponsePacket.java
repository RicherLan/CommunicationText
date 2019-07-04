package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.gson.MyFriendEasy;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.FreshAllFriendInfo_RESPONSE;

public class FreshAllFriendInfoResponsePacket extends Packet {

	Vector<MyFriendEasy> users;
	
    @Override
    public int getCommand() {

        return FreshAllFriendInfo_RESPONSE;
    }

	public Vector<MyFriendEasy> getUsers() {
		return users;
	}

	public void setUsers(Vector<MyFriendEasy> users) {
		this.users = users;
	}

}
