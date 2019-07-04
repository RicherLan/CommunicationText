package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.ReadDutyNoti_RESPONSE;

/*
    已经读取值班的通知   向服务器反馈
 */
public class ReadDutyNotiResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return ReadDutyNoti_RESPONSE;
    }

}
