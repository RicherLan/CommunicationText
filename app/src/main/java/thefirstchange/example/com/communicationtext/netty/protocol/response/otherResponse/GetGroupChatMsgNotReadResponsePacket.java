package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetGroupChatMsgNotRead_RESPONSE;

/*
拿到群聊天  未读信息     一般是刚登陆的时候
 */
public class GetGroupChatMsgNotReadResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return GetGroupChatMsgNotRead_RESPONSE;
    }

}
