package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.ExitGroup_RESPONSE;

/*
退群  服务器回执
 */
public class ExitGroupResponsePacket extends Packet {
	
	
	String result;
	int groupid;
    @Override
    public int getCommand() {

        return ExitGroup_RESPONSE;
    }
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}


}
