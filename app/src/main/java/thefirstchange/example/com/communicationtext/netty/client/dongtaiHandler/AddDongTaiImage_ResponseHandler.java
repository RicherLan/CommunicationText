package thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.dongtai.Dongtai;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.AddDongTaiImageResponsePacket;
import thefirstchange.example.com.communicationtext.service.DongtaiUploadThread;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    用户发表动态时   图片分开传送
    户发表动态时   图片分开传送  图片传输的 结果     发表成功该动态不要加入到动态的列表
 */
public class AddDongTaiImage_ResponseHandler extends SimpleChannelInboundHandler<AddDongTaiImageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AddDongTaiImageResponsePacket addDongTaiImageResponsePacket) throws Exception {


        String rs = addDongTaiImageResponsePacket.getResult();
        int dongid = addDongTaiImageResponsePacket.getId();
        String type = "adddongtaiimageResult";

        if(rs.equals("ok")){
            if(StaticAllList.nowDongtai.getImnum()>StaticAllList.dongtaiImageHasnotUpload){
//                        Intent intent = new Intent(this,DongtaiUploadService.class);
//                        intent.putExtra("type","addDongtaiImage");
//                        startService(intent);
                DongtaiUploadThread dongtaiUploadThread = new DongtaiUploadThread("addDongtaiImage",new Vector<Integer>());
                dongtaiUploadThread.start();
                //发表成功
            }else{
                //StaticAllList.dongtais.add(0,StaticAllList.nowDongtai);
                StaticAllList.dongtaiatate = "nodongtai";
                StaticAllList.dongtaiImageHasnotUpload=0;
                StaticAllList.nowDongtai = new Dongtai();


                StaticAllList.dongtaiatate = "nodongtai";
                StaticAllList.dongtaiImageHasnotUpload=0;
                StaticAllList.nowDongtai = new Dongtai();




                Intent intent=new Intent("thefirstchange.example.com.communicationtext.MAIN");
                intent.putExtra("type",type);
                intent.putExtra("rs",rs);
                intent.putExtra("dongtaiid",dongid);
                NettyService.nettyService.sendCast(intent);

                SendToServer.getnewDongtaiIDs();
            }
        }else{
            StaticAllList.dongtaiatate="failed";
        }

    }
}
