package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import java.util.Vector;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.schedulearrangement.RequestSchedule;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.SchduleArrangementRequestPacket;

/*
安排值班表
 */

public class SchduleArrangementCommandSender implements CommandSender{

    static SchduleArrangementCommandSender sender ;
    public static SchduleArrangementCommandSender getInsatnce(){
        if(sender==null){
            sender = new SchduleArrangementCommandSender();
        }
        return sender;
    }

    private SchduleArrangementCommandSender(){
    }

    SchduleArrangementRequestPacket packet;
    public SchduleArrangementCommandSender getPacket(int account, int year, int xq, int bz, int xz, Vector<RequestSchedule> requestSchedules, String type){
        packet = new SchduleArrangementRequestPacket( account,  year,  xq,  bz,  xz,  requestSchedules,  type);

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
