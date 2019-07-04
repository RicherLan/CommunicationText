package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.IsPhonenumberRegistedRequestPacket;

/*
        用户注册账号时  判断提交的手机号是否已经被注册
 */
public class IsPhonenumberRegistedCommandSender implements CommandSender {

    static IsPhonenumberRegistedCommandSender sender ;
    public static IsPhonenumberRegistedCommandSender getInsatnce(){
        if(sender==null){
            sender = new IsPhonenumberRegistedCommandSender();
        }
        return sender;
    }

    private IsPhonenumberRegistedCommandSender(){
    }

    IsPhonenumberRegistedRequestPacket packet;
    public IsPhonenumberRegistedCommandSender getPacket(String phonenumber){
        packet = new IsPhonenumberRegistedRequestPacket( phonenumber);

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
