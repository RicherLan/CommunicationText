package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.ReadSingleChatMsg_RESPONSE;

public class ReceiveSingleChatTextResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return ReadSingleChatMsg_RESPONSE;
    }
}
