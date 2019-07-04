package thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler;

import android.content.Intent;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetDongtaiICByDTID_ResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    请求某一条动态  获得动态的图片
 */
public class GetDongtaiICByDTID_ResponseHandler extends SimpleChannelInboundHandler<GetDongtaiICByDTID_ResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetDongtaiICByDTID_ResponsePacket getDongtaiICByDTID_responsePacket) throws Exception {

        int dongtaiid = getDongtaiICByDTID_responsePacket.getDtid();
        int fileid = getDongtaiICByDTID_responsePacket.getFileid();
        byte[] bs2 = getDongtaiICByDTID_responsePacket.getIc();
        //从动态列表中找到对应的动态id   把图片路径保存进去
        String path  = Config.dongtaipath+"/"+ UUID.randomUUID().toString()+".jpg";
        byte[] bs3 = Base64.decode(bs2, Base64.DEFAULT);
        OutputStream out = new FileOutputStream(path);
        InputStream is = new ByteArrayInputStream(bs3);
        byte[] buff = new byte[1024];
        int len = 0;
        while((len=is.read(buff))!=-1){
            out.write(buff, 0, len);
        }
        is.close();
        out.close();
        for(int i = 0; i< StaticAllList.dongtais.size(); ++i){
            if(StaticAllList.dongtais.get(i).getId()==dongtaiid){
                for(int j=0;j<StaticAllList.dongtais.get(i).getImph().size();++j){
                    if(StaticAllList.dongtais.get(i).getImph().get(j).equals(fileid+"")){
                        StaticAllList.dongtais.get(i).getImph().removeElementAt(j);
                        StaticAllList.dongtais.get(i).getImph().add(j,path);
                    }
                }

                break;
            }
        }

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.LIFESHOWFRAGMENT");
        intent.putExtra("type","getDtByDTIDImRs");
        NettyService.nettyService.sendCast(intent);
    }
}
