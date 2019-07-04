package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.HEARTBEAT_REQUEST;

public class HeartBeatRequestPacket extends Packet {
	public HeartBeatRequestPacket() {

	}

	@Override
	public int getCommand() {
		return HEARTBEAT_REQUEST;
	}
}
