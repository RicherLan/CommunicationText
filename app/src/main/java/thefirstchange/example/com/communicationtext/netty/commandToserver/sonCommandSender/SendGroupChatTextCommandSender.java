package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.SendGroupChatTextRequestPacket;

public class SendGroupChatTextCommandSender implements CommandSender {

    static SendGroupChatTextCommandSender sendGroupChatTextCommandSender ;
    public static SendGroupChatTextCommandSender getInsatnce(){
        if(sendGroupChatTextCommandSender==null){
            sendGroupChatTextCommandSender = new SendGroupChatTextCommandSender();
        }
        return sendGroupChatTextCommandSender;
    }

    private SendGroupChatTextCommandSender(){
    }

    SendGroupChatTextRequestPacket packet;
    public SendGroupChatTextCommandSender getPacket(int groupid,String senderid ,String sendergroupname,
                                        String message ,String msgtype ,long time ){
        packet = new SendGroupChatTextRequestPacket( groupid, senderid , sendergroupname,
                                                      message , msgtype , time );
        return sendGroupChatTextCommandSender;
    }

    @Override
    public void execute(Channel channel) {
        if(channel==null||!channel.isActive()){
            return;
        }
        channel.writeAndFlush(packet);
    }

}
