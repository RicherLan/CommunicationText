package thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.GetGroupIcByGidRequestPacket;

/*
    获得某群头像
 */
public class GetGroupIcByGidCommandSender implements CommandSender {
    static GetGroupIcByGidCommandSender sender ;
    public static GetGroupIcByGidCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetGroupIcByGidCommandSender();
        }
        return sender;
    }

    private GetGroupIcByGidCommandSender(){
    }

    GetGroupIcByGidRequestPacket packet;
    public GetGroupIcByGidCommandSender getPacket(int gid){
        packet = new GetGroupIcByGidRequestPacket( gid );

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
