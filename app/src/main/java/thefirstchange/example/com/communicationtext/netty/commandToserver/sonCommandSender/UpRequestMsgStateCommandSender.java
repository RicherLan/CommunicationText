package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.UpRequestMsgStateRequestPacket;

public class UpRequestMsgStateCommandSender implements CommandSender {
    static UpRequestMsgStateCommandSender sender ;
    public static UpRequestMsgStateCommandSender getInsatnce(){
        if(sender==null){
            sender = new UpRequestMsgStateCommandSender();
        }
        return sender;
    }

    private UpRequestMsgStateCommandSender(){
    }

    UpRequestMsgStateRequestPacket packet;
    public UpRequestMsgStateCommandSender getPacket(int msgid){
        packet = new UpRequestMsgStateRequestPacket(msgid);
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
