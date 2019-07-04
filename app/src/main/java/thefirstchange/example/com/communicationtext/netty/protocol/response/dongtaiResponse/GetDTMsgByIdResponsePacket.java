package thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetDTMsgById_RESPONSE;

/*
    获得动态消息的内容
 */
public class GetDTMsgByIdResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return GetDTMsgById_RESPONSE;
    }
}
