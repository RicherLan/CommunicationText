package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.OkGetGroupAllUserRequestPacket;

/*
    获得某群的所有成员后  向服务器回执     来让服务器发头像
 */
public class OkGetGroupAllUserCommandSender implements CommandSender {

    static OkGetGroupAllUserCommandSender sender ;
    public static OkGetGroupAllUserCommandSender getInsatnce(){
        if(sender==null){
            sender = new OkGetGroupAllUserCommandSender();
        }
        return sender;
    }

    private OkGetGroupAllUserCommandSender(){
    }

    OkGetGroupAllUserRequestPacket packet;
    public OkGetGroupAllUserCommandSender getPacket(int groupid ){
        packet = new OkGetGroupAllUserRequestPacket( groupid );

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
