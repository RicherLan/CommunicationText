package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.gson.UserCorp;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SearchEmptyCourse_RESPONSE;

/*
    社团组织获得   获得某几节课都有空的人
 */
public class SearchEmptyCourseResponsePacket extends Packet {

	Vector<UserCorp> userCorps;
	int groupid;
    @Override
    public int getCommand() {

        return SearchEmptyCourse_RESPONSE;
    }
	public Vector<UserCorp> getUserCorps() {
		return userCorps;
	}
	public void setUserCorps(Vector<UserCorp> userCorps) {
		this.userCorps = userCorps;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

}
