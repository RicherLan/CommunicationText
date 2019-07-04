package thefirstchange.example.com.communicationtext.netty.protocol.request.dongtaiRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;

import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.GetNewDongtaiMsgIDs_REQUEST;

/*
        用户下拉刷新动态消息的页面  就是加载新的动态消息        返回6条动态消息的id
 */
public class GetNewDongtaiMsgIDsRequestPacket extends Packet {

	public GetNewDongtaiMsgIDsRequestPacket() {

   	}
    @Override
    public int getCommand() {

        return GetNewDongtaiMsgIDs_REQUEST;
    }

}
