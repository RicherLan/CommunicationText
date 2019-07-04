package thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.personInfoResponse.GetIndexICOfPhResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.service.StaticAllListOperator;
import thefirstchange.example.com.communicationtext.util.MyFileTools;

/*
    进入某人的个人页面时  获得其头像
 */
public class GetIndexICOfPh_ResponseHandler extends SimpleChannelInboundHandler<GetIndexICOfPhResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetIndexICOfPhResponsePacket getIndexICOfPhResponsePacket) throws Exception {

        String ph = getIndexICOfPhResponsePacket.getPh();
        byte[] icon = getIndexICOfPhResponsePacket.getIcon();
        String path = MyFileTools.saveUserIcon(ph,icon);
        if(path==null){
            return;
        }


        if(StaticAllListOperator.isUserTempsContainPh(ph)){
            StaticAllList.usertemps.get(ph).setIcon(path);
        }

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.PERSONALHOMEPAGE");
        intent2.putExtra("type","getIndexIcInfoByPhRs");
        intent2.putExtra("ph",ph);
        NettyService.nettyService.sendCast(intent2);

        Intent intent3=new Intent("thefirstchange.example.com.communicationtext.FRAGMENT_TWO");
        intent3.putExtra("type","getIndexIcInfoByPhRs");
        intent3.putExtra("ph",ph);
        NettyService.nettyService.sendCast(intent3);
    }
}
