package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.CreateGroupRequestPacket;

/*
创建群
 */
public class CreateGroupCommandSender implements CommandSender {

    static CreateGroupCommandSender sender ;
    public static CreateGroupCommandSender getInsatnce(){
        if(sender==null){
            sender = new CreateGroupCommandSender();
        }
        return sender;
    }

    private CreateGroupCommandSender(){
    }

    CreateGroupRequestPacket packet;
    public CreateGroupCommandSender getPacket(String groupname){
        packet = new CreateGroupRequestPacket( groupname);

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
