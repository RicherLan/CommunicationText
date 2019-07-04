package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SendExitGroupToAdmi_PACKET;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;

/*
  退群信息   发给管理员
 */
public class SendExitGroupToAdmi_PacketHandler extends SimpleChannelInboundHandler<SendExitGroupToAdmi_PACKET> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendExitGroupToAdmi_PACKET sendExitGroupToAdmi_packet) throws Exception {

        int msgid = sendExitGroupToAdmi_packet.getMsgid();
        int groupid = sendExitGroupToAdmi_packet.getGroupid();
        String groupname = sendExitGroupToAdmi_packet.getGroupname();
        String phonenumber = sendExitGroupToAdmi_packet.getPh();
        String groupnickname = sendExitGroupToAdmi_packet.getGroupnickname(); //改用户的群昵称
        long time = sendExitGroupToAdmi_packet.getTime();
        String message = "";

        ChatMsg requestMsg = new ChatMsg();
        requestMsg.setMsgid(msgid);
        requestMsg.setSenderid(phonenumber);
        requestMsg.setSendername(groupnickname);
        requestMsg.setMsgtime(time);
        requestMsg.setType("exitgroup");
        requestMsg.setMsgbody(message);
        requestMsg.setGroupid(groupid);
        requestMsg.setSendersex("");
        requestMsg.setSendericon("");
        requestMsg.setMsgbody(groupname);

        Vector<ChatMsg> msgs = new Vector<>();
        for(int i = 0; i< MyMessageQueue.requestQueueNotHandle.size(); ++i){
            ChatMsg msg = MyMessageQueue.requestQueueNotHandle.get(i);
            if(msg.getSenderid().equals(requestMsg.getSenderid())&&msg.getType().equals(requestMsg.getType())){
                msgs.add(msg);
            }
        }
        for(int i=0;i<msgs.size();++i){
            ChatMsg index = msgs.get(i);
            MyMessageQueue.requestQueueNotHandle.removeElement(index);
        }

        MyMessageQueue.requestQueueNotHandle.add(requestMsg);

        if(StaticAllList.chatRecordDao!=null){
            StaticAllList.chatRecordDao.saveRequest(requestMsg);
        }else{
            MainActivity.initChatRecorddb();
            StaticAllList.chatRecordDao.saveRequest(requestMsg);
        }
    }
}
