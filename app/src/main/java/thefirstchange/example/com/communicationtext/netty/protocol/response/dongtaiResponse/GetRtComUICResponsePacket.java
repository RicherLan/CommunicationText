package thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetRtComUIC_RESPONSE;

/*
        进入某动态的所有评论界面    刚开始要刷新   返回根评论总共10条   大的评论下最多回执3条  获得头像
 */
public class GetRtComUICResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return GetRtComUIC_RESPONSE;
    }

}
