package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetSDOfGidRequestPacket;

/*
    获得自己加入的某个组织的值班表
 */
public class GetSDOfGidCommandSender implements CommandSender {

    static GetSDOfGidCommandSender sender ;
    public static GetSDOfGidCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetSDOfGidCommandSender();
        }
        return sender;
    }

    private GetSDOfGidCommandSender(){
    }

    GetSDOfGidRequestPacket packet;
    public GetSDOfGidCommandSender getPacket(int groupid,int dnid){
        packet = new GetSDOfGidRequestPacket( groupid, dnid);

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
