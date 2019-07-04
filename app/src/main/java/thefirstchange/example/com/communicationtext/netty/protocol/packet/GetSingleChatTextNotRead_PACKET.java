package thefirstchange.example.com.communicationtext.netty.protocol.packet;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import thefirstchange.example.com.communicationtext.service.ChatMsg;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetSingleChatTextNotRead_PACKET;

/*
 * 	拿到单人聊天  未读信息     一般是刚登陆的时候
 *  发送多条未读的文字消息
 */
public class GetSingleChatTextNotRead_PACKET extends Packet{
	
	Vector<ChatMsg> chatMsgs;
	
	@Override
	public int getCommand() {
		
		return GetSingleChatTextNotRead_PACKET;
	}

	public Vector<ChatMsg> getChatMsgs() {
		return chatMsgs;
	}

	public void setChatMsgs(Vector<ChatMsg> chatMsgs) {
		this.chatMsgs = chatMsgs;
	}
	
}
