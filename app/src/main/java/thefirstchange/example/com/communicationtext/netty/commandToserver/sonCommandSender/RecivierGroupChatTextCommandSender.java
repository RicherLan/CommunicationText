package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.ReceiveGroupChatTextRequestPacket;

public class RecivierGroupChatTextCommandSender implements CommandSender {
    static RecivierGroupChatTextCommandSender sender ;
    public static RecivierGroupChatTextCommandSender getInsatnce(){
        if(sender==null){
            sender = new RecivierGroupChatTextCommandSender();
        }
        return sender;
    }

    private RecivierGroupChatTextCommandSender(){
    }

    ReceiveGroupChatTextRequestPacket packet;
    public RecivierGroupChatTextCommandSender getPacket(int msgid,int groupid){
        packet = new ReceiveGroupChatTextRequestPacket( msgid, groupid);

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
