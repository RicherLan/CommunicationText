package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.SearchAddUserRequestPacket;

/*
客户端添加某人好友时   搜索账号 只需要看到其账号 网名 头像就行
 */
public class SearchAddUserCommandSender implements CommandSender {
    static SearchAddUserCommandSender sender ;
    public static SearchAddUserCommandSender getInsatnce(){
        if(sender==null){
            sender = new SearchAddUserCommandSender();
        }
        return sender;
    }

    private SearchAddUserCommandSender(){
    }

    SearchAddUserRequestPacket packet;
    public SearchAddUserCommandSender getPacket(String phonenumber){
        packet = new SearchAddUserRequestPacket(phonenumber);

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
