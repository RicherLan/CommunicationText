package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import  thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static  thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AddFriend_REQUEST;

/*
添加好友
 */
public class AddFriendRequestPacket extends Packet {
    String friendid;
    String addmsg;
    public AddFriendRequestPacket() {

   	}
    public AddFriendRequestPacket(String friendid,String addmsg){
        this.friendid = friendid;
        this.addmsg = addmsg;
    }

    @Override
    public int getCommand() {

        return AddFriend_REQUEST;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }

    public String getAddmsg() {
        return addmsg;
    }

    public void setAddmsg(String addmsg) {
        this.addmsg = addmsg;
    }
}
