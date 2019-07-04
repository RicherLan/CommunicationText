package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import android.content.Intent;

import java.util.HashMap;
import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SendAllUsersDutyNotiNO_PACKET;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    服务器发来值班通知
    把值班结果的编号发送给该社团的所有人
    值班 更新    通知
 */
public class SendAllUsersDutyNotiNO_PacketHandler extends SimpleChannelInboundHandler<SendAllUsersDutyNotiNO_PACKET> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendAllUsersDutyNotiNO_PACKET sendAllUsersDutyNotiNO_packet) throws Exception {

        int groupid = sendAllUsersDutyNotiNO_packet.getGroupid();
        int DNID = sendAllUsersDutyNotiNO_packet.getDnid();
        long time = sendAllUsersDutyNotiNO_packet.getTime();
        String cropname = sendAllUsersDutyNotiNO_packet.getCorpname();
        String myduty = sendAllUsersDutyNotiNO_packet.getMyduty();      //yes  no 代表我是否被安排值班

        ChatMsg requestMsg = new ChatMsg();
        requestMsg.setMsgid(DNID);
//                requestMsg.setSenderid(phonenumber);

        requestMsg.setSendername(cropname);
        requestMsg.setMsgtime(time);
        requestMsg.setType("schDuty");
//                requestMsg.setMsgbody(addmsg);
        requestMsg.setGroupid(groupid);
//                requestMsg.setSendersex(sex);
//                requestMsg.setSendericon(icon);
        requestMsg.setHandleRs("new");
        requestMsg.setMsgbody(myduty);


        //将已读的该社团的排班变为old
        if(MyMessageQueue.dutyQueueHadRead!=null){

            if(MyMessageQueue.dutyQueueHadRead.containsKey(groupid)){

                Vector<Integer> deleteindex = new Vector<>();
                for(int i=0;i<MyMessageQueue.dutyQueueHadRead.get(groupid).size();++i){
                    if(MyMessageQueue.dutyQueueHadRead.get(groupid).get(i).getMsgtime()==time){
                        deleteindex.add(i);
                    }
                }
                for(int index: deleteindex){
                    MyMessageQueue.dutyQueueHadRead.get(groupid).removeElementAt(index);
                }

                for(int i=0;i<MyMessageQueue.dutyQueueHadRead.get(groupid).size();++i){
//                        if(MyMessageQueue.dutyQueueHadRead.get(groupid).get(i).getHandleRs().equals("new")){
                    MyMessageQueue.dutyQueueHadRead.get(groupid).get(i).setHandleRs("old");
//                        }
                }
            }
        }

        //将未读的该社团的排班变为old
        if(MyMessageQueue.dutyQueueNotRead!=null){
            if(MyMessageQueue.dutyQueueNotRead.containsKey(groupid)){

                if(MyMessageQueue.dutyQueueHadRead==null){
                    MyMessageQueue.dutyQueueHadRead = new HashMap<>();
                }
                if(!MyMessageQueue.dutyQueueHadRead.containsKey(groupid)){
                    MyMessageQueue.dutyQueueHadRead.put(groupid,new Vector<ChatMsg>());
                }

                Vector<Integer> deleteindex = new Vector<>();
                for(int i=0;i<MyMessageQueue.dutyQueueNotRead.get(groupid).size();++i){
                    if(MyMessageQueue.dutyQueueNotRead.get(groupid).get(i).getMsgtime()==time){
                        deleteindex.add(i);
                    }
                }
                for(int index: deleteindex){
                    MyMessageQueue.dutyQueueNotRead.get(groupid).removeElementAt(index);
                }

                for(int i=0;i<MyMessageQueue.dutyQueueHadRead.get(groupid).size();++i){
                    //String a = MyMessageQueue.dutyQueueHadRead.get(groupid).get(i).handleRs;
//                        if(MyMessageQueue.dutyQueueHadRead.get(groupid).get(i).getHandleRs().equals("new")){
                    MyMessageQueue.dutyQueueHadRead.get(groupid).get(i).setHandleRs("old");
//                        }
                }
                for(int i=0;i<MyMessageQueue.dutyQueueNotRead.get(groupid).size();++i){
                    ChatMsg chatMsg = MyMessageQueue.dutyQueueNotRead.get(groupid).get(i);
                    chatMsg.setHandleRs("old");
                    MyMessageQueue.dutyQueueHadRead.get(groupid).add(chatMsg);
                }
                MyMessageQueue.dutyQueueNotRead.remove(groupid);
            }
        }



        MyMessageQueue.dutyQueueNotRead.put(groupid,new Vector<ChatMsg>());
        MyMessageQueue.dutyQueueNotRead.get(groupid).add(requestMsg);


        //保存窗体
        if(!MyMessageQueue.dutyframes.contains(groupid)){
            MyMessageQueue.dutyframes.add(groupid);
        }
        //保存到数据库

        if(StaticAllList.chatRecordDao!=null){
            StaticAllList.chatRecordDao.saveNoticeFrame("schDuty","",groupid);
            StaticAllList.chatRecordDao.saveSchDuty(requestMsg);
        }else{
            MainActivity.initChatRecorddb();
            StaticAllList.chatRecordDao.saveNoticeFrame("schDuty","",groupid);
            StaticAllList.chatRecordDao.saveSchDuty(requestMsg);
        }

        Intent intent1=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
        intent1.putExtra("type","dutynoti");
        NettyService.nettyService.sendCast(intent1);

    }
}
