package thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetGroupIcByGid_Request;

/*
 * 	获得某群头像
 */
public class GetGroupIcByGidRequestPacket extends Packet{

	int groupid;
	public GetGroupIcByGidRequestPacket() {
		
	}
	public GetGroupIcByGidRequestPacket(int gid) {
		this.groupid = gid;
	}
	@Override
	public int getCommand() {
		
		return GetGroupIcByGid_Request;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

}
