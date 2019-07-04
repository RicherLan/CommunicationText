package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetScoreOfUIDRequestPacket;

/*
    获取成绩
 */
public class GetScoreOfUIDCommandSender implements CommandSender {

    static GetScoreOfUIDCommandSender sender ;
    public static GetScoreOfUIDCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetScoreOfUIDCommandSender();
        }
        return sender;
    }

    private GetScoreOfUIDCommandSender(){
    }

    GetScoreOfUIDRequestPacket packet;
    public GetScoreOfUIDCommandSender getPacket(int xnm,int xqm,String count,String password){
        packet = new GetScoreOfUIDRequestPacket( xnm, xqm, count, password);

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
