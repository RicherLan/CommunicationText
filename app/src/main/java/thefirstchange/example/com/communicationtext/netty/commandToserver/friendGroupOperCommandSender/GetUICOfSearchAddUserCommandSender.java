package thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.GetUICOfSearchAddUserRequestPacket;

/*
    添加好友时    首先查询     获得对方的头像
 */
public class GetUICOfSearchAddUserCommandSender implements CommandSender {
    static GetUICOfSearchAddUserCommandSender sender ;
    public static GetUICOfSearchAddUserCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetUICOfSearchAddUserCommandSender();
        }
        return sender;
    }

    private GetUICOfSearchAddUserCommandSender(){
    }

    GetUICOfSearchAddUserRequestPacket packet;
    public GetUICOfSearchAddUserCommandSender getPacket(String phonenumber){
        packet = new GetUICOfSearchAddUserRequestPacket(phonenumber );

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
