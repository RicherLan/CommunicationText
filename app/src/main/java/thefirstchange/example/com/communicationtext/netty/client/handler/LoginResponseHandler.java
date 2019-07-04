package thefirstchange.example.com.communicationtext.netty.client.handler;

import android.content.Intent;

import java.io.File;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.LoginResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.service.ObjectService;


public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) {



        String type = loginResponsePacket.getType();
        String rs = loginResponsePacket.getRs();
        if(rs.equals("ok")){
            Config.isLogined = true;
            if(NettyService.loginTimer!=null){
                NettyService.loginTimer.cancel();
            }
            new Thread(){

                public void run(){

                    SendToServer.getPersonalInfo(ObjectService.personalInfo.getPhonenumber());

                    File dir = new File(Config.path);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }

                    dir = new File(Config.path1);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }

                    dir = new File(Config.path2);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }

                    dir = new File(Config.dongtaipath);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }

                    dir = new File(Config.usericonpath);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }

                    dir = new File(Config.groupiconpath);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }

                    dir = new File(Config.DtIcpath);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }

                    dir = new File(Config.groupusericonpath);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }

                    SendToServer.getPerIc(ObjectService.personalInfo.getPhonenumber());
                    SendToServer.getSingleChatMsgNotRead(ObjectService.personalInfo.getPhonenumber());
                    SendToServer.getGroupChatMsgNotRead(ObjectService.personalInfo.getPhonenumber());
                    SendToServer.getAllFriendInfo();
                    SendToServer.getGroupsInfoOfUser(ObjectService.personalInfo.getPhonenumber());
                    SendToServer.getRequestFriendOrGroup(ObjectService.personalInfo.getPhonenumber());
                    SendToServer.getAlldutynoti();

                }

            }.start();
        }
        //登录成功
        if(type.equals("loginUI")){
            Intent intent=new Intent("thefirstchange.example.com.communicationtext.LOGIN_INFO");
            intent.putExtra("loginRs",rs);
            NettyService.nettyService.sendCast(intent);
        }else if(type.equals("MainUI")){
            Intent intent2=new Intent("thefirstchange.example.com.communicationtext.MAIN");
            intent2.putExtra("type","loginRs");
            intent2.putExtra("login_info",rs);
            NettyService.nettyService.sendCast(intent2);
        }


    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
       // System.out.println("客户端连接被关闭!");
    }
}
