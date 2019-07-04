package thefirstchange.example.com.communicationtext.netty.protocol.request.notificationRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetNotiIconOfGroup_Request;

/*
 * 	获得某群头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
 */
public class GetNotiIconOfGroupRequestPacket extends Packet {

	int groupid;

	public GetNotiIconOfGroupRequestPacket() {

	}

	public GetNotiIconOfGroupRequestPacket(int gid) {
		this.groupid = gid;
	}

	@Override
	public int getCommand() {

		return GetNotiIconOfGroup_Request;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

}
