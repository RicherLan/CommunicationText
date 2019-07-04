package thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.DongTaicommentComment_RESPONSE;

/*
        给动态的评论评论
 */
public class DongTaicommentCommentResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return DongTaicommentComment_RESPONSE;
    }

}
