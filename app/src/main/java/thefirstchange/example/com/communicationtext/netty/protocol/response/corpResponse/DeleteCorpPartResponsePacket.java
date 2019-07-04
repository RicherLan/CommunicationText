package thefirstchange.example.com.communicationtext.netty.protocol.response.corpResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.DeleteCorpPart_RESPONSE;

/*
    删除社团组织的某一个部门
 */
public class DeleteCorpPartResponsePacket extends Packet{

	String result;
	int groupid;
	String name;
	
	@Override
    public int getCommand() {

        return DeleteCorpPart_RESPONSE;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
