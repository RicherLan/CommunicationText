package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.schedulearrangement.ClientArrangement;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetAllSD_RESPONSE;

/*
   获得自己加入的所有组织的值班表
 */
public class GetAllSDResponsePacket extends Packet {

	int groupid;
	Vector<ClientArrangement> clientArrangements;
    @Override
    public int getCommand() {

        return GetAllSD_RESPONSE;
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
