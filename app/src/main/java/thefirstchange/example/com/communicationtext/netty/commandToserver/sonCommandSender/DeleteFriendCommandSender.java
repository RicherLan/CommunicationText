package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.DeleteFriendRequestPacket;

public class DeleteFriendCommandSender implements CommandSender {

    static DeleteFriendCommandSender sender ;
    public static DeleteFriendCommandSender getInsatnce(){
        if(sender==null){
            sender = new DeleteFriendCommandSender();
        }
        return sender;
    }

    private DeleteFriendCommandSender(){
    }

    DeleteFriendRequestPacket packet;
    public DeleteFriendCommandSender getPacket(String friendphonenumber){
        packet = new DeleteFriendRequestPacket(friendphonenumber);

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
