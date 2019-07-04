package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.UpGroupRemarkRequestPacket;

/*
修改自己的群名片
 */
public class UpGroupRemarkCommandSender  implements CommandSender {
    static UpGroupRemarkCommandSender sender ;
    public static UpGroupRemarkCommandSender getInsatnce(){
        if(sender==null){
            sender = new UpGroupRemarkCommandSender();
        }
        return sender;
    }

    private UpGroupRemarkCommandSender(){
    }

    UpGroupRemarkRequestPacket packet;
    public UpGroupRemarkCommandSender getPacket(int groupid ,String remark){
        packet = new UpGroupRemarkRequestPacket( groupid , remark);

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
