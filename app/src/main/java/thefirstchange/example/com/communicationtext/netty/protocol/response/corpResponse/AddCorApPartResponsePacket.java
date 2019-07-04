package thefirstchange.example.com.communicationtext.netty.protocol.response.corpResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AddCorApPart_RESPONSE;


/*
    添加社团组织的某一个部门
 */
public class AddCorApPartResponsePacket extends Packet{
	
	String result;
	int grouid;
	String name;
    @Override
    public int getCommand() {

        return AddCorApPart_RESPONSE;
    }
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getGrouid() {
		return grouid;
	}
	public void setGrouid(int grouid) {
		this.grouid = grouid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
}
