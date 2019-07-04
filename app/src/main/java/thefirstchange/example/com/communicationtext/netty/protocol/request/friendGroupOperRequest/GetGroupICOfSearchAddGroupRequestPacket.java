package thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetGroupICOfSearchAddGroup_Request;

/*
 * 	添加群时    首先查询     获得群的头像
 */
public class GetGroupICOfSearchAddGroupRequestPacket extends Packet{

	int groupid;
	public GetGroupICOfSearchAddGroupRequestPacket() {
		
	}
	public GetGroupICOfSearchAddGroupRequestPacket(int groupid) {
		this.groupid = groupid;
	}
	@Override
	public int getCommand() {
		
		return GetGroupICOfSearchAddGroup_Request;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

}
