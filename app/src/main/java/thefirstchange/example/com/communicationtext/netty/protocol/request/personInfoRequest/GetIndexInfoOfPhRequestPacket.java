package thefirstchange.example.com.communicationtext.netty.protocol.request.personInfoRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetIndexInfoOfPh_REQUEST;

/*
 * 	进入某人的个人页面时  获得其基本信息
 */
public class GetIndexInfoOfPhRequestPacket extends Packet {

	String phonenumber;
	public GetIndexInfoOfPhRequestPacket() {

   	}
	public GetIndexInfoOfPhRequestPacket(String phonenumber){
		this.phonenumber = phonenumber;
	}
	@Override
	public int getCommand() {

		return GetIndexInfoOfPh_REQUEST;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
}
