package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.IsPhRegisted_RESPONSE;

/*
        用户注册账号时  判断提交的手机号是否已经被注册
 */
public class IsPhonenumberRegistedResponsePacket extends Packet {

	String result;
	
    @Override
    public int getCommand() {

        return IsPhRegisted_RESPONSE;
    }

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
