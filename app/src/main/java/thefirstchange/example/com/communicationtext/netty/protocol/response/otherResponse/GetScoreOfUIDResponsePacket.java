package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.course.object.ListViewScore;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetScoreOfUID_RESPONSE;

/*
    获取成绩
 */
public class GetScoreOfUIDResponsePacket extends Packet {
	
	String result;
	int grade;
	int xueqi;
	Vector<ListViewScore> scores;
    @Override
    public int getCommand() {

        return GetScoreOfUID_RESPONSE;
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

	public Vector<ListViewScore> getScores() {
		return scores;
	}

	public void setScores(Vector<ListViewScore> scores) {
		this.scores = scores;
	}

}
