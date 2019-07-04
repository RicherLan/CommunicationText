package thefirstchange.example.com.communicationtext.netty.commandToserver.personInfoCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.personInfoRequest.GetIndexICOfPhRequestPacket;

/*
    进入某人的个人页面时  获得其头像
 */
public class GetIndexICOfPhCommandSender implements CommandSender {
    static GetIndexICOfPhCommandSender sender ;
    public static GetIndexICOfPhCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetIndexICOfPhCommandSender();
        }
        return sender;
    }

    private GetIndexICOfPhCommandSender(){
    }

    GetIndexICOfPhRequestPacket packet;
    public GetIndexICOfPhCommandSender getPacket(String phonenumber){
        packet = new GetIndexICOfPhRequestPacket(phonenumber );

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
