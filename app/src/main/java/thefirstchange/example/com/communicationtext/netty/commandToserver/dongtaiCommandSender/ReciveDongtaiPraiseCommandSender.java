package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.ReciveDongtaiPraiseRequestPacket;

/*
    收到了别人给自己的动态的点赞  回执服务器已读
 */
public class ReciveDongtaiPraiseCommandSender implements CommandSender {

    static ReciveDongtaiPraiseCommandSender sender ;
    public static ReciveDongtaiPraiseCommandSender getInsatnce(){
        if(sender==null){
            sender = new ReciveDongtaiPraiseCommandSender();
        }
        return sender;
    }

    private ReciveDongtaiPraiseCommandSender(){
    }

    ReciveDongtaiPraiseRequestPacket packet;
    public ReciveDongtaiPraiseCommandSender getPacket(int id){
        packet = new ReciveDongtaiPraiseRequestPacket( id);

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
