package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetPersonalInfoRequestPacket;

/*
获得个人信息     一般是刚登陆的时候
 */
public class GetPersonalInfoCommandSender implements CommandSender {

    static GetPersonalInfoCommandSender sender ;
    public static GetPersonalInfoCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetPersonalInfoCommandSender();
        }
        return sender;
    }

    private GetPersonalInfoCommandSender(){
    }

    GetPersonalInfoRequestPacket packet;
    public GetPersonalInfoCommandSender getPacket(){
        packet = new GetPersonalInfoRequestPacket();

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
