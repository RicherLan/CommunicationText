package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetGroupUserIconByPh_REQUEST;

/*
    获得某群的所有成员后  向服务器回执     来让服务器发头像
 */
public class OkGetGroupAllUserRequestPacket extends Packet {
    int groupid;
    public OkGetGroupAllUserRequestPacket(int groupid ){
        this.groupid = groupid;
    }
    @Override
    public int getCommand() {

        return GetGroupUserIconByPh_REQUEST;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }
}
