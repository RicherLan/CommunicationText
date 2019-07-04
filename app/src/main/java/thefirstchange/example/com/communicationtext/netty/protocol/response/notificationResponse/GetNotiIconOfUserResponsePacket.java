package thefirstchange.example.com.communicationtext.netty.protocol.response.notificationResponse;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetNotiIconOfUser_RESPONSE;

/*
 * 	获得某用户头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
 */
public class GetNotiIconOfUserResponsePacket extends Packet{

	String ph;
	byte[] icon;
	@Override
	public int getCommand() {
		
		return GetNotiIconOfUser_RESPONSE;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public byte[] getIcon() {
		return icon;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

}
