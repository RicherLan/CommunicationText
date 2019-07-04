package thefirstchange.example.com.communicationtext.netty.client.handler.chatHandler;

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
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.protocol.packet.SingleChatFile_PACKET;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.MyTools;

/*
    单人聊天发送消息   文件  信息包
 */
public class SingleChatFile_PacketHandler extends SimpleChannelInboundHandler<SingleChatFile_PACKET> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SingleChatFile_PACKET packet) throws Exception {

        int msgid = packet.getMsgid();
        String senderid = packet.getSenderid();
        String msgtype = packet.getMsgtype();

        byte[] bs = packet.getBs();
        //此时message中存的是路径   bs2 这个字节数组就是内容
        String message = null;
        if(msgtype.equals("voice")){
            // File(Environment.getExternalStorageDirectory()+"/recorder_audios", UUID.randomUUID().toString()+".amr");
            message = Config.path1+"/"+ UUID.randomUUID().toString()+".amr";
            OutputStream out = new FileOutputStream(message);
            InputStream is = new ByteArrayInputStream(bs);
            byte[] buff = new byte[1024];
            int len = 0;
            while((len=is.read(buff))!=-1){
                out.write(buff, 0, len);
            }
            is.close();
            out.close();
        }else{
            message = Config.path2+"/"+UUID.randomUUID().toString()+".jpg";
            byte[] bs3 =  Base64.decode(bs,Base64.DEFAULT);
            OutputStream out = new FileOutputStream(message);
            InputStream is = new ByteArrayInputStream(bs3);
            byte[] buff = new byte[1024];
            int len = 0;
            while((len=is.read(buff))!=-1){
                out.write(buff, 0, len);
            }
            is.close();
            out.close();
        }

        long msgtime = packet.getMsgtime();
        double voicetime = packet.getVoicetime();
        //好友的备注   服务器不发送   手机上有好友列表   从里面查询就行
        String sendergroupnickname = "";
        for(String id: StaticAllList.friendList.keySet()){

            if(id.equals(senderid)){
                sendergroupnickname = StaticAllList.friendList.get(id).getNickname();
            }
        }


        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setMsgid(msgid);
        chatMsg.setType("single");
        chatMsg.setSenderid(senderid);
        chatMsg.setSendername(sendergroupnickname);
        chatMsg.setReceiverid(ObjectService.personalInfo.getPhonenumber());
        chatMsg.setReceivername(ObjectService.personalInfo.getNickname());
        chatMsg.setMsgtype(msgtype);
        chatMsg.setMsgbody(message);
        chatMsg.setMsgtime(msgtime);
        chatMsg.setVoicetime(voicetime);

        String iconpath = MyTools.getIconPath(senderid);
        if(iconpath!=null){
            chatMsg.setSendericon(iconpath);
        }

        //如果已经存在   那么就说明   已经保存到消息队列和数据库中了   直接return
        if(MyMessageQueue.chatQueueNotRead.containsKey(senderid)){
            for(int i=0;i<MyMessageQueue.chatQueueNotRead.get(senderid).size();++i){
                if(chatMsg.getMsgid()==MyMessageQueue.chatQueueNotRead.get(senderid).get(i).getMsgid()){
                    return;
                }
            }
        }

        //如果已经存在   那么就说明   已经保存到消息队列和数据库中了   直接return
        if(MyMessageQueue.chatQueueHadRead.containsKey(senderid)){
            for(int i=0;i<MyMessageQueue.chatQueueHadRead.get(senderid).size();++i){
                if(chatMsg.getMsgid()==MyMessageQueue.chatQueueHadRead.get(senderid).get(i).getMsgid()){
                    return;
                }
            }
        }

        if(StaticAllList.chatRecordDao!=null){
            int id = StaticAllList.chatRecordDao.saveSingle(chatMsg);
            chatMsg.setId(id);
        }else{
            MainActivity.initChatRecorddb();
            int id = StaticAllList.chatRecordDao.saveSingle(chatMsg);
            chatMsg.setId(id);
        }

        boolean firstNotice = true;
        if( MyMessageQueue.chatframes.containsKey(senderid)){
            firstNotice = false;
        }else{
            //加入到聊天框队列
            MyMessageQueue.chatframes.put(senderid,"single");
        }
        if(MyMessageQueue.chatQueueNotRead.containsKey(senderid)){
            MyMessageQueue.chatQueueNotRead.get(senderid).add(chatMsg);
        }else{
            MyMessageQueue.chatQueueNotRead.put(senderid,new Vector<ChatMsg>());
            MyMessageQueue.chatQueueNotRead.get(senderid).add(chatMsg);
        }
        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.SINGLE_MSG_NO");
        //intent.addFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
        intent2.putExtra("type","recivesinglechatfile");
        NettyService.nettyService.sendCast(intent2);
        Intent intent1=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
        intent1.putExtra("type","recivesinglechatfile");
        NettyService.nettyService.sendCast(intent1);

        if(firstNotice){
            if(StaticAllList.chatRecordDao!=null){
                StaticAllList.chatRecordDao.saveNoticeFrame("single",senderid,-1);
            }else{
                MainActivity.initChatRecorddb();
                StaticAllList.chatRecordDao.saveNoticeFrame("single",senderid,-1);
            }
        }
    }
}
