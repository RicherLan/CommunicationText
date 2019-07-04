package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.SendSingleChatTextRequestPacket;

public class SendSingleChatTextCommandSender implements CommandSender {

    static SendSingleChatTextCommandSender sender ;
    public static SendSingleChatTextCommandSender getInsatnce(){
        if(sender==null){
            sender = new SendSingleChatTextCommandSender();
        }
        return sender;
    }

    private SendSingleChatTextCommandSender(){
    }

    SendSingleChatTextRequestPacket packet;
    public SendSingleChatTextCommandSender getPacket(String senderid ,String reciverid ,String message,String msgtype,long time){
        packet = new SendSingleChatTextRequestPacket( senderid , reciverid , message, msgtype, time);

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
