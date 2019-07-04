package thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler;

import android.content.Intent;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetDongtaiUICByDTIDResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    请求某一条动态  获得动态主人头像
 */
public class GetDongtaiUICByDTID_ResponseHandler  extends SimpleChannelInboundHandler<GetDongtaiUICByDTIDResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetDongtaiUICByDTIDResponsePacket getDongtaiUICByDTIDResponsePacket) throws Exception {

        int dongtaiid = getDongtaiUICByDTIDResponsePacket.getDtid();
        byte[] bs2 = getDongtaiUICByDTIDResponsePacket.getUic();

        //从动态列表中找到对应的动态id   把图片路径保存进去
        String path  = Config.DtIcpath+"/"+ UUID.randomUUID().toString()+".jpg";
        // byte[] bs3 = Base64.decode(bs2,Base64.DEFAULT);
        OutputStream out = new FileOutputStream(path);
        InputStream is = new ByteArrayInputStream(bs2);
        byte[] buff = new byte[1024];
        int len = 0;
        while((len=is.read(buff))!=-1){
            out.write(buff, 0, len);
        }
        is.close();
        out.close();
        for(int i = 0; i< StaticAllList.dongtais.size(); ++i){
            if(StaticAllList.dongtais.get(i).getId()==dongtaiid){
                StaticAllList.dongtais.get(i).setSdic(path);
                break;
            }
        }

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.LIFESHOWFRAGMENT");
        intent.putExtra("type","getDtByDTIDIcRs");
        NettyService.nettyService.sendCast(intent);
    }
}
