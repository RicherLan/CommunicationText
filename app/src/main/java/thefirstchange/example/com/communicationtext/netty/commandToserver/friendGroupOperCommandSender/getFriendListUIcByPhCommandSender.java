package thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.GetFriendListUIcByPhRequestPacket;

/*
    进入好友列表   如果本地没有好友的头像   获取好友的的头像
 */
public class getFriendListUIcByPhCommandSender implements CommandSender {
    static getFriendListUIcByPhCommandSender sender ;
    public static getFriendListUIcByPhCommandSender getInsatnce(){
        if(sender==null){
            sender = new getFriendListUIcByPhCommandSender();
        }
        return sender;
    }

    private getFriendListUIcByPhCommandSender(){
    }

    GetFriendListUIcByPhRequestPacket packet;
    public getFriendListUIcByPhCommandSender getPacket(String ph){
        packet = new GetFriendListUIcByPhRequestPacket( ph );

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
