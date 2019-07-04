package thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest;

import  thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static  thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetDTMsgById_REQUEST;


/*
    获得动态消息的内容
 */
public class GetDTMsgByIdRequestPacket extends Packet{
    int id;
    public GetDTMsgByIdRequestPacket() {

   	}
    public GetDTMsgByIdRequestPacket(int id){
        this.id = id;
    }
    @Override
    public int getCommand() {

        return GetDTMsgById_REQUEST;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
