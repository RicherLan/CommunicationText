package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.DongtaiPraiseRequestPacket;

/*
        用户给某一条动态点赞
 */
public class DongtaiPraiseCommandSender implements CommandSender {
    static DongtaiPraiseCommandSender sender ;
    public static DongtaiPraiseCommandSender getInsatnce(){
        if(sender==null){
            sender = new DongtaiPraiseCommandSender();
        }
        return sender;
    }

    private DongtaiPraiseCommandSender(){
    }

    DongtaiPraiseRequestPacket packet;
    public DongtaiPraiseCommandSender getPacket(int dongtaiid){
        packet = new DongtaiPraiseRequestPacket( dongtaiid);

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
