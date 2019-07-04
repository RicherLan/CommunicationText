package thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.dongtai.Dongtai;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetDongtaiByDTIDResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    请求某一条动态
    用户刷新动态的页面  就是请求新的动态    返回动态基本信息   不包括图片
 */
public class GetDongtaiByDTID_ResponseHandler extends SimpleChannelInboundHandler<GetDongtaiByDTIDResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetDongtaiByDTIDResponsePacket getDongtaiByDTIDResponsePacket) throws Exception {
        Dongtai dongtai = getDongtaiByDTIDResponsePacket.getDongtai();
     //   dongtai.setImph(new Vector<String>());
        if(StaticAllList.dongtais.size()==0){
            StaticAllList.dongtais.add(dongtai);
        }else if(StaticAllList.dongtais.get(0).getId()<dongtai.getId()){
            StaticAllList.dongtais.add(0,dongtai);
        }else if(StaticAllList.dongtais.get(0).getId()>=dongtai.getId()){
            int index = -1;
            boolean flag = false;
            for(int i=0;i<StaticAllList.dongtais.size();++i){
                if(StaticAllList.dongtais.get(i).getId()==dongtai.getId()){
                    index = i;
                    flag = true;
                    break;
                }
                if(StaticAllList.dongtais.get(i).getId()<dongtai.getId()){
                    index = i;
                }
            }
            if(flag){
                StaticAllList.dongtais.remove(index);
            }
            if(index!=-1){
                StaticAllList.dongtais.add(index,dongtai);
            }else{
                StaticAllList.dongtais.add(dongtai);
            }
        }


        Intent intent=new Intent("thefirstchange.example.com.communicationtext.LIFESHOWFRAGMENT");
        intent.putExtra("type","getDtByDTIDRs");
        NettyService.nettyService.sendCast(intent);

        //获得动态主人的头像
//        SendToServer.getDongtaiUICByDTID(dongtai.getSdid(),dongtai.getId());

        //获得动态的图片
//        for (int j = 0; dongtai.getImph() != null && j < dongtai.getImph().size(); ++j) {
//            int fileid = Integer.parseInt(dongtai.getImph().get(j));
//            SendToServer.getDongtaiIcByIcid(dongtai.getId(),fileid);
//        }

    }
}
