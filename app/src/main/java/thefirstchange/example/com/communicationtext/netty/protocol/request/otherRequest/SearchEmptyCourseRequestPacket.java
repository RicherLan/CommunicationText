package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;


import java.util.Vector;

import thefirstchange.example.com.communicationtext.gson.SearchEmptyCourse;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.SearchEmptyCourse_REQUEST;

/*
    社团组织获得   获得某几节课都有空的人
 */
public class SearchEmptyCourseRequestPacket extends Packet {
    int groupid;
    int year;
    int xueqi ;
    Vector<SearchEmptyCourse> searchEmptyCourses;
    public SearchEmptyCourseRequestPacket() {
    	
    }
    public SearchEmptyCourseRequestPacket(int groupid, int year, int xueqi , Vector<SearchEmptyCourse> searchEmptyCourses){
        this.groupid = groupid;
        this.year = year;
        this.xueqi = xueqi;
        this.searchEmptyCourses = searchEmptyCourses;

    }
    @Override
    public int getCommand() {

        return SearchEmptyCourse_REQUEST;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getXueqi() {
        return xueqi;
    }

    public void setXueqi(int xueqi) {
        this.xueqi = xueqi;
    }

    public Vector<SearchEmptyCourse> getSearchEmptyCourses() {
        return searchEmptyCourses;
    }

    public void setSearchEmptyCourses(Vector<SearchEmptyCourse> searchEmptyCourses) {
        this.searchEmptyCourses = searchEmptyCourses;
    }
}
