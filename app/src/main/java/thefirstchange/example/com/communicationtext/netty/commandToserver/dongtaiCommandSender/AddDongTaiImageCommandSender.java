package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.AddDongTaiImageRequestPacket;

/*
    用户发表动态时   图片分开传送
 */
public class AddDongTaiImageCommandSender implements CommandSender {

    static AddDongTaiImageCommandSender sender ;
    public static AddDongTaiImageCommandSender getInsatnce(){
        if(sender==null){
            sender = new AddDongTaiImageCommandSender();
        }
        return sender;
    }

    private AddDongTaiImageCommandSender(){
    }

    AddDongTaiImageRequestPacket packet;
    public AddDongTaiImageCommandSender getPacket(int dongtaiid,long time,byte[] bytes){
        packet = new AddDongTaiImageRequestPacket( dongtaiid, time, bytes);

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
