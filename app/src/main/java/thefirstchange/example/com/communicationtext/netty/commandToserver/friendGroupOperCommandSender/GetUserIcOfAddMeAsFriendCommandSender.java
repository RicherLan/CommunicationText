package thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.GetUserIcOfAddMeAsFriendRequestPacket;

/*
    添加好友  被添加方要获得对方的头像
 */
public class GetUserIcOfAddMeAsFriendCommandSender implements CommandSender {
    static GetUserIcOfAddMeAsFriendCommandSender sender ;
    public static GetUserIcOfAddMeAsFriendCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetUserIcOfAddMeAsFriendCommandSender();
        }
        return sender;
    }

    private GetUserIcOfAddMeAsFriendCommandSender(){
    }

    GetUserIcOfAddMeAsFriendRequestPacket packet;
    public GetUserIcOfAddMeAsFriendCommandSender getPacket(String phonenumber){
        packet = new GetUserIcOfAddMeAsFriendRequestPacket(phonenumber );

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
