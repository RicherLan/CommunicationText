package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.db.dao.BasicDataDao;
import thefirstchange.example.com.communicationtext.gson.MyFriend;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.User;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.AddUserToFriendListResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.util.MyObjectTools;

/*
    客户端添加好友成功后  将好友加入到自己的好友列表  拿到其资料
 */
public class AddUserToFriendList_ResponseHandler extends SimpleChannelInboundHandler<AddUserToFriendListResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AddUserToFriendListResponsePacket addUserToFriendListResponsePacket) throws Exception {
        User user = addUserToFriendListResponsePacket.getUser();

        MyFriend myFriend = MyObjectTools.UserConvertToMyFriend(user);

        StaticAllList.friendList.put(user.getPhonenumber(),myFriend);

        BasicDataDao basicDataDao = BasicDataDao.getInstance(NettyService.nettyService);
        basicDataDao.saveFriend(myFriend);

    }



}
