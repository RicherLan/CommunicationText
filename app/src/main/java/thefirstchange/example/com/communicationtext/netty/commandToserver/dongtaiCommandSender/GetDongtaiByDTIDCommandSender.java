package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetDongtaiByDTIDRequestPacket;

/*
    请求某一条动态
 */
public class GetDongtaiByDTIDCommandSender implements CommandSender {
    static GetDongtaiByDTIDCommandSender sender ;
    public static GetDongtaiByDTIDCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetDongtaiByDTIDCommandSender();
        }
        return sender;
    }

    private GetDongtaiByDTIDCommandSender(){
    }

    GetDongtaiByDTIDRequestPacket packet;
    public GetDongtaiByDTIDCommandSender getPacket(int id){
        packet = new GetDongtaiByDTIDRequestPacket( id);

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
