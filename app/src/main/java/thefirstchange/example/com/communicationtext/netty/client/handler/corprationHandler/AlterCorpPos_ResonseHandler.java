package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.corpResponse.AlterCorpPosResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    修改自己的部门职位的结果
 */
public class AlterCorpPos_ResonseHandler extends SimpleChannelInboundHandler<AlterCorpPosResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AlterCorpPosResponsePacket alterCorpPosResponsePacket) throws Exception {

        String rs = alterCorpPosResponsePacket.getResult();      //结果
        if(rs.equals("ok")){
            int groupid = alterCorpPosResponsePacket.getGroupid();
            String pos = alterCorpPosResponsePacket.getPos();
            StaticAllList.groupList.get(groupid).setCorppos(pos);
        }

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.ALTERCORPPOS");
        intent.putExtra("rs",rs);
        NettyService.nettyService.sendCast(intent);
    }
}
