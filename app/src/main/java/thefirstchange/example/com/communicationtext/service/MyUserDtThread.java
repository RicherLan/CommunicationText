package thefirstchange.example.com.communicationtext.service;

import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;

/*
进入用户的界面时     请求动态
 */
public class MyUserDtThread extends Thread{

    String ph;
    int id;
    String type;
    public MyUserDtThread(String type,String ph,int id){
        this.ph = ph;
        this.id = id;
        this.type=type;
    }

    public void run(){
        if(type.equals("gUsNDtIDs")){              //刚进入时   请求新的
            SendToServer.gUsNDtIDs(ObjectService.personalInfo.getPhonenumber(),ph);
        }else if(type.equals("gUsODtIDs")){       //上拉刷新
            SendToServer.gUsODtIDs(ObjectService.personalInfo.getPhonenumber(),id,ph);
        }
    }

}
