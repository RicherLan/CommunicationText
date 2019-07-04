package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GroupAdmiReciveExitGroupRequestPacket;

public class GroupAdmiReciveExitGroupCommandSender  implements CommandSender {

    static GroupAdmiReciveExitGroupCommandSender sender ;
    public static GroupAdmiReciveExitGroupCommandSender getInsatnce(){
        if(sender==null){
            sender = new GroupAdmiReciveExitGroupCommandSender();
        }
        return sender;
    }

    private GroupAdmiReciveExitGroupCommandSender(){
    }

    GroupAdmiReciveExitGroupRequestPacket packet;
    public GroupAdmiReciveExitGroupCommandSender getPacket(int msgid){
        packet = new GroupAdmiReciveExitGroupRequestPacket(msgid);

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
