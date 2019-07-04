package thefirstchange.example.com.communicationtext.netty.commandToserver.userOtherInfoCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.userOtherInfoRequest.GetUserIcOfPhRequestPacket;

/*
    GetUserIcByPhCommandSender
 */
public class GetUserIcByPhCommandSender implements CommandSender {

    static GetUserIcByPhCommandSender sender ;
    public static GetUserIcByPhCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetUserIcByPhCommandSender();
        }
        return sender;
    }

    private GetUserIcByPhCommandSender(){
    }

    GetUserIcOfPhRequestPacket packet;
    public GetUserIcByPhCommandSender getPacket(String ph){
        packet = new GetUserIcOfPhRequestPacket( ph);

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
