package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.UpGroupPart_REQUEST;

/*
修改自己的部室
 */
public class UpGroupPartRequestPacket extends Packet {
    int groupid ;
    String part;
    public UpGroupPartRequestPacket() {

   	}
    public UpGroupPartRequestPacket(int groupid ,String part){
        this.groupid = groupid;
        this.part = part;
    }
    @Override
    public int getCommand() {

        return UpGroupPart_REQUEST;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }
}
