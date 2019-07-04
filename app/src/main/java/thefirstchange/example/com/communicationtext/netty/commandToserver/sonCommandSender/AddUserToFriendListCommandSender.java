package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.AddUserToFriendListRequestPacket;

/*
客户端添加好友成功后  将好友加入到自己的好友列表  因此需要好友的资料
 */
public class AddUserToFriendListCommandSender implements CommandSender {
    static AddUserToFriendListCommandSender sender ;
    public static AddUserToFriendListCommandSender getInsatnce(){
        if(sender==null){
            sender = new AddUserToFriendListCommandSender();
        }
        return sender;
    }

    private AddUserToFriendListCommandSender(){
    }

    AddUserToFriendListRequestPacket packet;
    public AddUserToFriendListCommandSender getPacket(String phonenumber){
        packet = new AddUserToFriendListRequestPacket(phonenumber);

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
