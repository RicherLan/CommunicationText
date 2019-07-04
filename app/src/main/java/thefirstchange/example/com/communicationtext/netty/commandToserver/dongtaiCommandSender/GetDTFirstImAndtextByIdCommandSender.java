package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetDTFirstImAndtextByIdRequestPacket;

/*
        获得动态的第一张图片和内容
 */
public class GetDTFirstImAndtextByIdCommandSender implements CommandSender {

    static GetDTFirstImAndtextByIdCommandSender sender ;
    public static GetDTFirstImAndtextByIdCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetDTFirstImAndtextByIdCommandSender();
        }
        return sender;
    }

    private GetDTFirstImAndtextByIdCommandSender(){
    }

    GetDTFirstImAndtextByIdRequestPacket packet;
    public GetDTFirstImAndtextByIdCommandSender getPacket(int id){
        packet = new GetDTFirstImAndtextByIdRequestPacket( id);

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
