package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.UpFriendRemarkRequestPacket;

/*
    修改好友的备注
 */
public class UpFriendRemarkCommandSender implements CommandSender {

    static UpFriendRemarkCommandSender sender ;
    public static UpFriendRemarkCommandSender getInsatnce(){
        if(sender==null){
            sender = new UpFriendRemarkCommandSender();
        }
        return sender;
    }

    private UpFriendRemarkCommandSender(){
    }

    UpFriendRemarkRequestPacket packet;
    public UpFriendRemarkCommandSender getPacket(String friendph ,String remark){
        packet = new UpFriendRemarkRequestPacket( friendph , remark);

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
