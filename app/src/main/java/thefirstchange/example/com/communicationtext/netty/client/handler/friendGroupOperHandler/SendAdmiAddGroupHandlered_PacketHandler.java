package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SendAdmiAddGroupHandlered_PACKET;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    通知其他管理员 加群请求已经被其他管理员处理了
 */
public class SendAdmiAddGroupHandlered_PacketHandler extends SimpleChannelInboundHandler<SendAdmiAddGroupHandlered_PACKET>{
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendAdmiAddGroupHandlered_PACKET sendAdmiAddGroupHandlered_packet) throws Exception {

        int groupid = sendAdmiAddGroupHandlered_packet.getGroupid();
        String handlerid= sendAdmiAddGroupHandlered_packet.getHandlerid();
        String handlernickname= sendAdmiAddGroupHandlered_packet.getHandlernickname();
        String otherphonenumber= sendAdmiAddGroupHandlered_packet.getOtherphonenumber();
        String othernickname= sendAdmiAddGroupHandlered_packet.getOthernickname();
        String type= sendAdmiAddGroupHandlered_packet.getType();                      //同意还是拒绝

        ChatMsg chatMsg=null;
        //若某成员重复加群  那么去掉以前的
        Vector<Integer> msgs = new Vector<>();

        Vector<ChatMsg> chatMsgs = new Vector<>();
        for(int i = 0; i< MyMessageQueue.requestQueueNotHandle.size(); ++i){
            ChatMsg msg = MyMessageQueue.requestQueueNotHandle.get(i);
            if(msg.getType().equals("addgroup")&&msg.getSenderid().equals(otherphonenumber)&&msg.getGroupid()==groupid){
             //   msgs.add(i);
                msg.setHandleRs(type);
                msg.setType("someoneHasHandledAddGroup");
                msg.setReceiverid(handlerid);
                msg.setReceivername(handlernickname);
                chatMsg=msg;
                chatMsgs.add(msg);
            }
        }

//        for(int i=0;i<msgs.size();++i){
//            int index = msgs.get(i);
//            MyMessageQueue.requestQueueNotHandle.remove(index);
//        }
//
//        for(ChatMsg chatMsg1 : chatMsgs){
//            MyMessageQueue.requestQueueHadHandled.add(chatMsg1);
//        }

        Intent intent1=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
        intent1.putExtra("type","someoneHasHandledAddGroup");
        NettyService.nettyService.sendCast(intent1);

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.OTHERPEOPLEAPPLICATION");
        intent2.putExtra("type","someoneHasHandledAddGroup");
        NettyService.nettyService.sendCast(intent2);

        //更新本地群列表  包括更新数据库
        if(type.equals("agree")){
            if(StaticAllList.groupList!=null&&StaticAllList.groupList.containsKey(groupid)){
                StaticAllList.groupList.get(groupid).setUsernum(StaticAllList.groupList.get(groupid).getUsernum()+1);

                //更新数据库
                if(StaticAllList.chatRecordDao!=null){
                    StaticAllList.basicDataDao.saveGroup(StaticAllList.groupList.get(groupid));
                }else{
                    MainActivity.initChatRecorddb();
                    StaticAllList.basicDataDao.saveGroup(StaticAllList.groupList.get(groupid));
                }

            }
        }

        //更新数据库
        if(StaticAllList.chatRecordDao!=null){
            //先删除原来的  在保存
            StaticAllList.chatRecordDao.deleteAddGroupRequest(otherphonenumber,groupid);
            StaticAllList.chatRecordDao.saveRequest(chatMsg);
        }else{
            MainActivity.initChatRecorddb();
            StaticAllList.chatRecordDao.deleteAddGroupRequest(otherphonenumber,groupid);
            StaticAllList.chatRecordDao.saveRequest(chatMsg);
        }

    }
}
