package thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.friendGroupOperRequest.GetUserIcOfAddGroupRequestPacket;

/*
    添加群  群管理员要获得对方的头像
 */
public class GetUserIcOfAddGroupCommandSender implements CommandSender {

    static GetUserIcOfAddGroupCommandSender sender ;
    public static GetUserIcOfAddGroupCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetUserIcOfAddGroupCommandSender();
        }
        return sender;
    }

    private GetUserIcOfAddGroupCommandSender(){
    }

    GetUserIcOfAddGroupRequestPacket packet;
    public GetUserIcOfAddGroupCommandSender getPacket(int groupid,String ph){
        packet = new GetUserIcOfAddGroupRequestPacket( groupid,ph );

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
