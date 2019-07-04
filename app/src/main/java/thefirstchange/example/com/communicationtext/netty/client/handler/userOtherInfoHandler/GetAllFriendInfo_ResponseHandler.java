package thefirstchange.example.com.communicationtext.netty.client.handler.userOtherInfoHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.db.dao.BasicDataDao;
import thefirstchange.example.com.communicationtext.gson.MyFriend;
import thefirstchange.example.com.communicationtext.gson.MyFriendEasy;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetAllFriendInfoResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.MyTools;

/*
    获得自己的所有好友信息    一般是刚登陆
 */
public class GetAllFriendInfo_ResponseHandler extends SimpleChannelInboundHandler<GetAllFriendInfoResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetAllFriendInfoResponsePacket responsePacket) throws Exception {
        Vector<MyFriendEasy> myFriends = responsePacket.getUsers();
        if(myFriends==null){
            return;
        }

        StaticAllList.friendList.clear();

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

//
//            if(StaticAllList.friendList.containsKey(ph)){
//                StaticAllList.friendList.get(ph).setNickname(nickname);
//                StaticAllList.friendList.get(ph).setSex(sex);
//                StaticAllList.friendList.get(ph).setRemark(remark);
//                StaticAllList.friendList.get(ph).setFriendgroup(friendgroup);
//                String iconpath = MyTools.getIconPath(ph);
//                StaticAllList.friendList.get(ph).setIcon(iconpath);
//
//            }else{
                MyFriend myFriend = new MyFriend();
                myFriend.setPhonenumber(ph);
                myFriend.setNickname(nickname);
                myFriend.setSex(sex);
                myFriend.setRemark(remark);
                myFriend.setFriendgroup(friendgroup);

                String iconpath = MyTools.getIconPath(ph);
                myFriend.setIcon(iconpath);

                StaticAllList.friendList.put(ph,myFriend);

//            }

        }

//        for(String ph:StaticAllList.friendList.keySet()){
//            if(!phs.contains(ph)){
//                StaticAllList.friendList.remove(ph);
//            }
//        }

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.CONTACTSWIPE");
        intent.putExtra("type","getAllFriendInfoResult");
        NettyService.nettyService.sendCast(intent);


        //保存到数据库
        BasicDataDao basicDataDao = BasicDataDao.getInstance(NettyService.nettyService);
        basicDataDao.deleteUserFriendByPh(ObjectService.personalInfo.getPhonenumber());
        Vector<MyFriend> myFriends1 = new Vector<>();
        for(MyFriend myFriend:StaticAllList.friendList.values()){
            myFriends1.add(myFriend);
        }
        basicDataDao.saveUserFriendList(myFriends1);

    }
}
