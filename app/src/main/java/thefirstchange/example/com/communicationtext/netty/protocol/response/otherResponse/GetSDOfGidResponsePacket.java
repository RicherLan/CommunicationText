package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.schedulearrangement.ClientArrangement;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetSDOfGid_RESPONSE;

/*
    获得自己加入的某个组织的值班表
 */
public class GetSDOfGidResponsePacket extends Packet {

	int dnid;
	int groupid;
	Vector<ClientArrangement> clientArrangements;
	
    @Override
    public int getCommand() {

        return GetSDOfGid_RESPONSE;
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

	public int getDnid() {
		return dnid;
	}

	public void setDnid(int dnid) {
		this.dnid = dnid;
	}
}
