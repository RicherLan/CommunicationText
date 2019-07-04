package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.HEARTBEAT_RESPONSE;


public class HeartBeatResponsePacket extends Packet {
    @Override
    public int getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
