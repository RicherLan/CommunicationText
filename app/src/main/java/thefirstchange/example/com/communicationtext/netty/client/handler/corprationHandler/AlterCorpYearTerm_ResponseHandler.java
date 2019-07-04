package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.AlterCorpYearTermResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    社团组织群管理修改学年学期
 */
public class AlterCorpYearTerm_ResponseHandler extends SimpleChannelInboundHandler<AlterCorpYearTermResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AlterCorpYearTermResponsePacket alterCorpYearTermResponsePacket) throws Exception {

        String rs = alterCorpYearTermResponsePacket.getResult();
        int gid = alterCorpYearTermResponsePacket.getGroupid();
        Intent intent2 = new Intent("thefirstchange.example.com.communicationtext.ALTERCORPTERM");
        intent2.putExtra("type", "alterCorpTermRs");
        intent2.putExtra("rs", rs);
        intent2.putExtra("gid", gid);
        if(rs.equals("ok")){
            int year = alterCorpYearTermResponsePacket.getYear();
            int xueqi = alterCorpYearTermResponsePacket.getXueqi();
            int zhou = alterCorpYearTermResponsePacket.getZhou();

            if(StaticAllList.groupList.containsKey(gid)){
                StaticAllList.groupList.get(gid).setYear(year);
                StaticAllList.groupList.get(gid).setXueqi(xueqi);
                StaticAllList.groupList.get(gid).setZhou(zhou);
            }

        }
        NettyService.nettyService.sendCast(intent2);
    }
}
