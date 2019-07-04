package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetOldDongtaiMsgIDsRequestPacket;

/*
        用户上拉刷新动态消息的页面  就是加载旧的动态消息        返回6条动态消息的id
    //从当前的id开始往前找6条以前的
 */
public class GetOldDongtaiMsgIDsCommandSender implements CommandSender {

    static GetOldDongtaiMsgIDsCommandSender sender ;
    public static GetOldDongtaiMsgIDsCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetOldDongtaiMsgIDsCommandSender();
        }
        return sender;
    }

    private GetOldDongtaiMsgIDsCommandSender(){
    }

    GetOldDongtaiMsgIDsRequestPacket packet;
    public GetOldDongtaiMsgIDsCommandSender getPacket(int dongtaimsgid){
        packet = new GetOldDongtaiMsgIDsRequestPacket( dongtaimsgid);

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
