package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetGroupsInfoOfUser_REQUEST;


public class GetGroupsInfoOfUserRequestPacket extends Packet {
    String phonenumber;
    public GetGroupsInfoOfUserRequestPacket() {

	}
    public GetGroupsInfoOfUserRequestPacket(String phonenumber){
        this.phonenumber = phonenumber;
    }
    @Override
    public int getCommand() {

        return GetGroupsInfoOfUser_REQUEST;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
