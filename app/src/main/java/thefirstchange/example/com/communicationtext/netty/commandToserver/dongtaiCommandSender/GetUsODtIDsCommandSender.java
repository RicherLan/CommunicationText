package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetUsODtIDsRequestPacket;

/*
    进入用户资料界面   用户上拉刷新动态的页面  就是加载旧的动态        返回6条动态的id
    //从当前的dongtaiid开始往前找6条以前的
 */
public class GetUsODtIDsCommandSender implements CommandSender {
    static GetUsODtIDsCommandSender sender ;
    public static GetUsODtIDsCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetUsODtIDsCommandSender();
        }
        return sender;
    }

    private GetUsODtIDsCommandSender(){
    }

    GetUsODtIDsRequestPacket packet;
    public GetUsODtIDsCommandSender getPacket(String ph,int dongtaiid){
        packet = new GetUsODtIDsRequestPacket( ph, dongtaiid);

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
