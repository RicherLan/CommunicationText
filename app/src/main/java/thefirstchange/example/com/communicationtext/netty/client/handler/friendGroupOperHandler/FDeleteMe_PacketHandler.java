package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.FDeleteMe_Packet;

/*
好友删除自己时   自己收到被删除的消息   要回执服务器
 */
public class FDeleteMe_PacketHandler extends SimpleChannelInboundHandler<FDeleteMe_Packet> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FDeleteMe_Packet fDeleteMe_packet) throws Exception {
        //自己的一个好友删除了自己   自己这边不用显示  就把他从好友列表删除就行
        String friendphonenumber = fDeleteMe_packet.getPhonenumber();
        int msgid = fDeleteMe_packet.getMsgid();
        StaticAllList.friendList.remove(friendphonenumber);

        //向服务器回执  自己已经收到
        SendToServer.reciveDeleteMe(msgid);

    }
}
