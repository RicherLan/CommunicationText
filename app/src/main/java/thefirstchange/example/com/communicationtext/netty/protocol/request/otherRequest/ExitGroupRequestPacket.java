package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.ExitGroup_REQUEST;

/*
退群
 */
public class ExitGroupRequestPacket extends Packet {

	int groupid;

	public ExitGroupRequestPacket() {

	}

	public ExitGroupRequestPacket(int groupid) {
		this.groupid = groupid;
	}

	@Override
	public int getCommand() {

		return ExitGroup_REQUEST;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
}
