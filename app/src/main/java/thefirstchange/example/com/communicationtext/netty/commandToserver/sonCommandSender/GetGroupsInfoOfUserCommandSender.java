package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetGroupsInfoOfUserRequestPacket;

public class GetGroupsInfoOfUserCommandSender implements CommandSender {


    static GetGroupsInfoOfUserCommandSender sender ;
    public static GetGroupsInfoOfUserCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetGroupsInfoOfUserCommandSender();
        }
        return sender;
    }

    private GetGroupsInfoOfUserCommandSender(){
    }

    GetGroupsInfoOfUserRequestPacket packet;
    public GetGroupsInfoOfUserCommandSender getPacket(String phonenumber){
        packet = new GetGroupsInfoOfUserRequestPacket(phonenumber);

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
