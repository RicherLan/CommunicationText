package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.corpResponse.AppointCorpPosResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    社团组织任命职位
 */
public class AppointCorpPosResponseHandler  extends SimpleChannelInboundHandler<AppointCorpPosResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AppointCorpPosResponsePacket appointCorpPosResponsePacket) throws Exception {

        int groupid = appointCorpPosResponsePacket.getGroupid();
        String ph = appointCorpPosResponsePacket.getPh();
        String oldph = appointCorpPosResponsePacket.getOldph();
        String rs = appointCorpPosResponsePacket.getRs();
        String postype = appointCorpPosResponsePacket.getPostype();

        Intent intent = new Intent("thefirstchange.example.com.communicationtext.APPOINTZHUXIACTIVITY");
        if(postype.equals("主席")){

        }else if(postype.equals("干事")){
            intent = new Intent("thefirstchange.example.com.communicationtext.APPOINTGANSHIACTIVITY");
        }else{
            intent = new Intent("thefirstchange.example.com.communicationtext.APPOINTBUZHANGACTIVITY");
        }

        intent.putExtra("type", "appointPos");
        intent.putExtra("rs", rs);
        intent.putExtra("groupid", groupid);
        intent.putExtra("ph", ph);
        intent.putExtra("oldph", oldph);

        NettyService.nettyService.sendCast(intent);

    }
}
