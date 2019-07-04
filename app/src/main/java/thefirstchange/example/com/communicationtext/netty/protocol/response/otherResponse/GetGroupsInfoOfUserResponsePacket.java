package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.gson.UserGroup;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetGroupsInfoOfUser_RESPONSE;

/*
 * 	获得某用户的群的基本信息
 */

public class GetGroupsInfoOfUserResponsePacket extends Packet {
	Vector<UserGroup> userGroups;
	
    @Override
    public int getCommand() {

        return GetGroupsInfoOfUser_RESPONSE;
    }

	public Vector<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Vector<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}
    
}
