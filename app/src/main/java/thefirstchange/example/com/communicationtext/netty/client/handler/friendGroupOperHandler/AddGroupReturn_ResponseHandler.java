package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.AddGroupReturnResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
        群管理员对用户的加群  同意或不同意时    该结果
 */
public class AddGroupReturn_ResponseHandler extends SimpleChannelInboundHandler<AddGroupReturnResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AddGroupReturnResponsePacket addGroupReturnResponsePacket) throws Exception {

        String rs = addGroupReturnResponsePacket.getRs();
        int groupid = addGroupReturnResponsePacket.getGroupid();
        String otherphonenumber = addGroupReturnResponsePacket.getOtherphonenumber();
        int msgid =addGroupReturnResponsePacket.getMsgid();
        String operator = addGroupReturnResponsePacket.getType();



        Intent intent=new Intent("thefirstchange.example.com.communicationtext.OTHERPEOPLEAPPLICATION");
        intent.putExtra("type","handlerAddGroupResult");
        intent.putExtra("rs",rs);
        intent.putExtra("msgid",msgid);
        intent.putExtra("operator",operator);

        NettyService.nettyService.sendCast(intent);

        //刷新该群信息
        if(rs.equals("ok")&&operator.equals("agree")){
            if(StaticAllList.groupList.containsKey(groupid)){
                StaticAllList.groupList.get(groupid).setUsernum(StaticAllList.groupList.get(groupid).getUsernum()+1);
                //更新数据库
                if(StaticAllList.chatRecordDao!=null){
                    StaticAllList.basicDataDao.saveGroup(StaticAllList.groupList.get(groupid));
                }else{
                    MainActivity.initChatRecorddb();
                    StaticAllList.basicDataDao.saveGroup(StaticAllList.groupList.get(groupid));
                }
            }
        }

    }
}
