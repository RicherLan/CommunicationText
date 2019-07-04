package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;


import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static  thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetFriendIconOfUID_RESPONSE;

/*
 * 获得好友头像
 */
public class GetFriendIconOfUIDResponsePacket extends Packet{
	@Override
	public int getCommand() {
		
		return GetFriendIconOfUID_RESPONSE;
	}
	
}
