package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.CreateCorprationResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    创建社团的结果
 */
public class CreateCorpration_ResponseHandler extends SimpleChannelInboundHandler<CreateCorprationResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreateCorprationResponsePacket createCorprationResponsePacket) throws Exception {

        String rs = createCorprationResponsePacket.getResult();
        int count= createCorprationResponsePacket.getCount();
        String password = createCorprationResponsePacket.getPassword();
        int groupid= createCorprationResponsePacket.getGroupid();

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.CREATEPART");
        intent.putExtra("rs",rs);
        intent.putExtra("count",count);
        intent.putExtra("password",password);
        intent.putExtra("groupid",groupid);
        NettyService.nettyService.sendCast(intent);

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.CORPORATIONCREATE");
        intent2.putExtra("rs",rs);
        NettyService.nettyService.sendCast(intent2);
    }
}
