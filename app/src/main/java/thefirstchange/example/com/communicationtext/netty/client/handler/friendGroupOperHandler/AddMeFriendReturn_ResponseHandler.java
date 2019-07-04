package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.AddMeFriendReturnResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    添加好友
    用户添加自己为好友   自己给回复  同意还是不同意  结果
 */
public class AddMeFriendReturn_ResponseHandler extends SimpleChannelInboundHandler<AddMeFriendReturnResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AddMeFriendReturnResponsePacket addMeFriendReturnResponsePacket) throws Exception {

        String otherphonenumber = addMeFriendReturnResponsePacket.getOtherphonenumber();  //自己要添加的对方的账号
        String rs = addMeFriendReturnResponsePacket.getRs();
        int msgid = addMeFriendReturnResponsePacket.getMsgid();
        String operator = addMeFriendReturnResponsePacket.getType();

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.OTHERPEOPLEAPPLICATION");
        intent.putExtra("type","AddFriendReturnResult");
        intent.putExtra("rs",rs);
        intent.putExtra("msgid",msgid);
        intent.putExtra("operator",operator);

        NettyService.nettyService.sendCast(intent);
    }
}
