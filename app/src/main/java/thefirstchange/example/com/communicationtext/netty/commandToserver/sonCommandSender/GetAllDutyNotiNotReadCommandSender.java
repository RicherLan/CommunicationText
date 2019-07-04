package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;


import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetAllDutyNotiNotReadRequestPacket;

/*
    获得自己的未读的值班的通知
 */
public class GetAllDutyNotiNotReadCommandSender implements CommandSender{

    static GetAllDutyNotiNotReadCommandSender sender ;
    public static GetAllDutyNotiNotReadCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetAllDutyNotiNotReadCommandSender();
        }
        return sender;
    }

    private GetAllDutyNotiNotReadCommandSender(){
    }

    GetAllDutyNotiNotReadRequestPacket packet;
    public GetAllDutyNotiNotReadCommandSender getPacket(){
        packet = new GetAllDutyNotiNotReadRequestPacket();
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
