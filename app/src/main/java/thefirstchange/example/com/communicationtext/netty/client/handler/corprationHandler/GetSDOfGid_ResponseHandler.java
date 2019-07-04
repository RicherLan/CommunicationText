package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.CourseAndScore;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.schedulearrangement.ClientArrangement;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetSDOfGidResponsePacket;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    获得自己加入的某个组织的值班表
 */
public class GetSDOfGid_ResponseHandler extends SimpleChannelInboundHandler<GetSDOfGidResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetSDOfGidResponsePacket getSDOfGidResponsePacket) throws Exception {

        final int groupid = getSDOfGidResponsePacket.getGroupid();
        final int dnid = getSDOfGidResponsePacket.getDnid();


        ChatMsg requestMsg = null;
        CourseAndScore.clientArrangementsTemp = getSDOfGidResponsePacket.getClientArrangements();


        if( CourseAndScore.clientArrangementsTemp!=null&& CourseAndScore.clientArrangementsTemp.size()!=0){



            Vector<ClientArrangement> delete = new Vector<>();
            for(int i=0;i<CourseAndScore.clientArrangements.size();++i){
                if(CourseAndScore.clientArrangements.get(i).getGroupid()==groupid){
                    delete.add(CourseAndScore.clientArrangements.get(i));
                }
            }


            for(int i=0;i<delete.size();++i){
                CourseAndScore.clientArrangements.remove(delete.get(i));
            }
            for(int i=0;i< CourseAndScore.clientArrangementsTemp.size();++i){

                CourseAndScore.clientArrangements.add(CourseAndScore.clientArrangementsTemp.get(i));
            }

            if(!MyMessageQueue.dutyQueueHadRead.containsKey(groupid)){
                MyMessageQueue.dutyQueueHadRead.put(groupid,new Vector<ChatMsg>());
            }

            if(MyMessageQueue.dutyQueueNotRead.containsKey(groupid)){
                for(int i=0;i<MyMessageQueue.dutyQueueNotRead.get(groupid).size();++i){
                    if(MyMessageQueue.dutyQueueNotRead.get(groupid).get(i).getMsgid()==dnid){
                        MyMessageQueue.dutyQueueNotRead.get(groupid).get(i).setHandleRs("hasread");
                        requestMsg =  MyMessageQueue.dutyQueueNotRead.get(groupid).get(i);
                    }else{
                        MyMessageQueue.dutyQueueNotRead.get(groupid).get(i).setHandleRs("old");
                    }

                    MyMessageQueue.dutyQueueHadRead.get(groupid).add(MyMessageQueue.dutyQueueNotRead.get(groupid).get(i));
                }
                MyMessageQueue.dutyQueueNotRead.remove(groupid);
            }



            Intent intent = new Intent("thefirstchange.example.com.communicationtext.DUTYNOTIRECYVIEW");
            intent.putExtra("type","DutySche");
            intent.putExtra("rs","ok");
            intent.putExtra("groupid",groupid);
            intent.putExtra("dnid",dnid);
            NettyService.nettyService.sendCast(intent);


            //保存到数据库
            if(StaticAllList.chatRecordDao!=null){
                StaticAllList.chatRecordDao.saveSchDuty(requestMsg);
            }else{
                MainActivity.initChatRecorddb();
                StaticAllList.chatRecordDao.saveSchDuty(requestMsg);
            }

        }else{
            Intent intent = new Intent("thefirstchange.example.com.communicationtext.DUTYNOTIRECYVIEW");
            intent.putExtra("type","DutySche");
            intent.putExtra("rs","error");
            intent.putExtra("groupid",groupid);
            intent.putExtra("dnid",dnid);
            NettyService.nettyService.sendCast(intent);
        }
    }
}
