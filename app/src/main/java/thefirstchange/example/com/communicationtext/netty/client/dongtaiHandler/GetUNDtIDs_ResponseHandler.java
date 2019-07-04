package thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.dongtai.Dongtai;
import thefirstchange.example.com.communicationtext.dongtai.DongtaiPCTNum;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetUNDtIDsResponsePacket;
import thefirstchange.example.com.communicationtext.service.DongtaiUploadThread;

/*
    进入某用户的资料界面       用户下拉刷新动态的页面  就是请求新的动态
    服务器收到返回动态的id     否则客户端超时  就提示刷新失败
 */
public class GetUNDtIDs_ResponseHandler extends SimpleChannelInboundHandler<GetUNDtIDsResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetUNDtIDsResponsePacket getUNDtIDsResponsePacket) throws Exception {

        String ph = getUNDtIDsResponsePacket.getPh();

        Vector<DongtaiPCTNum> dongtaiPCTNums = getUNDtIDsResponsePacket.getDongtaiPCTNums();

        Vector<Integer> dongtaiIDs = new Vector<>();
        if (dongtaiPCTNums != null) {
            for (int i = 0; i < dongtaiPCTNums.size(); ++i) {
                dongtaiIDs.add(dongtaiPCTNums.get(i).id);
            }
        }

        Vector<Dongtai> dongtais = new Vector<>();
        if (StaticAllList.personalDongtais.containsKey(ph)) {
            dongtais = StaticAllList.personalDongtais.get(ph);
        }



        Vector<Integer> need = new Vector<>();
        Vector<Dongtai> delete = new Vector<>();
        if (dongtaiIDs != null && dongtaiIDs.size() != 0) {
            int minid = dongtaiIDs.get(dongtaiIDs.size() - 1);

            //找出被动态主人删除的动态
            for (int i = 0; i < dongtais.size() && i < 6; ++i) {
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
                        delete.add( dongtais.get(i));
                    }
                }
            }

            //找出要请求的动态  即本地没有缓存的动态
            for (int i = 0; i < dongtaiIDs.size(); ++i) {
                int id = dongtaiIDs.get(i);
                boolean flag = true;
                for (int j = 0; j < dongtais.size() && j < 6; ++j) {
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

            for (Dongtai dongtai : delete) {
                StaticAllList.personalDongtais.get(ph).remove(dongtai);
            }

           // int a = need.size();
            DongtaiUploadThread dongtaiUploadThread = new DongtaiUploadThread("gUsDtByDTID", need);
            dongtaiUploadThread.start();

        }
    }
}
