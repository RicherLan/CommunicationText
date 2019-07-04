package thefirstchange.example.com.communicationtext.netty.client.handler.userPersonalInfoHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.RegisterTestResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;



/*
    注册用户  测试阶段
 */
public class RegisterTestesponseHandler extends SimpleChannelInboundHandler<RegisterTestResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterTestResponsePacket responsePacket) throws Exception {
        String rs = responsePacket.getResult();
        Intent intent3=new Intent("thefirstchange.example.com.communicationtext.REGISTERPHONENUMBER");
        intent3.putExtra("rs",rs);
        NettyService.nettyService.sendCast(intent3);
    }
}
