package thefirstchange.example.com.communicationtext.service;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;

public class DongtaiUploadThread extends Thread {

    String type;
    Vector<Integer> ids;
    public DongtaiUploadThread(String type,Vector<Integer> ids){
        this.type = type;
        this.ids = ids;
    }

    public void run(){

        //发表动态时    图片的上传
        if(type.equals("addDongtaiImage")){
            long time = -1;
            if(StaticAllList.nowDongtai.getImnum()==StaticAllList.dongtaiImageHasnotUpload+1){
                time = System.currentTimeMillis();
            }
            SendToServer.adddongtaiimage(ObjectService.personalInfo.getPhonenumber(),StaticAllList.nowDongtai.getId(),time,StaticAllList.nowDongtai.getImph().get(StaticAllList.dongtaiImageHasnotUpload));
            StaticAllList.dongtaiImageHasnotUpload++;

            //获得动态
        }else if(type.equals("getDongtaiByDTID")){
            for(int i=0;i<ids.size();++i){
                int id = ids.get(i);
                SendToServer.getDongtaiByDTID(id);
            }

            //获得动态消息的基本信息
        }else if(type.equals("getDTMsgById")){
            for(int i=0;i<ids.size();++i){
                int id = ids.get(i);
                SendToServer.getDTMsgById(ObjectService.personalInfo.getPhonenumber(),id);
            }

            //获得动态的第一张图片和内容
        }else if(type.equals("getDTFirstImAndtextById")){
            for(int i=0;i<ids.size();++i){
                int id = ids.get(i);
                SendToServer.getDTFirstImAndtextById(ObjectService.personalInfo.getPhonenumber(),id);
            }
        }else if(type.equals("getnewDongtaiIds")){
            SendToServer.getnewDongtaiIDs();
        }else if(type.equals("getoldDongtaiIds")){
            SendToServer.getoldDongtaiIDs(ObjectService.personalInfo.getPhonenumber(),ids.get(0));
        }else if(type.equals("dongtaipraise")){            //动态点赞
            int id = ids.get(0);
            SendToServer.dongtaipraise(ObjectService.personalInfo.getPhonenumber(),id);

        }else if(type.equals("getnewDongtaiMsgIDs")){
            SendToServer.getnewDongtaiMsgIDs(ObjectService.personalInfo.getPhonenumber());
        }

        else if(type.equals("gUsDtByDTID")){
            for(int i=0;i<ids.size();++i) {
                int id = ids.get(i);
                SendToServer.gUsDtByDTID(ObjectService.personalInfo.getPhonenumber(), id);
            }
        }
    }

}
