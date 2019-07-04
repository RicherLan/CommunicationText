package thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.dongtai.DongtaiPCTNum;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetNewDongtaiIDs_RESPONSE;

/*
        用户下拉刷新动态的页面  就是加载新的动态        返回6条动态的id
 */
public class GetNewDongtaiIDsResponsePacket extends Packet {

	Vector<DongtaiPCTNum> dongtaiPCTNums;
    @Override
    public int getCommand() {

        return GetNewDongtaiIDs_RESPONSE;
    }
	public Vector<DongtaiPCTNum> getDongtaiPCTNums() {
		return dongtaiPCTNums;
	}
	public void setDongtaiPCTNums(Vector<DongtaiPCTNum> dongtaiPCTNums) {
		this.dongtaiPCTNums = dongtaiPCTNums;
	}
    
}
