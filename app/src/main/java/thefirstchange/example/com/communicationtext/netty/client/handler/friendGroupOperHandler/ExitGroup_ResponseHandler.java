package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.ExitGroupResponsePacket;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    退群
 */
public class ExitGroup_ResponseHandler extends SimpleChannelInboundHandler<ExitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ExitGroupResponsePacket exitGroupResponsePacket) throws Exception {
        //退群的申请结果

        String rs = exitGroupResponsePacket.getResult();
        int groupid = exitGroupResponsePacket.getGroupid();

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.GROUPPAGE");
        intent2.putExtra("type","exitGroupRs");
        intent2.putExtra("rs",rs);
        intent2.putExtra("gid",groupid);
        NettyService.nettyService.sendCast(intent2);

        if(rs.equals("ok")){
            Intent intent=new Intent("thefirstchange.example.com.communicationtext.GROUP_MSG");
            intent.putExtra("type","exitGroupRs");
            intent.putExtra("gid",groupid);
            NettyService.nettyService.sendCast(intent);


            //窗口数据库删除该好友的窗口
            if(MyMessageQueue.chatframes.containsKey(groupid+"")){

                if(StaticAllList.chatRecordDao!=null){
                    StaticAllList.chatRecordDao.deleteNoticeFrame("group","",groupid);
                }else{
                    MainActivity.initChatRecorddb();
                    StaticAllList.chatRecordDao.deleteNoticeFrame("group","",-groupid);
                }
            }

            StaticAllList.groupList.remove(groupid);
            MyMessageQueue.chatframes.remove(groupid+"");         //删除聊天框
            MyMessageQueue.chatQueueNotRead.remove(groupid+"");
            MyMessageQueue.chatQueueHadRead.remove(groupid+"");

            //通知  通知界面刷新
            Intent intent3=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
            intent3.putExtra("type","exitGroupResult");
            NettyService.nettyService.sendCast(intent3);

            //群数据库中删除群
            {
                if(StaticAllList.basicDataDao!=null){
                    StaticAllList.basicDataDao.deleteGroupByGid(groupid);
                }else{
                    MainActivity.initBasicDataDb();
                    StaticAllList.basicDataDao.deleteGroupByGid(groupid);
                }
            }


        }
    }
}
