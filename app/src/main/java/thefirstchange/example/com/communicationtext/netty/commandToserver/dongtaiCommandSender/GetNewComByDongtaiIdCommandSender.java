package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetNewComByDongtaiIdRequestPacket;

/*
        进入某动态的所有评论界面    刚开始要刷新   返回根评论总共10条   大的评论下最多回执3条
 */
public class GetNewComByDongtaiIdCommandSender implements CommandSender {

    static GetNewComByDongtaiIdCommandSender sender ;
    public static GetNewComByDongtaiIdCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetNewComByDongtaiIdCommandSender();
        }
        return sender;
    }

    private GetNewComByDongtaiIdCommandSender(){
    }

    GetNewComByDongtaiIdRequestPacket packet;
    public GetNewComByDongtaiIdCommandSender getPacket(int dongtaiid){
        packet = new GetNewComByDongtaiIdRequestPacket( dongtaiid);

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
