package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.SearchAddUserResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
客户端添加某人好友时   搜索账号 只需要看到其账号 网名 头像就行
 */
public class SearchAddUser_ResponseHandler extends SimpleChannelInboundHandler<SearchAddUserResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SearchAddUserResponsePacket searchAddUserResponsePacket) throws Exception {

        //  ok    notexist
        String rs = searchAddUserResponsePacket.getResult();
        String phonenumber = searchAddUserResponsePacket.getPh();
        if(rs.equals("ok")){
            String nickname = searchAddUserResponsePacket.getNickname();
//            String icon = jsonObject.getString("usericonpath");

            Intent intent=new Intent("thefirstchange.example.com.communicationtext.ADD_FRIEND");
            intent.putExtra("type","searchUInfoRs");
            intent.putExtra("rs",rs);
            intent.putExtra("phonenumber",phonenumber);
            intent.putExtra("nickname",nickname);
//            intent.putExtra("usericonpath",icon);
            NettyService.nettyService.sendCast(intent);

            //获得头像
            SendToServer.getUICOfSearchAddUser(phonenumber);


        }else if(rs.equals("notexist")){
            Intent intent=new Intent("thefirstchange.example.com.communicationtext.ADD_FRIEND");
            intent.putExtra("type","getNameAndIconByPhonenumberResult");
            intent.putExtra("rs",rs);
            intent.putExtra("phonenumber",phonenumber);
//            intent.putExtra("usericonpath",icon);
            NettyService.nettyService.sendCast(intent);
        }
    }
}
