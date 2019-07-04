package thefirstchange.example.com.communicationtext.netty.client.handler.userPersonalInfoHandler;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Calendar;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.gson.PersonalInfo;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetPersonalInfoResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.service.ObjectService;

/*
获得个人信息     一般是刚登陆的时候
 */
public class GetPersonalInfo_ResponseHandler extends SimpleChannelInboundHandler<GetPersonalInfoResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetPersonalInfoResponsePacket getPersonalInfoResponsePacket) throws Exception {
        PersonalInfo personalInfo = getPersonalInfoResponsePacket.getPersonalInfo();

        if(personalInfo!=null){
            String ph = ObjectService.personalInfo.getPhonenumber();
            String icon = ObjectService.personalInfo.getIcon();
            ObjectService.personalInfo = personalInfo;

            Calendar c = Calendar.getInstance();
            int year =  c.get(Calendar.YEAR) ;  //获取当前年
            int month =  c.get(Calendar.MONTH)+1;// 获取当前月
            int xueqi = 1;
            if(month>=9&&month<=12||month>=1&&month<=2){
                xueqi = 1;
            }else{
                xueqi = 2;
            }

            int grade = year-ObjectService.personalInfo.getRuxueyear();
            if(month>=9&&month<=12){
                grade+=1;
            }

            ObjectService.personalInfo.setGrade(grade);
            ObjectService.personalInfo.setXueqi(xueqi);

            if(ph.equals(personalInfo.getPhonenumber())&&(personalInfo.getIcon()==null||personalInfo.getIcon().equals(""))){
                ObjectService.personalInfo.setIcon(icon);
            }

        }else{
            return;
        }

        SharedPreferences sh = NettyService.nettyService.getSharedPreferences( Config.sharedPreferences_personal_info, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = gson.toJson(personalInfo);
        SharedPreferences.Editor editor = sh.edit() ;
        editor.putString(Config.sharedPreferences_personal_info, json) ; //存入json串
        editor.commit() ; //提交


    }
}
