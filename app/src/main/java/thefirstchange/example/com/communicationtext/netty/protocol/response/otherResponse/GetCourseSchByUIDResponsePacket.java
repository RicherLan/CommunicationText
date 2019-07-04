package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.course.object.Course;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetCourseSchOfUID_RESPONSE;

/*
    获取课表
 */
public class GetCourseSchByUIDResponsePacket extends Packet {

	
	String result;
	int grade;
	int xueqi;
	Vector<Course> courses;
	
    @Override
    public int getCommand() {

        return GetCourseSchOfUID_RESPONSE;
    }
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getXueqi() {
		return xueqi;
	}
	public void setXueqi(int xueqi) {
		this.xueqi = xueqi;
	}
	public Vector<Course> getCourses() {
		return courses;
	}
	public void setCourses(Vector<Course> courses) {
		this.courses = courses;
	}

}
