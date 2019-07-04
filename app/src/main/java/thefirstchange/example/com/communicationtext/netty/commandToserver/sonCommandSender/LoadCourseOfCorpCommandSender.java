package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.LoadCourseOfCorpRequestPacket;

/*
    社团组织成员导入自己的课表
 */
public class LoadCourseOfCorpCommandSender implements CommandSender {

    static LoadCourseOfCorpCommandSender sender ;
    public static LoadCourseOfCorpCommandSender getInsatnce(){
        if(sender==null){
            sender = new LoadCourseOfCorpCommandSender();
        }
        return sender;
    }

    private LoadCourseOfCorpCommandSender(){
    }

    LoadCourseOfCorpRequestPacket packet;
    public LoadCourseOfCorpCommandSender getPacket(int year,int xueqi,String count,String pwd){
        packet = new LoadCourseOfCorpRequestPacket( year, xueqi, count, pwd);

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
