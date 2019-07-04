package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import  thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static  thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetAllFriendInfo_REQUEST;

public class GetAllFriendInfoRequestPacket extends Packet {

	public GetAllFriendInfoRequestPacket() {

	}
    @Override
    public int getCommand() {
        return GetAllFriendInfo_REQUEST;
    }

}
