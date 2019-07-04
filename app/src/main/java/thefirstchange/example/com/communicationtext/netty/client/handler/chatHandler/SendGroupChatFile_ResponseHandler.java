package thefirstchange.example.com.communicationtext.netty.client.handler.chatHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.SendGroupChatFileResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    群聊时   发送图片或语音等是否成功
 */
public class SendGroupChatFile_ResponseHandler extends SimpleChannelInboundHandler<SendGroupChatFileResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendGroupChatFileResponsePacket responsePacket) throws Exception {

        String rs = responsePacket.getResult();
        //这个时间是当初客户端发送时  发送给服务器的时间戳  用来找是哪条消息  因为到服务器返回来 可能有多条消息
        long msgtime = responsePacket.getMsgtime();

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.SINGLE_MSG_NO");
        //intent.addFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
        intent2.putExtra("type","sendergroupchatfileResult");
        intent2.putExtra("rs",rs);
        intent2.putExtra("msgtime",msgtime);
        NettyService.nettyService.sendCast(intent2);
    }
}
