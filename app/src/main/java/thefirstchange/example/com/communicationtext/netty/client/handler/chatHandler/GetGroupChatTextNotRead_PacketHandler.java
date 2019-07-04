package thefirstchange.example.com.communicationtext.netty.client.handler.chatHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.GetGroupChatTextNotRead_PACKET;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
/拿到群给自己发的未读的消息    一上线的时候
发送多条未读的文字消息
 */
public class GetGroupChatTextNotRead_PacketHandler extends SimpleChannelInboundHandler<GetGroupChatTextNotRead_PACKET> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetGroupChatTextNotRead_PACKET getGroupChatTextNotRead_packet) throws Exception {
        Vector<ChatMsg> groupChatMsgs = getGroupChatTextNotRead_packet.getChatMsgs();
        for(int i=0;i<groupChatMsgs.size();++i){

            ChatMsg chatMsg = groupChatMsgs.get(i);
            String ss = chatMsg.getMsgbody();
            int groupid = chatMsg.getGroupid();
            boolean flag = false;
            if(MyMessageQueue.chatQueueNotRead.containsKey(groupid+"")){
                for(int j=0;j<MyMessageQueue.chatQueueNotRead.get(groupid+"").size();++j){
                    if(chatMsg.getMsgid()==MyMessageQueue.chatQueueNotRead.get(groupid+"").get(j).getMsgid()){
                        flag = true;
                        break;
                    }
                }
            }
            if(flag){
                continue;
            }

            if(MyMessageQueue.chatQueueHadRead.containsKey(groupid+"")){
                for(int j=0;j<MyMessageQueue.chatQueueHadRead.get(groupid+"").size();++j){
                    if(chatMsg.getMsgid()==MyMessageQueue.chatQueueHadRead.get(groupid+"").get(j).getMsgid()){
                        flag = true;
                        break;
                    }
                }
            }

            if(flag){
                continue;
            }

            if(StaticAllList.chatRecordDao!=null){
                int id = StaticAllList.chatRecordDao.saveGroup(chatMsg);
                chatMsg.setId(id);
            }else{
                MainActivity.initChatRecorddb();
                int id = StaticAllList.chatRecordDao.saveGroup(chatMsg);
                chatMsg.setId(id);
            }


            if(MyMessageQueue.chatQueueNotRead.containsKey(groupid+"")){
                MyMessageQueue.chatQueueNotRead.get(groupid+"").add(chatMsg);
            }else{
                MyMessageQueue.chatQueueNotRead.put(groupid+"",new Vector<ChatMsg>());
                MyMessageQueue.chatQueueNotRead.get(groupid+"").add(chatMsg);
            }

            boolean firstNotice = true;
            if(MyMessageQueue.chatframes.containsKey(groupid+"")){
                firstNotice = false;
            }else{
                MyMessageQueue.chatframes.put(groupid+"","group");
            }
            if(firstNotice){
                if(StaticAllList.chatRecordDao!=null){
                    StaticAllList.chatRecordDao.saveNoticeFrame("group","",groupid);
                }else{
                    MainActivity.initChatRecorddb();
                    StaticAllList.chatRecordDao.saveNoticeFrame("group","",groupid);
                }
            }


        }
        Intent intent1=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
        intent1.putExtra("type","getGChatMsgNRRs");
        NettyService.nettyService.sendCast(intent1);
    }
}
