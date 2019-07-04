package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import java.util.Vector;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.FreshNotificationRequestPacket;

public class FreshNotificationCommandSender implements CommandSender{

    static FreshNotificationCommandSender sender ;
    public static FreshNotificationCommandSender getInsatnce(){
        if(sender==null){
            sender = new FreshNotificationCommandSender();
        }
        return sender;
    }

    private FreshNotificationCommandSender(){
    }

    FreshNotificationRequestPacket packet;
    public FreshNotificationCommandSender getPacket(Vector<String> phs){
        packet = new FreshNotificationRequestPacket();
        packet.setPhs(phs);
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
