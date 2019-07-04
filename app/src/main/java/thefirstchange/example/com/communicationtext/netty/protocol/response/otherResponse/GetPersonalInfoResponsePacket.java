package thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse;

import thefirstchange.example.com.communicationtext.gson.PersonalInfo;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetPersonalInfo_RESPONSE;

/*
获得个人信息     一般是刚登陆的时候
 */
public class GetPersonalInfoResponsePacket extends Packet {
	PersonalInfo personalInfo;
	
    @Override
    public int getCommand() {

        return GetPersonalInfo_RESPONSE;
    }

	public PersonalInfo getPersonalInfo() {
		return personalInfo;
	}

	public void setPersonalInfo(PersonalInfo personalInfo) {
		this.personalInfo = personalInfo;
	}

}
