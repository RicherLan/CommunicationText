package thefirstchange.example.com.communicationtext.netty.client.handler.eduSysHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.course.object.Course;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.CourseAndScore;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetCourseSchByUIDResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    获取课表
 */
public class GetCourseSchOfUID_ResponseHandler extends SimpleChannelInboundHandler<GetCourseSchByUIDResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetCourseSchByUIDResponsePacket getCourseSchByUIDResponsePacket) throws Exception {

        String rs = getCourseSchByUIDResponsePacket.getResult();

        String type = "getkBRs";
        if(rs.equals("errUNOrPwd")){
            Intent intent2=new Intent("thefirstchange.example.com.communicationtext.REQUESTKEBIAOORSCORE");
            intent2.putExtra("type",type);
            intent2.putExtra("rs",rs);

            NettyService.nettyService.sendCast(intent2);
            return;
        }
        int xueqi = getCourseSchByUIDResponsePacket.getXueqi();
        int grade = getCourseSchByUIDResponsePacket.getGrade();

        //存入数据库  并更新UI

        Vector<Course> courses = getCourseSchByUIDResponsePacket.getCourses();

//                for(int i=0;i<courses.size();++i) {
//							 System.out.println(courses.get(i).getCN()+"   *********************");
//						}

        boolean iskong = true;      //判断是不是空值   也就是当前查询的成绩  教务处是否有
        if(courses!=null&&courses.size()!=0){
            iskong = false;
            CourseAndScore.courses = courses;
            CourseAndScore.courseGrade = grade;
            CourseAndScore.courseXueqi = xueqi;
        }




        if(!iskong){
            Intent intent=new Intent("thefirstchange.example.com.communicationtext.CSS");
            intent.putExtra("type",type);
            NettyService.nettyService.sendCast(intent);
        }

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.REQUESTKEBIAOORSCORE");
        intent2.putExtra("type",type);
        intent2.putExtra("rs",rs);
        intent2.putExtra("iskong",iskong);

        NettyService.nettyService.sendCast(intent2);
    }
}
