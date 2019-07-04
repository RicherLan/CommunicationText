package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.AddFriendResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
添加好友   服务器回执结果   这个请求失败或成功
 */
public class AddFriend_ResponseHandler extends SimpleChannelInboundHandler<AddFriendResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AddFriendResponsePacket addFriendResponsePacket) throws Exception {
        String friendphonenumber = addFriendResponsePacket.getFriendPh();  //自己要添加的对方的账号
        String type = addFriendResponsePacket.getResult();

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.ADD_FRIEND");
        intent.putExtra("type","requestAddFriendResult");
        intent.putExtra("friendphonenumber","friendphonenumber");
        intent.putExtra("rs",type);
        NettyService.nettyService.sendCast(intent);
    }
}
