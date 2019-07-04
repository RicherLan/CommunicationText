package thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest;

import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import static thefirstchange.example.com.communicationtext.netty.protocol.command.Command.IsPhRegisted_REQUEST;

/*
    用户注册账号时  判断提交的手机号是否已经被注册
 */
public class IsPhonenumberRegistedRequestPacket extends Packet {
    String phonenumber;
    public IsPhonenumberRegistedRequestPacket() {

	}
    public IsPhonenumberRegistedRequestPacket(String phonenumber){
        this.phonenumber = phonenumber;
    }
    @Override
    public int getCommand() {

        return IsPhRegisted_REQUEST;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
