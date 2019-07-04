package thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetDongtaiByDTID_REQUEST;

/*
    请求某一条动态
 */
public class GetDongtaiByDTIDRequestPacket extends Packet {
    int id;
    public GetDongtaiByDTIDRequestPacket() {

	}
    public GetDongtaiByDTIDRequestPacket(int id){
        this.id = id;
    }
    @Override
    public int getCommand() {

        return GetDongtaiByDTID_REQUEST;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
