package thefirstchange.example.com.communicationtext.netty.client.handler.notificationHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.notificationResponse.GetNotiIconOfGroupResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.util.MyFileTools;

/*
    获得某群头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
 */
public class GetNotiIconOfGroupResponseHandler extends SimpleChannelInboundHandler<GetNotiIconOfGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetNotiIconOfGroupResponsePacket getNotiIconOfGroupResponsePacket) throws Exception {

        int groupid = getNotiIconOfGroupResponsePacket.getGroupid();
        byte[] icon = getNotiIconOfGroupResponsePacket.getIcon();

        String path = MyFileTools.saveGroupIcon(groupid,icon);

        for(int gid: StaticAllList.groupList.keySet()){
            if(gid==groupid){
                StaticAllList.groupList.get(gid).setGroupicon(path);
            }
        }

        if(path!=null&&!path.equals("")){
            //通知消息通知界面   更新头像
            Intent intent = new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
            intent.putExtra("type","GetNotiIconOfGroup");
            NettyService.nettyService.sendCast(intent);
        }

    }
}
