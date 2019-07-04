package thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetDongtaiMsg_REQUEST;

/*
    拿到自己的动态消息  一般是刚上线的时候
 */
public class GetDongtaiMsgRequestPacket extends Packet {

	public GetDongtaiMsgRequestPacket() {

	}

	@Override
	public int getCommand() {

		return GetDongtaiMsg_REQUEST;
	}

}
