package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.FreshNotification_RESPONSE;

public class FreshNotificationResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return FreshNotification_RESPONSE;
    }

}
