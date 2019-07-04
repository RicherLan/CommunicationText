package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetOldComByDongtaiIdRequestPacket;

/*
        进入某动态的所有评论界面  上拉刷新   返回根评论总共10条   大的评论下最多回执3条  没有头像
 */
public class GetOldComByDongtaiIdCommandSender implements CommandSender {
    static GetOldComByDongtaiIdCommandSender sender ;
    public static GetOldComByDongtaiIdCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetOldComByDongtaiIdCommandSender();
        }
        return sender;
    }

    private GetOldComByDongtaiIdCommandSender(){
    }

    GetOldComByDongtaiIdRequestPacket packet;
    public GetOldComByDongtaiIdCommandSender getPacket(String ph,int dtid,int comid){
        packet = new GetOldComByDongtaiIdRequestPacket( ph, dtid, comid);

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
