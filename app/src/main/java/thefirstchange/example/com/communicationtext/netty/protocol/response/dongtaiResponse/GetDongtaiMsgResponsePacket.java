package thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.getDongtaiMsg_RESPONSE;

/*
    拿到自己的动态消息  一般是刚上线的时候
 */
public class GetDongtaiMsgResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return getDongtaiMsg_RESPONSE;
    }

}
