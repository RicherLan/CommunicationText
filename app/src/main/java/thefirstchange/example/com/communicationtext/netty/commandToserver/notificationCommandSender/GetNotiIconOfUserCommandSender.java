package thefirstchange.example.com.communicationtext.netty.commandToserver.notificationCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.notificationRequest.GetNotiIconOfUserRequestPacket;

/*
    获得某用户头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
 */
public class GetNotiIconOfUserCommandSender implements CommandSender {
    static GetNotiIconOfUserCommandSender sender ;
    public static GetNotiIconOfUserCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetNotiIconOfUserCommandSender();
        }
        return sender;
    }

    private GetNotiIconOfUserCommandSender(){
    }

    GetNotiIconOfUserRequestPacket packet;
    public GetNotiIconOfUserCommandSender getPacket(String ph){
        packet = new GetNotiIconOfUserRequestPacket( ph );

        return sender;
    }

    @Override
    public void execute(Channel channel) {
        if(channel==null||!channel.isActive()){
            return;
        }
        channel.writeAndFlush(packet);
    }
}
