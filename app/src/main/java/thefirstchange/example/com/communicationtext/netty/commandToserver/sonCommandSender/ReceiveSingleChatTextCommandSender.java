package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.ReceiveSingleChatTextRequestPacket;

public class ReceiveSingleChatTextCommandSender implements CommandSender {

    static ReceiveSingleChatTextCommandSender sender ;
    public static ReceiveSingleChatTextCommandSender getInsatnce(){
        if(sender==null){
            sender = new ReceiveSingleChatTextCommandSender();
        }
        return sender;
    }

    private ReceiveSingleChatTextCommandSender(){
    }

    ReceiveSingleChatTextRequestPacket packet;
    public ReceiveSingleChatTextCommandSender getPacket(String senderid, int msgid){
        packet = new ReceiveSingleChatTextRequestPacket( senderid, msgid);


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
