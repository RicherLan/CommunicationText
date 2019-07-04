package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.GetGroupICOfSearchAddGroupResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.util.MyFileTools;

/*
    添加群时    首先查询     获得群的头像
 */
public class GetGroupICOfSearchAddGroup_ResponseHandler extends SimpleChannelInboundHandler<GetGroupICOfSearchAddGroupResponsePacket>{
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetGroupICOfSearchAddGroupResponsePacket getGroupICOfSearchAddGroupResponsePacket) throws Exception {

        int groupid = getGroupICOfSearchAddGroupResponsePacket.getGroupid();
        byte[] icon = getGroupICOfSearchAddGroupResponsePacket.getIc();

        //   把图片路径保存进去
        String path = MyFileTools.saveGroupIcon(groupid,icon);

        if(path==null||path.equals("")){
            return;
        }

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.ADD_GROUP");
        intent.putExtra("type","getGroupICOfsearchGroup");
        intent.putExtra("groupid",groupid);
        intent.putExtra("icpath",path);
        NettyService.nettyService.sendCast(intent);

    }
}
