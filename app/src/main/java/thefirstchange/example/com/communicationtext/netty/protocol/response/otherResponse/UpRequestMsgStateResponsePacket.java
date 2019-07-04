package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.UpRequestMsgState_RESPONSE;

/*
好友或群请求   收到消息后   更改消息的读取状态   服务器回执
 */
public class UpRequestMsgStateResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return UpRequestMsgState_RESPONSE;
    }

}
