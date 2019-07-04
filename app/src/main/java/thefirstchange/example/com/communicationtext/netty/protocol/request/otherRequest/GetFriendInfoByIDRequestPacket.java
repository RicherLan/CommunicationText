package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import  thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static  thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetFriendInfoByID_REQUEST;

public class GetFriendInfoByIDRequestPacket extends Packet {

    String phonenumber;
    public GetFriendInfoByIDRequestPacket() {

	}
    public  GetFriendInfoByIDRequestPacket(String phonenumber){
        this.phonenumber = phonenumber;
    }

    @Override
    public int getCommand() {

        return GetFriendInfoByID_REQUEST;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
