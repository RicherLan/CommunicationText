package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetUDtByDTIDRequestPacket;

/*
    进入用户资料界面  请求某一条动态
 */
public class GetUDtByDTIDCommandSender  implements CommandSender {

    static GetUDtByDTIDCommandSender sender ;
    public static GetUDtByDTIDCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetUDtByDTIDCommandSender();
        }
        return sender;
    }

    private GetUDtByDTIDCommandSender(){
    }

    GetUDtByDTIDRequestPacket packet;
    public GetUDtByDTIDCommandSender getPacket(String ph,int id){
        packet = new GetUDtByDTIDRequestPacket( ph, id);

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
