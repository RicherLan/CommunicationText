package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetNewDongtaiIDsRequestPacket;

/*
    用户下拉刷新动态的页面  就是加载新的动态        返回6条动态的id
 */
public class GetnewDongtaiIDsCommandSender implements CommandSender {

    static GetnewDongtaiIDsCommandSender sender ;
    public static GetnewDongtaiIDsCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetnewDongtaiIDsCommandSender();
        }
        return sender;
    }

    private GetnewDongtaiIDsCommandSender(){
    }

    GetNewDongtaiIDsRequestPacket packet;
    public GetnewDongtaiIDsCommandSender getPacket(){
        packet = new GetNewDongtaiIDsRequestPacket();

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
