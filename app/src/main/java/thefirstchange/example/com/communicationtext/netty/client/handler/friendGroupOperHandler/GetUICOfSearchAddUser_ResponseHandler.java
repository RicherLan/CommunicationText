package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.GetUICOfSearchAddUserResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.util.MyFileTools;

/*
    添加好友时    首先查询     获得对方的头像
 */
public class GetUICOfSearchAddUser_ResponseHandler  extends SimpleChannelInboundHandler<GetUICOfSearchAddUserResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetUICOfSearchAddUserResponsePacket getUICOfSearchAddUserResponsePacket) throws Exception {

        String ph = getUICOfSearchAddUserResponsePacket.getPh();
        byte[] icon = getUICOfSearchAddUserResponsePacket.getIc();

        //   把图片路径保存进去
        String path = MyFileTools.saveUserIcon(ph,icon);

        if(path==null||path.equals("")){
            return;
        }
        Intent intent=new Intent("thefirstchange.example.com.communicationtext.ADD_FRIEND");
        intent.putExtra("type","getUICOfsearchUInfoRs");
        intent.putExtra("phonenumber",ph);
        intent.putExtra("icpath",path);
//            intent.putExtra("usericonpath",icon);
        NettyService.nettyService.sendCast(intent);

    }
}
