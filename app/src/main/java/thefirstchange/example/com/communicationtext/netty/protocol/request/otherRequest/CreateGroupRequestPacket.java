package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import  thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static  thefirstchange.example.com.communicationtext.netty.protocol.command.Command.CreateGroup_REQUEST;


/*
    创建群
 */
public class CreateGroupRequestPacket extends Packet {
    String groupname;
    public CreateGroupRequestPacket() {

   	}
    public CreateGroupRequestPacket(String groupname){
        this.groupname = groupname;
    }
    @Override
    public int getCommand() {

        return CreateGroup_REQUEST;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}
