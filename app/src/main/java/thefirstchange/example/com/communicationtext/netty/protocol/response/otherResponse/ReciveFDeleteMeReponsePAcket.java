package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.ReciveFDeleteMe_RESPONSE;

/*
好友删除自己时   自己收到被删除的消息   要回执   服务器回执
 */
public class ReciveFDeleteMeReponsePAcket extends Packet {
    @Override
    public int getCommand() {

        return ReciveFDeleteMe_RESPONSE;
    }

}
