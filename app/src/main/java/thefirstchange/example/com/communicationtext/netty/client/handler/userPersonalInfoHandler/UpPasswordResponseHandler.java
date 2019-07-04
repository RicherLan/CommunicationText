package thefirstchange.example.com.communicationtext.netty.client.handler.userPersonalInfoHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.UpPasswordResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
        修改密码
 */
public class UpPasswordResponseHandler extends SimpleChannelInboundHandler<UpPasswordResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UpPasswordResponsePacket responsePacket) throws Exception {

        String rs = responsePacket.getResult();

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.ALTERPASSWORD");
        intent.putExtra("type","upPwdRs");
        intent.putExtra("rs",rs);

        NettyService.nettyService.sendCast(intent);

    }
}
