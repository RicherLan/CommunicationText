package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.GetGroupIcByGidResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.util.MyFileTools;

/*
      获得某群头像
 */

public class GetGroupIcByGid_ResponseHandler extends SimpleChannelInboundHandler<GetGroupIcByGidResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetGroupIcByGidResponsePacket getGroupIcByGidResponsePacket) throws Exception {

        int groupid = getGroupIcByGidResponsePacket.getGroupid();
        byte[] icon = getGroupIcByGidResponsePacket.getIcon();

        String path = MyFileTools.saveGroupIcon(groupid,icon);

        for(int gid: StaticAllList.groupList.keySet()){
            if(gid==groupid){
                StaticAllList.groupList.get(gid).setGroupicon(path);
            }
        }

        if(path!=null&&!path.equals("")){
            Intent intent = new Intent("thefirstchange.example.com.communicationtext.GROUPLIST");
            intent.putExtra("type","getGroupiIcRs");
            NettyService.nettyService.sendCast(intent);
        }

    }
}
