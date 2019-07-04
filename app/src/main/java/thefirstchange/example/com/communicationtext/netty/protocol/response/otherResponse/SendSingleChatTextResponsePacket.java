package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SendSingleChatText_RESPONSE;

public class SendSingleChatTextResponsePacket extends Packet {
    @Override
    public int getCommand() {

        return SendSingleChatText_RESPONSE;
    }

}
