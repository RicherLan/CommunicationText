package thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.MyFriend;
import thefirstchange.example.com.communicationtext.gson.MyFriendEasy;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.FreshAllFriendInfoResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.util.MyTools;

/*
获得自己的所有好友信息    一般是客户端刷新好友列表时用到
 */
public class FreshAllFriendInfo_ResponseHandler extends SimpleChannelInboundHandler<FreshAllFriendInfoResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FreshAllFriendInfoResponsePacket freshAllFriendInfoResponsePacket) throws Exception {
        Vector<MyFriendEasy> myFriends = freshAllFriendInfoResponsePacket.getUsers();
        Vector<String> phs = new Vector<>();
        for(int i=0;i<myFriends.size();++i){
            MyFriendEasy myFriendEasy = myFriends.get(i);
            String ph = myFriendEasy.getPhonenumber();
            phs.add(ph);
            String nickname =myFriendEasy.getNickname();
            String sex = myFriendEasy.getSex();
            String remark = myFriendEasy.getRemark();
            int friendgroup = myFriendEasy.getFriendgroup();
            String icon = myFriendEasy.getIcon();

            MyFriend myFriend = new MyFriend();
            if(StaticAllList.friendList.containsKey(ph)){
                StaticAllList.friendList.get(ph).setNickname(nickname);
                StaticAllList.friendList.get(ph).setSchoolname(sex);
                StaticAllList.friendList.get(ph).setRemark(remark);
                StaticAllList.friendList.get(ph).setFriendgroup(friendgroup);
                String iconpath = MyTools.getIconPath(ph);
                StaticAllList.friendList.get(ph).setIcon(iconpath);

            }else{
                StaticAllList.friendList.put(ph,myFriend);
                String iconpath = MyTools.getIconPath(ph);
                StaticAllList.friendList.get(ph).setIcon(iconpath);

            }

        }

        for(String ph:StaticAllList.friendList.keySet()){
            if(!phs.contains(ph)){
                StaticAllList.friendList.remove(ph);
            }
        }

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.CONTACTSWIPE");
        intent.putExtra("type","freshAllFriendInfoRs");
        NettyService.nettyService.sendCast(intent);
    }
}
