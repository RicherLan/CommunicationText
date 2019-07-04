package thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.ReciveDongtaiComment_RESPONSE;

/*
        收到了别人给自己的动态的评论  回执服务器已读
 */
public class ReciveDongtaiCommentResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return ReciveDongtaiComment_RESPONSE;
    }


}
