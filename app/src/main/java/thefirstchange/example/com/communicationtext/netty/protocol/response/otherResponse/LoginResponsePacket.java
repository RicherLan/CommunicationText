package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.LOGIN_RESPONSE;


public class LoginResponsePacket extends Packet {
  
	private String rs;
	private String type;

    @Override
    public int getCommand() {

        return LOGIN_RESPONSE;
    }

	public String getRs() {
		return rs;
	}

	public void setRs(String rs) {
		this.rs = rs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


    
}
