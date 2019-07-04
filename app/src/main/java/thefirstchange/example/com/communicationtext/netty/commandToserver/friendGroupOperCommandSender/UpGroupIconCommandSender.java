package thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.UpGroupIconRequestPacket;

/*
    更改群头像
 */
public class UpGroupIconCommandSender implements CommandSender {
    static UpGroupIconCommandSender sender ;
    public static UpGroupIconCommandSender getInsatnce(){
        if(sender==null){
            sender = new UpGroupIconCommandSender();
        }
        return sender;
    }

    private UpGroupIconCommandSender(){
    }

    UpGroupIconRequestPacket packet;
    public UpGroupIconCommandSender getPacket(int groupid,byte[] bs){
        packet = new UpGroupIconRequestPacket( groupid, bs );

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
