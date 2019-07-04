package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.group.util.UserInGroupInfoUtil;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.UserInGroupInfo;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetGroupAllUserResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    获得某群的所有成员   没有头像
 */
public class GetGroupAllUser_ResponseHandler extends SimpleChannelInboundHandler<GetGroupAllUserResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetGroupAllUserResponsePacket getGroupAllUserResponsePacket) throws Exception {

        Vector<UserInGroupInfo> users = getGroupAllUserResponsePacket.getUsers();
        int groupid = getGroupAllUserResponsePacket.getGroupid();
        if (groupid == -1 || !StaticAllList.groupList.containsKey(groupid)) {
            return;
        }

        Vector<UserInGroupInfo> userInGroupInfos = new Vector<>();
        if(StaticAllList.groupList.get(groupid).getGrouptype().contains("社团")){
            String[] corpParts = StaticAllList.groupList.get(groupid).getCorppart();
            userInGroupInfos = UserInGroupInfoUtil.sortUserInGroupInfo(users,corpParts);
        }else{
            userInGroupInfos = users;
        }
        StaticAllList.groupUsersInfo.put(groupid, userInGroupInfos);
        String type = getGroupAllUserResponsePacket.getType();

        Intent intent = new Intent("thefirstchange.example.com.communicationtext.ALLPEOPLESHOW");
        if(type.equals("getGroupAllURs")){   //群成员列表

        }else if(type.equals("appoint_zhuxi")){  //任命主席
            intent = new Intent("thefirstchange.example.com.communicationtext.APPOINTZHUXIACTIVITY");
        }else if(type.equals("appoint_buzhang")){ //任命部长
            intent = new Intent("thefirstchange.example.com.communicationtext.APPOINTBUZHANGACTIVITY");
        }else if(type.equals("appoint_ganshi")){//任命干事
            intent = new Intent("thefirstchange.example.com.communicationtext.APPOINTGANSHIACTIVITY");
        }

        intent.putExtra("type", "getGroupAllURs");
        intent.putExtra("groupid", groupid);
        NettyService.nettyService.sendCast(intent);
    }
}
