package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetAllFriendInfoRequestPacket;

public class GetAllFriendInfoCommandSender implements CommandSender {

    static GetAllFriendInfoCommandSender sender ;
    public static GetAllFriendInfoCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetAllFriendInfoCommandSender();
        }
        return sender;
    }

    private GetAllFriendInfoCommandSender(){
    }

    GetAllFriendInfoRequestPacket packet;
    public GetAllFriendInfoCommandSender getPacket(){
        packet = new GetAllFriendInfoRequestPacket();

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
