package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.ReadSingleChatMsg_REQUEST;

public class ReceiveSingleChatTextRequestPacket extends Packet {

    String senderid;
    int msgid;

    public ReceiveSingleChatTextRequestPacket(String senderid, int msgid){
        this.senderid = senderid;
        this.msgid = msgid;

    }


    @Override
    public int getCommand() {

        return ReadSingleChatMsg_REQUEST;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }
}
