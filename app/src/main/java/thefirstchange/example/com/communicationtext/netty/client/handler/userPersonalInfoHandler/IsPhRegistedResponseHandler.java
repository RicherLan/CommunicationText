package thefirstchange.example.com.communicationtext.netty.client.handler.userPersonalInfoHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.IsPhonenumberRegistedResponsePacket;

/*
用户注册账号时  判断提交的手机号是否已经被注册
 */
public class IsPhRegistedResponseHandler extends SimpleChannelInboundHandler<IsPhonenumberRegistedResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IsPhonenumberRegistedResponsePacket responsePacket) throws Exception {
       String rs = responsePacket.getResult();
        if(rs.equals("ok")){

            //该号码已经被注册
        }else if(rs.equals("hasregisted")){

        }else {

        }
    }
}
