package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.ReciveFDeleteMeRequestPAcket;

/*
好友删除自己时   自己收到被删除的消息   要回执
 */
public class ReciveFDeleteMeCommandSender  implements CommandSender {

    static ReciveFDeleteMeCommandSender sender ;
    public static ReciveFDeleteMeCommandSender getInsatnce(){
        if(sender==null){
            sender = new ReciveFDeleteMeCommandSender();
        }
        return sender;
    }

    private ReciveFDeleteMeCommandSender(){
    }

    ReciveFDeleteMeRequestPAcket packet;
    public ReciveFDeleteMeCommandSender getPacket(int msgid){
        packet = new ReciveFDeleteMeRequestPAcket(msgid);

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
