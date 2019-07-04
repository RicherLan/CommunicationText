package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.AddGroupToGroupListRequestPacket;

/*
加群  或  创建群成功后    把该群的信息加入到自己的群列表中
 */
public class AddGroupToGroupListCommandSender  implements CommandSender {

    static AddGroupToGroupListCommandSender sender ;
    public static AddGroupToGroupListCommandSender getInsatnce(){
        if(sender==null){
            sender = new AddGroupToGroupListCommandSender();
        }
        return sender;
    }

    private AddGroupToGroupListCommandSender(){
    }

    AddGroupToGroupListRequestPacket packet;
    public AddGroupToGroupListCommandSender getPacket(int groupid ){
        packet = new AddGroupToGroupListRequestPacket( groupid );

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
