package thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.dongtai.Dongtai;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.AddDongtaiResponsePacket;
import thefirstchange.example.com.communicationtext.service.DongtaiUploadThread;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    发表动态    普通用户的话  类型用user表示
    发表动态的基本信息是否成功
 */
public class AddDongtai_ResponseHandler extends SimpleChannelInboundHandler<AddDongtaiResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AddDongtaiResponsePacket addDongtaiResponsePacket) throws Exception {

        String rs = addDongtaiResponsePacket.getResult();
        int dongid = addDongtaiResponsePacket.getId();
        String type = "addDongtaiResult";

        if(rs.equals("ok")){

            StaticAllList.nowDongtai.setId(dongid);
            if(StaticAllList.nowDongtai.getImnum()!=0){

                DongtaiUploadThread dongtaiUploadThread = new DongtaiUploadThread("addDongtaiImage",new Vector<Integer>());
                dongtaiUploadThread.start();

                //该动态没有图片  且发表成功  那么该动态发表成功    发表成功该动态不要加入到动态的列表
            }else {
                // StaticAllList.dongtais.add(0,StaticAllList.nowDongtai);
                StaticAllList.dongtaiatate = "nodongtai";
                StaticAllList.dongtaiImageHasnotUpload=0;
                StaticAllList.nowDongtai = new Dongtai();

                Intent intent=new Intent("thefirstchange.example.com.communicationtext.MAIN");
                intent.putExtra("type",type);
                intent.putExtra("rs",rs);
                intent.putExtra("dongtaiid",dongid);
                NettyService.nettyService.sendCast(intent);

                //发表成功后  请求新的动态
                DongtaiUploadThread dongtaiUploadThread = new DongtaiUploadThread("getnewDongtaiIds",new Vector<Integer>());
                dongtaiUploadThread.start();

            }


        }else{
            StaticAllList.dongtaiatate="failed";
        }


    }
}
