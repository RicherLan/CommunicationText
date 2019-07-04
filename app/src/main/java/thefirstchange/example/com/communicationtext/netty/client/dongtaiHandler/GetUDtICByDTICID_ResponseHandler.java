package thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler;

import android.content.Intent;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.dongtai.Dongtai;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetUDtICByDTICIDResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    进入某用户的资料界面    刷新动态的页面  就是请求新的动态   返回动态的图片
 */
public class GetUDtICByDTICID_ResponseHandler extends SimpleChannelInboundHandler<GetUDtICByDTICIDResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetUDtICByDTICIDResponsePacket getUDtICByDTICIDResponsePacket) throws Exception {

        int dongtaiid = getUDtICByDTICIDResponsePacket.getDtid();
        String ph = getUDtICByDTICIDResponsePacket.getPh();
        byte[] bs2 = getUDtICByDTICIDResponsePacket.getIc();
        int fileid =getUDtICByDTICIDResponsePacket.getFileid();
        //从动态列表中找到对应的动态id   把图片路径保存进去
        String path  = Config.dongtaipath+"/"+ UUID.randomUUID().toString()+".jpg";
        byte[] bs3 = Base64.decode(bs2,Base64.DEFAULT);
        OutputStream out = new FileOutputStream(path);
        InputStream is = new ByteArrayInputStream(bs3);
        byte[] buff = new byte[1024];
        int len = 0;
        while((len=is.read(buff))!=-1){
            out.write(buff, 0, len);
        }
        is.close();
        out.close();

        if(!StaticAllList.personalDongtais.containsKey(ph)){
            StaticAllList.personalDongtais.put(ph,new Vector<Dongtai>());
        }

        for(int i=0;i<StaticAllList.personalDongtais.get(ph).size();++i){
            if(StaticAllList.personalDongtais.get(ph).get(i).getId()==dongtaiid){

                for(int j=0;j<StaticAllList.personalDongtais.get(ph).get(i).getImph().size();++j){
                    if(StaticAllList.personalDongtais.get(ph).get(i).getImph().get(j).equals(fileid+"")){
                        StaticAllList.personalDongtais.get(ph).get(i).getImph().removeElementAt(j);
                        StaticAllList.personalDongtais.get(ph).get(i).getImph().add(j,path);
                    }
                }
               // StaticAllList.personalDongtais.get(ph).get(i).getImph().add(path);
                break;
            }
        }

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.FRAGMENT_TWO");
        intent.putExtra("type","gUsDtByDTIDImRs");
        NettyService.nettyService.sendCast(intent);

    }
}
