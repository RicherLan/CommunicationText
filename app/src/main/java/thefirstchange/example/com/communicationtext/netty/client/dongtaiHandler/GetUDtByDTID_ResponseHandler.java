package thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler;

import android.content.Intent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.dongtai.Dongtai;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetUDtByDTIDResponsePacket;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    进入用户资料界面  请求某一条动态
    进入某用户资料界面   用户刷新动态的页面  就是请求新的动态    返回动态基本信息   不包括图片

    收到后  请求动态的图片
 */
public class GetUDtByDTID_ResponseHandler extends SimpleChannelInboundHandler<GetUDtByDTIDResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetUDtByDTIDResponsePacket getUDtByDTIDResponsePacket) throws Exception {
        Dongtai dongtai = getUDtByDTIDResponsePacket.getDongtai();
        String ph = "";

        if(dongtai!=null){
            ph = dongtai.getSdid();
        }


//        dongtai.setImph(new Vector<String>());

//                boolean rs = StaticAllList.personalDongtais.containsKey(ph);

        if (!StaticAllList.personalDongtais.containsKey(ph)||StaticAllList.personalDongtais.get(ph).size()==0) {
//                Vector<Dongtai> dongtais1 = new Vector<>();
//                dongtais1.add(dongtai);
            StaticAllList.personalDongtais.get(ph).add(dongtai);
        } else if (StaticAllList.personalDongtais.get(ph).get(0).getId() < dongtai.getId()) {
            StaticAllList.personalDongtais.get(ph).add(0, dongtai);
        } else if (StaticAllList.personalDongtais.get(ph).get(0).getId() >= dongtai.getId()) {
            int index = -1;
            boolean flag = false;
            for (int i = 0; i < StaticAllList.personalDongtais.get(ph).size(); ++i) {
                if (StaticAllList.personalDongtais.get(ph).get(i).getId() == dongtai.getId()) {
                    index = i;
                    flag = true;
                    break;
                }
                if (StaticAllList.personalDongtais.get(ph).get(i).getId() < dongtai.getId()) {
                    index = i;
                }
            }
            if (flag) {
                StaticAllList.personalDongtais.get(ph).remove(index);
            }
            if (index != -1) {
                StaticAllList.personalDongtais.get(ph).add(index, dongtai);
            } else {
                StaticAllList.personalDongtais.get(ph).add(dongtai);
            }
        }

        Intent intent = new Intent("thefirstchange.example.com.communicationtext.FRAGMENT_TWO");
        intent.putExtra("type", "gUsDtByDTIDRs");
        NettyService.nettyService.sendCast(intent);

//        for (int j = 0; dongtai.getImph() != null && j < dongtai.getImph().size(); ++j) {
//            int fileid = Integer.parseInt(dongtai.getImph().get(j));
//            SendToServer.getUserDongtaiIconByIcId(dongtai.getId(),dongtai.getSdid(),fileid);
//        }

        }
}
