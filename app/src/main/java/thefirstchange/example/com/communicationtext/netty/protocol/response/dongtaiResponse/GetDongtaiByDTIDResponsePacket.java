package thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse;

import thefirstchange.example.com.communicationtext.dongtai.Dongtai;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetDongtaiByDTID_RESPONSE;

/*
    请求某一条动态
 */
public class GetDongtaiByDTIDResponsePacket extends Packet {
	
	Dongtai dongtai;
	
    @Override
    public int getCommand() {

        return GetDongtaiByDTID_RESPONSE;
    }

	public Dongtai getDongtai() {
		return dongtai;
	}

	public void setDongtai(Dongtai dongtai) {
		this.dongtai = dongtai;
	}

}
