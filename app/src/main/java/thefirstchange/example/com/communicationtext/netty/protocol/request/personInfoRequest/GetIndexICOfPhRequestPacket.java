package thefirstchange.example.com.communicationtext.netty.protocol.request.personInfoRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetIndexICOfPh_REQUEST;

/*
 * 	进入某人的个人页面时  获得其头像
 */
public class GetIndexICOfPhRequestPacket extends Packet {
	
	String phonenumber;
	public GetIndexICOfPhRequestPacket() {

   	}
	public GetIndexICOfPhRequestPacket(String phonenumber){
		this.phonenumber = phonenumber;
	}
	@Override
	public int getCommand() {

		return GetIndexICOfPh_REQUEST;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
}
