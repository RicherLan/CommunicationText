package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.FreshAllFriendInfoRequestPacket;

public class FreshAllFriendInfoCommandSender implements CommandSender {

    static FreshAllFriendInfoCommandSender sender ;
    public static FreshAllFriendInfoCommandSender getInsatnce(){
        if(sender==null){
            sender = new FreshAllFriendInfoCommandSender();
        }
        return sender;
    }

    private FreshAllFriendInfoCommandSender(){
    }

    FreshAllFriendInfoRequestPacket packet;
    public FreshAllFriendInfoCommandSender getPacket(){
        packet = new FreshAllFriendInfoRequestPacket();

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
