package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.LOGOUT_REQUEST;

public class LogoutRequestPacket extends Packet {
	
	public LogoutRequestPacket() {

	}
    @Override
    public int getCommand() {

        return LOGOUT_REQUEST;
    }
    
    
}
