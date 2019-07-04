package thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.GetUIcOfAgreeYourFriendRequestPacket;

/*
    对方同意了你的好友请求   请求服务器获得对方头像s
 */
public class getUIcOfAgreeYourFriendCommandSender implements CommandSender {

    static getUIcOfAgreeYourFriendCommandSender sender ;
    public static getUIcOfAgreeYourFriendCommandSender getInsatnce(){
        if(sender==null){
            sender = new getUIcOfAgreeYourFriendCommandSender();
        }
        return sender;
    }

    private getUIcOfAgreeYourFriendCommandSender(){
    }

    GetUIcOfAgreeYourFriendRequestPacket packet;
    public getUIcOfAgreeYourFriendCommandSender getPacket(String phonenumber){
        packet = new GetUIcOfAgreeYourFriendRequestPacket(phonenumber );

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
