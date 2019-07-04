package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.AddFriendRequestPacket;

public class AddFriendCommandSender implements CommandSender {

    static AddFriendCommandSender sender ;
    public static AddFriendCommandSender getInsatnce(){
        if(sender==null){
            sender = new AddFriendCommandSender();
        }
        return sender;
    }

    private AddFriendCommandSender(){
    }

    AddFriendRequestPacket packet;
    public AddFriendCommandSender getPacket(String friendid,String addmsg){
        packet = new AddFriendRequestPacket( friendid, addmsg);

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
