package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.UpGroupRemarkResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    修改自己群名片成功还是失败
 */
public class UpGroupRemarkt_ResponseHandler extends SimpleChannelInboundHandler<UpGroupRemarkResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, UpGroupRemarkResponsePacket upGroupRemarkResponsePacket) throws Exception {

        String rs = upGroupRemarkResponsePacket.getResult();      //结果
        if(rs.equals("ok")){
            int groupid = upGroupRemarkResponsePacket.getGroupid();
            String remark = upGroupRemarkResponsePacket.getRemark();
            StaticAllList.groupList.get(groupid).setGroupnickname(remark);

        }

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.ALTERGROUPNAME");
        intent.putExtra("rs",rs);
        NettyService.nettyService.sendCast(intent);
    }

}
