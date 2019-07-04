package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.REGISTERTEST_RESPONSE;

public class RegisterTestResponsePacket extends Packet {

	String result;
    @Override
    public int getCommand() {

        return REGISTERTEST_RESPONSE;
    }
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

}
