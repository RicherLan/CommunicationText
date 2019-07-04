package thefirstchange.example.com.communicationtext.netty.commandToserver.notificationCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.notificationRequest.GetNotiIconOfGroupRequestPacket;

/*
    获得某群头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
 */
public class GetNotiIconOfGroupCommandSender implements CommandSender {
    static GetNotiIconOfGroupCommandSender sender ;
    public static GetNotiIconOfGroupCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetNotiIconOfGroupCommandSender();
        }
        return sender;
    }

    private GetNotiIconOfGroupCommandSender(){
    }

    GetNotiIconOfGroupRequestPacket packet;
    public GetNotiIconOfGroupCommandSender getPacket(int groupid){
        packet = new GetNotiIconOfGroupRequestPacket( groupid );

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
