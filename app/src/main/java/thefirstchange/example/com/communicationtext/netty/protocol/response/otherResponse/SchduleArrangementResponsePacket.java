package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SchduleArrangement_RESPONSE;

/*
    安排值班表
 */
public class SchduleArrangementResponsePacket extends Packet {

	String result;
    @Override
    public int getCommand() {

        return SchduleArrangement_RESPONSE;
    }
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

}
