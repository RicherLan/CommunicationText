package thefirstchange.example.com.communicationtext.netty.client.handler.friendGroupOperHandler;

import android.content.Intent;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.netty.protocol.response.otherResponse.GetRequestFriendOrGroupResponsePacket;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.NettyService;

/*
    拿到所有的好友、群请求信息  一般在刚登陆的时候
 */
public class GetRequestFriendOrGroup_ResponseHandler extends SimpleChannelInboundHandler<GetRequestFriendOrGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GetRequestFriendOrGroupResponsePacket getRequestFriendOrGroupResponsePacket) throws Exception {
        Vector<ChatMsg> chatMsgs = getRequestFriendOrGroupResponsePacket.getRequestFriendOrGroups();
        if(chatMsgs!=null&&chatMsgs.size()!=0){
            MyMessageQueue.requestQueueNotHandle = chatMsgs;
        }

        for(ChatMsg chatMsg:chatMsgs){
            String senid = chatMsg.getSenderid();
            String name = chatMsg.getSendername();
            int a =0;
        }

        boolean saveFrame = false;
        String ph = null;
        int msgid = -1;
        //加群请求被其他管理员处理过
        ChatMsg someoneHasHandledAddGroupchatmsg = null;
        Vector<ChatMsg> deleteMsg = new Vector<>();
        for(int i=0;i< MyMessageQueue.requestQueueNotHandle.size();++i){
            ChatMsg chatMsg =  MyMessageQueue.requestQueueNotHandle.get(i);
            if(chatMsg.getType().equals("yourfrienddeleteyou")){
                ph = chatMsg.getSenderid();
                msgid = chatMsg.getMsgid();
                StaticAllList.friendList.remove(ph);
                deleteMsg.add(chatMsg);
            }
            else{
                saveFrame=true;
            }
        }

        for(ChatMsg chatMsg:deleteMsg){
            MyMessageQueue.requestQueueNotHandle.remove(chatMsg);
            SendToServer.reciveDeleteMe(chatMsg.getMsgid());
        }


        for(ChatMsg chatMsg :   MyMessageQueue.requestQueueNotHandle){
            String type = chatMsg.getType();             //群消息还是单人聊天消息  group single
                        // 请求  addgroup   addfriend     agreeYourAddFriend  disagreeYourAddFriend  agreeAddGroup  disagreeAddGroup
                        //yourfrienddeleteyou
                        //值班表到来通知  schDuty
            //获得其头像
            if(type.equals("addfriend")){
                String uph = chatMsg.getSenderid();
                SendToServer.getUserIcOfAddMeAsFriend(uph);

            }else if(type.equals("agreeYourAddFriend")){
                String uph = chatMsg.getSenderid();
                SendToServer.getUIcOfAgreeYourFriendRequest(uph);

            }

        }

        Vector<ChatMsg> delete = new Vector<>();
        if(MyMessageQueue.requestQueueHadHandled!=null&&MyMessageQueue.requestQueueNotHandle!=null){
            for(int i=0;i<MyMessageQueue.requestQueueHadHandled.size();++i){
                ChatMsg chatMsg = MyMessageQueue.requestQueueHadHandled.get(i);

                for(ChatMsg chatMsg1:MyMessageQueue.requestQueueNotHandle){
                    if(chatMsg.getSenderid().equals(chatMsg1.getSenderid())&&
                            chatMsg.getType().equals(chatMsg1.getType())){
                        delete.add(chatMsg);
                        break;
                    }
                }
            }
        }
        if(MyMessageQueue.requestQueueHadHandled!=null){
            for(ChatMsg integer:delete){
                MyMessageQueue.requestQueueHadHandled.removeElement(integer);
            }
        }


        for(ChatMsg chatMsg:chatMsgs){
            if(chatMsg.getType().equals("someoneHasHandledAddGroup")){

                String msg = chatMsg.getMsgbody();
                String[] strings = msg.split(" ");
                String operateor = "";
                String handleid = "";
                String handlername = "";
                for(int i=0;i<strings.length;++i){
                    if(i==0){
                        operateor = strings[i];
                    }else if(i==1){
                        handleid = strings[i];
                    }else if(i==2){
                        handlername = strings[i];
                    }
                }

                for(int i=0;i<MyMessageQueue.requestQueueNotHandle.size();++i){
                    if(chatMsg.getMsgid()==MyMessageQueue.requestQueueNotHandle.get(i).getMsgid()){
                        MyMessageQueue.requestQueueNotHandle.get(i).setType("someoneHasHandledAddGroup");
                        MyMessageQueue.requestQueueNotHandle.get(i).setHandleRs(operateor);
                        MyMessageQueue.requestQueueNotHandle.get(i).setReceiverid(handleid);
                        MyMessageQueue.requestQueueNotHandle.get(i).setReceivername(handlername);
                    }
                }


                Vector<ChatMsg> deletechatMsgs = new Vector<>();
                for(int i=0;i<MyMessageQueue.requestQueueHadHandled.size();++i){
                    if(chatMsg.getMsgid()==MyMessageQueue.requestQueueHadHandled.get(i).getMsgid()){
                        deletechatMsgs.add(MyMessageQueue.requestQueueHadHandled.get(i));
                    }
                }
                for(ChatMsg index: deletechatMsgs){
                    MyMessageQueue.requestQueueHadHandled.removeElement(index);
                }
            }
        }

        Intent intent1=new Intent("thefirstchange.example.com.communicationtext.NOTIFICATION");
        intent1.putExtra("type","getRequestRs");
        NettyService.nettyService.sendCast(intent1);

        Intent intent2=new Intent("thefirstchange.example.com.communicationtext.OTHERPEOPLEAPPLICATION");
        intent2.putExtra("type","getRequestRs");
        NettyService.nettyService.sendCast(intent2);


        if(saveFrame){

            if(StaticAllList.chatRecordDao!=null){
                StaticAllList.chatRecordDao.saveNoticeFrame("request","",-1);
            }else{
                MainActivity.initChatRecorddb();
                StaticAllList.chatRecordDao.saveNoticeFrame("request","",-1);
            }
        }

        for(ChatMsg requestMsg :   MyMessageQueue.requestQueueNotHandle){

            if(StaticAllList.chatRecordDao!=null){
                StaticAllList.chatRecordDao.saveRequest(requestMsg);
            }else{
                MainActivity.initChatRecorddb();
                StaticAllList.chatRecordDao.saveRequest(requestMsg);
            }
        }

    }
}
