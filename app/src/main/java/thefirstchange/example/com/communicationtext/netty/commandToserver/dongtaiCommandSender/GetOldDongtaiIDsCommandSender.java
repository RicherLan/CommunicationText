package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetOldDongtaiIDsRequestPacket;

/*
    用户上拉刷新动态的页面  就是加载旧的动态        返回6条动态的id
    //从当前的dongtaiid开始往前找6条以前的
 */
public class GetOldDongtaiIDsCommandSender implements CommandSender {

    static GetOldDongtaiIDsCommandSender sender ;
    public static GetOldDongtaiIDsCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetOldDongtaiIDsCommandSender();
        }
        return sender;
    }

    private GetOldDongtaiIDsCommandSender(){
    }

    GetOldDongtaiIDsRequestPacket packet;
    public GetOldDongtaiIDsCommandSender getPacket(int dongtaiid){
        packet = new GetOldDongtaiIDsRequestPacket( dongtaiid);

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
