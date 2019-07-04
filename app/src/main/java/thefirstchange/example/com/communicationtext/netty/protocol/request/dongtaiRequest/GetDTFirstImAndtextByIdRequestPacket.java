package thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetDTFirstImAndtextById_REQUEST;

/*
        获得动态的第一张图片和内容
 */
public class GetDTFirstImAndtextByIdRequestPacket extends Packet {
    int id;
    public GetDTFirstImAndtextByIdRequestPacket() {

   	}
    public GetDTFirstImAndtextByIdRequestPacket(int id){
        this.id = id;
    }
    @Override
    public int getCommand() {

        return GetDTFirstImAndtextById_REQUEST;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
