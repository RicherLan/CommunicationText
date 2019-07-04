package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetFriendInfoByIDRequestPacket;

public class GetFriendInfoByIDCommandSender implements CommandSender {
    static GetFriendInfoByIDCommandSender sender ;
    public static GetFriendInfoByIDCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetFriendInfoByIDCommandSender();
        }
        return sender;
    }

    private GetFriendInfoByIDCommandSender(){
    }

    GetFriendInfoByIDRequestPacket packet;
    public GetFriendInfoByIDCommandSender getPacket(String phonenumber){
        packet = new GetFriendInfoByIDRequestPacket(phonenumber);

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
