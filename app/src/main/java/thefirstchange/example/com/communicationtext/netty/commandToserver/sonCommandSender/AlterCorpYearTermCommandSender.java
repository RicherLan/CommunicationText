package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.AlterCorpYearTermRequestPacket;

/*
    社团组织群管理修改学年学期
 */
public class AlterCorpYearTermCommandSender implements CommandSender {

    static AlterCorpYearTermCommandSender sender ;
    public static AlterCorpYearTermCommandSender getInsatnce(){
        if(sender==null){
            sender = new AlterCorpYearTermCommandSender();
        }
        return sender;
    }

    private AlterCorpYearTermCommandSender(){
    }

    AlterCorpYearTermRequestPacket packet;
    public AlterCorpYearTermCommandSender getPacket(int groupid,int year,int xueqi,int zhou){
        packet = new AlterCorpYearTermRequestPacket( groupid, year, xueqi, zhou);

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
