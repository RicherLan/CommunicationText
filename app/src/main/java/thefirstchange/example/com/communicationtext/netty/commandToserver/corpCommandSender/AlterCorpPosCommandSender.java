package thefirstchange.example.com.communicationtext.netty.commandToserver.corpCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.corpRequest.AlterCorpPosRequestPacket;

/*
    修改自己在社团的职位
 */
public class AlterCorpPosCommandSender implements CommandSender {
    static AlterCorpPosCommandSender sender ;
    public static AlterCorpPosCommandSender getInsatnce(){
        if(sender==null){
            sender = new AlterCorpPosCommandSender();
        }
        return sender;
    }

    private AlterCorpPosCommandSender(){
    }

    AlterCorpPosRequestPacket packet;
    public AlterCorpPosCommandSender getPacket(int groupid,String newname){
        packet = new AlterCorpPosRequestPacket( groupid, newname);

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
