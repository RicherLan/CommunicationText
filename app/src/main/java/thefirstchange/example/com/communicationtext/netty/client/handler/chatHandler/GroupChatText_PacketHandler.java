package thefirstchange.example.com.communicationtext.netty.client.handler.chatHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.GroupChatText_PACKET;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.service.ObjectService;

/*
    群聊发送消息   字符串   信息包
 */
public class GroupChatText_PacketHandler extends SimpleChannelInboundHandler<GroupChatText_PACKET> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatText_PACKET packet) throws Exception {
        //接收到群聊的   文本消息   消息体     读完后要回执服务器我已经读完  加入消息队列
        //当消息队列中该群的消息很多时  只需要回复最大的消息id即可
        // "{\"msgid\":\""+id2+"\",\"senderid\":\""+senderid+"\",\"groupid\":\""+groupid+"\",\"message\":\""+message+"\",\"msgtype\":\""+msgtype+"\",\"msgtime\":\""+msgtime+"\"}";



        int msgid = packet.getMsgid();
        String senderid = packet.getSenderid();
        String sendergroupnickname = packet.getSendergroupname();
        int groupid = packet.getGroupid();
        String message = packet.getMessage();
        String msgtype = packet.getMsgtype();
        long msgtime = packet.getMsgtime();


        ChatMsg chatMsg = new ChatMsg();

        chatMsg.setMsgid(msgid);
        chatMsg.setType("group");
        chatMsg.setGroupid(groupid);
        chatMsg.setSenderid(senderid);
        chatMsg.setSendername(sendergroupnickname);
        chatMsg.setReceiverid(ObjectService.personalInfo.getPhonenumber());
        chatMsg.setReceivername(ObjectService.personalInfo.getNickname());
        chatMsg.setMsgtype("text");
        chatMsg.setMsgbody(message);
        chatMsg.setMsgtime(msgtime);

        //说明  该消息已经在 本机保存了    是因为之前来了消息自己一直没有读取
        if(MyMessageQueue.chatQueueNotRead.containsKey(groupid+"")){
            for(int i=0;i<MyMessageQueue.chatQueueNotRead.get(groupid+"").size();++i){
                if(chatMsg.getMsgid()==MyMessageQueue.chatQueueNotRead.get(groupid+"").get(i).getMsgid()){
                    return;
                }
            }
        }

        //说明  该消息已经在 本机保存了    是因为之前来了消息自己一直没有读取
        if(MyMessageQueue.chatQueueHadRead.containsKey(groupid+"")){
            for(int i=0;i<MyMessageQueue.chatQueueHadRead.get(groupid+"").size();++i){
                if(chatMsg.getMsgid()==MyMessageQueue.chatQueueHadRead.get(groupid+"").get(i).getMsgid()){
                    return;
                }
            }
        }


        if(StaticAllList.chatRecordDao!=null){
            int id = StaticAllList.chatRecordDao.saveGroup(chatMsg);
            chatMsg.setId(id);
        }else{
            MainActivity.initChatRecorddb();
            int id = StaticAllList.chatRecordDao.saveGroup(chatMsg);
            chatMsg.setId(id);
        }

        boolean firstNotice = true;
        if( MyMessageQueue.chatframes.containsKey(groupid+"")){
            firstNotice = false;
        }else{
            //加入到聊天框队列
            MyMessageQueue.chatframes.put(groupid+"","group");
        }

        if(MyMessageQueue.chatQueueNotRead.containsKey(groupid+"")){
            MyMessageQueue.chatQueueNotRead.get(groupid+"").add(chatMsg);

        }else{
            MyMessageQueue.chatQueueNotRead.put(groupid+"",new Vector<ChatMsg>());
            MyMessageQueue.chatQueueNotRead.get(groupid+"").add(chatMsg);
        }

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.GROUP_MSG");
        //intent.addFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
        intent2.putExtra("type","recivegroupchattext");
        NettyService.nettyService.sendCast(intent2);

        Intent intent1=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
        intent1.putExtra("type","recivegroupchattext");
        NettyService.nettyService.sendCast(intent1);



        if(firstNotice){
            if(StaticAllList.chatRecordDao!=null){
                StaticAllList.chatRecordDao.saveNoticeFrame("group","",groupid);
            }else{
                MainActivity.initChatRecorddb();
                StaticAllList.chatRecordDao.saveNoticeFrame("group","",groupid);
            }
        }
    }
}
