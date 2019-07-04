package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.corpResponse.AddCorApPartResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    添加社团组织的某一个部门
 */
public class AddCorApPart_ResponseHandler extends SimpleChannelInboundHandler<AddCorApPartResponsePacket>{
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AddCorApPartResponsePacket addCorApPartResponsePacket) throws Exception {

        String rs = addCorApPartResponsePacket.getResult();      //结果
        int gid = addCorApPartResponsePacket.getGrouid();
        String partname = addCorApPartResponsePacket.getName();

        if(StaticAllList.groupList.containsKey(gid)){
            String partstring="";
            if(StaticAllList.groupList.get(gid).getCorppart()!=null){
                for(String part:StaticAllList.groupList.get(gid).getCorppart()){
                    partstring+=part+" ";
                }
            }

            partstring+=partname;
            partstring=partstring.trim();
            StaticAllList.groupList.get(gid).setCorppart(partstring.split(" "));
        }

        Intent intent2 = new Intent("thefirstchange.example.com.communicationtext.ADDALTERCORPPART");
        intent2.putExtra("type", "addCorpPartRs");
        intent2.putExtra("rs", rs);
        intent2.putExtra("gid", gid);
        NettyService.nettyService.sendCast(intent2);

    }
}
