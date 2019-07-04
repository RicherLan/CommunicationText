package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import  thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.DeleteFriend_REQUEST;


public class DeleteFriendRequestPacket extends Packet {
    String friendphonenumber;
    public DeleteFriendRequestPacket() {
    	
    }
    public DeleteFriendRequestPacket(String friendphonenumber){
        this.friendphonenumber = friendphonenumber;
    }
    @Override
    public int getCommand() {

        return DeleteFriend_REQUEST;
    }

    public String getFriendphonenumber() {
        return friendphonenumber;
    }

    public void setFriendphonenumber(String friendphonenumber) {
        this.friendphonenumber = friendphonenumber;
    }
}
