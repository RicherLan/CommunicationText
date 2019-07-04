package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import  thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.AddGroup_REQUEST;
/*
申请加群
 */
public class AddGroupRequestPacket extends Packet {
    int groupid;
    String addmsg;
    public AddGroupRequestPacket() {
    	
    }
   
    public AddGroupRequestPacket(int groupid,String addmsg){
        this.groupid = groupid;
        this.addmsg = addmsg;
    }
    @Override
    public int getCommand() {

        return AddGroup_REQUEST;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getAddmsg() {
        return addmsg;
    }

    public void setAddmsg(String addmsg) {
        this.addmsg = addmsg;
    }
}
