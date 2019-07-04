package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.UserCorp;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.SearchEmptyCourseResponsePacket;
import thefirstchange.example.com.communicationtext.service.MessageTemp;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    社团组织获得   获得某几节课都有空的人
    社团组织查看空课表
 */
public class SearchEmptyCourse_ResponseHandler extends SimpleChannelInboundHandler<SearchEmptyCourseResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SearchEmptyCourseResponsePacket searchEmptyCourseResponsePacket) throws Exception {

        int groupid = searchEmptyCourseResponsePacket.getGroupid();
        Vector<UserCorp> userCorps = searchEmptyCourseResponsePacket.getUserCorps();

        String rs = "ok";
        if(userCorps!=null){
            MessageTemp.userCorps = userCorps;
        }else{
            rs = "error";
        }
        String type = "SECP";
        Intent intent=new Intent("thefirstchange.example.com.communicationtext.SEARCHEMPTYCOURSEPEOPLE");
        intent.putExtra("type",type);
        intent.putExtra("groupid",groupid);
        intent.putExtra("rs",rs);
        NettyService.nettyService.sendCast(intent);
    }
}
