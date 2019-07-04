package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.AddDongtaiRequestPacket;

/*
        发表动态    普通用户的话  类型用user表示
 */
public class AddDongtaiCommandSender implements CommandSender {

    static AddDongtaiCommandSender sender ;
    public static AddDongtaiCommandSender getInsatnce(){
        if(sender==null){
            sender = new AddDongtaiCommandSender();
        }
        return sender;
    }

    private AddDongtaiCommandSender(){
    }

    AddDongtaiRequestPacket packet;
    public AddDongtaiCommandSender getPacket(String usertype,String text,int picturenum,long time){
        packet = new AddDongtaiRequestPacket( usertype, text, picturenum, time);

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
