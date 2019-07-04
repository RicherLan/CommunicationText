package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SendGroupChatText_RESPONSE;




public class SendGroupChatTextResponsePacket extends Packet{

	
	
    @Override
    public int getCommand() {
        return SendGroupChatText_RESPONSE;
    }

	
    
}
