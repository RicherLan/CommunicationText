package thefirstchange.example.com.communicationtext.netty.commandToserver.eduCommandSender;

import java.util.Vector;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.edu.SearchEmptyClassroomRequestPacket;

/*
    查询空教室
 */
public class SearchEmptyClassRoomCommandSender implements CommandSender {
    static SearchEmptyClassRoomCommandSender sender ;
    public static SearchEmptyClassRoomCommandSender getInsatnce(){
        if(sender==null){
            sender = new SearchEmptyClassRoomCommandSender();
        }
        return sender;
    }

    private SearchEmptyClassRoomCommandSender(){
    }

    SearchEmptyClassroomRequestPacket packet;
    public SearchEmptyClassRoomCommandSender getPacket( int xnm ,int xqm, String lh,int zcd ,int xqj,Vector<Integer> jieshu){
        packet = new SearchEmptyClassroomRequestPacket( xnm , xqm,  lh, zcd , xqj, jieshu);

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
