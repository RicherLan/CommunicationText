package thefirstchange.example.com.communicationtext.netty.client.handler.userPersonalInfoHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.UpPersonalIconResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
/修改个人头像的结果
 */
public class UpPersonalIcon_ResponseHandler extends SimpleChannelInboundHandler<UpPersonalIconResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, UpPersonalIconResponsePacket upPersonalIconResponsePacket) throws Exception {

        String rs = upPersonalIconResponsePacket.getResult();

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.PERSONALHOMEPAGE");
        intent2.putExtra("type","ChPerIcRs");
        intent2.putExtra("rs",rs);
        NettyService.nettyService.sendCast(intent2);


        if(rs.equals("ok")){
            Intent intent=new Intent(" thefirstchange.example.com.communicationtext.INDEXFRAGMENT");
            intent.putExtra("type","ChPerIcRs");
            NettyService.nettyService.sendCast(intent);
        }




    }
}
