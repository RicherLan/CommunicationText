package thefirstchange.example.com.communicationtext.netty.client.handler.eduSysHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.course.object.ListViewScore;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.CourseAndScore;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetScoreOfUIDResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    获取成绩
 */
public class GetScoreOfUID_ResponseHandler extends SimpleChannelInboundHandler<GetScoreOfUIDResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetScoreOfUIDResponsePacket getScoreOfUIDResponsePacket) throws Exception {

        String rs = getScoreOfUIDResponsePacket.getResult();
        String type = "getscoRs";
        if(rs.equals("errUNOrPwd")){
            Intent intent2=new Intent("thefirstchange.example.com.communicationtext.REQUESTKEBIAOORSCORE");
            intent2.putExtra("type",type);
            intent2.putExtra("rs",rs);

            NettyService.nettyService.sendCast(intent2);
            return;
        }
        int xueqi = getScoreOfUIDResponsePacket.getXueqi();
        int grade = getScoreOfUIDResponsePacket.getGrade();

        //存入数据库  并更新UI

        Vector<ListViewScore> listViewScores = getScoreOfUIDResponsePacket.getScores();


//                for(int i=0;i<listViewScores.size();++i) {
//							 System.out.println(listViewScores.get(i).courseName+"   *********************");
//						}

        boolean iskong = true;      //判断是不是空值   也就是当前查询的成绩  教务处是否有
        if(listViewScores!=null&&listViewScores.size()!=0){
            iskong = false;
            CourseAndScore.scores = listViewScores;
            CourseAndScore.scoreGrade = grade;
            CourseAndScore.scoreXueqi = xueqi;
        }

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.REQUESTKEBIAOORSCORE");
        intent2.putExtra("type",type);
        intent2.putExtra("rs",rs);
        intent2.putExtra("iskong",iskong);

        NettyService.nettyService.sendCast(intent2);
        if(!iskong){
            Intent intent=new Intent("thefirstchange.example.com.communicationtext.CSS");
            intent.putExtra("type",type);
            NettyService.nettyService.sendCast(intent);
        }

    }
}
