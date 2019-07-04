package thefirstchange.example.com.communicationtext.netty.commandToserver.corpCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.corpRequest.AppointCorpPosRequestPacket;

/*
    社团组织任命职位
 */
public class AppointCorpPosCommandSender implements CommandSender {
    static AppointCorpPosCommandSender sender ;
    public static AppointCorpPosCommandSender getInsatnce(){
        if(sender==null){
            sender = new AppointCorpPosCommandSender();
        }
        return sender;
    }

    private AppointCorpPosCommandSender(){
    }

    AppointCorpPosRequestPacket packet;
    public AppointCorpPosCommandSender getPacket(int groupid,String ph,String postype,String oldph){
        packet = new AppointCorpPosRequestPacket( groupid, ph, postype, oldph);

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
