package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetGroupChatMsgNotReadRequestPacket;

/*
拿到群聊天  未读信息     一般是刚登陆的时候
 */
public class GetGroupChatMsgNotReadCommandSender implements CommandSender {
    static GetGroupChatMsgNotReadCommandSender sender ;
    public static GetGroupChatMsgNotReadCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetGroupChatMsgNotReadCommandSender();
        }
        return sender;
    }

    private GetGroupChatMsgNotReadCommandSender(){
    }

    GetGroupChatMsgNotReadRequestPacket packet;
    public GetGroupChatMsgNotReadCommandSender getPacket(){
        packet = new GetGroupChatMsgNotReadRequestPacket();

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
