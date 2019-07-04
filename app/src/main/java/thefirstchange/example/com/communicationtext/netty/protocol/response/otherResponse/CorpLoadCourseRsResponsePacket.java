package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.gson.CorpUserNotLoadCourse;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.CorpLoadCourseRs_RESPONSE;

/*
    社团组织查看课表导入情况
 */
public class CorpLoadCourseRsResponsePacket extends Packet {

	int groupid;
	Vector<CorpUserNotLoadCourse> corpUserNotLoadCourses;
	int groupusernum;        //群人数
    @Override
    public int getCommand() {

        return CorpLoadCourseRs_RESPONSE;
    }
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public Vector<CorpUserNotLoadCourse> getCorpUserNotLoadCourses() {
		return corpUserNotLoadCourses;
	}
	public void setCorpUserNotLoadCourses(Vector<CorpUserNotLoadCourse> corpUserNotLoadCourses) {
		this.corpUserNotLoadCourses = corpUserNotLoadCourses;
	}

	public int getGroupusernum() {
		return groupusernum;
	}

	public void setGroupusernum(int groupusernum) {
		this.groupusernum = groupusernum;
	}
}
