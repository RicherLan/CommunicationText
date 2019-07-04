package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetGroupUserIconByPhResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.util.MyFileTools;

/*
    获得群成员头像
 */
public class GetGroupUserIconByPh_ResponseHandler extends SimpleChannelInboundHandler<GetGroupUserIconByPhResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetGroupUserIconByPhResponsePacket getGroupUserIconByPhResponsePacket) throws Exception {

        int groupid = getGroupUserIconByPhResponsePacket.getGroupid();
        String userph = getGroupUserIconByPhResponsePacket.getPh();
        String type = getGroupUserIconByPhResponsePacket.getType();
        byte[] icon = getGroupUserIconByPhResponsePacket.getIcon();

        String path = MyFileTools.saveUserIcon(userph,icon);
        if(path==null||path.equals("")){
            return;
        }


        Intent intent = new Intent("thefirstchange.example.com.communicationtext.ALLPEOPLESHOW");
        if(type.equals("getGroupAllURs")){   //群成员列表

        }else if(type.equals("appoint_zhuxi")){  //任命主席
            intent = new Intent("thefirstchange.example.com.communicationtext.APPOINTZHUXIACTIVITY");
        }else if(type.equals("appoint_buzhang")){ //任命部长
            intent = new Intent("thefirstchange.example.com.communicationtext.APPOINTBUZHANGACTIVITY");
        }else if(type.equals("appoint_ganshi")){//任命干事
            intent = new Intent("thefirstchange.example.com.communicationtext.APPOINTGANSHIACTIVITY");
        }else if(type.equals("searchEmptyCourseUICRs")){  //查询空课
            intent = new Intent("thefirstchange.example.com.communicationtext.SEARCHEMPTYCOURSEPEOPLELIST");
        }

        intent.putExtra("type", "getGroupUserIconRs");
        intent.putExtra("groupid", groupid);
        intent.putExtra("userph",userph);
        NettyService.nettyService.sendCast(intent);

    }
}
