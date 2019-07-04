package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.CourseAndScore;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.schedulearrangement.ClientArrangement;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetAllSDResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    获得自己加入的所有组织的值班表
 */
public class GetAllSD_ResponseHandler extends SimpleChannelInboundHandler<GetAllSDResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetAllSDResponsePacket getAllSDResponsePacket) throws Exception {

       int groupid = getAllSDResponsePacket.getGroupid();
        if(groupid==-1){          //自己没有参与任何社团组织



        }else{

            CourseAndScore.clientArrangementsTemp = getAllSDResponsePacket.getClientArrangements();
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

                for(int i=0;i<CourseAndScore.clientArrangements.size();++i){
                    if(CourseAndScore.clientArrangements.get(i).getGroupid()==CourseAndScore.duty_groupid){
                        int schyear = CourseAndScore.clientArrangements.get(i).getYear();
                        int  schmonth = CourseAndScore.clientArrangements.get(i).getMonth();
                        int daytime = CourseAndScore.clientArrangements.get(i).getDaytime();
//                    break;
                    }
                }

                String type = "DutySche";
                Intent intent=new Intent("thefirstchange.example.com.communicationtext.SCHDUTY");
                intent.putExtra("type",type);
                intent.putExtra("groupid",groupid);

                NettyService.nettyService.sendCast(intent);
            }

        }
    }
}
