package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetDongtaiUICByDTID_RequestPacket;

/*
    请求某一条动态  获得动态主人头像
 */
public class GetDongtaiUICByDTID_RequestHandler implements CommandSender {
    static GetDongtaiUICByDTID_RequestHandler sender ;
    public static GetDongtaiUICByDTID_RequestHandler getInsatnce(){
        if(sender==null){
            sender = new GetDongtaiUICByDTID_RequestHandler();
        }
        return sender;
    }

    private GetDongtaiUICByDTID_RequestHandler(){
    }

    GetDongtaiUICByDTID_RequestPacket packet;
    public GetDongtaiUICByDTID_RequestHandler getPacket(String ph,int dtid){
        packet = new GetDongtaiUICByDTID_RequestPacket(ph,dtid);

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
