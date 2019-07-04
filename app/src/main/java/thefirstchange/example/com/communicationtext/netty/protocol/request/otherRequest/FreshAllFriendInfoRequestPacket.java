package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import  thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static  thefirstchange.example.com.communicationtext.netty.protocol.command.Command.FreshAllFriendInfo_REQUEST;

public class FreshAllFriendInfoRequestPacket extends Packet {
	public FreshAllFriendInfoRequestPacket() {

	}
    @Override
    public int getCommand() {

        return FreshAllFriendInfo_REQUEST;
    }

}
