package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.AddGroupResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
申请加群   该申请的结果
 */
public class AddGroup_ResponseHandler extends SimpleChannelInboundHandler<AddGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AddGroupResponsePacket addGroupResponsePacket) throws Exception {
        int groupid = addGroupResponsePacket.getGroupid();
        String type = addGroupResponsePacket.getResult();

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.ADD_GROUP");
        intent.putExtra("type","requestAddGroupResult");
        intent.putExtra("groupid",groupid);
        intent.putExtra("rs",type);
        NettyService.nettyService.sendCast(intent);
    }
}
