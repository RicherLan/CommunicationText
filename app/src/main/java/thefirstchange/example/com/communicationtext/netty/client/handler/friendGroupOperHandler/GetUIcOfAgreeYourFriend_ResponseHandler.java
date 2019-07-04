package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.response.friendGroupOperResponse.GetUIcOfAgreeYourFriendResponsePacket;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    对方同意了你的好友请求    获得对方的头像
 */
public class GetUIcOfAgreeYourFriend_ResponseHandler  extends SimpleChannelInboundHandler<GetUIcOfAgreeYourFriendResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetUIcOfAgreeYourFriendResponsePacket getUIcOfAgreeYourFriendResponsePacket) throws Exception {

        String ph = getUIcOfAgreeYourFriendResponsePacket.getPh();
        byte[] icon = getUIcOfAgreeYourFriendResponsePacket.getIcon();

        if(MyMessageQueue.requestQueueNotHandle==null&&MyMessageQueue.requestQueueHadHandled==null){
            return;
        }

        //   把图片路径保存进去
        String path  = Config.usericonpath+"/"+ ph+".jpg";
        // byte[] bs3 = Base64.decode(bs2,Base64.DEFAULT);
        OutputStream out = new FileOutputStream(path);
        InputStream is = new ByteArrayInputStream(icon);
        byte[] buff = new byte[1024];
        int len = 0;
        while((len=is.read(buff))!=-1){
            out.write(buff, 0, len);
        }
        is.close();
        out.close();

        ChatMsg msg = null;
        for(int i = 0; i< MyMessageQueue.requestQueueNotHandle.size(); ++i){

            if( MyMessageQueue.requestQueueNotHandle.get(i).getType().equals("agreeYourAddFriend")&& MyMessageQueue.requestQueueNotHandle.get(i).getSenderid().equals(ph)){
                MyMessageQueue.requestQueueNotHandle.get(i).setSendericon(path);
                msg = MyMessageQueue.requestQueueNotHandle.get(i);
            }
        }

        for(int i = 0; i< MyMessageQueue.requestQueueHadHandled.size(); ++i){
            if( MyMessageQueue.requestQueueHadHandled.get(i).getType().equals("agreeYourAddFriend")&& MyMessageQueue.requestQueueHadHandled.get(i).getSenderid().equals(ph)){
                MyMessageQueue.requestQueueHadHandled.get(i).setSendericon(path);
                msg = MyMessageQueue.requestQueueHadHandled.get(i);
            }
        }


        Intent intent1=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
        intent1.putExtra("type","agreeYourAddFriendIcon");
        NettyService.nettyService.sendCast(intent1);

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.OTHERPEOPLEAPPLICATION");
        intent2.putExtra("type","otherIcon");
        NettyService.nettyService.sendCast(intent2);



        if(msg!=null){
            if(StaticAllList.chatRecordDao!=null){
                StaticAllList.chatRecordDao.saveRequest(msg);
            }else{
                MainActivity.initChatRecorddb();
                StaticAllList.chatRecordDao.saveRequest(msg);
            }
        }

    }
}
