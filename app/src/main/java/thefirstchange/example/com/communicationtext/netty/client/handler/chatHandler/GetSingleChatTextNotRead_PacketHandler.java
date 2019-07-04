package thefirstchange.example.com.communicationtext.netty.client.handler.chatHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.GetSingleChatTextNotRead_PACKET;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.util.MyTools;

/*
    拿到别人给自己发的自己未读的消息    一上线的时候
    发送多条未读的文字消息
 */
public class GetSingleChatTextNotRead_PacketHandler extends SimpleChannelInboundHandler<GetSingleChatTextNotRead_PACKET> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetSingleChatTextNotRead_PACKET getSingleChatTextNotRead_packet) throws Exception {
        Vector<ChatMsg> singleChatMsgs = getSingleChatTextNotRead_packet.getChatMsgs();
        for(int i=0;i<singleChatMsgs.size();++i){

            ChatMsg chatMsg = singleChatMsgs.get(i);
            String senderid = chatMsg.getSenderid();
            //如果已经存在   那么就说明   已经保存到消息队列和数据库中了
            boolean flag = false;
            if(MyMessageQueue.chatQueueNotRead.containsKey(senderid)){
                for(int j=0;j<MyMessageQueue.chatQueueNotRead.get(senderid).size();++j){
                    if(chatMsg.getMsgid()==MyMessageQueue.chatQueueNotRead.get(senderid).get(j).getMsgid()){
                        flag = true;
                        break;
                    }
                }
            }

            if(flag){
                continue;
            }

            if(MyMessageQueue.chatQueueHadRead.containsKey(senderid)){
                for(int j=0;j<MyMessageQueue.chatQueueHadRead.get(senderid).size();++j){
                    if(chatMsg.getMsgid()==MyMessageQueue.chatQueueHadRead.get(senderid).get(j).getMsgid()){
                        flag = true;
                        break;
                    }
                }
            }

            if(flag){
                continue;
            }

            String iconpath = MyTools.getIconPath(senderid);
            if(iconpath!=null){
                chatMsg.setSendericon(iconpath);
            }

            if(StaticAllList.chatRecordDao!=null){
                int id = StaticAllList.chatRecordDao.saveSingle(chatMsg);
                chatMsg.setId(id);
            }else{
                MainActivity.initChatRecorddb();
                int id = StaticAllList.chatRecordDao.saveSingle(chatMsg);
                chatMsg.setId(id);
            }


            if(MyMessageQueue.chatQueueNotRead.containsKey(senderid)){
                MyMessageQueue.chatQueueNotRead.get(senderid).add(chatMsg);
            }else{
                MyMessageQueue.chatQueueNotRead.put(senderid,new Vector<ChatMsg>());
                MyMessageQueue.chatQueueNotRead.get(senderid).add(chatMsg);
            }
            boolean firstNotice = true;
            if( MyMessageQueue.chatframes.containsKey(senderid)){
                firstNotice = false;
            }else{
                MyMessageQueue.chatframes.put(senderid,"single");
            }
            if(firstNotice){
                if(StaticAllList.chatRecordDao!=null){
                    StaticAllList.chatRecordDao.saveNoticeFrame("single",senderid,-1);
                }else{
                    MainActivity.initChatRecorddb();
                    StaticAllList.chatRecordDao.saveNoticeFrame("single",senderid,-1);
                }
            }
        }
        Intent intent1=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
        intent1.putExtra("type","getSChatMsgNRRs");
        NettyService.nettyService.sendCast(intent1);
    }
}
