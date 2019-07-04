package thefirstchange.example.com.communicationtext.netty.protocol.packet;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SendUserAlterCorpPart_PACKET;

/*
 * 修改部门 还要通知该部室的成员 不进行通知
 */
public class SendUserAlterCorpPart_PACKET extends Packet{
	
	int grouid;
	String oldname;
	String newname;
	@Override
	public int getCommand() {
		
		return SendUserAlterCorpPart_PACKET;
	}
	public int getGrouid() {
		return grouid;
	}
	public void setGrouid(int grouid) {
		this.grouid = grouid;
	}
	public String getOldname() {
		return oldname;
	}
	public void setOldname(String oldname) {
		this.oldname = oldname;
	}
	public String getNewname() {
		return newname;
	}
	public void setNewname(String newname) {
		this.newname = newname;
	}
	
}
