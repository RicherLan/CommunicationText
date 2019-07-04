package thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AddDongTaiImage_RESPONSE;

/*
        用户发表动态时   图片分开传送
 */
public class AddDongTaiImageResponsePacket extends Packet {

	String result;
	int id;
	
    @Override
    public int getCommand() {

        return AddDongTaiImage_RESPONSE;
    }

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
