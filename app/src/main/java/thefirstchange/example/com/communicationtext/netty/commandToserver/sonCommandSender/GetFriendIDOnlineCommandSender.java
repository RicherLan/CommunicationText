package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetFriendIDOnlineRequestPacket;

public class GetFriendIDOnlineCommandSender implements CommandSender {

    static GetFriendIDOnlineCommandSender sender ;
    public static GetFriendIDOnlineCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetFriendIDOnlineCommandSender();
        }
        return sender;
    }

    private GetFriendIDOnlineCommandSender(){
    }

    GetFriendIDOnlineRequestPacket packet;
    public GetFriendIDOnlineCommandSender getPacket(){
        packet = new GetFriendIDOnlineRequestPacket();

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
