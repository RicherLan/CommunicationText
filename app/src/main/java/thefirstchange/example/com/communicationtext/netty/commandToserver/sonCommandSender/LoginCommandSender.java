package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.LoginRequestPacket;

public class LoginCommandSender implements CommandSender {

    static LoginCommandSender sender ;
    public static LoginCommandSender getInsatnce(){
        if(sender==null){
            sender = new LoginCommandSender();
        }
        return sender;
    }

    private LoginCommandSender(){
    }

    LoginRequestPacket packet;
    public LoginCommandSender getPacket(String phonenumber,String password,String type){
        packet = new LoginRequestPacket();
        packet.setUserName(phonenumber);
        packet.setPassword(password);
        packet.setType(type);
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
