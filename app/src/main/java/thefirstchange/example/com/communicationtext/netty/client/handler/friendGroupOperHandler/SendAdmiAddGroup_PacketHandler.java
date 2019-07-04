package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SendAdmiAddGroup_Packet;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    用户加群时  自己是该群的管理员   那么自己会收到该消息
 */
public class SendAdmiAddGroup_PacketHandler extends SimpleChannelInboundHandler<SendAdmiAddGroup_Packet> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendAdmiAddGroup_Packet sendAdmiAddGroup_packet) throws Exception {

        int msgid = sendAdmiAddGroup_packet.getMsgid();
        int groupid = sendAdmiAddGroup_packet.getGroupid();
        String phonenumber = sendAdmiAddGroup_packet.getPh();
        String icon = "";      //对方的头像
        String sex = sendAdmiAddGroup_packet.getSex();
        String nickname = sendAdmiAddGroup_packet.getNickname();
        long time = sendAdmiAddGroup_packet.getTime();
        String message = sendAdmiAddGroup_packet.getAddmsg();  //加群的验证信息

        ChatMsg requestMsg = new ChatMsg();
        requestMsg.setMsgid(msgid);
        requestMsg.setSenderid(phonenumber);
        requestMsg.setSendername(nickname);
        requestMsg.setMsgtime(time);
        requestMsg.setType("addgroup");
        requestMsg.setMsgbody(message);
        requestMsg.setGroupid(groupid);
        requestMsg.setSendersex(sex);
        requestMsg.setSendericon(icon);

        //若某成员重复加群  那么去掉以前的
        Vector<ChatMsg> msgs = new Vector<>();
        for(int i=0;i<MyMessageQueue.requestQueueNotHandle.size();++i){
            ChatMsg msg = MyMessageQueue.requestQueueNotHandle.get(i);
            if(msg.getType().equals("addgroup")&&msg.getSenderid().equals(requestMsg.getSenderid())&&msg.getGroupid()==groupid){
                msgs.add(msg);
            }
        }
        for(int i=0;i<msgs.size();++i){
            ChatMsg index = msgs.get(i);
            MyMessageQueue.requestQueueNotHandle.removeElement(index);
        }


        MyMessageQueue.requestQueueNotHandle.add(requestMsg);



        Intent intent1=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
        intent1.putExtra("type","uRstAddGroup");
        NettyService.nettyService.sendCast(intent1);

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.OTHERPEOPLEAPPLICATION");
        intent2.putExtra("type","other");
        NettyService.nettyService.sendCast(intent2);


        //保存窗口
        if(StaticAllList.chatRecordDao!=null){
            StaticAllList.chatRecordDao.saveNoticeFrame("request","",-1);
            StaticAllList.chatRecordDao.saveRequest(requestMsg);
        }else{
            MainActivity.initChatRecorddb();
            StaticAllList.chatRecordDao.saveNoticeFrame("request","",-1);
            StaticAllList.chatRecordDao.saveRequest(requestMsg);
        }

        //获得头像
        SendToServer.getUserIcOfAddGroup(groupid,phonenumber);

    }
}
