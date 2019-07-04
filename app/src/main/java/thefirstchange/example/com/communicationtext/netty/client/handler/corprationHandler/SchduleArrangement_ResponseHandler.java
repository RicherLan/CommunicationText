package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.SchduleArrangementResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    组织的值班管理者  安排值班的结果
 */
public class SchduleArrangement_ResponseHandler extends SimpleChannelInboundHandler<SchduleArrangementResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SchduleArrangementResponsePacket schduleArrangementResponsePacket) throws Exception {

        String rs = schduleArrangementResponsePacket.getResult();
        String type = "SARs";

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.SCHEDULEARRANGEMENT");
        intent.putExtra("type",type);
        intent.putExtra("rs",rs);
        NettyService.nettyService.sendCast(intent);
    }
}
