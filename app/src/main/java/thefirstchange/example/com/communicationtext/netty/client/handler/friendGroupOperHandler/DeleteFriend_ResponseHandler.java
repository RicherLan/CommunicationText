package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.DeleteFriendResponsePacket;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
删除自己的某一个好友   服务器回执删除情况
 */
public class DeleteFriend_ResponseHandler extends SimpleChannelInboundHandler<DeleteFriendResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DeleteFriendResponsePacket deleteFriendResponsePacket) throws Exception {

        String rs = deleteFriendResponsePacket.getResult();
        String ph = deleteFriendResponsePacket.getPh();

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.CONTACTSWIPE");
        intent.putExtra("type","deleteFriendResult");
        intent.putExtra("rs",rs);
        NettyService.nettyService.sendCast(intent);


        //删除好友后  对应的数据库（好友数据库，通知窗口等）都要删除
        if(rs.equals("ok")){

            //窗口数据库删除该好友的窗口
            if(MyMessageQueue.chatframes.containsKey(ph)){

                if(StaticAllList.chatRecordDao!=null){
                    StaticAllList.chatRecordDao.deleteNoticeFrame("single",ph,-1);
                }else{
                    MainActivity.initChatRecorddb();
                    StaticAllList.chatRecordDao.deleteNoticeFrame("single",ph,-1);
                }
            }


            StaticAllList.friendList.remove(ph);
            MyMessageQueue.chatframes.remove(ph);         //删除聊天框
            MyMessageQueue.chatQueueNotRead.remove(ph);
            MyMessageQueue.chatQueueHadRead.remove(ph);


            //通知  通知界面刷新
            Intent intent2=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
            intent2.putExtra("type","deleteFriendResult");
            NettyService.nettyService.sendCast(intent2);

            //好友数据库中删除好友
           {
                if(StaticAllList.basicDataDao!=null){
                    StaticAllList.basicDataDao.deleteFriend(ph);
                }else{
                    MainActivity.initBasicDataDb();
                    StaticAllList.basicDataDao.deleteFriend(ph);
                }
            }




        }


    }
}
