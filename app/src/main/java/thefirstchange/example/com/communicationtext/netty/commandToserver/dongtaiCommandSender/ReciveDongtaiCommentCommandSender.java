package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.ReciveDongtaiCommentRequestPacket;

/*
    收到了别人给自己的动态的评论  回执服务器已读
 */
public class ReciveDongtaiCommentCommandSender implements CommandSender {

    static ReciveDongtaiCommentCommandSender sender ;
    public static ReciveDongtaiCommentCommandSender getInsatnce(){
        if(sender==null){
            sender = new ReciveDongtaiCommentCommandSender();
        }
        return sender;
    }

    private ReciveDongtaiCommentCommandSender(){
    }

    ReciveDongtaiCommentRequestPacket packet;
    public ReciveDongtaiCommentCommandSender getPacket(int id){
        packet = new ReciveDongtaiCommentRequestPacket( id);

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
