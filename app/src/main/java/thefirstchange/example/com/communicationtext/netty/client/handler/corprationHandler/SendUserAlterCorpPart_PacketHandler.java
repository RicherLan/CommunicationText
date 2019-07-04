package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.UserGroup;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SendUserAlterCorpPart_PACKET;

/*
    修改部门 还要通知该部室的成员 不进行通知
    社团组织的执行者修改部门   那么该部门中的成员就要将自己的部室设置
 */
public class SendUserAlterCorpPart_PacketHandler extends SimpleChannelInboundHandler<SendUserAlterCorpPart_PACKET> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendUserAlterCorpPart_PACKET sendUserAlterCorpPart_packet) throws Exception {

        int gid = sendUserAlterCorpPart_packet.getGrouid();
        String oldname = sendUserAlterCorpPart_packet.getOldname();
        String newname = sendUserAlterCorpPart_packet.getNewname();

        if(StaticAllList.groupList.containsKey(gid)){
            UserGroup userGroup = StaticAllList.groupList.get(gid);
            if(userGroup.getPart().equals(oldname)){
                StaticAllList.groupList.get(gid).setPart(newname);
            }
        }
    }
}
