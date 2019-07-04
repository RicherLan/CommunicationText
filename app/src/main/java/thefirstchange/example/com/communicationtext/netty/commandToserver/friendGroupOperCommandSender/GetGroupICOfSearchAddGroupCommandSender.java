package thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.GetGroupICOfSearchAddGroupRequestPacket;

/*
    添加群时    首先查询     获得群的头像
 */
public class GetGroupICOfSearchAddGroupCommandSender implements CommandSender {
    static GetGroupICOfSearchAddGroupCommandSender sender ;
    public static GetGroupICOfSearchAddGroupCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetGroupICOfSearchAddGroupCommandSender();
        }
        return sender;
    }

    private GetGroupICOfSearchAddGroupCommandSender(){
    }

    GetGroupICOfSearchAddGroupRequestPacket packet;
    public GetGroupICOfSearchAddGroupCommandSender getPacket(int groupid){
        packet = new GetGroupICOfSearchAddGroupRequestPacket(groupid );

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
