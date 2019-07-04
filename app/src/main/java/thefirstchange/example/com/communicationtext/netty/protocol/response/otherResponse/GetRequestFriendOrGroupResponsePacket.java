package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import thefirstchange.example.com.communicationtext.service.ChatMsg;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetRequestFriendOrGroup_RESPONSE;

/*
拿到所有的好友、群请求信息  一般在刚登陆的时候
服务器响应
 */
public class GetRequestFriendOrGroupResponsePacket extends Packet {

	Vector<ChatMsg> requestFriendOrGroups;
	
    @Override
    public int getCommand() {

        return GetRequestFriendOrGroup_RESPONSE;
    }

	public Vector<ChatMsg> getRequestFriendOrGroups() {
		return requestFriendOrGroups;
	}

	public void setRequestFriendOrGroups(Vector<ChatMsg> requestFriendOrGroups) {
		this.requestFriendOrGroups = requestFriendOrGroups;
	}

}
