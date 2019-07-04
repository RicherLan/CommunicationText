package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.UserGroup;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SendUserDeleteCorpPart_PACKET;

/*
    删除部门 还要通知该部室的成员 暂时只把成员的部室设为空 不进行通知
    社团组织的执行者删除部门   那么该部门中的成员就要将自己的部室设置为空
 */
public class SendUserDeleteCorpPart_PacketHandler extends SimpleChannelInboundHandler<SendUserDeleteCorpPart_PACKET> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendUserDeleteCorpPart_PACKET sendUserDeleteCorpPart_packet) throws Exception {

        int gid = sendUserDeleteCorpPart_packet.getGroupid();
        String partname = sendUserDeleteCorpPart_packet.getName();

        if(StaticAllList.groupList.containsKey(gid)){
            UserGroup userGroup = StaticAllList.groupList.get(gid);
            if(userGroup.getPart().equals(partname)){
                StaticAllList.groupList.get(gid).setPart("");
            }

        }
    }
}
