package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.LogoutRequestPacket;

public class LoginOutCommandSender implements CommandSender {

    static LoginOutCommandSender loginOutCommandSender ;
    public static LoginOutCommandSender getInsatnce(){
        if(loginOutCommandSender==null){
            loginOutCommandSender = new LoginOutCommandSender();
        }
        return loginOutCommandSender;
    }

    private LoginOutCommandSender(){
    }

    LogoutRequestPacket logoutRequestPacket;
    public LoginOutCommandSender getPacket(){
        logoutRequestPacket = new LogoutRequestPacket();
        return loginOutCommandSender;
    }

    @Override
    public void execute(Channel channel) {
        if(channel==null||!channel.isActive()){
            return;
        }
        channel.writeAndFlush(logoutRequestPacket);
    }

}
