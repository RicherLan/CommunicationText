package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import  thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static  thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetRequestFriendOrGroup_REQUEST;

/*
拿到所有的好友、群请求信息  一般在刚登陆的时候
 */
public class GetRequestFriendOrGroupRequestPacket extends Packet {
	public GetRequestFriendOrGroupRequestPacket() {

	}
    @Override
    public int getCommand() {

        return GetRequestFriendOrGroup_REQUEST;
    }

}
