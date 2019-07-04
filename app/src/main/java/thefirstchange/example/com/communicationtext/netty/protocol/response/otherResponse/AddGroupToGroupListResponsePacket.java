package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.gson.UserGroup;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AddGroupToGroupList_RESPONSE;

/*
加群  或  创建群成功后    把该群的信息加入到自己的群列表中
 */
public class AddGroupToGroupListResponsePacket extends Packet {

	UserGroup userGroup;
    @Override
    public int getCommand() {

        return AddGroupToGroupList_RESPONSE;
    }
	public UserGroup getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

}
