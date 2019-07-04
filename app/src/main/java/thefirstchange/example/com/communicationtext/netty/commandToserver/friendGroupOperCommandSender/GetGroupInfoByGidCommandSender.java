package thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.GetGroupInfoByGidRequestPacket;

/*
    获得用户的某群的信息   一般用在更新某群信息时
 */
public class GetGroupInfoByGidCommandSender implements CommandSender {
    static GetGroupInfoByGidCommandSender sender ;
    public static GetGroupInfoByGidCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetGroupInfoByGidCommandSender();
        }
        return sender;
    }

    private GetGroupInfoByGidCommandSender(){
    }

    GetGroupInfoByGidRequestPacket packet;
    public GetGroupInfoByGidCommandSender getPacket(int groupid){
        packet = new GetGroupInfoByGidRequestPacket(groupid );

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
