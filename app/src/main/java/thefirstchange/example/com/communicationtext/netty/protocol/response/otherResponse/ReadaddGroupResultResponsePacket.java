package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.ReadAddGroupResult_RESPONSE;


/*
歧义
 */
public class ReadaddGroupResultResponsePacket extends Packet {


    @Override
    public int getCommand() {

        return ReadAddGroupResult_RESPONSE;
    }

}
