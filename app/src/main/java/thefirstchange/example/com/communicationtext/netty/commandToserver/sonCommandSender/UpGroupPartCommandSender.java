package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.UpGroupPartRequestPacket;

/*
修改自己的部室
 */
public class UpGroupPartCommandSender implements CommandSender {
    static UpGroupPartCommandSender sender ;
    public static UpGroupPartCommandSender getInsatnce(){
        if(sender==null){
            sender = new UpGroupPartCommandSender();
        }
        return sender;
    }

    private UpGroupPartCommandSender(){
    }

    UpGroupPartRequestPacket packet;
    public UpGroupPartCommandSender getPacket(int groupid ,String part){
        packet = new UpGroupPartRequestPacket( groupid , part);

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
