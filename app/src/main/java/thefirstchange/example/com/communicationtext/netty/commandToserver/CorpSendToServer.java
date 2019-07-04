package thefirstchange.example.com.communicationtext.netty.commandToserver;

import thefirstchange.example.com.communicationtext.netty.client.NettyClient;
import thefirstchange.example.com.communicationtext.netty.commandToserver.corpCommandSender.AddCorApPartCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.corpCommandSender.AlterCorpPartCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.corpCommandSender.AlterCorpPosCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.corpCommandSender.AppointCorpPosCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.corpCommandSender.DeleteCorpPartCommandSender;

public class CorpSendToServer {


    //添加社团组织的某一个部门
    public static void addCorpPart(int groupid,String name){

       // String json = "{\"ph\":\""+ ObjectService.personalInfo.getPhonenumber()+"\",\"gid\":\""+groupid+"\",\"name\":\""+name+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                AddCorApPartCommandSender
                        .getInsatnce()
                        .getPacket( groupid, name)
                        .execute(NettyClient.channel);
            }
        }).start();


    }

    //删除社团组织的某一个部门
    public static void deleteCorpPart(int groupid,String name){
        new Thread(new Runnable() {
            @Override
            public void run() {
                DeleteCorpPartCommandSender
                        .getInsatnce()
                        .getPacket( groupid, name)
                        .execute(NettyClient.channel);
            }
        }).start();
        //String json = "{\"ph\":\""+ ObjectService.personalInfo.getPhonenumber()+"\",\"gid\":\""+groupid+"\",\"name\":\""+name+"\"}";


    }


    //修改社团组织的某一个部门的名字
    public static void alterCorpPart(int groupid,String oldname,String newname){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AlterCorpPartCommandSender
                        .getInsatnce()
                        .getPacket(  groupid, oldname, newname)
                        .execute(NettyClient.channel);
            }
        }).start();
      //  String json = "{\"ph\":\""+ ObjectService.personalInfo.getPhonenumber()+"\",\"gid\":\""+groupid+"\",\"oldname\":\""+oldname+"\",\"newname\":\""+newname+"\"}";


    }


    //修改自己在社团的职位
    public static void alterCorpPos(int groupid,String newname){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AlterCorpPosCommandSender
                        .getInsatnce()
                        .getPacket(  groupid, newname)
                        .execute(NettyClient.channel);
            }
        }).start();


    }


    //任命职位
    public static void appointCorpPos(int groupid,String ph,String postype,String oldph){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppointCorpPosCommandSender
                        .getInsatnce()
                        .getPacket( groupid, ph, postype, oldph)
                        .execute(NettyClient.channel);
            }
        }).start();

    }

}
