package thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.User;
import thefirstchange.example.com.communicationtext.netty.protocol.response.personInfoResponse.GetIndexInfoOfPhResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.service.StaticAllListOperator;

/*
    进入某人的个人页面时  获得其基本信息
 */
public class GetIndexInfoOfPh_ResponseHandler extends SimpleChannelInboundHandler<GetIndexInfoOfPhResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetIndexInfoOfPhResponsePacket getIndexInfoOfPhResponsePacket) throws Exception {
        User user = getIndexInfoOfPhResponsePacket.getUser();

        if(user==null){
            return;
        }

        if(StaticAllList.friendList.containsKey(user.getPhonenumber())){
            user.setIcon(StaticAllList.friendList.get(user.getPhonenumber()).getIcon());
        }
        StaticAllListOperator.add2UserTemps(user);

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.PERSONALHOMEPAGE");
        intent2.putExtra("type","getIndexInfoByPhRs");
        intent2.putExtra("ph",user.getPhonenumber());
        NettyService.nettyService.sendCast(intent2);

        Intent intent3=new Intent("thefirstchange.example.com.communicationtext.FRAGMENT_ONE");
        intent3.putExtra("type","getIndexInfoByPhRs");
        intent2.putExtra("ph",user.getPhonenumber());
        NettyService.nettyService.sendCast(intent3);

        //获得其头像
       // SendToServer.getIndexICByPh(user.getPhonenumber());

    }
}
