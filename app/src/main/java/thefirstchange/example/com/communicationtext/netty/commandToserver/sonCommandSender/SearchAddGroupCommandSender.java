package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.SearchAddGroupRequestPacket;

/*
客户端添加群时   搜索账号 只需要看到其账号 网名 头像就行
 */
public class SearchAddGroupCommandSender implements CommandSender {

    static SearchAddGroupCommandSender sender ;
    public static SearchAddGroupCommandSender getInsatnce(){
        if(sender==null){
            sender = new SearchAddGroupCommandSender();
        }
        return sender;
    }

    private SearchAddGroupCommandSender(){
    }

    SearchAddGroupRequestPacket packet;
    public SearchAddGroupCommandSender getPacket(int groupid){
        packet = new SearchAddGroupRequestPacket( groupid);

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
