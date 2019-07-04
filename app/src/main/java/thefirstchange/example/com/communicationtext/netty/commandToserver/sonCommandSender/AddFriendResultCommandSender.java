package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.AddFriendResultRequestPacket;

/*
添加好友时   对方同意或拒绝了你的请求   你已经读到了对方同意还是拒绝   给服务器回执
 */
public class AddFriendResultCommandSender implements CommandSender {

    static AddFriendResultCommandSender sender ;
    public static AddFriendResultCommandSender getInsatnce(){
        if(sender==null){
            sender = new AddFriendResultCommandSender();
        }
        return sender;
    }

    private AddFriendResultCommandSender(){
    }

    AddFriendResultRequestPacket packet;
    public AddFriendResultCommandSender getPacket(int msgid){
        packet = new AddFriendResultRequestPacket( msgid);

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
