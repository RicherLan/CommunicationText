package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetFriendIconOfUID_REQUEST;

/*
 * 	获得好友头像
 */
public class GetFriendIconOfUIDRequestPacket extends Packet {

	public GetFriendIconOfUIDRequestPacket() {

	}

	@Override
	public int getCommand() {

		return GetFriendIconOfUID_REQUEST;
	}

}
