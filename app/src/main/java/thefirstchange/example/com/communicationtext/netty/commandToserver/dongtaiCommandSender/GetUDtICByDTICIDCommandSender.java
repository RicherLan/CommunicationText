package thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest.GetUDtICByDTICIDRequestPacket;

/*
    进入用户资料界面  请求某一条动态    获得图片
 */
public class GetUDtICByDTICIDCommandSender  implements CommandSender {
    static GetUDtICByDTICIDCommandSender sender ;
    public static GetUDtICByDTICIDCommandSender getInsatnce(){
        if(sender==null){
            sender = new GetUDtICByDTICIDCommandSender();
        }
        return sender;
    }

    private GetUDtICByDTICIDCommandSender(){
    }

    GetUDtICByDTICIDRequestPacket packet;
    public GetUDtICByDTICIDCommandSender getPacket(int dtid,String ph,int fileid){
        packet = new GetUDtICByDTICIDRequestPacket( dtid, ph, fileid);

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
