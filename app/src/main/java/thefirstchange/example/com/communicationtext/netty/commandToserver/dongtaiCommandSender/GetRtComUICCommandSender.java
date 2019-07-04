package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetRtComUICRequestPacket;

/*
        进入某动态的所有评论界面    刚开始要刷新   返回根评论总共10条   大的评论下最多回执3条  获得头像
 */
public class GetRtComUICCommandSender implements CommandSender {
    static GetRtComUICCommandSender sender ;
    public static GetRtComUICCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetRtComUICCommandSender();
        }
        return sender;
    }

    private GetRtComUICCommandSender(){
    }

    GetRtComUICRequestPacket packet;
    public GetRtComUICCommandSender getPacket(String userid){
        packet = new GetRtComUICRequestPacket(userid);

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
