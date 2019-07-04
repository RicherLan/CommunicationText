package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.UpPasswordRequestPacket;

public class UpPasswordCommandSender implements CommandSender {

    static UpPasswordCommandSender upPasswordCommandSender ;
    public static UpPasswordCommandSender getInsatnce(){
        if(upPasswordCommandSender==null){
            upPasswordCommandSender = new UpPasswordCommandSender();
        }
        return upPasswordCommandSender;
    }

    private UpPasswordCommandSender(){
    }

    UpPasswordRequestPacket upPasswordRequestPacket;
    public UpPasswordCommandSender getPacket(String oldpassword,String newpassword){
        upPasswordRequestPacket = new UpPasswordRequestPacket(oldpassword,newpassword);

        return upPasswordCommandSender;
    }

    @Override
    public void execute(Channel channel) {
        if(channel==null||!channel.isActive()){
            return;
        }
        channel.writeAndFlush(upPasswordRequestPacket);
    }

}
