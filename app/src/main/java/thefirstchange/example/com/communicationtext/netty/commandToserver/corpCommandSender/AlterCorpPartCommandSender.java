package thefirstchange.example.com.communicationtext.netty.commandToserver.corpCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.corpRequest.AlterCorpPartRequestPacket;

/*
    修改社团组织的某一个部门的名字
 */
public class AlterCorpPartCommandSender implements CommandSender {
    static AlterCorpPartCommandSender sender ;
    public static AlterCorpPartCommandSender getInsatnce(){
        if(sender==null){
            sender = new AlterCorpPartCommandSender();
        }
        return sender;
    }

    private AlterCorpPartCommandSender(){
    }

    AlterCorpPartRequestPacket packet;
    public AlterCorpPartCommandSender getPacket(int groupid,String oldname,String newname){
        packet = new AlterCorpPartRequestPacket( groupid, oldname, newname);

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
