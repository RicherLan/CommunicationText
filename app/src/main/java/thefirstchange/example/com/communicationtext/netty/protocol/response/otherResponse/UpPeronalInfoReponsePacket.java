package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.UpPeronalInfo_RESPONSE;

/*
修改个人资料
 */
public class UpPeronalInfoReponsePacket extends Packet {

	String result;
    @Override
    public int getCommand() {

        return UpPeronalInfo_RESPONSE;
    }
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

}
