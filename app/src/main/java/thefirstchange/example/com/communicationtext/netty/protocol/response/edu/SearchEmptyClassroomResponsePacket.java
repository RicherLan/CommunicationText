package thefirstchange.example.com.communicationtext.netty.protocol.response.edu;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.gson.ClassRoom;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SearchEmptyClassroom_RESPONSE;

/*
 * 	查询空教室
 */
public class SearchEmptyClassroomResponsePacket extends Packet{
	String rString;
	Vector<ClassRoom> classRoom;
	@Override
	public int getCommand() {
		// TODO Auto-generated method stub
		return SearchEmptyClassroom_RESPONSE;
	}
	public Vector<ClassRoom> getClassRoom() {
		return classRoom;
	}
	public void setClassRoom(Vector<ClassRoom> classRoom) {
		this.classRoom = classRoom;
	}

	public String getrString() {
		return rString;
	}

	public void setrString(String rString) {
		this.rString = rString;
	}
}
