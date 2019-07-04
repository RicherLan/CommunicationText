package thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetFriendIDOnlineResponsePacket;

/*
获得在线的好友有哪些    就是返回在线的账号就行
 */
public class GetFriendIDOnline_ResponseHandler extends SimpleChannelInboundHandler<GetFriendIDOnlineResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetFriendIDOnlineResponsePacket getFriendIDOnlineResponsePacket) throws Exception {
        Vector<String> userGroups = getFriendIDOnlineResponsePacket.getPhonenumbers();

    }
}
