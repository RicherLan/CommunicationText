package thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.DongtaiComment_RESPONSE;

/*
    给动态评论
 */
public class DongtaiCommentResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return DongtaiComment_RESPONSE;
    }

}
