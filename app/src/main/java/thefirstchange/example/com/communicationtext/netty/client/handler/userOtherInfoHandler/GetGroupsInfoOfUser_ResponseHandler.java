package thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.db.dao.BasicDataDao;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.UserGroup;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetGroupsInfoOfUserResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.MyFileTools;

/*
获得某用户的群的基本信息
 */
public class GetGroupsInfoOfUser_ResponseHandler extends SimpleChannelInboundHandler<GetGroupsInfoOfUserResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GetGroupsInfoOfUserResponsePacket responsePacket) throws Exception {
        Vector<UserGroup> userGroups =  responsePacket.getUserGroups();
        if(userGroups==null){
            return;
        }
        StaticAllList.groupList.clear();
        for(int i=0;i<userGroups.size();++i){
            userGroups.get(i).setGroupicon(MyFileTools.getGroupIconPath(userGroups.get(i).getGroupid()));
            StaticAllList.groupList.put(userGroups.get(i).getGroupid(),userGroups.get(i));
        }

        //若本地没有保存群头像   那么向服务器请求头像
        for(int gid : StaticAllList.groupList.keySet()){
            String path = StaticAllList.groupList.get(gid).getGroupicon();
            if(path==null||path.equals("")){
                SendToServer.getGroupIcByGid(gid);
            }
        }

        //保存到数据库
        BasicDataDao basicDataDao = BasicDataDao.getInstance(NettyService.nettyService);
        basicDataDao.deleteUserGroupByPh(ObjectService.personalInfo.getPhonenumber());
        basicDataDao.saveUserGroupList(userGroups);
    }
}
