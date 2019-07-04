package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.CorpUserNotLoadCourse;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.CorpLoadCourseRsResponsePacket;
import thefirstchange.example.com.communicationtext.service.MessageTemp;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    社团组织查看课表导入情况
 */
public class CorpLoadCourseRs_ResponseHandler extends SimpleChannelInboundHandler<CorpLoadCourseRsResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CorpLoadCourseRsResponsePacket corpLoadCourseRsResponsePacket) throws Exception {


        int groupid = corpLoadCourseRsResponsePacket.getGroupid();
        Vector<CorpUserNotLoadCourse> corpUserNotLoadCourses = corpLoadCourseRsResponsePacket.getCorpUserNotLoadCourses();
        int groupusernum = corpLoadCourseRsResponsePacket.getGroupusernum();

        String rs = "ok";
        if(corpUserNotLoadCourses!=null){
            MessageTemp.corpUserNotLoadCourses = corpUserNotLoadCourses;
        }else{
            rs = "error";
        }

        if(StaticAllList.groupList.containsKey(groupid)){
            StaticAllList.groupList.get(groupid).setUsernum(groupusernum);
        }

        String type = "corpLoadCourseRs";
        Intent intent=new Intent("thefirstchange.example.com.communicationtext.GROUPARRANGEA");
        intent.putExtra("type",type);
        intent.putExtra("groupid",groupid);
        intent.putExtra("groupusernum",groupusernum);
        intent.putExtra("rs",rs);
        NettyService.nettyService.sendCast(intent);
    }
}
