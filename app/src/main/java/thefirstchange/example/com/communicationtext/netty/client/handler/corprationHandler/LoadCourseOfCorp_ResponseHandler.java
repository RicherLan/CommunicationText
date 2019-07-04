package thefirstchange.example.com.communicationtext.netty.client.handler.corprationHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.course.object.Course;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.CourseAndScore;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.LoadCourseOfCorpResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
社团组织成员导入自己的课表
 */
public class LoadCourseOfCorp_ResponseHandler extends SimpleChannelInboundHandler<LoadCourseOfCorpResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoadCourseOfCorpResponsePacket loadCourseOfCorpResponsePacket) throws Exception {


        String rs = loadCourseOfCorpResponsePacket.getResult();

        String type = "getkBRs";
        if (rs.equals("errUNOrPwd")) {
            Intent intent2 = new Intent("thefirstchange.example.com.communicationtext.CORPLOADSCOREACTIVITY");
            intent2.putExtra("type", type);
            intent2.putExtra("rs", rs);

            NettyService.nettyService.sendCast(intent2);
            return;
        }
        int xueqi = loadCourseOfCorpResponsePacket.getXueqi();
        int grade = loadCourseOfCorpResponsePacket.getGrade();

        //存入数据库  并更新UI

        Vector<Course> courses = loadCourseOfCorpResponsePacket.getCourses();

//                for(int i=0;i<courses.size();++i) {
//							 System.out.println(courses.get(i).getCN()+"   *********************");
//						}

        boolean iskong = true;      //判断是不是空值   也就是当前查询的成绩  教务处是否有
        if (courses != null && courses.size() != 0) {
            iskong = false;
            CourseAndScore.courses = courses;
            CourseAndScore.courseGrade = grade;
            CourseAndScore.courseXueqi = xueqi;
        }


        if (!iskong) {


        }


//                    Intent intent = new Intent("thefirstchange.example.com.communicationtext.CSS");
//                    intent.putExtra("type", type);
//                    sendCast(intent);


        Intent intent2 = new Intent("thefirstchange.example.com.communicationtext.CORPLOADSCOREACTIVITY");
        intent2.putExtra("type", type);
        intent2.putExtra("rs", rs);
        intent2.putExtra("iskong", iskong);

        NettyService.nettyService.sendCast(intent2);
    }
}
