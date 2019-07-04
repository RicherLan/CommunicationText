package thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.dongtai.Dongtai;
import thefirstchange.example.com.communicationtext.dongtai.DongtaiPCTNum;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetUsODtIDsResponsePacket;
import thefirstchange.example.com.communicationtext.service.DongtaiUploadThread;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    进入用户资料界面   用户上拉刷新动态的页面  就是加载旧的动态        返回6条动态的id
    从当前的dongtaiid开始往前找6条以前的
 */
public class GetUsODtIDs_ResponseHandler extends SimpleChannelInboundHandler<GetUsODtIDsResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetUsODtIDsResponsePacket getUsODtIDsResponsePacket) throws Exception {

        String ph = getUsODtIDsResponsePacket.getPh();

        Vector<DongtaiPCTNum> dongtaiPCTNums = getUsODtIDsResponsePacket.getDongtaiPCTNums();

        Vector<Integer> dongtaiIDs = new Vector<>();
        if (dongtaiPCTNums != null) {
            for (int i = 0; i < dongtaiPCTNums.size(); ++i) {
                dongtaiIDs.add(dongtaiPCTNums.get(i).id);
            }
        }

        Vector<Dongtai> dongtais = new Vector<>();
        if (StaticAllList.personalDongtais.containsKey(ph)) {
//                    StaticAllList.personalDongtais.put(ph, new Vector<Dongtai>());
            dongtais = StaticAllList.personalDongtais.get(ph);
        }



        Vector<Integer> need = new Vector<>();
        Vector<Integer> delete = new Vector<>();
        if (dongtaiIDs != null && dongtaiIDs.size() > 1) {
            int dongtaiid = dongtaiIDs.get(dongtaiIDs.size() - 1);
            int minid = dongtaiIDs.get(dongtaiIDs.size() - 2);
            int index = -1;
            for (int i = 0; i < dongtais.size(); ++i) {
                if (dongtais.get(i).getId() == dongtaiid) {
                    index = i;
                }
            }
            //找出被用户删除的动态
            for (int i = index + 1; index != -1 && i < dongtais.size() && i < index + 7; ++i) {
                int id = dongtais.get(i).getId();
                if (id > minid) {
                    boolean flag = true;
                    for (int j = 0; j < dongtaiIDs.size(); ++j) {
                        if (id == dongtaiIDs.get(j)) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        delete.add(i);
                    }
                }
            }

            //找出要请求的动态  即本地没有缓存的动态
            for (int i = 0; i < dongtaiIDs.size(); ++i) {
                int id = dongtaiIDs.get(i);
                boolean flag = true;
                for (int j = 0; j < dongtais.size(); ++j) {
                    if (id == dongtais.get(j).getId()) {
                        flag = false;

                        //更新 点赞 评论 转发数量
                        if (dongtaiPCTNums != null) {
                            for (int k = 0; k < dongtaiPCTNums.size(); ++k) {
                                if (dongtaiPCTNums.get(k).id == id) {
                                    StaticAllList.personalDongtais.get(ph).get(j).setPranum(dongtaiPCTNums.get(k).pNum);
                                    StaticAllList.personalDongtais.get(ph).get(j).setComnum(dongtaiPCTNums.get(k).cNum);
                                    StaticAllList.personalDongtais.get(ph).get(j).setTransnum(dongtaiPCTNums.get(k).tNum);
                                }
                            }
                        }

                        break;
                    }
                }
                if (flag) {
                    need.add(id);
                }
            }

            for (int i = 0; i < delete.size(); ++i) {
                int index2 = delete.get(i);
                StaticAllList.personalDongtais.get(ph).remove(index2);

            }
            DongtaiUploadThread dongtaiUploadThread = new DongtaiUploadThread("gUsDtByDTID", need);
            dongtaiUploadThread.start();


        }
        String type = "gUsODtIDsRs";
        Intent intent2 = new Intent("thefirstchange.example.com.communicationtext.FRAGMENT_TWO");
        intent2.putExtra("type", type);
        int size = 0;
        if (dongtaiIDs != null) {
            size = dongtaiIDs.size();
        }
        intent2.putExtra("size", size);
        NettyService.nettyService.sendCast(intent2);
    }
}
