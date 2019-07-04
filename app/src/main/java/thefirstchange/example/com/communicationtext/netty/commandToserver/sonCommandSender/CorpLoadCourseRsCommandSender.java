package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.CorpLoadCourseRsRequestPacket;

/*
    社团组织查看课表导入情况
 */
public class CorpLoadCourseRsCommandSender implements CommandSender {


    static CorpLoadCourseRsCommandSender sender ;
    public static CorpLoadCourseRsCommandSender getInsatnce(){
        if(sender==null){
            sender = new CorpLoadCourseRsCommandSender();
        }
        return sender;
    }

    private CorpLoadCourseRsCommandSender(){
    }

    CorpLoadCourseRsRequestPacket packet;
    public CorpLoadCourseRsCommandSender getPacket(int groupid){
        packet = new CorpLoadCourseRsRequestPacket( groupid);

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
