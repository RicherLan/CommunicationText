package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.AddMeAsFriend_PACKET;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
      添加好友  被添加方收到
 */
public class AddMeAsFriend_PacketHandler extends SimpleChannelInboundHandler<AddMeAsFriend_PACKET> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AddMeAsFriend_PACKET addMeAsFriend_packet) throws Exception {
        int msgid = addMeAsFriend_packet.getMsgid();
        String phonenumber = addMeAsFriend_packet.getPhonenumber();
        String icon = "";      //对方的头像
        String sex = addMeAsFriend_packet.getSex();
        String nickname = addMeAsFriend_packet.getNickname();
        String addmsg = addMeAsFriend_packet.getAddmsg();
        Long time = addMeAsFriend_packet.getTime();

        ChatMsg requestMsg = new ChatMsg();
        requestMsg.setMsgid(msgid);
        requestMsg.setSenderid(phonenumber);
        requestMsg.setSendername(nickname);
        requestMsg.setMsgtime(time);
        requestMsg.setType("addfriend");
        requestMsg.setMsgbody(addmsg);
        requestMsg.setGroupid(-1);
        requestMsg.setSendersex(sex);
        requestMsg.setSendericon(icon);

        Vector<ChatMsg> msgs = new Vector<>();
        for(int i = 0; i< MyMessageQueue.requestQueueNotHandle.size(); ++i){
            ChatMsg msg = MyMessageQueue.requestQueueNotHandle.get(i);
            if(msg.getType().equals("addfriend")&&msg.getSenderid().equals(requestMsg.getSenderid())){
                msgs.add(msg);
            }
        }

        for(int i=0;i<msgs.size();++i){
            ChatMsg index = msgs.get(i);
            MyMessageQueue.requestQueueNotHandle.removeElement(index);
        }

        MyMessageQueue.requestQueueNotHandle.add(0,requestMsg);

        Intent intent1=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
        intent1.putExtra("type","AddYouAsFriend");
        NettyService.nettyService.sendCast(intent1);

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.OTHERPEOPLEAPPLICATION");
        intent2.putExtra("type","other");
        NettyService.nettyService.sendCast(intent2);


        if(StaticAllList.chatRecordDao!=null){
            StaticAllList.chatRecordDao.saveNoticeFrame("request","",-1);
            StaticAllList.chatRecordDao.saveRequest(requestMsg);
        }else{
            MainActivity.initChatRecorddb();
            StaticAllList.chatRecordDao.saveNoticeFrame("request","",-1);
            StaticAllList.chatRecordDao.saveRequest(requestMsg);
        }

        //获得头像
        SendToServer.getUserIcOfAddMeAsFriend(phonenumber);

    }
}
