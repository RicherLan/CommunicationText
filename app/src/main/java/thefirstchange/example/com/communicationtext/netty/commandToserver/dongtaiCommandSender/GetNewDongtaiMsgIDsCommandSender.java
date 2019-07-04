package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetNewDongtaiMsgIDsRequestPacket;

/*
    用户下拉刷新动态消息的页面  就是加载新的动态消息        返回6条动态消息的id
 */
public class GetNewDongtaiMsgIDsCommandSender implements CommandSender {

    static GetNewDongtaiMsgIDsCommandSender sender ;
    public static GetNewDongtaiMsgIDsCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetNewDongtaiMsgIDsCommandSender();
        }
        return sender;
    }

    private GetNewDongtaiMsgIDsCommandSender(){
    }

    GetNewDongtaiMsgIDsRequestPacket packet;
    public GetNewDongtaiMsgIDsCommandSender getPacket(){
        packet = new GetNewDongtaiMsgIDsRequestPacket();

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
