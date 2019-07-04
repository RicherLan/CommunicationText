package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.UpGroupPartResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    修改自己的部室的结果
 */
public class UpGroupPart_ResponseHandler extends SimpleChannelInboundHandler<UpGroupPartResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, UpGroupPartResponsePacket upGroupPartResponsePacket) throws Exception {

        String rs = upGroupPartResponsePacket.getResult();      //结果
        if(rs.equals("ok")){
            int groupid = upGroupPartResponsePacket.getGroupid();
            String part = upGroupPartResponsePacket.getPart();
            StaticAllList.groupList.get(groupid).setPart(part);
        }

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.ALTERGROUPPART");
        intent.putExtra("rs",rs);
        NettyService.nettyService.sendCast(intent);
    }
}
