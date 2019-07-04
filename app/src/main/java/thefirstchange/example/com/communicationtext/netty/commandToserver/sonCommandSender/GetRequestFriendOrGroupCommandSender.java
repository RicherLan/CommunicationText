package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetRequestFriendOrGroupRequestPacket;

/*
拿到所有的好友、群请求信息  一般在刚登陆的时候
 */
public class GetRequestFriendOrGroupCommandSender implements CommandSender {

    static GetRequestFriendOrGroupCommandSender sender ;
    public static GetRequestFriendOrGroupCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetRequestFriendOrGroupCommandSender();
        }
        return sender;
    }

    private GetRequestFriendOrGroupCommandSender(){
    }

    GetRequestFriendOrGroupRequestPacket packet;
    public GetRequestFriendOrGroupCommandSender getPacket(){
        packet = new GetRequestFriendOrGroupRequestPacket();

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
