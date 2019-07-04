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
import thefirstchange.example.com.communicationtext.netty.protocol.packet.GroupChatFile_PACKET;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.service.ObjectService;

/*
群聊发送消息   语音 照片   信息包
 */
public class GroupChatFile_PacketHandler extends SimpleChannelInboundHandler<GroupChatFile_PACKET> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatFile_PACKET packet) throws Exception {
        int msgid = packet.getMsgid();
        String senderid = packet.getSenderid();
        String sendergroupnickname = packet.getSendergroupname();
        int groupid = packet.getGroupid();
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
        ChatMsg chatMsg = new ChatMsg();


        chatMsg.setMsgid(msgid);
        chatMsg.setType("group");
        chatMsg.setGroupid(groupid);
        chatMsg.setSenderid(senderid);
        chatMsg.setSendername(sendergroupnickname);
        chatMsg.setReceiverid(ObjectService.personalInfo.getPhonenumber());
        chatMsg.setReceivername(ObjectService.personalInfo.getNickname());
        chatMsg.setMsgtype(msgtype);
        chatMsg.setMsgbody(message);
        chatMsg.setMsgtime(msgtime);
        chatMsg.setVoicetime(voicetime);

        if(MyMessageQueue.chatQueueNotRead.containsKey(groupid+"")){
            for(int i=0;i<MyMessageQueue.chatQueueNotRead.get(groupid+"").size();++i){
                if(chatMsg.getMsgid()==MyMessageQueue.chatQueueNotRead.get(groupid+"").get(i).getMsgid()){
                    return;
                }
            }
        }

        //说明  该消息已经在 本机保存了    是因为之前来了消息自己一直没有读取
        if(MyMessageQueue.chatQueueHadRead.containsKey(groupid+"")){
            for(int i=0;i<MyMessageQueue.chatQueueHadRead.get(groupid+"").size();++i){
                if(chatMsg.getMsgid()==MyMessageQueue.chatQueueHadRead.get(groupid+"").get(i).getMsgid()){
                    return;
                }
            }
        }


        if(StaticAllList.chatRecordDao!=null){
            int id = StaticAllList.chatRecordDao.saveGroup(chatMsg);
            chatMsg.setId(id);
        }else{
            MainActivity.initChatRecorddb();
            int id = StaticAllList.chatRecordDao.saveGroup(chatMsg);
            chatMsg.setId(id);
        }


        boolean firstNotice = true;
        if( MyMessageQueue.chatframes.containsKey(groupid+"")){
            firstNotice = false;
        }else{
            //加入到聊天框队列
            MyMessageQueue.chatframes.put(groupid+"","group");
        }

        if(MyMessageQueue.chatQueueNotRead.containsKey(groupid+"")){
            MyMessageQueue.chatQueueNotRead.get(groupid+"").add(chatMsg);

        }else{
            MyMessageQueue.chatQueueNotRead.put(groupid+"",new Vector<ChatMsg>());
            MyMessageQueue.chatQueueNotRead.get(groupid+"").add(chatMsg);
        }

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.GROUP_MSG");
        //intent.addFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
        intent2.putExtra("type","recivegroupchatfile");
        NettyService.nettyService.sendCast(intent2);
        Intent intent1=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
        intent1.putExtra("type","recivegroupchatfile");
        NettyService.nettyService.sendCast(intent1);

        if(firstNotice){
            if(StaticAllList.chatRecordDao!=null){
                StaticAllList.chatRecordDao.saveNoticeFrame("group","",groupid);
            }else{
                MainActivity.initChatRecorddb();
                StaticAllList.chatRecordDao.saveNoticeFrame("group","",groupid);
            }
        }
    }
}
