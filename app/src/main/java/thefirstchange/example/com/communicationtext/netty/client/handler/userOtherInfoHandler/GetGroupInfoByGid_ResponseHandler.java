package thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.db.dao.BasicDataDao;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.UserGroup;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.GetGroupInfoByGidResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    获得用户的某群的信息   一般用在更新某群信息时
 */
public class GetGroupInfoByGid_ResponseHandler extends SimpleChannelInboundHandler<GetGroupInfoByGidResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetGroupInfoByGidResponsePacket getGroupInfoByGidResponsePacket) throws Exception {

        UserGroup userGroup = getGroupInfoByGidResponsePacket.getUserGroup();
        int groupid = getGroupInfoByGidResponsePacket.getGroupid();

        if(userGroup==null){
            return;
        }
        StaticAllList.groupList.remove(groupid);
        StaticAllList.groupList.put(groupid,userGroup);

        //更群新头像
        SendToServer.getGroupIcByGid(groupid);


        //保存到数据库
        BasicDataDao basicDataDao = BasicDataDao.getInstance(NettyService.nettyService);
        basicDataDao.deleteGroupByGid(groupid);
        basicDataDao.saveGroup(userGroup);
    }
}
