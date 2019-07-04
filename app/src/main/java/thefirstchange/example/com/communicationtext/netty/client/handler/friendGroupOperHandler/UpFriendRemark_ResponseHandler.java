package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.UpFriendRemarkResponsePacket;

/*

 */
public class UpFriendRemark_ResponseHandler extends SimpleChannelInboundHandler<UpFriendRemarkResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, UpFriendRemarkResponsePacket upFriendRemarkResponsePacket) throws Exception {

    }
}
