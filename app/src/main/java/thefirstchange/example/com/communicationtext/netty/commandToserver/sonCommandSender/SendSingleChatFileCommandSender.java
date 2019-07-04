package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.SendSingleChatFileRequestPacket;

public class SendSingleChatFileCommandSender implements CommandSender {

    static SendSingleChatFileCommandSender sender ;
    public static SendSingleChatFileCommandSender getInsatnce(){
        if(sender==null){
            sender = new SendSingleChatFileCommandSender();
        }
        return sender;
    }

    private SendSingleChatFileCommandSender(){
    }

    SendSingleChatFileRequestPacket packet;
    public SendSingleChatFileCommandSender getPacket(String senderid, String reciverid ,
                                                     String message, String msgtype,long time ,double voicetime ,byte[] bytes){
        packet = new SendSingleChatFileRequestPacket( senderid,  reciverid , message,  msgtype, time , voicetime , bytes);

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
