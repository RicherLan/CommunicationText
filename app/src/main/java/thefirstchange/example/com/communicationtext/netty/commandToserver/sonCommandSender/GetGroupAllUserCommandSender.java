package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetGroupAllUserRequestPacket;

/*
    获得某群的所有成员   没有头像
 */
public class GetGroupAllUserCommandSender implements CommandSender {
    static GetGroupAllUserCommandSender sender ;
    public static GetGroupAllUserCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetGroupAllUserCommandSender();
        }
        return sender;
    }

    private GetGroupAllUserCommandSender(){
    }

    GetGroupAllUserRequestPacket packet;
    public GetGroupAllUserCommandSender getPacket(int groupid,String type){
        packet = new GetGroupAllUserRequestPacket( groupid,type);

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
