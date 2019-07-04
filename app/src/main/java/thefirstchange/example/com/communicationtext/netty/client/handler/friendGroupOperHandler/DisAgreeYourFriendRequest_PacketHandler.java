package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.DisAgreeYourFriendRequest_PACKET;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    对方不同意你的好友请求
 */
public class DisAgreeYourFriendRequest_PacketHandler extends SimpleChannelInboundHandler<DisAgreeYourFriendRequest_PACKET> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DisAgreeYourFriendRequest_PACKET disAgreeYourFriendRequest_packet) throws Exception {

        int msgid = disAgreeYourFriendRequest_packet.getMsgid();
        String phonenumber = disAgreeYourFriendRequest_packet.getPhonenumber();
        String icon = "";      //对方的头像
        String sex = disAgreeYourFriendRequest_packet.getSex();
        String nickname = disAgreeYourFriendRequest_packet.getNickname();
        long time = disAgreeYourFriendRequest_packet.getTime();


        ChatMsg requestMsg = new ChatMsg();
        requestMsg.setMsgid(msgid);
        requestMsg.setSenderid(phonenumber);
        requestMsg.setSendername(nickname);
        requestMsg.setMsgtime(time);
        requestMsg.setType("disagreeYourAddFriend");
        requestMsg.setMsgbody("");
        requestMsg.setGroupid(-1);
        requestMsg.setSendersex(sex);
        requestMsg.setSendericon(icon);

        MyMessageQueue.requestQueueNotHandle.add(requestMsg);

        Intent intent1=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
        intent1.putExtra("type","disagreeYourAddFriend");
        NettyService.nettyService.sendCast(intent1);

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.OTHERPEOPLEAPPLICATION");
        intent2.putExtra("type","other");
        NettyService.nettyService.sendCast(intent2);

        if(StaticAllList.chatRecordDao!=null){
            StaticAllList.chatRecordDao.saveRequest(requestMsg);
        }else{
            MainActivity.initChatRecorddb();
            StaticAllList.chatRecordDao.saveRequest(requestMsg);
        }
    }
}
