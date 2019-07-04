package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.FreshNotification_REQUEST;

public class FreshNotificationRequestPacket extends Packet {

    Vector<String> phs;
    public FreshNotificationRequestPacket() {
    	
    }
    @Override
    public int getCommand() {

        return FreshNotification_REQUEST;
    }

    public Vector<String> getPhs() {
        return phs;
    }

    public void setPhs(Vector<String> phs) {
        this.phs = phs;
    }
}
