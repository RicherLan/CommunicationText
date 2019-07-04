package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import java.util.Vector;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.gson.SearchEmptyCourse;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.SearchEmptyCourseRequestPacket;

/*
    社团组织获得   获得某几节课都有空的人
 */
public class SearchEmptyCourseCommandSender implements CommandSender {

    static SearchEmptyCourseCommandSender sender ;
    public static SearchEmptyCourseCommandSender getInsatnce(){
        if(sender==null){
            sender = new SearchEmptyCourseCommandSender();
        }
        return sender;
    }

    private SearchEmptyCourseCommandSender(){
    }

    SearchEmptyCourseRequestPacket packet;
    public SearchEmptyCourseCommandSender getPacket(int groupid, int year,int xueqi ,Vector<SearchEmptyCourse> searchEmptyCourses){
        packet = new SearchEmptyCourseRequestPacket( groupid,  year, xueqi , searchEmptyCourses);

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
