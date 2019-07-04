package thefirstchange.example.com.communicationtext.netty.client.handler.notificationHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.notificationResponse.GetNotiIconOfUserResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.util.MyFileTools;

/*
    获得某用户头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
 */
public class GetNotiIconOfUserResponseHandler  extends SimpleChannelInboundHandler<GetNotiIconOfUserResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetNotiIconOfUserResponsePacket getNotiIconOfUserResponsePacket) throws Exception {
        String ph = getNotiIconOfUserResponsePacket.getPh();
        byte[] icon = getNotiIconOfUserResponsePacket.getIcon();

        String path = MyFileTools.saveUserIcon(ph,icon);
        if(path==null||path.equals("")){
            return;
        }

        Intent intent1=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
        intent1.putExtra("type","GetNotiIconOfUser");
        NettyService.nettyService.sendCast(intent1);

    }
}
