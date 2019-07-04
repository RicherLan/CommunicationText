package thefirstchange.example.com.communicationtext.netty.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.LogoutResponsePacket;
import thefirstchange.example.com.communicationtext.netty.util.SessionUtil;

@ChannelHandler.Sharable
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket logoutResponsePacket) {
        SessionUtil.unBindSession(ctx.channel());
    }
}
