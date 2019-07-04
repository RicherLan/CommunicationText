package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetDTMsgByIdRequestPacket;

/*
        获得动态消息的内容
 */
public class GetDTMsgByIdCommandSender implements CommandSender {

    static GetDTMsgByIdCommandSender sender ;
    public static GetDTMsgByIdCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetDTMsgByIdCommandSender();
        }
        return sender;
    }

    private GetDTMsgByIdCommandSender(){
    }

    GetDTMsgByIdRequestPacket packet;
    public GetDTMsgByIdCommandSender getPacket(int id){
        packet = new GetDTMsgByIdRequestPacket( id);

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
