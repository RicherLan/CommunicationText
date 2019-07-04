package thefirstchange.example.com.communicationtext.netty.commandToserver.personInfoCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.personInfoRequest.GetIndexInfoOfPhRequestPacket;

/*
    进入某人的个人页面时  获得其基本信息
 */
public class GetIndexInfoOfPhCommandSender implements CommandSender {
    static GetIndexInfoOfPhCommandSender sender ;
    public static GetIndexInfoOfPhCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetIndexInfoOfPhCommandSender();
        }
        return sender;
    }

    private GetIndexInfoOfPhCommandSender(){
    }

    GetIndexInfoOfPhRequestPacket packet;
    public GetIndexInfoOfPhCommandSender getPacket(String phonenumber){
        packet = new GetIndexInfoOfPhRequestPacket( phonenumber);

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
