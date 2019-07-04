package thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.User;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetFriendInfoByIDResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.service.StaticAllListOperator;

/*
获得某用户的user表中的基本信息
 */
public class GetFriendInfoByID_ResponseHandler extends SimpleChannelInboundHandler<GetFriendInfoByIDResponsePacket>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GetFriendInfoByIDResponsePacket responsePacket) throws Exception {
        User user =responsePacket.getUser();

        if(StaticAllList.friendList.containsKey(user.getPhonenumber())){
            user.setIcon(StaticAllList.friendList.get(user.getPhonenumber()).getIcon());
        }
        StaticAllListOperator.add2UserTemps(user);

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.PERSONALHOMEPAGE");
        intent2.putExtra("type","getFrInByPhRs");
        NettyService.nettyService.sendCast(intent2);

        Intent intent3=new Intent("thefirstchange.example.com.communicationtext.FRAGMENT_ONE");
        intent3.putExtra("type","getFrInByPhRs");
        NettyService.nettyService.sendCast(intent3);
    }
}
