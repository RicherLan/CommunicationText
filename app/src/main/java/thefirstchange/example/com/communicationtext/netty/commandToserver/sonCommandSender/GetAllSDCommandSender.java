package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetAllSDRequestPacket;

/*
    获得自己加入的所有组织的值班表
 */
public class GetAllSDCommandSender implements CommandSender{

    static GetAllSDCommandSender sender ;
    public static GetAllSDCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetAllSDCommandSender();
        }
        return sender;
    }

    private GetAllSDCommandSender(){
    }

    GetAllSDRequestPacket packet;
    public GetAllSDCommandSender getPacket(){
        packet = new GetAllSDRequestPacket();

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
