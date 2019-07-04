package thefirstchange.example.com.communicationtext.netty.commandToserver.corpCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.corpRequest.AddCorApPartRequestPacket;

/*
    添加社团组织的某一个部门
 */
public class AddCorApPartCommandSender implements CommandSender {
    static AddCorApPartCommandSender sender ;
    public static AddCorApPartCommandSender getInsatnce(){
        if(sender==null){
            sender = new AddCorApPartCommandSender();
        }
        return sender;
    }

    private AddCorApPartCommandSender(){
    }

    AddCorApPartRequestPacket packet;
    public AddCorApPartCommandSender getPacket(int groupid,String name){
        packet = new AddCorApPartRequestPacket( groupid, name);

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
