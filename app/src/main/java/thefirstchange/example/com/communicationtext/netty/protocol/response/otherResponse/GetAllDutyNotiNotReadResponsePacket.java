package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetAllDutyNotiNotRead_RESPONSE;

/*
    获得自己的未读的值班的通知
 */
public class GetAllDutyNotiNotReadResponsePacket extends Packet {

    @Override
    public int getCommand() {
        return GetAllDutyNotiNotRead_RESPONSE;
    }

}
