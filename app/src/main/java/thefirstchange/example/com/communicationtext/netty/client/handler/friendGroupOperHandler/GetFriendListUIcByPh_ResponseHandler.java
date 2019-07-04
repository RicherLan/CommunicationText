package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.GetFriendListUIcByPhResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.util.MyFileTools;

/*
    进入好友列表   如果本地没有好友的头像   获取好友的的头像
 */
public class GetFriendListUIcByPh_ResponseHandler  extends SimpleChannelInboundHandler<GetFriendListUIcByPhResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetFriendListUIcByPhResponsePacket getFriendListUIcByPhResponsePacket) throws Exception {
        String ph = getFriendListUIcByPhResponsePacket.getPh();
        byte[] bs = getFriendListUIcByPhResponsePacket.getIc();

        String path = MyFileTools.saveUserIcon(ph,bs);
        if(path==null||path.equals("")){
            return;
        }

        for(String uid : StaticAllList.friendList.keySet()){
            if(uid.equals(ph)){
                StaticAllList.friendList.get(uid).setIcon(path);
                break;
            }
        }

        //通知好友列表
        Intent intent = new Intent("thefirstchange.example.com.communicationtext.CONTACTSWIPE");
        intent.putExtra("type","GetFriendListUIcByPh");
        NettyService.nettyService.sendCast(intent);

    }
}
