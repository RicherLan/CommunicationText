package thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.DongtaiPraise_RESPONSE;

/*
        用户给某一条动态点赞
 */
public class DongtaiPraiseResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return DongtaiPraise_RESPONSE;
    }

}
