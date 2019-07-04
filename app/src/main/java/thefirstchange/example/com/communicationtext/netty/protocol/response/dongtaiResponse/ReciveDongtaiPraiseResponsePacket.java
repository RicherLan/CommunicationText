package thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.ReciveDongtaiPraise_RESPONSE;

/*

        收到了别人给自己的动态的点赞  回执服务器已读
 */
public class ReciveDongtaiPraiseResponsePacket extends Packet {


    @Override
    public int getCommand() {

        return ReciveDongtaiPraise_RESPONSE;
    }

}
