package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetUNDtIDsRequestPacket;

/*
    进入用户资料界面  用户下拉刷新动态的页面  就是加载新的动态        返回6条动态的id    ph2是要查询的人的账号
 */
public class GetUNDtIDsCommandSender implements CommandSender {

    static GetUNDtIDsCommandSender sender ;
    public static GetUNDtIDsCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetUNDtIDsCommandSender();
        }
        return sender;
    }

    private GetUNDtIDsCommandSender(){
    }

    GetUNDtIDsRequestPacket packet;
    public GetUNDtIDsCommandSender getPacket(String phonenumber){
        packet = new GetUNDtIDsRequestPacket(phonenumber);

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
