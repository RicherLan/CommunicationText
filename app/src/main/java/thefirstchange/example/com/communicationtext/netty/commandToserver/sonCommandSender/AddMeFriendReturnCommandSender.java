package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.AddMeFriendReturnRequestPacket;

public class AddMeFriendReturnCommandSender implements CommandSender {

    static AddMeFriendReturnCommandSender sender ;
    public static AddMeFriendReturnCommandSender getInsatnce(){
        if(sender==null){
            sender = new AddMeFriendReturnCommandSender();
        }
        return sender;
    }

    private AddMeFriendReturnCommandSender(){
    }

    AddMeFriendReturnRequestPacket packet;
    public AddMeFriendReturnCommandSender getPacket(int msgid,String otherphonenumber,String othernickname,String result){
        packet = new AddMeFriendReturnRequestPacket( msgid, otherphonenumber, othernickname, result);

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
