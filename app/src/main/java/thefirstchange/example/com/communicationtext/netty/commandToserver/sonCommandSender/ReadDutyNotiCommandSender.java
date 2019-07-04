package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.ReadDutyNotiRequestPacket;

/*
    已经读取值班的通知   向服务器反馈
 */
public class ReadDutyNotiCommandSender implements CommandSender {
    static ReadDutyNotiCommandSender sender ;
    public static ReadDutyNotiCommandSender getInsatnce(){
        if(sender==null){
            sender = new ReadDutyNotiCommandSender();
        }
        return sender;
    }

    private ReadDutyNotiCommandSender(){
    }

    ReadDutyNotiRequestPacket packet;
    public ReadDutyNotiCommandSender getPacket(int dnid,int gid){
        packet = new ReadDutyNotiRequestPacket( dnid, gid);

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
