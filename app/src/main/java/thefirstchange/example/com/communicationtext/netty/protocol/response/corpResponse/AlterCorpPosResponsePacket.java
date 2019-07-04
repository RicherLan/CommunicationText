package thefirstchange.example.com.communicationtext.netty.protocol.response.corpResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AlterCorpPos_RESPONSE;

/*
    修改自己在社团的职位
 */
public class AlterCorpPosResponsePacket extends Packet {
	
	String result;
	int groupid;
	String pos;
    @Override
    public int getCommand() {

        return AlterCorpPos_RESPONSE;
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
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
    
}
