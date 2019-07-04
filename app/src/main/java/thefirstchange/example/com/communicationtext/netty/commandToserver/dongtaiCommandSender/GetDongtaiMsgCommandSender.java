package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetDongtaiMsgRequestPacket;

/*
    拿到自己的动态消息  一般是刚上线的时候
 */
public class GetDongtaiMsgCommandSender implements CommandSender {
    static GetDongtaiMsgCommandSender sender ;
    public static GetDongtaiMsgCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetDongtaiMsgCommandSender();
        }
        return sender;
    }

    private GetDongtaiMsgCommandSender(){
    }

    GetDongtaiMsgRequestPacket packet;
    public GetDongtaiMsgCommandSender getPacket(){
        packet = new GetDongtaiMsgRequestPacket();

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
