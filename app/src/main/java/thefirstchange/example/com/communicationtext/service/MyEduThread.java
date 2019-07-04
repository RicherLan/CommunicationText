package thefirstchange.example.com.communicationtext.service;

import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;

public class MyEduThread extends Thread {

    private String type;
    private int year;
    private int xueqi;
    private String account;
    private String pwd;

    public MyEduThread(String type, int year, int xueqi, String account, String pwd){
        this.type = type;
        this.xueqi = xueqi;
        this.year = year;
        this.account = account;
        this.pwd = pwd;
    }

    public void run(){

        if(type.equals("getKBByPh")){                 //获得教务课表
            SendToServer.getKBByPh(ObjectService.personalInfo.getPhonenumber(),year,xueqi,account,pwd);

        }else if(type.equals("getscoByPh")){          //获得教务成绩
            SendToServer.getscoByPh(ObjectService.personalInfo.getPhonenumber(),year,xueqi,account,pwd);

        }

    }

}
