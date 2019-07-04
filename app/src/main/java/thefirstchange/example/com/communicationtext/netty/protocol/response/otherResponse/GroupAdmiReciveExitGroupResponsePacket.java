package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GroupAdmiReciveExitGroup_RESPONSE;

/*
群成员退群时   群管理员收到退群消息时   回执         服务器回执
 */
public class GroupAdmiReciveExitGroupResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return GroupAdmiReciveExitGroup_RESPONSE;
    }

}
