package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetSingleChatMsgNotReadRequestPacket;

/*
拿到单人聊天  未读信息     一般是刚登陆的时候
 */
public class GetSingleChatMsgNotReadCommandSender implements CommandSender {
    static GetSingleChatMsgNotReadCommandSender sender ;
    public static GetSingleChatMsgNotReadCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetSingleChatMsgNotReadCommandSender();
        }
        return sender;
    }

    private GetSingleChatMsgNotReadCommandSender(){
    }

    GetSingleChatMsgNotReadRequestPacket packet;
    public GetSingleChatMsgNotReadCommandSender getPacket(){
        packet = new GetSingleChatMsgNotReadRequestPacket();

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
