package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.GetCourseSchByUIDRequestPacket;

/*
    获取课表
 */
public class GetCourseSchByUIDCommandSender implements CommandSender {

    static GetCourseSchByUIDCommandSender sender ;
    public static GetCourseSchByUIDCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetCourseSchByUIDCommandSender();
        }
        return sender;
    }

    private GetCourseSchByUIDCommandSender(){
    }


    GetCourseSchByUIDRequestPacket packet;
    public GetCourseSchByUIDCommandSender getPacket( int xnm,int xqm,String count,String password){
        packet = new GetCourseSchByUIDRequestPacket(xnm, xqm, count, password);

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
