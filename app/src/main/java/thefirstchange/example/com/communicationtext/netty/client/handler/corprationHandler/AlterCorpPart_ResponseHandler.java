package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.corpResponse.AlterCorpPartResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    修改社团组织的某一个部门的名字
 */
public class AlterCorpPart_ResponseHandler extends SimpleChannelInboundHandler<AlterCorpPartResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AlterCorpPartResponsePacket alterCorpPartResponsePacket) throws Exception {


        String rs = alterCorpPartResponsePacket.getResult();      //结果
        int gid = alterCorpPartResponsePacket.getGrouid();
        String oldname = alterCorpPartResponsePacket.getOldname();
        String newname = alterCorpPartResponsePacket.getNewname();

        if(StaticAllList.groupList.containsKey(gid)){
            String partstring="";
            for(String part:StaticAllList.groupList.get(gid).getCorppart()){
                if(part.equals(oldname)){
                    partstring+=newname+" ";
                }else{
                    partstring+=part+" ";
                }
            }
            partstring=partstring.trim();
            StaticAllList.groupList.get(gid).setCorppart(partstring.split(" "));
        }

        Intent intent2 = new Intent("thefirstchange.example.com.communicationtext.ADDALTERCORPPART");
        intent2.putExtra("type", "alterCorpPartRs");
        intent2.putExtra("rs", rs);
        intent2.putExtra("gid", gid);
        NettyService.nettyService.sendCast(intent2);
    }
}
