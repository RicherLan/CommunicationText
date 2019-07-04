package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.gson.User;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AddUserToFriendList_RESPONSE;

/*
    客户端添加好友成功后  将好友加入到自己的好友列表  因此需要好友的资料
 */
public class AddUserToFriendListResponsePacket extends Packet {
	User user;
    @Override
    public int getCommand() {

        return AddUserToFriendList_RESPONSE;
    }
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
