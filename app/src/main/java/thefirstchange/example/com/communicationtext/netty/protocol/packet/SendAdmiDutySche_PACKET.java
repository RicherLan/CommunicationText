package thefirstchange.example.com.communicationtext.netty.protocol.packet;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.schedulearrangement.ClientArrangement;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SendAdmiDutySche_PACKET;

/*
 * 	安排值班表
 * 给社团管理(就是安排值班的人)发送排班结果
 */
public class SendAdmiDutySche_PACKET extends Packet{
	
	String schway;
	int groupid;
	Vector<ClientArrangement> clientArrangements;
	
	@Override
	public int getCommand() {
		
		return SendAdmiDutySche_PACKET;
	}

	public String getSchway() {
		return schway;
	}

	public void setSchway(String schway) {
		this.schway = schway;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public Vector<ClientArrangement> getClientArrangements() {
		return clientArrangements;
	}

	public void setClientArrangements(Vector<ClientArrangement> clientArrangements) {
		this.clientArrangements = clientArrangements;
	}
	

}
