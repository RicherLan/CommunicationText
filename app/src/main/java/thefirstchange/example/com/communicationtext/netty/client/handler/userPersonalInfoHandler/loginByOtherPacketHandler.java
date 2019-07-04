package thefirstchange.example.com.communicationtext.netty.client.handler.userPersonalInfoHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.LoginByOther_PACKET;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    自己登陆   有人异地登陆  顶替自己
 */
public class loginByOtherPacketHandler  extends SimpleChannelInboundHandler<LoginByOther_PACKET> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginByOther_PACKET loginByOther_packet) throws Exception {

        long time = loginByOther_packet.getTime();
        String msg = loginByOther_packet.getMsg();


        Intent intent = new Intent("thefirstchange.example.com.communicationtext.MAIN");
        intent.putExtra("type","loginByOther");
        intent.putExtra("time",time);
        NettyService.nettyService.sendCast(intent);

    }
}
