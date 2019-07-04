package thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetGroupUserIconByPhRequestPacket;

/*
    获得群成员头像
 */
public class GetGroupUserIconByPhCommandSender implements CommandSender {
    static GetGroupUserIconByPhCommandSender sender ;
    public static GetGroupUserIconByPhCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetGroupUserIconByPhCommandSender();
        }
        return sender;
    }

    private GetGroupUserIconByPhCommandSender(){
    }

    GetGroupUserIconByPhRequestPacket packet;
    public GetGroupUserIconByPhCommandSender getPacket(int groupid,String ph,String type){
        packet = new GetGroupUserIconByPhRequestPacket(groupid ,ph,type);

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
