package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.DongTaicommentCommentRequestPacket;

/*
    给动态的评论评论
 */
public class DongTaicommentCommentCommandSender  implements CommandSender {

    static DongTaicommentCommentCommandSender sender ;
    public static DongTaicommentCommentCommandSender getInsatnce(){
        if(sender==null){
            sender = new DongTaicommentCommentCommandSender();
        }
        return sender;
    }

    private DongTaicommentCommentCommandSender(){
    }

    DongTaicommentCommentRequestPacket packet;
    public DongTaicommentCommentCommandSender getPacket(int dongtaiid,int commentid,String msg){
        packet = new DongTaicommentCommentRequestPacket( dongtaiid, commentid, msg);

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
