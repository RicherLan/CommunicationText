package thefirstchange.example.com.communicationtext.service;

import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;

public class UserInfoThread extends Thread{

    String type;
    String ph;

    public  UserInfoThread(String type,String ph){
        this.type = type;
        this.ph = ph;
    }
    public void run(){
        if(type.equals("getFrInByPh")){
            SendToServer.getFriendInfoByPhonenumber(ph);
        }

    }

}
