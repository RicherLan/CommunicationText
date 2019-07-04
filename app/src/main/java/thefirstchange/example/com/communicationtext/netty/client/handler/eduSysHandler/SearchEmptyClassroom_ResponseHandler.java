package thefirstchange.example.com.communicationtext.netty.client.handler.eduSysHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.CourseAndScore;
import thefirstchange.example.com.communicationtext.gson.ClassRoom;
import thefirstchange.example.com.communicationtext.netty.protocol.response.edu.SearchEmptyClassroomResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    查询空教室
 */
public class SearchEmptyClassroom_ResponseHandler extends SimpleChannelInboundHandler<SearchEmptyClassroomResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SearchEmptyClassroomResponsePacket searchEmptyClassroomResponsePacket) throws Exception {
        String rs = searchEmptyClassroomResponsePacket.getrString();
        Vector<ClassRoom> classRooms = searchEmptyClassroomResponsePacket.getClassRoom();

        if(rs.equals("ok")){
            CourseAndScore.classRooms = classRooms;
        }

        Intent intent = new Intent("thefirstchange.example.com.communicationtext.SEARCHEMPTYCLASSROOM");

        intent.putExtra("type","searchEmptyClassroom");
        intent.putExtra("rs",rs);

        NettyService.nettyService.sendCast(intent);

    }
}
