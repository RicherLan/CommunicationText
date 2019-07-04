package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.GetUserIcOfAddGroupResponsePacket;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.util.MyFileTools;

/*
    添加群  群管理员要获得对方的头像
 */
public class GetUserIcOfAddGroup_ResponseHandler extends SimpleChannelInboundHandler<GetUserIcOfAddGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetUserIcOfAddGroupResponsePacket getUserIcOfAddGroupResponsePacket) throws Exception {

        int groupid = getUserIcOfAddGroupResponsePacket.getGroupid();
        String ph = getUserIcOfAddGroupResponsePacket.getPh();
        byte[] icon = getUserIcOfAddGroupResponsePacket.getIcon();

        String path = MyFileTools.saveUserIcon(ph,icon);

        if(path==null||path.equals("")){
            return;
        }

        ChatMsg msg=null;
        for(int i = 0; i< MyMessageQueue.requestQueueNotHandle.size(); ++i){
            if(MyMessageQueue.requestQueueNotHandle.get(i).getType().equals("addgroup")&&MyMessageQueue.requestQueueNotHandle.get(i).getSenderid().equals(ph)){
                MyMessageQueue.requestQueueNotHandle.get(i).setSendericon(path);
                msg = MyMessageQueue.requestQueueNotHandle.get(i);
            }
        }

        for(int i = 0; i< MyMessageQueue.requestQueueHadHandled.size(); ++i){
            if(MyMessageQueue.requestQueueHadHandled.get(i).getType().equals("addgroup")&&MyMessageQueue.requestQueueHadHandled.get(i).getSenderid().equals(ph)){
                MyMessageQueue.requestQueueHadHandled.get(i).setSendericon(path);
                msg = MyMessageQueue.requestQueueHadHandled.get(i);
            }
        }


        Intent intent1=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
        intent1.putExtra("type","GetNotiIconOfUser");
        NettyService.nettyService.sendCast(intent1);

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.OTHERPEOPLEAPPLICATION");
        intent2.putExtra("type","otherIcon");
        NettyService.nettyService.sendCast(intent2);


    }

}
