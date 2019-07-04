package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.GetUserIcOfAddMeAsFriendResponsePacket;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.util.MyFileTools;

/*
    添加好友  被添加方要获得对方的头像
 */
public class GetUserIcOfAddMeAsFriend_ResponseHandler extends SimpleChannelInboundHandler<GetUserIcOfAddMeAsFriendResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetUserIcOfAddMeAsFriendResponsePacket getUserIcOfAddMeAsFriendResponsePacket) throws Exception {

        String ph = getUserIcOfAddMeAsFriendResponsePacket.getPh();
        byte[] icon = getUserIcOfAddMeAsFriendResponsePacket.getIcon();



        String path = MyFileTools.saveUserIcon(ph,icon);

       if(path==null||path.equals("")){
           return;
       }

        ChatMsg msg=null;
        for(int i = 0; i< MyMessageQueue.requestQueueNotHandle.size(); ++i){
            if(MyMessageQueue.requestQueueNotHandle.get(i).getType().equals("addfriend")&&MyMessageQueue.requestQueueNotHandle.get(i).getSenderid().equals(ph)){
                MyMessageQueue.requestQueueNotHandle.get(i).setSendericon(path);
                msg = MyMessageQueue.requestQueueNotHandle.get(i);
            }
        }

        for(int i = 0; i< MyMessageQueue.requestQueueHadHandled.size(); ++i){
            if(MyMessageQueue.requestQueueHadHandled.get(i).getType().equals("addfriend")&&MyMessageQueue.requestQueueHadHandled.get(i).getSenderid().equals(ph)){
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


        if(msg!=null){
            if(StaticAllList.chatRecordDao!=null){
                StaticAllList.chatRecordDao.saveRequest(msg);
            }else{
                MainActivity.initChatRecorddb();
                StaticAllList.chatRecordDao.saveRequest(msg);
            }
        }

    }

}
