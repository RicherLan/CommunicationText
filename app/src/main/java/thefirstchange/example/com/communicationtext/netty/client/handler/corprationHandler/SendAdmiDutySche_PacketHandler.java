package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.CourseAndScore;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.schedulearrangement.ClientArrangement;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SendAdmiDutySche_PACKET;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    给社团管理(就是安排值班的人)发送排班结果
    值班表到来   把旧的该组织的值班表删除
 */
public class SendAdmiDutySche_PacketHandler extends SimpleChannelInboundHandler<SendAdmiDutySche_PACKET> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendAdmiDutySche_PACKET sendAdmiDutySche_packet) throws Exception {


        int groupid = sendAdmiDutySche_packet.getGroupid();


        CourseAndScore.clientArrangementsTemp = sendAdmiDutySche_packet.getClientArrangements();
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
//                        System.out.println(CourseAndScore.clientArrangementsTemp.get(i).getDaytime());
                CourseAndScore.clientArrangements.add(CourseAndScore.clientArrangementsTemp.get(i));
            }

//                for(int i=0;i<CourseAndScore.clientArrangements.size();++i){
//                    if(CourseAndScore.clientArrangements.get(i).getGroupid()==CourseAndScore.duty_groupid){
//                        int schyear = CourseAndScore.clientArrangements.get(i).getYear();
//                        int  schmonth = CourseAndScore.clientArrangements.get(i).getMonth();
//                        int daytime = CourseAndScore.clientArrangements.get(i).getDaytime();
////                    break;
//                    }
//                }

            String type = "DutySche";
            Intent intent=new Intent("thefirstchange.example.com.communicationtext.SCHDUTY");
            intent.putExtra("type",type);
            intent.putExtra("groupid",groupid);

            NettyService.nettyService.sendCast(intent);


            Intent intent2=new Intent("thefirstchange.example.com.communicationtext.DUTYNOTIRECYVIEW");
            intent2.putExtra("type",type);
            intent2.putExtra("groupid",groupid);
            NettyService.nettyService.sendCast(intent2);

        }
    }
}
