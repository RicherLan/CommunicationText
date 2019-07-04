package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.AgreeYourFriendRequest_PACKET;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    对方同意你的好友请求
 */
public class AgreeYourFriendRequest_PacketHandler extends SimpleChannelInboundHandler<AgreeYourFriendRequest_PACKET> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AgreeYourFriendRequest_PACKET agreeYourFriendRequest_packet) throws Exception {

        int msgid = agreeYourFriendRequest_packet.getMsgid();
        final String phonenumber = agreeYourFriendRequest_packet.getPhonenumber();
        String icon = "";      //对方的头像
        String sex = agreeYourFriendRequest_packet.getSex();
        String nickname = agreeYourFriendRequest_packet.getNickname();
        long time = agreeYourFriendRequest_packet.getTime();
        ChatMsg requestMsg = new ChatMsg();
        requestMsg.setMsgid(msgid);
        requestMsg.setSenderid(phonenumber);
        requestMsg.setSendername(nickname);
        requestMsg.setMsgtime(time);
        requestMsg.setType("agreeYourAddFriend");
        requestMsg.setMsgbody("同意了你的好友请求");
        requestMsg.setGroupid(-1);
        requestMsg.setSendersex(sex);
        requestMsg.setSendericon(icon);

        MyMessageQueue.requestQueueNotHandle.add(requestMsg);

        Intent intent1=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
        intent1.putExtra("type","agreeYourAddFriend");
        NettyService.nettyService.sendCast(intent1);

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.OTHERPEOPLEAPPLICATION");
        intent2.putExtra("type","other");
        NettyService.nettyService.sendCast(intent2);



        SendToServer.addUserToFriendList(phonenumber);



        if(StaticAllList.chatRecordDao!=null){
            StaticAllList.chatRecordDao.saveNoticeFrame("request","",-1);
            StaticAllList.chatRecordDao.saveRequest(requestMsg);
        }else{
            MainActivity.initChatRecorddb();
            StaticAllList.chatRecordDao.saveNoticeFrame("request","",-1);
            StaticAllList.chatRecordDao.saveRequest(requestMsg);
        }

        //获得对方头像
        SendToServer.getUIcOfAgreeYourFriendRequest(phonenumber);


    }
}
