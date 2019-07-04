package thefirstchange.example.com.communicationtext.netty.commandToserver.corpCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.corpRequest.DeleteCorpPartRequestPacket;

/*
    删除社团组织的某一个部门
 */
public class DeleteCorpPartCommandSender implements CommandSender{

    static DeleteCorpPartCommandSender sender ;
    public static DeleteCorpPartCommandSender getInsatnce(){
        if(sender==null){
            sender = new DeleteCorpPartCommandSender();
        }
        return sender;
    }

    private DeleteCorpPartCommandSender(){
    }

    DeleteCorpPartRequestPacket packet;
    public DeleteCorpPartCommandSender getPacket(int groupid,String name){
        packet = new DeleteCorpPartRequestPacket( groupid, name);

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
