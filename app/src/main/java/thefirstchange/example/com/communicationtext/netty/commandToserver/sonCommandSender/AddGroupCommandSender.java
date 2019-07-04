package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.AddGroupRequestPacket;

/*
申请加群
 */
public class AddGroupCommandSender implements CommandSender {

    static AddGroupCommandSender sender ;
    public static AddGroupCommandSender getInsatnce(){
        if(sender==null){
            sender = new AddGroupCommandSender();
        }
        return sender;
    }

    private AddGroupCommandSender(){
    }

    AddGroupRequestPacket packet;
    public AddGroupCommandSender getPacket(int groupid,String addmsg){
        packet = new AddGroupRequestPacket( groupid, addmsg);

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
