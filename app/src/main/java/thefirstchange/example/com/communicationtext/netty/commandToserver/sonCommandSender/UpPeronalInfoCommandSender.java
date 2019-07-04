package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.UpPeronalInfoRequestPacket;

/*
    修改个人资料
 */
public class UpPeronalInfoCommandSender implements CommandSender {

    static UpPeronalInfoCommandSender sender ;
    public static UpPeronalInfoCommandSender getInsatnce(){
        if(sender==null){
            sender = new UpPeronalInfoCommandSender();
        }
        return sender;
    }

    private UpPeronalInfoCommandSender(){
    }

    UpPeronalInfoRequestPacket packet;
    public UpPeronalInfoCommandSender getPacket(String nickname,String sex,String from,String add,String sch,String dep,String maj,String bir,int rxy,String info){
        packet = new UpPeronalInfoRequestPacket( nickname, sex, from, add, sch, dep, maj, bir, rxy, info);

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
