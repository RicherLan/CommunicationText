package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.AddGroupReturnRequestPacket;

/*
    用户申请加群时   管理员同意或不同意   回
 */
public class AddGroupReturnCommandSender implements CommandSender {

    static AddGroupReturnCommandSender sender ;
    public static AddGroupReturnCommandSender getInsatnce(){
        if(sender==null){
            sender = new AddGroupReturnCommandSender();
        }
        return sender;
    }

    private AddGroupReturnCommandSender(){
    }

    AddGroupReturnRequestPacket packet;
    public AddGroupReturnCommandSender getPacket(int msgid,String otherphonenumber,int groupid,String rs){
        packet = new AddGroupReturnRequestPacket( msgid, otherphonenumber, groupid, rs);

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
