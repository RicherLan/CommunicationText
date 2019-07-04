package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetPerIconRequestPacket;

/*
获取自己的的头像
 */
public class GetPerIconCommandSender implements CommandSender {


    static GetPerIconCommandSender sender ;
    public static GetPerIconCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetPerIconCommandSender();
        }
        return sender;
    }

    private GetPerIconCommandSender(){
    }

    GetPerIconRequestPacket packet;
    public GetPerIconCommandSender getPacket( String phonenumber){
        packet = new GetPerIconRequestPacket( phonenumber);

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
