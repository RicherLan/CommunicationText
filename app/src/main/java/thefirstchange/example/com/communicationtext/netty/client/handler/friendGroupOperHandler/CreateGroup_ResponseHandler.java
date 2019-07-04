package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.CreateGroupResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    创建群的结果
 */
public class CreateGroup_ResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreateGroupResponsePacket createGroupResponsePacket) throws Exception {

        String rs = createGroupResponsePacket.getResult();      //结果
        int groupid = -1;
        if(rs.equals("ok")){
            groupid = createGroupResponsePacket.getGroupid();

            Intent intent=new Intent("thefirstchange.example.com.communicationtext.CREATEGROUP");
            intent.putExtra("rs",rs);
            intent.putExtra("groupid",groupid);
            NettyService.nettyService.sendCast(intent);

            // 创建群时  还要把群资料加入到自己的群列表   请求服务器拿到该群的资料
            SendToServer.addGroupToListByGroupid(groupid);

        }else{
            Intent intent=new Intent("thefirstchange.example.com.communicationtext.CREATEGROUP");
            intent.putExtra("rs",rs);
            intent.putExtra("groupid",groupid);
            NettyService.nettyService.sendCast(intent);
        }

    }
}
