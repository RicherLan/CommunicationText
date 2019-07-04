package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.LOGOUT_RESPONSE;


public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;


    @Override
    public int getCommand() {

        return LOGOUT_RESPONSE;
    }


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}
    
}
