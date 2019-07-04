package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.UserGroup;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.AddGroupToGroupListResponsePacket;

/*
创建群时  还要把群资料加入到自己的群列表   请求服务器拿到该群的资料
    加群  或  创建群成功后    把该群的信息加入到自己的群列表中
 */
public class AddGroupToGroupList_ResponseHandler extends SimpleChannelInboundHandler<AddGroupToGroupListResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AddGroupToGroupListResponsePacket addGroupToGroupListResponsePacket) throws Exception {
        UserGroup userGroup = addGroupToGroupListResponsePacket.getUserGroup();
        if(userGroup==null){
            return;
        }
        int id = userGroup.getGroupid();
        StaticAllList.groupList.remove(id);
        StaticAllList.groupList.put(userGroup.getGroupid(),userGroup);

        //通知群列表刷新
//      Intent intent=new Intent("thefirstchange.example.com.communicationtext.CHOOSEGROUP");
//      sendCast(intent);
    }
}
