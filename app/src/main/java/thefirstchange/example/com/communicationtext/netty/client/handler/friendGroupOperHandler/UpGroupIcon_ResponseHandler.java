package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.UpGroupIconResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    更改群头像
 */
public class UpGroupIcon_ResponseHandler extends SimpleChannelInboundHandler<UpGroupIconResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, UpGroupIconResponsePacket upGroupIconResponsePacket) throws Exception {
       int groupid = upGroupIconResponsePacket.getGroupid();
        String rs = upGroupIconResponsePacket.getRs();


        Intent intent=new Intent("thefirstchange.example.com.communicationtext.GROUPPAGE");
        intent.putExtra("type","ChGroupIcRs");
        intent.putExtra("groupid",groupid);
        intent.putExtra("rs",rs);
        NettyService.nettyService.sendCast(intent);



    }
}
