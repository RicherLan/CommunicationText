package thefirstchange.example.com.communicationtext.netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.HeartBeatResponsePacket;

public class HeartBeatResponseHandler extends SimpleChannelInboundHandler<HeartBeatResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HeartBeatResponsePacket heartBeatResponsePacket) throws Exception {

    }

}
