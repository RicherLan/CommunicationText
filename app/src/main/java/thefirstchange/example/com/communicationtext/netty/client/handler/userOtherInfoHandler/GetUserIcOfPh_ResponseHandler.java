package thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.userOtherInfoResponse.GetUserIcOfPhResponsePacket;
import thefirstchange.example.com.communicationtext.util.MyFileTools;

/*
    获得某用户头像
 */
public class GetUserIcOfPh_ResponseHandler extends SimpleChannelInboundHandler<GetUserIcOfPhResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetUserIcOfPhResponsePacket getUserIcOfPhResponsePacket) throws Exception {
        byte[] bs = getUserIcOfPhResponsePacket.getIc();
        String ph = getUserIcOfPhResponsePacket.getPh();
        String path = MyFileTools.saveUserIcon(ph,bs);

    }
}
