package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.ReadaddGroupResultRequestPacket;

/*
歧义
 */
public class ReadaddGroupResultCommandSender implements CommandSender {

    static ReadaddGroupResultCommandSender sender ;
    public static ReadaddGroupResultCommandSender getInsatnce(){
        if(sender==null){
            sender = new ReadaddGroupResultCommandSender();
        }
        return sender;
    }

    private ReadaddGroupResultCommandSender(){
    }

    ReadaddGroupResultRequestPacket packet;
    public ReadaddGroupResultCommandSender getPacket(int msgid){
        packet = new ReadaddGroupResultRequestPacket( msgid);

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
