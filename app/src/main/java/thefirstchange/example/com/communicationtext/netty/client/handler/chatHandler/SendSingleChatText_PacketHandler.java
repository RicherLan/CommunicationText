package thefirstchange.example.com.communicationtext.netty.client.handler.chatHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SingleChatText_PACKET;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.MyTools;

/*
    单人聊天 发送消息   字符串
 */
public class SendSingleChatText_PacketHandler extends SimpleChannelInboundHandler<SingleChatText_PACKET> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SingleChatText_PACKET packet) throws Exception {
        //接收到群聊的   文本消息   消息体     读完后要回执服务器我已经读完  加入消息队列
        //当消息队列中该群的消息很多时  只需要回复最大的消息id即可
        // String json = "{\"msgid\":\""+id+"\",\"senderid\":\""+senderid+"\",\"message\":\""+message+"\",\"msgtype\":\""+msgtype+"\",\"msgtime\":\""+msgtime+"\"}";

        int msgid = packet.getMsgid();
        String senderid = packet.getSenderid();
        // String sendergroupnickname = jsonObject.getString("sendergroupnickname");
        String message =packet.getMessage();
        String msgtype = packet.getMsgtype();
        long msgtime = packet.getMsgtime();

        //好友的备注   服务器不发送   手机上有好友列表   从里面查询就行
        String sendergroupnickname = "";
        if(StaticAllList.friendList.containsKey(senderid)){
            sendergroupnickname = StaticAllList.friendList.get(senderid).getNickname();
        }

        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setMsgid(msgid);
        chatMsg.setType("single");
        chatMsg.setSenderid(senderid);
        chatMsg.setSendername(sendergroupnickname);
        chatMsg.setReceiverid(ObjectService.personalInfo.getPhonenumber());
        chatMsg.setReceivername(ObjectService.personalInfo.getNickname());
        chatMsg.setMsgtype("text");
        chatMsg.setMsgbody(message);
        chatMsg.setMsgtime(msgtime);

        String iconpath = MyTools.getIconPath(senderid);
        if(iconpath!=null){
            chatMsg.setSendericon(iconpath);
        }

        //如果已经存在   那么就说明   已经保存到消息队列和数据库中了   直接return
        if(MyMessageQueue.chatQueueNotRead.containsKey(senderid)){
            for(int i=0;i<MyMessageQueue.chatQueueNotRead.get(senderid).size();++i){
                if(chatMsg.getMsgid()==MyMessageQueue.chatQueueNotRead.get(senderid).get(i).getMsgid()){
                    return;
                }
            }
        }

        //如果已经存在   那么就说明   已经保存到消息队列和数据库中了   直接return
        if(MyMessageQueue.chatQueueHadRead.containsKey(senderid)){
            for(int i=0;i<MyMessageQueue.chatQueueHadRead.get(senderid).size();++i){
                if(chatMsg.getMsgid()==MyMessageQueue.chatQueueHadRead.get(senderid).get(i).getMsgid()){
                    return;
                }
            }
        }



        if(StaticAllList.chatRecordDao!=null){
            int id = StaticAllList.chatRecordDao.saveSingle(chatMsg);
            chatMsg.setId(id);
        }else{
            MainActivity.initChatRecorddb();
            int id = StaticAllList.chatRecordDao.saveSingle(chatMsg);
            chatMsg.setId(id);
        }

        boolean firstNotice = true;
        if( MyMessageQueue.chatframes.containsKey(senderid)){
            firstNotice = false;
        }else{
            //加入到聊天框队列
            MyMessageQueue.chatframes.put(senderid,"single");
        }
        if(MyMessageQueue.chatQueueNotRead.containsKey(senderid)){
            MyMessageQueue.chatQueueNotRead.get(senderid).add(chatMsg);

        }else{
            MyMessageQueue.chatQueueNotRead.put(senderid,new Vector<ChatMsg>());
            MyMessageQueue.chatQueueNotRead.get(senderid).add(chatMsg);
        }
        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.SINGLE_MSG_NO");
        //intent.addFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
        intent2.putExtra("type","recivesinglechattext");
        NettyService.nettyService.sendCast(intent2);
        Intent intent1=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
        intent1.putExtra("type","recivesinglechattext");
        NettyService.nettyService.sendCast(intent1);

        if(firstNotice){
            if(StaticAllList.chatRecordDao!=null){
                StaticAllList.chatRecordDao.saveNoticeFrame("single",senderid,-1);
            }else{
                MainActivity.initChatRecorddb();
                StaticAllList.chatRecordDao.saveNoticeFrame("single",senderid,-1);
            }
        }
    }
}
