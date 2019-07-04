package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.RegisterTestRequestPacket;

public class RegisterTestCommandSender implements CommandSender {

    static RegisterTestCommandSender registerTestCommandSender ;
    public static RegisterTestCommandSender getInsatnce(){
        if(registerTestCommandSender==null){
            registerTestCommandSender = new RegisterTestCommandSender();
        }
        return registerTestCommandSender;
    }

    private RegisterTestCommandSender(){
    }

    RegisterTestRequestPacket registerTestRequestPacket;
    public RegisterTestCommandSender getPacket(String schoolname,String collegename,String majorname,int ruxueyear,String phonenumber,String password){
        registerTestRequestPacket = new RegisterTestRequestPacket( schoolname, collegename, majorname, ruxueyear, phonenumber, password);

        return registerTestCommandSender;
    }

    @Override
    public void execute(Channel channel) {
        if(channel==null||!channel.isActive()){
            return;
        }
        channel.writeAndFlush(registerTestRequestPacket);
    }

}
