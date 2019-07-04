package thefirstchange.example.com.communicationtext.netty.client.handler.userPersonalInfoHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.UpPeronalInfoReponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    修改个人信息的结果
 */
public class UpPeronalInfo_ResponseHandler extends SimpleChannelInboundHandler<UpPeronalInfoReponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, UpPeronalInfoReponsePacket upPeronalInfoReponsePacket) throws Exception {

        String rs = upPeronalInfoReponsePacket.getResult();

        String type = "ChPerInRs";
        Intent intent=new Intent("thefirstchange.example.com.communicationtext.UPDATEPERSONALINFO");
        intent.putExtra("rs",rs);
        NettyService.nettyService.sendCast(intent);
    }
}
