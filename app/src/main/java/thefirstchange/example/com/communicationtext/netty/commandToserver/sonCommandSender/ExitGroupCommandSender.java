package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.ExitGroupRequestPacket;

public class ExitGroupCommandSender implements CommandSender {

    static ExitGroupCommandSender sender ;
    public static ExitGroupCommandSender getInsatnce(){
        if(sender==null){
            sender = new ExitGroupCommandSender();
        }
        return sender;
    }

    private ExitGroupCommandSender(){
    }

    ExitGroupRequestPacket packet;
    public ExitGroupCommandSender getPacket(int groupid){
        packet = new ExitGroupRequestPacket(groupid);

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
