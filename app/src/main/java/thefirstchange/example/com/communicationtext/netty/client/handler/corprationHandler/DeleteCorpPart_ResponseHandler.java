package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.corpResponse.DeleteCorpPartResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    删除社团组织的某一个部门
 */
public class DeleteCorpPart_ResponseHandler extends SimpleChannelInboundHandler<DeleteCorpPartResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DeleteCorpPartResponsePacket deleteCorpPartResponsePacket) throws Exception {

        String rs = deleteCorpPartResponsePacket.getResult();      //结果
        int gid = deleteCorpPartResponsePacket.getGroupid();
        String partname = deleteCorpPartResponsePacket.getName();

        if(StaticAllList.groupList.containsKey(gid)){
            String partstring="";
            for(String part:StaticAllList.groupList.get(gid).getCorppart()){
                if(!part.equals(partname)){
                    partstring+=part+" ";
                }
            }
            partstring=partstring.trim();
            StaticAllList.groupList.get(gid).setCorppart(partstring.split(" "));
        }

        Intent intent2 = new Intent("thefirstchange.example.com.communicationtext.ADDALTERCORPPART");
        intent2.putExtra("type", "deleteCorpPartRs");
        intent2.putExtra("rs", rs);
        intent2.putExtra("gid", gid);
        NettyService.nettyService.sendCast(intent2);
    }
}
