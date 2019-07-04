package thefirstchange.example.com.communicationtext.netty.client.dongtaiHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.dongtai.DongtaiPCTNum;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.dongtaiResponse.GetOldDongtaiIDsResponsePacket;
import thefirstchange.example.com.communicationtext.service.DongtaiUploadThread;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    用户上拉刷新动态的页面  就是加载旧的动态        返回6条动态的id
    从当前的dongtaiid开始往前找6条以前的
 */
public class GetOldDongtaiIDs_ResponseHandler extends SimpleChannelInboundHandler<GetOldDongtaiIDsResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetOldDongtaiIDsResponsePacket getOldDongtaiIDsResponsePacket) throws Exception {

        Vector<DongtaiPCTNum> dongtaiPCTNums = getOldDongtaiIDsResponsePacket.getDongtaiPCTNums();
        Vector<Integer> dongtaiIDs = new Vector<>();
        if(dongtaiPCTNums!=null){
            for(int i=0;i<dongtaiPCTNums.size();++i){
                dongtaiIDs.add(dongtaiPCTNums.get(i).id);
            }
        }
        Vector<Integer> need = new Vector<>();
        Vector<Integer> delete = new Vector<>();
        if(dongtaiIDs!=null&&dongtaiIDs.size()>1){
            int dongtaiid = dongtaiIDs.get(dongtaiIDs.size()-1);
            int minid = dongtaiIDs.get(dongtaiIDs.size()-2);
            int index = -1;
            for(int i = 0; i< StaticAllList.dongtais.size(); ++i){
                if(StaticAllList.dongtais.get(i).getId()==dongtaiid){
                    index = i;
                }
            }
            //找出被用户删除的动态
            for(int i=index+1;index!=-1&&i<StaticAllList.dongtais.size()&&i<index+7;++i){
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
                        delete.add(i);
                    }
                }
            }

            //找出要请求的动态  即本地没有缓存的动态
            for(int i=0;i<dongtaiIDs.size();++i){
                int id = dongtaiIDs.get(i);
                boolean flag = true;
                for(int j=0;j<StaticAllList.dongtais.size();++j){
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

            for(int i=0;i<delete.size();++i){
                int index2 = delete.get(i);
                StaticAllList.dongtais.remove(index2);

            }
            DongtaiUploadThread dongtaiUploadThread = new DongtaiUploadThread("getDongtaiByDTID",need);
            dongtaiUploadThread.start();


        }

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.LIFESHOWFRAGMENT");
        intent2.putExtra("type","getoldDongtaiIDsResult");
        int size = 0;
        if(dongtaiIDs!=null){
            size = dongtaiIDs.size();
        }
        intent2.putExtra("size",size);
        NettyService.nettyService.sendCast(intent2);

        String type = "getoldDongtaiIDsResult";
        Intent intent=new Intent("thefirstchange.example.com.communicationtext.MAIN");
        intent.putExtra("rs","ok");
        intent.putExtra("type",type);
        NettyService.nettyService.sendCast(intent);

    }
}
