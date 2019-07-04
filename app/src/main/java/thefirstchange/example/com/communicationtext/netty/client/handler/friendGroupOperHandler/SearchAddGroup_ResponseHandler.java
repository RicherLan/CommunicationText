package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.GroupBasicInfo;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.SearchAddGroupResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.service.ObjectService;

/*
客户端添加群时   搜索账号 只需要看到其账号 网名 头像就行
 */
public class SearchAddGroup_ResponseHandler extends SimpleChannelInboundHandler<SearchAddGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SearchAddGroupResponsePacket searchAddGroupResponsePacket) throws Exception {

        GroupBasicInfo groupBasicInfo = searchAddGroupResponsePacket.getGroupBasicInfo();
        String rs = searchAddGroupResponsePacket.getResult();

        if(groupBasicInfo!=null){
            int groupid = groupBasicInfo.getGroupid();
            if(rs.equals("ok")){
                ObjectService.groupBasicInfo = groupBasicInfo;

                Intent intent=new Intent("thefirstchange.example.com.communicationtext.ADD_GROUP");
                intent.putExtra("type","searchGInfoRs");
                intent.putExtra("rs",rs);
                intent.putExtra("groupid",groupid);
                NettyService.nettyService.sendCast(intent);

                //获得头像
                SendToServer.getGroupICOfSearchAddGroup(groupid);
            }else if(rs.equals("notexist")){
                Intent intent=new Intent("thefirstchange.example.com.communicationtext.ADD_GROUP");
                intent.putExtra("type","searchGInfoRs");
                intent.putExtra("groupid",groupid);
                intent.putExtra("rs",rs);
                NettyService.nettyService.sendCast(intent);
            }
        }

    }
}
