package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetDongtaiICByDTIDRequestPacket;

/*
    请求某一条动态  获得动态的图片
 */
public class GetDongtaiICByDTIDCommandSender implements CommandSender {
    static GetDongtaiICByDTIDCommandSender sender ;
    public static GetDongtaiICByDTIDCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetDongtaiICByDTIDCommandSender();
        }
        return sender;
    }

    private GetDongtaiICByDTIDCommandSender(){
    }

    GetDongtaiICByDTIDRequestPacket packet;
    public GetDongtaiICByDTIDCommandSender getPacket(int dtid,int fileid){
        packet = new GetDongtaiICByDTIDRequestPacket(  dtid, fileid);

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
