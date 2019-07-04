package thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.dongtai.Dongtai;
import thefirstchange.example.com.communicationtext.dongtai.DongtaiPCTNum;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetNewDongtaiIDsResponsePacket;
import thefirstchange.example.com.communicationtext.service.DongtaiUploadThread;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    用户下拉刷新动态的页面  就是加载新的动态        返回6条动态的id
 */
public class GetNewDongtaiIDs_ResponseHandler extends SimpleChannelInboundHandler<GetNewDongtaiIDsResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetNewDongtaiIDsResponsePacket getNewDongtaiIDsResponsePacket) throws Exception {
        Vector<DongtaiPCTNum> dongtaiPCTNums = getNewDongtaiIDsResponsePacket.getDongtaiPCTNums();
        Vector<Integer> dongtaiIDs = new Vector<>();
        if(dongtaiPCTNums!=null){
            for(int i=0;i<dongtaiPCTNums.size();++i){
                dongtaiIDs.add(dongtaiPCTNums.get(i).id);
            }
        }

        //需要请求的动态id
        Vector<Integer> need = new Vector<>();
        Vector<Dongtai> delete = new Vector<>();
        if(dongtaiIDs!=null&&dongtaiIDs.size()!=0){
            int minid = dongtaiIDs.get(dongtaiIDs.size()-1);

            //找出被动态主人删除的动态
            for(int i = 0; i< StaticAllList.dongtais.size()&&i<6; ++i){
                int id = StaticAllList.dongtais.get(i).getId();
                if(id>minid){
                    boolean flag = true;
                    for(int j=0;j<dongtaiIDs.size();++j){
                        if(id==dongtaiIDs.get(j)){
                            flag = false;
                            break;
                        }
                    }
                    if(flag){
                        delete.add(StaticAllList.dongtais.get(i));
                    }
                }
            }

            //找出要请求的动态  即本地没有缓存的动态
            for(int i=0;i<dongtaiIDs.size();++i){
                int id = dongtaiIDs.get(i);
                boolean flag = true;
                for(int j=0;j<StaticAllList.dongtais.size()&&j<6;++j){
                    if(id==StaticAllList.dongtais.get(j).getId()){
                        flag = false;
                        //更新 点赞 评论 转发数量
                        if(dongtaiPCTNums!=null) {
                            for (int k = 0; k < dongtaiPCTNums.size(); ++k) {
                                if(dongtaiPCTNums.get(k).id==id){
                                    StaticAllList.dongtais.get(j).setPranum(dongtaiPCTNums.get(k).pNum);
                                    StaticAllList.dongtais.get(j).setComnum(dongtaiPCTNums.get(k).cNum);
                                    StaticAllList.dongtais.get(j).setTransnum(dongtaiPCTNums.get(k).tNum);
                                }
                            }
                        }

                        break;
                    }
                }
                if(flag){
                    need.add(id);
                }
            }

            for(Dongtai dongtai : delete){
                StaticAllList.dongtais.remove(dongtai);
            }

//            int a = need.size();

            DongtaiUploadThread dongtaiUploadThread = new DongtaiUploadThread("getDongtaiByDTID",need);
            dongtaiUploadThread.start();

        }

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.LIFESHOWFRAGMENT");
        intent2.putExtra("type","getnewDongtaiIDsResult");
        NettyService.nettyService.sendCast(intent2);

        Intent intent=new Intent("thefirstchange.example.com.communicationtext.MAIN");
        intent.putExtra("rs","ok");
        intent.putExtra("type","getnewDongtaiIDsResult");
        NettyService.nettyService.sendCast(intent);

    }
}
