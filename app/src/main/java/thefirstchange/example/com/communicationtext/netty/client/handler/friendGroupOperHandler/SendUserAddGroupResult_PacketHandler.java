package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SendUserAddGroupResult_PACKET;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
加群的结果   被同意了还是不同意
管理员做出同意或不同意 发给用户
 */
public class SendUserAddGroupResult_PacketHandler extends SimpleChannelInboundHandler<SendUserAddGroupResult_PACKET> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendUserAddGroupResult_PACKET sendUserAddGroupResult_packet) throws Exception {


        int msgid = sendUserAddGroupResult_packet.getMsgid();
        String type = sendUserAddGroupResult_packet.getType();      //结果 agree  disagree
        final int groupid = sendUserAddGroupResult_packet.getGroupid();
        String handlerid = sendUserAddGroupResult_packet.getHandlerid();  //处理人id
        String handlernickname = sendUserAddGroupResult_packet.getHandlernickname(); //处理人的昵称
        String icon = "";
        long time = sendUserAddGroupResult_packet.getTime();

        ChatMsg requestMsg = new ChatMsg();
        requestMsg.setMsgid(msgid);
        requestMsg.setSenderid(handlerid);
        requestMsg.setSendername(handlernickname);
        requestMsg.setMsgtime(time);
        if(type.equals("agree")){
            requestMsg.setType("agreeAddGroup");

        }else{
            requestMsg.setType("disagreeAddGroup");
        }

        requestMsg.setMsgbody("");
        requestMsg.setGroupid(groupid);
        requestMsg.setSendericon(icon);

        MyMessageQueue.requestQueueNotHandle.add(requestMsg);

        Intent intent1=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
        intent1.putExtra("type","AddGroupResult");
        NettyService.nettyService.sendCast(intent1);

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.OTHERPEOPLEAPPLICATION");
        intent2.putExtra("type","other");
        NettyService.nettyService.sendCast(intent2);

        if(type.equals("agree")){
            SendToServer.addGroupToListByGroupid(groupid);
        }

        if(StaticAllList.chatRecordDao!=null){
            StaticAllList.chatRecordDao.saveRequest(requestMsg);
        }else{
            MainActivity.initChatRecorddb();
            StaticAllList.chatRecordDao.saveRequest(requestMsg);
        }
    }
}
