package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.SendGroupChatFileRequestPacket;

public class SendGroupChatFileCommandSender implements CommandSender {

    static SendGroupChatFileCommandSender sender ;
    public static SendGroupChatFileCommandSender getInsatnce(){
        if(sender==null){
            sender = new SendGroupChatFileCommandSender();
        }
        return sender;
    }

    private SendGroupChatFileCommandSender(){
    }

    SendGroupChatFileRequestPacket packet;
    public SendGroupChatFileCommandSender getPacket(int groupid ,String senderid ,String sendergroupname ,
                                                    String message,String msgtype,long time ,double voicetime,byte[] bytes){

        packet = new SendGroupChatFileRequestPacket( groupid , senderid , sendergroupname ,
                                                    message,  msgtype, time , voicetime,bytes);

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
