package thefirstchange.example.com.communicationtext.netty.client.handler.userPersonalInfoHandler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;

import java.io.FileInputStream;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetPerIconResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.MyFileTools;

/*
    获得个人头像
 */
public class GetPerIcon_ResponseHandler extends SimpleChannelInboundHandler<GetPerIconResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetPerIconResponsePacket getPerIconResponsePacket) throws Exception {

        byte[] bs2 = getPerIconResponsePacket.getBs();
        String path = MyFileTools.saveUserIcon(ObjectService.personalInfo.getPhonenumber(),bs2);

        if(path==null||path.equals("")){
            return;
        }

        ObjectService.personalInfo.setIcon(path);

        FileInputStream fis = null;
//                try {
        fis = new FileInputStream(path);
        Bitmap bitmap  = BitmapFactory.decodeStream(fis);
        ObjectService.personalIcon=bitmap;

//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.INDEXFRAGMENT");
        intent.putExtra("type","getPerIc");
        NettyService.nettyService.sendCast(intent);

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.MYSELFFRAGMENT");
        intent2.putExtra("type","getPerIc");
        NettyService.nettyService.sendCast(intent2);

        Intent intent3=new Intent("thefirstchange.example.com.communicationtext.PERSONALHOMEPAGE");
        intent3.putExtra("type","getPerIc");
        NettyService.nettyService.sendCast(intent3);


        SharedPreferences sh = NettyService.nettyService.getSharedPreferences( Config.sharedPreferences_personal_info, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = gson.toJson(ObjectService.personalInfo);
        SharedPreferences.Editor editor = sh.edit() ;
        editor.putString(Config.sharedPreferences_personal_info, json) ; //存入json串
        editor.commit() ; //提交
    }
}
