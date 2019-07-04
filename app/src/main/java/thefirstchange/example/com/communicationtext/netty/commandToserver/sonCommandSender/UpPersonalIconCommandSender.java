package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.UpPersonalIconRequestPacket;

/*
更改自己的头像
 */
public class UpPersonalIconCommandSender  implements CommandSender {
    static UpPersonalIconCommandSender sender ;

    public static UpPersonalIconCommandSender getInsatnce(){
        if(sender==null){
            sender = new UpPersonalIconCommandSender();
        }
        return sender;
    }

    private UpPersonalIconCommandSender(){
    }

    UpPersonalIconRequestPacket packet;
    public UpPersonalIconCommandSender getPacket(byte[] bytes){
        packet = new UpPersonalIconRequestPacket(bytes);
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
