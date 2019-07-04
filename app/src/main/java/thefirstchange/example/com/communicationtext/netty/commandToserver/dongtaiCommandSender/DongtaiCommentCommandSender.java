package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.DongtaiCommentRequestPacket;

/*
    给动态评论
 */
public class DongtaiCommentCommandSender   implements CommandSender {
    static DongtaiCommentCommandSender sender ;
    public static DongtaiCommentCommandSender getInsatnce(){
        if(sender==null){
            sender = new DongtaiCommentCommandSender();
        }
        return sender;
    }

    private DongtaiCommentCommandSender(){
    }

    DongtaiCommentRequestPacket packet;
    public DongtaiCommentCommandSender getPacket(int dongtaiid,String msg){
        packet = new DongtaiCommentRequestPacket( dongtaiid, msg);

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
