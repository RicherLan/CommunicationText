package thefirstchange.example.com.communicationtext.netty.commandToserver;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.schedulearrangement.RequestSchedule;
import thefirstchange.example.com.communicationtext.gson.SearchEmptyCourse;
import thefirstchange.example.com.communicationtext.netty.client.NettyClient;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.AddDongTaiImageCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.AddDongtaiCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.DongTaicommentCommentCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.DongtaiCommentCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.DongtaiPraiseCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.GetDTFirstImAndtextByIdCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.GetDTMsgByIdCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.GetDongtaiByDTIDCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.GetDongtaiICByDTIDCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.GetDongtaiMsgCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.GetDongtaiUICByDTID_RequestHandler;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.GetNewComByDongtaiIdCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.GetNewDongtaiMsgIDsCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.GetOldComByDongtaiIdCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.GetOldDongtaiIDsCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.GetOldDongtaiMsgIDsCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.GetRtComUICCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.GetUDtByDTIDCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.GetUDtICByDTICIDCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.GetUNDtIDsCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.GetUsODtIDsCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.GetnewDongtaiIDsCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.ReciveDongtaiCommentCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.dongtaiCommandSender.ReciveDongtaiPraiseCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.eduCommandSender.SearchEmptyClassRoomCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender.GetGroupICOfSearchAddGroupCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender.GetGroupIcByGidCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender.GetGroupInfoByGidCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender.GetGroupUserIconByPhCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender.GetUICOfSearchAddUserCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender.GetUserIcOfAddGroupCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender.GetUserIcOfAddMeAsFriendCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender.UpGroupIconCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender.getFriendListUIcByPhCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.friendGroupOperCommandSender.getUIcOfAgreeYourFriendCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.notificationCommandSender.GetNotiIconOfGroupCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.notificationCommandSender.GetNotiIconOfUserCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.personInfoCommandSender.GetIndexICOfPhCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.personInfoCommandSender.GetIndexInfoOfPhCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.AddFriendCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.AddFriendResultCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.AddGroupCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.AddGroupReturnCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.AddGroupToGroupListCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.AddMeFriendReturnCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.AddUserToFriendListCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.AlterCorpYearTermCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.CorpLoadCourseRsCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.CreateCorprationCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.CreateGroupCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.DeleteFriendCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.ExitGroupCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.FreshAllFriendInfoCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.FreshNotificationCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.GetAllDutyNotiNotReadCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.GetAllFriendInfoCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.GetAllSDCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.GetCourseSchByUIDCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.GetFriendIDOnlineCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.GetFriendInfoByIDCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.GetGroupAllUserCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.GetGroupChatMsgNotReadCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.GetGroupsInfoOfUserCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.GetPerIconCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.GetPersonalInfoCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.GetRequestFriendOrGroupCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.GetSDOfGidCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.GetScoreOfUIDCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.GetSingleChatMsgNotReadCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.GroupAdmiReciveExitGroupCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.IsPhonenumberRegistedCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.LoadCourseOfCorpCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.LoginCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.LoginOutCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.OkGetGroupAllUserCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.ReadDutyNotiCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.ReadaddGroupResultCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.ReceiveSingleChatTextCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.ReciveFDeleteMeCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.RecivierGroupChatTextCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.RegisterTestCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.SchduleArrangementCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.SearchAddGroupCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.SearchAddUserCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.SearchEmptyCourseCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.SendGroupChatFileCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.SendGroupChatTextCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.SendSingleChatFileCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.SendSingleChatTextCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.UpFriendRemarkCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.UpGroupPartCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.UpGroupRemarkCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.UpPasswordCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.UpPeronalInfoCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.UpPersonalIconCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender.UpRequestMsgStateCommandSender;
import thefirstchange.example.com.communicationtext.netty.commandToserver.userOtherInfoCommandSender.GetUserIcByPhCommandSender;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.util.MyTools;
import thefirstchange.example.com.communicationtext.util.PictureUtil;

/*
向服务器的所有请求
 */
public class SendToServer {

    public static String TAG = "SendToServer";

    //用户注册  测试阶段
    public static void registest(String schoolname,String collegename,String majorname,int ruxueyear,String phonenumber,String password){

        new Thread(new Runnable() {
            @Override
            public void run() {
                RegisterTestCommandSender
                        .getInsatnce()
                        .getPacket(schoolname, collegename, majorname, ruxueyear, phonenumber, password)
                        .execute(NettyClient.channel);
            }
        }).start();


    }



    //群聊天   发送消息
    public static void sendergroupchattext(ChatMsg msg){

        new Thread(new Runnable() {
            @Override
            public void run() {
                int groupid = msg.getGroupid();
                String senderid = msg.getSenderid();
                String sendergroupname = msg.getSendername();
                String message = msg.getMsgbody();
                String msgtype = msg.getMsgtype();
                long time = msg.getMsgtime();

                SendGroupChatTextCommandSender
                        .getInsatnce()
                        .getPacket(groupid, senderid , sendergroupname, message , msgtype , time )
                        .execute(NettyClient.channel);

            }
        }).start();


    }

    //群聊天   发送语音 照片
    public static void sendergroupchatfile(ChatMsg msg){

        new Thread(new Runnable() {
            @Override
            public void run() {
                int groupid = msg.getGroupid();
                String senderid = msg.getSenderid();
                String sendergroupname = msg.getSendername();
                String message = msg.getMsgbody();
                String msgtype = msg.getMsgtype();
                long time = msg.getMsgtime();
                double voicetime = msg.getVoicetime();


                String filepath = msg.getMsgbody();
                byte[] bytes = null;
                if(msg.getMsgtype().equals("voice")){
                    try{
                        File file = new File(filepath);
                        FileInputStream inputFile = new FileInputStream(file);
                        bytes = new byte[(int)file.length()];
                        inputFile.read(bytes);
                        inputFile.close();
                    }catch(FileNotFoundException a){
                        // a.printStackTrace();
                       // return "filenotfound";
                    }catch (IOException a){
                        //a.printStackTrace();
                       // return "error";
                    }
                }else if(msg.getMsgtype().equals("image")){
                    bytes = PictureUtil.bitmapToByte(filepath);
                }

                if(bytes!=null){
                    SendGroupChatFileCommandSender
                            .getInsatnce()
                            .getPacket(groupid, senderid , sendergroupname, message , msgtype , time,voicetime,bytes )
                            .execute(NettyClient.channel);
                   // return "ok";
                }
               // return "error";
            }
        }).start();


    }


    //群聊   接收方接收消息后   回执该消息已读
    public static void reciviergroupchattext(int msgid,int groupid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                RecivierGroupChatTextCommandSender
                        .getInsatnce()
                        .getPacket(msgid, groupid)
                        .execute(NettyClient.channel);
            }
        }).start();

    }

    //单人聊天   接收方接收消息后   回执该消息已读
    public static void reciversinglechattext(String senderid,int msgid){

        //String json = "{\"senderid\":\""+senderid+"\",\"reciverid\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"msgid\":\""+msgid+"\",}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                ReceiveSingleChatTextCommandSender
                        .getInsatnce()
                        .getPacket(senderid, msgid )
                        .execute(NettyClient.channel);

            }
        }).start();

    }


    //单人聊天   发送消息
    public static void sendersinglechattext(ChatMsg msg){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String senderid = msg.getSenderid();
                String reciverid = msg.getReceiverid();
                String message = msg.getMsgbody();
                String msgtype = msg.getMsgtype();
                long time = msg.getMsgtime();


                SendSingleChatTextCommandSender
                        .getInsatnce()
                        .getPacket( senderid , reciverid , message, msgtype, time)
                        .execute(NettyClient.channel);
            }
        }).start();


    }

    //单人聊天   发送语音 照片
    public static void sendersinglechatfile(ChatMsg msg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String senderid = msg.getSenderid();
                String reciverid = msg.getReceiverid();
                String message = msg.getMsgbody();
                String msgtype = msg.getMsgtype();
                long time = msg.getMsgtime();
                double voicetime = msg.getVoicetime();
                //String json = "{\"senderid\":\""+senderid+"\",\"reciverid\":\""+reciverid+"\",\"msgtype\":\""+msgtype+"\",\"time\":\""+time+"\",\"voicetime\":\""+voicetime+"\"}";
                String filepath = msg.getMsgbody();
                byte[] bytes = null;
                if(msg.getMsgtype().equals("voice")){
                    try{
                        File file = new File(filepath);
                        FileInputStream inputFile = new FileInputStream(file);
                        bytes = new byte[(int)file.length()];
                        inputFile.read(bytes);
                        inputFile.close();
                    }catch(FileNotFoundException a){
                        //a.printStackTrace();
                       // return "filenotfound";
                    }catch (IOException a){
                        a.printStackTrace();
                      //  return "error";
                    }
                }else if(msg.getMsgtype().equals("image")){
                    bytes = PictureUtil.bitmapToByte(filepath);
                }

                if(bytes!=null){

                    SendSingleChatFileCommandSender
                            .getInsatnce()
                            .getPacket(senderid,  reciverid , message,  msgtype, time , voicetime , bytes)
                            .execute(NettyClient.channel);
                   // return "ok";
                }
             //   return "error";
            }
        }).start();

    }





    //登录   结果在loginresult中
    public static void login(String phonenumber,String password,String type){
        new Thread(new Runnable() {
            @Override
            public void run() {
                LoginCommandSender
                        .getInsatnce()
                        .getPacket( phonenumber, password, type)
                        .execute(NettyClient.channel);
            }
        }).start();

    }


    //修改密码   结果在changePasswordResult中
    public static void changePassword(String oldpassword,String newpassword){
        new Thread(new Runnable() {
            @Override
            public void run() {
                UpPasswordCommandSender
                        .getInsatnce()
                        .getPacket( oldpassword,newpassword)
                        .execute(NettyClient.channel);
            }
        }).start();


    }


    ///用户注册账号时  判断提交的手机号是否已经被注册
    public static void isPhonenumberRegisted(String phonenumber){

        new Thread(new Runnable() {
            @Override
            public void run() {
                IsPhonenumberRegistedCommandSender
                        .getInsatnce()
                        .getPacket( phonenumber)
                        .execute(NettyClient.channel);
            }
        }).start();

    }


    /*

    //获得短信验证码
    public static void getPhonenumberCode(String phonenumber) {

        String json = "{\"phonenumber\":\"" + phonenumber + "\"}";

        NettyClient.getInstance().sendMsgToServer("notfile", "getPhonenumberCode", "", json, new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {                //4
                    Log.d(TAG, "Write auth successful");
                    //logSend(msg);
                } else {
                    Log.d(TAG, "Write auth error");
                }
            }
        });
    }


    //用户注册
    public static void registeruser(String schoolname,String collegename,String majorname,String nickname,String phonenumber,String password,String code){

        String json = "{\"schoolname\":\""+schoolname+"\",\"collegename\":\""+collegename+"\",\"majorname\":\""+majorname+"\",\"nickname\":\""+nickname+"\",\"phonenumber\":\""+phonenumber+"\",\"password\":\""+password+"\",\"code\":\""+code+"\"}";


        NettyClient.getInstance().sendMsgToServer("notfile","registeruser","",json,new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {                //4
                    Log.d(TAG, "Write auth successful");
                    //logSend(msg);
                } else {
                    Log.d(TAG, "Write auth error");
                }
            }
        });

    }

*/


    //获得某用户的user表中的基本信息
    public static void getFriendInfoByPhonenumber(String phonenumber){

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetFriendInfoByIDCommandSender
                        .getInsatnce()
                        .getPacket( phonenumber)
                        .execute(NettyClient.channel);
            }
        }).start();
        //String json = "{\"ph1\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"ph2\":\""+phonenumber+"\"}";

    }


    //进入某人的个人页面时  获得其基本信息
    public static void getIndexInfoByPh(String phonenumber){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetIndexInfoOfPhCommandSender
                        .getInsatnce()
                        .getPacket( phonenumber)
                        .execute(NettyClient.channel);
            }
        }).start();
       // String json = "{\"ph1\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"ph2\":\""+phonenumber+"\"}";

    }

    //进入某人的个人页面时  获得其头像息
    public static void getIndexICByPh(String phonenumber){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetIndexICOfPhCommandSender
                        .getInsatnce()
                        .getPacket( phonenumber)
                        .execute(NettyClient.channel);
            }
        }).start();

    }


    //获得某用户的群的基本信息
    public static void getGroupsInfoOfUser(String phonenumber){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetGroupsInfoOfUserCommandSender
                        .getInsatnce()
                        .getPacket( phonenumber)
                        .execute(NettyClient.channel);
            }
        }).start();
       // String json = "{\"phonenumber\":\""+phonenumber+"\"}";

    }

    //获得某群的头像
    public static void getGroupIcByGid(int gid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetGroupIcByGidCommandSender
                        .getInsatnce()
                        .getPacket( gid)
                        .execute(NettyClient.channel);
            }
        }).start();

    }



    //获得自己的所有好友信息    一般是刚登陆
    public static void getAllFriendInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetAllFriendInfoCommandSender
                        .getInsatnce()
                        .getPacket()
                        .execute(NettyClient.channel);
            }
        }).start();
//        String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\"}";


    }

    //获得自己的所有好友信息    一般是客户端刷新好友列表时用到
    public static void freshAllFriendInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                FreshAllFriendInfoCommandSender
                        .getInsatnce()
                        .getPacket()
                        .execute(NettyClient.channel);
            }
        }).start();
       // String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\"}";


    }

    //刷新  消息界面
    public static void freshNotification(Vector<String> phs){
        new Thread(new Runnable() {
            @Override
            public void run() {
                FreshNotificationCommandSender
                        .getInsatnce()
                        .getPacket(phs)
                        .execute(NettyClient.channel);
            }
        }).start();
//        String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\"}";
//        String s = new Gson().toJson(phs);



    }

    //获得在线的好友有哪些    就是返回在线的账号就行
    public static void getFriendOnline(String phonenumber){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetFriendIDOnlineCommandSender
                        .getInsatnce()
                        .getPacket()
                        .execute(NettyClient.channel);
            }
        }).start();
        //String json = "{\"phonenumber\":\""+phonenumber+"\"}";

    }


    //删除自己的某一个好友
    public static void deleteFriend(String friendphonenumber ){

        new Thread(new Runnable() {
            @Override
            public void run() {
                DeleteFriendCommandSender
                        .getInsatnce()
                        .getPacket(friendphonenumber)
                        .execute(NettyClient.channel);
            }
        }).start();
       // String json = "{\"phonenumber\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"friendphonenumber\":\""+friendphonenumber+"\"}";



    }


    //好友删除自己时   自己收到被删除的消息   要回执
    public static void reciveDeleteMe(int msgid ){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ReciveFDeleteMeCommandSender
                        .getInsatnce()
                        .getPacket(msgid)
                        .execute(NettyClient.channel);
            }
        }).start();
       // String json = "{\"msgid\":\""+msgid+"\"}";

    }


    //退群
    public static void exitGroup(int groupid ){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ExitGroupCommandSender
                        .getInsatnce()
                        .getPacket(groupid)
                        .execute(NettyClient.channel);
            }
        }).start();
       // String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"gid\":\""+groupid+"\"}";


    }

    //群成员退群时   群管理员收到退群消息时   回执
    public static void GroupAdmiReciveExitGroup(String phonenumber,int msgid ){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GroupAdmiReciveExitGroupCommandSender
                        .getInsatnce()
                        .getPacket(msgid)
                        .execute(NettyClient.channel);
            }
        }).start();
        //String json = "{\"phonenumber\":\""+phonenumber+"\",\"msgid\":\""+msgid+"\"}";


    }



    //好友或群请求   收到消息后   更改消息的读取状态
    public static void changeRequestMsgState(int msgid){

        new Thread(new Runnable() {
            @Override
            public void run() {
                UpRequestMsgStateCommandSender
                        .getInsatnce()
                        .getPacket(msgid)
                        .execute(NettyClient.channel);
            }
        }).start();
        //String json = "{\"mid\":\""+msgid+"\"}";



    }

    //添加好友
    public static void addFriend(String phonenumber,String friendid,String addmsg){

        new Thread(new Runnable() {
            @Override
            public void run() {
                AddFriendCommandSender
                        .getInsatnce()
                        .getPacket(friendid, addmsg)
                        .execute(NettyClient.channel);
            }
        }).start();
       // String json = "{\"phonenumber\":\""+phonenumber+"\",\"friendid\":\""+friendid+"\",\"addmsg\":\""+addmsg+"\"}";


    }

    //添加好友  被添加方要获得对方的头像
    public static void getUserIcOfAddMeAsFriend(String phonenumber){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetUserIcOfAddMeAsFriendCommandSender
                        .getInsatnce()
                        .getPacket(phonenumber)
                        .execute(NettyClient.channel);
            }
        }).start();

    }


    //添加群  群管理员要获得对方的头像
    public static void getUserIcOfAddGroup(int groupid,String phonenumber){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetUserIcOfAddGroupCommandSender
                        .getInsatnce()
                        .getPacket(groupid,phonenumber)
                        .execute(NettyClient.channel);
            }
        }).start();

    }

    //对方同意了你的好友请求  获得对方头像
    public static void getUIcOfAgreeYourFriendRequest(String phonenumber){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getUIcOfAgreeYourFriendCommandSender
                        .getInsatnce()
                        .getPacket(phonenumber)
                        .execute(NettyClient.channel);
            }
        }).start();

    }

    //用户添加自己为好友   自己给回复  同意还是不同意
    public static void AddFriendReturn(int msgid,String mynickname,String otherphonenumber,String othernickname,String result){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AddMeFriendReturnCommandSender
                        .getInsatnce()
                        .getPacket( msgid, otherphonenumber, othernickname, result)
                        .execute(NettyClient.channel);
            }
        }).start();
        //String json = "{\"phonenumber\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"msgid\":\""+msgid+"\",\"mynickname\":\""+mynickname+"\",\"otherphonenumber\":\""+otherphonenumber+"\",\"othernickname\":\""+othernickname+"\",\"result\":\""+result+"\"}";



    }

    //添加好友时   对方同意或拒绝了你的请求   你已经读到了对方同意还是拒绝   给服务器回执
    public static void AddFriendResult(int msgid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AddFriendResultCommandSender
                        .getInsatnce()
                        .getPacket( msgid)
                        .execute(NettyClient.channel);
            }
        }).start();
        //String json = "{\"msgid\":\""+msgid+"\"}";



    }

    //申请加群
    public static void requestAddGroup(int groupid,String addmsg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AddGroupCommandSender
                        .getInsatnce()
                        .getPacket(  groupid, addmsg)
                        .execute(NettyClient.channel);
            }
        }).start();
       // String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"gid\":\""+groupid+"\",\"addmsg\":\""+addmsg+"\"}";



    }

    //用户申请加群时   管理员同意或不同意   回执
    public static void requestAddGroupReturn(int msgid,String otherphonenumber,int groupid,String rs){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AddGroupReturnCommandSender
                        .getInsatnce()
                        .getPacket( msgid, otherphonenumber, groupid, rs)
                        .execute(NettyClient.channel);
            }
        }).start();
        //String json = "{\"msgid\":\""+msgid+"\",\"phonenumber\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"otherphonenumber\":\""+otherphonenumber+"\",\"groupid\":\""+groupid+"\",\"rs\":\""+rs+"\"}";



    }

    //用户申请加群时   管理员同意或不同意   回执
    public static void ReadaddGroupResult(int msgid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ReadaddGroupResultCommandSender
                        .getInsatnce()
                        .getPacket( msgid)
                        .execute(NettyClient.channel);
            }
        }).start();
        //String json = "{\"msgid\":\""+msgid+"\"}";



    }


    //拿到所有的好友、群请求信息  一般在刚登陆的时候
    public static void getRequestFriendOrGroup(String phonenumber){

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetRequestFriendOrGroupCommandSender
                        .getInsatnce()
                        .getPacket( )
                        .execute(NettyClient.channel);
            }
        }).start();
       // String json = "{\"phonenumber\":\""+phonenumber+"\"}";

    }

    //拿到单人聊天  未读信息     一般是刚登陆的时候
    public static void getSingleChatMsgNotRead(String phonenumber){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetSingleChatMsgNotReadCommandSender
                        .getInsatnce()
                        .getPacket( )
                        .execute(NettyClient.channel);
            }
        }).start();
        //String json = "{\"phonenumber\":\""+phonenumber+"\"}";

    }

    //拿到群聊天  未读信息     一般是刚登陆的时候
    public static void getGroupChatMsgNotRead(String phonenumber){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetGroupChatMsgNotReadCommandSender
                        .getInsatnce()
                        .getPacket()
                        .execute(NettyClient.channel);
            }
        }).start();
        //String json = "{\"ph\":\""+phonenumber+"\"}";


    }


    //获得个人信息     一般是刚登陆的时候
    public static void getPersonalInfo(String phonenumber){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetPersonalInfoCommandSender
                        .getInsatnce()
                        .getPacket()
                        .execute(NettyClient.channel);
            }
        }).start();
        //String json = "{\"phonenumber\":\""+phonenumber+"\"}";

    }

    //客户端添加某人好友时   搜索账号 只需要看到其账号 网名 头像就行
    public static void searchAddUser(String phonenumber){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SearchAddUserCommandSender
                        .getInsatnce()
                        .getPacket(phonenumber)
                        .execute(NettyClient.channel);
            }
        }).start();
        //String json = "{\"phonenumber\":\""+phonenumber+"\",\"Myphonenumber\":\""+Myphonenumber+"\"}";


    }

    //客户端添加群时   搜索账号 只需要看到其账号 网名 头像就行
    public static void searchAddGroup(int groupid){
       // String json = "{\"gid\":\""+groupid+"\",\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                SearchAddGroupCommandSender
                        .getInsatnce()
                        .getPacket(groupid)
                        .execute(NettyClient.channel);
            }
        }).start();

    }


    //进入用户资料界面  请求某一条动态
    //获得动态的基本内容
    public static void gUsDtByDTID(String ph,int id){
        //String json = "{\"ph\":\""+ph+"\",\"id\":\""+id+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetUDtByDTIDCommandSender
                        .getInsatnce()
                        .getPacket( ph, id)
                        .execute(NettyClient.channel);
            }
        }).start();


    }

    //进入用户资料界面  请求某一条动态
    //获得动态的的一张图片
    public static void getUserDongtaiIconByIcId(int dtid,String ph,int fileid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetUDtICByDTICIDCommandSender
                        .getInsatnce()
                        .getPacket(dtid, ph, fileid)
                        .execute(NettyClient.channel);
            }
        }).start();

    }


    //进入用户资料界面  用户下拉刷新动态的页面  就是加载新的动态        返回6条动态的id    ph2是要查询的人的账号
    public static void gUsNDtIDs(String ph1,String ph2){
       // String json = "{\"ph1\":\""+ph1+"\",\"ph2\":\""+ph2+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetUNDtIDsCommandSender
                        .getInsatnce()
                        .getPacket( ph2)
                        .execute(NettyClient.channel);
            }
        }).start();


    }

    //进入用户资料界面   用户上拉刷新动态的页面  就是加载旧的动态        返回6条动态的id
    //从当前的dongtaiid开始往前找6条以前的  ph2为id的主人
    public static void gUsODtIDs(String ph1,int dongtaiid,String ph2){
       // String json = "{\"ph1\":\""+ph1+"\",\"id\":\""+dongtaiid+"\",\"ph2\":\""+ph2+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetUsODtIDsCommandSender
                        .getInsatnce()
                        .getPacket( ph2,dongtaiid)
                        .execute(NettyClient.channel);
            }
        }).start();


    }



    //请求某一条动态
    public static void getDongtaiByDTID(int id ){
       // String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"id\":\""+id+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetDongtaiByDTIDCommandSender
                        .getInsatnce()
                        .getPacket(id)
                        .execute(NettyClient.channel);
            }
        }).start();


    }

    //请求某一条动态  获得动态主人头像
    public static void getDongtaiUICByDTID(String ph,int dtid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetDongtaiUICByDTID_RequestHandler
                        .getInsatnce()
                        .getPacket( ph,dtid)
                        .execute(NettyClient.channel);
            }
        }).start();

    }

    //请求某一条动态  获得动态图片
    public static void  getDongtaiIcByIcid(int dtid,int fileid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetDongtaiICByDTIDCommandSender
                        .getInsatnce()
                        .getPacket(dtid, fileid)
                        .execute(NettyClient.channel);
            }
        }).start();

    }



    //用户下拉刷新动态的页面  就是加载新的动态        返回6条动态的id
    public static void getnewDongtaiIDs(){
      //  String json = "{\"ph\":\""+userid+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetnewDongtaiIDsCommandSender
                        .getInsatnce()
                        .getPacket()
                        .execute(NettyClient.channel);
            }
        }).start();


    }

    //用户上拉刷新动态的页面  就是加载旧的动态        返回6条动态的id
    //从当前的dongtaiid开始往前找6条以前的
    public static void getoldDongtaiIDs(String userid,int dongtaiid){
       // String json = "{\"ph\":\""+userid+"\",\"id\":\""+dongtaiid+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetOldDongtaiIDsCommandSender
                        .getInsatnce()
                        .getPacket(dongtaiid)
                        .execute(NettyClient.channel);
            }
        }).start();


    }


    //获得动态消息的内容
    public static void getDTMsgById(String userid,int id){
       // String json = "{\"ph\":\""+userid+"\",\"id\":\""+id+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetDTMsgByIdCommandSender
                        .getInsatnce()
                        .getPacket(id)
                        .execute(NettyClient.channel);
            }
        }).start();


    }

    //获得动态的第一张图片和内容
    public static void getDTFirstImAndtextById(String userid,int id){
       // String json = "{\"ph\":\""+userid+"\",\"id\":\""+id+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetDTFirstImAndtextByIdCommandSender
                        .getInsatnce()
                        .getPacket(id)
                        .execute(NettyClient.channel);
            }
        }).start();


    }


    //用户下拉刷新动态消息的页面  就是加载新的动态消息        返回6条动态消息的id
    public static void getnewDongtaiMsgIDs(String userid){
       // String json = "{\"ph\":\""+userid+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetNewDongtaiMsgIDsCommandSender
                        .getInsatnce()
                        .getPacket()
                        .execute(NettyClient.channel);
            }
        }).start();


    }

    //用户上拉刷新动态消息的页面  就是加载旧的动态消息        返回6条动态消息的id
    //从当前的id开始往前找6条以前的
    public static void getoldDongtaiMsgIDs(String userid,int dongtaimsgid){
        //String json = "{\"ph\":\""+userid+"\",\"id\":\""+dongtaimsgid+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetOldDongtaiMsgIDsCommandSender
                        .getInsatnce()
                        .getPacket(dongtaimsgid)
                        .execute(NettyClient.channel);
            }
        }).start();


    }

    //进入某动态的所有评论界面    刚开始要刷新   返回根评论总共10条   大的评论下最多回执3条
    public static void getNewComByDongtaiId(int dongtaiid){
     //   String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"id\":\""+dongtaiid+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetNewComByDongtaiIdCommandSender
                        .getInsatnce()
                        .getPacket(dongtaiid)
                        .execute(NettyClient.channel);
            }
        }).start();


    }


    //进入某动态的所有评论界面    刚开始要刷新   返回根评论总共10条   大的评论下最多回执3条  获得头像
    public static void getRtComUIC(String ph2){
       // String json = "{\"ph1\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"ph2\":\""+ph2+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetRtComUICCommandSender
                        .getInsatnce()
                        .getPacket(ph2)
                        .execute(NettyClient.channel);
            }
        }).start();


    }

    //进入某动态的所有评论界面  上拉刷新   返回根评论总共10条   大的评论下最多回执3条  没有头像
    public static void getOldComByDongtaiId(String ph,int dtid,int comid){
       // String json = "{\"ph\":\""+ph+"\",\"dtid\":\""+dtid+"\",\"comid\":\""+comid+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetOldComByDongtaiIdCommandSender
                        .getInsatnce()
                        .getPacket( ph, dtid, comid)
                        .execute(NettyClient.channel);
            }
        }).start();


    }



    //发表动态    普通用户的话  类型用user表示
    public static void addDongtai(String usertype,String text,int picturenum,long time){
     //   String json = "{\"usertype\":\""+usertype+"\",\"userid\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"text\":\""+text+"\",\"picturenum\":\""+picturenum+"\",\"time\":\""+time+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                AddDongtaiCommandSender
                        .getInsatnce()
                        .getPacket( usertype, text, picturenum, time)
                        .execute(NettyClient.channel);
            }
        }).start();


    }

    //用户发表动态时   图片分开传送
    public static void adddongtaiimage(String ph,int dongtaiid,long time,String filepath){
//        String json = "{\"ph\":\""+ph+"\",\"id\":\""+dongtaiid+"\",\"time\":\""+time+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes  = PictureUtil.bitmapToByte(filepath);

                AddDongTaiImageCommandSender
                        .getInsatnce()
                        .getPacket( dongtaiid, time, bytes)
                        .execute(NettyClient.channel);
            }
        }).start();



    }


    //用户给某一条动态点赞
    public static void dongtaipraise(String phonenumber,int dongtaiid){
      //  String json = "{\"phonenumber\":\""+phonenumber+"\",\"dongtaiid\":\""+dongtaiid+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                DongtaiPraiseCommandSender
                        .getInsatnce()
                        .getPacket( dongtaiid)
                        .execute(NettyClient.channel);

            }
        }).start();

    }



    //收到了别人给自己的动态的点赞  回执服务器已读
    public static void reciveDongtaiPraise(int id){
        //String json = "{\"id\":\""+id+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                ReciveDongtaiPraiseCommandSender
                        .getInsatnce()
                        .getPacket( id)
                        .execute(NettyClient.channel);
            }
        }).start();


    }

    //给动态评论
    public static void dongtaiComment(int dongtaiid,String msg){
        //String json = "{\"phonenumber\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"dongtaiid\":\""+dongtaiid+"\",\"msg\":\""+msg+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                DongtaiCommentCommandSender
                        .getInsatnce()
                        .getPacket( dongtaiid, msg)
                        .execute(NettyClient.channel);
            }
        }).start();


    }

    //给动态的评论评论
    public static void dongtaicommentComment(String phonenumber,int dongtaiid,int commentid,String msg){
      //  String json = "{\"phonenumber\":\""+phonenumber+"\",\"dongtaiid\":\""+dongtaiid+"\",\"commentid\":\""+commentid+"\",\"msg\":\""+msg+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                DongTaicommentCommentCommandSender
                        .getInsatnce()
                        .getPacket( dongtaiid, commentid, msg)
                        .execute(NettyClient.channel);
            }
        }).start();


    }


    //收到了别人给自己的动态的评论  回执服务器已读
    public static void reciveDongtaiComment(int id){
        //String json = "{\"id\":\""+id+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                ReciveDongtaiCommentCommandSender
                        .getInsatnce()
                        .getPacket(id)
                        .execute(NettyClient.channel);
            }
        }).start();


    }

    //拿到自己的动态消息  一般是刚上线的时候
    public static void getDongtaiMsg(String phonenumber){
        //String json = "{\"phonenumber\":\""+phonenumber+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetDongtaiMsgCommandSender
                        .getInsatnce()
                        .getPacket()
                        .execute(NettyClient.channel);
            }
        }).start();


    }


    //更改自己的头像
    public static void ChPerIc(Bitmap bitmap){

        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes;
                bytes = MyTools.Bitmap2Bytes(bitmap);
                UpPersonalIconCommandSender
                        .getInsatnce()
                        .getPacket(bytes)
                        .execute(NettyClient.channel);
            }
        }).start();

    }



    //更改群头像
    public static void ChGroupIc(int groupid,Bitmap bitmap){

        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes;
                bytes = MyTools.Bitmap2Bytes(bitmap);
                UpGroupIconCommandSender
                        .getInsatnce()
                        .getPacket(groupid,bytes)
                        .execute(NettyClient.channel);
            }
        }).start();



    }


    //获取自己的的头像
    public static void getPerIc(String phonenumber){
        //String json = "{\"type\":\""+type+"\",\"myph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"ph\":\""+phonenumber+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetPerIconCommandSender
                        .getInsatnce()
                        .getPacket( phonenumber)
                        .execute(NettyClient.channel);
            }
        }).start();

    }

    //进入好友列表   如果本地没有好友的头像   获取好友的的头像
    public static void getFriendListUIcByPh(String phonenumber){
        //String json = "{\"type\":\""+type+"\",\"myph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"ph\":\""+phonenumber+"\"}";

        new Thread(new Runnable() {
            @Override
            public void run() {
                getFriendListUIcByPhCommandSender
                        .getInsatnce()
                        .getPacket( phonenumber)
                        .execute(NettyClient.channel);
            }
        }).start();

    }


    //修改个人资料
    public static void ChPerIn(String nickname,String sex,String from,String add,String sch,String dep,String maj,String bir,int rxy,String info){
//        String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"ni\":\""+ni+"\",\"sex\":\""+sex+"\"," +
//                "\"from\":\""+from+"\",\"add\":\""+add+"\",\"sch\":\""+sch+"\",\"dep\":\""+dep+"\",\"maj\":\""+maj+"\",\"bir\":\""+bir+"\",\"rxy\":\""+rxy+"\",\"info\":\""+info+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                UpPeronalInfoCommandSender
                        .getInsatnce()
                        .getPacket(  nickname, sex, from, add, sch, dep, maj, bir, rxy, info)
                        .execute(NettyClient.channel);
            }
        }).start();


    }




    //修改好友的备注
    public static void changeFriendRemark(String friendph ,String remark){
       // String json = "{\"myph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"friendph\":\""+friendph+"\",\"remark\":\""+remark+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                UpFriendRemarkCommandSender
                        .getInsatnce()
                        .getPacket( friendph , remark)
                        .execute(NettyClient.channel);
            }
        }).start();


    }

    //客户端添加好友成功后  将好友加入到自己的好友列表  因此需要好友的资料
    public static void addUserToFriendList(String phonenumber){
        //String json = "{\"myph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"ph\":\""+phonenumber+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                AddUserToFriendListCommandSender
                        .getInsatnce()
                        .getPacket(phonenumber)
                        .execute(NettyClient.channel);
            }
        }).start();


    }

    //修改自己的群名片
    public static void upGroupRemark(int groupid ,String remark){
      //  String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"gid\":\""+groupid+"\",\"remark\":\""+remark+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                UpGroupRemarkCommandSender
                        .getInsatnce()
                        .getPacket( groupid , remark)
                        .execute(NettyClient.channel);
            }
        }).start();



    }

    //修改自己的部室
    public static void upGroupPart(int groupid ,String part){
       // String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"gid\":\""+groupid+"\",\"part\":\""+part+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                UpGroupPartCommandSender
                        .getInsatnce()
                        .getPacket( groupid , part)
                        .execute(NettyClient.channel);
            }
        }).start();


    }

    //获得某群的所有成员   没有头像
    public static void getGroupAllU(int groupid,String type ){
        //String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"groupid\":\""+groupid+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetGroupAllUserCommandSender
                        .getInsatnce()
                        .getPacket( groupid ,type)
                        .execute(NettyClient.channel);
            }
        }).start();


    }


    //获得某群的所有成员后  向服务器回执     来让服务器发头像
    public static void okGroupAllU(int groupid ){
       // String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"groupid\":\""+groupid+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkGetGroupAllUserCommandSender
                        .getInsatnce()
                        .getPacket( groupid )
                        .execute(NettyClient.channel);
            }
        }).start();


    }


    //创建社团
    public static void createCorp(String name,String type,String info ,String corppart,int year,int xueqi){
        //String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"name\":\""+name+"\",\"info\":\""+info+"\",\"type\":\""+type+"\",\"corppart\":\""+corppart+"\",\"year\":\""+year+"\",\"xueqi\":\""+xueqi+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                CreateCorprationCommandSender
                        .getInsatnce()
                        .getPacket(  name, type, info , corppart, year, xueqi )
                        .execute(NettyClient.channel);
            }
        }).start();

    }



    //创建群
    public static void createGroup(String groupname ){
       // String json = "{\"phonenumber\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"groupname\":\""+groupname+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                CreateGroupCommandSender
                        .getInsatnce()
                        .getPacket(groupname)
                        .execute(NettyClient.channel);
            }
        }).start();


    }


    //加群  或  创建群成功后    把该群的信息加入到自己的群列表中
    public static void addGroupToListByGroupid(int groupid ){
       // String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"groupid\":\""+groupid+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                AddGroupToGroupListCommandSender
                        .getInsatnce()
                        .getPacket(groupid)
                        .execute(NettyClient.channel);
            }
        }).start();


    }


    /*
    //获取课表
    public static void getkebiaoByPhonenumber(String phonenumber,String schoolname,String xnm,String xqm,String count,String password){
       // String json = "{\"phonenumber\":\""+phonenumber+"\",\"schoolname\":\""+schoolname+"\",\"xnm\":\""+xnm+"\"," +
      //          "\"xqm\":\""+xqm+"\",\"count\":\""+count+"\",\"password\":\""+password+"\"}";
        GetCourseSchByUIDCommandSender
                .getInsatnce()
                .getPacket(schoolname, xnm, xqm, count, password)
                .execute(NettyClient.channel);

    }


    //获取成绩
    public static void getscoreByPhonenumber(String phonenumber,String schoolname,String xnm,String xqm,String count,String password){
//        String json = "{\"phonenumber\":\""+phonenumber+"\",\"schoolname\":\""+schoolname+"\",\"xnm\":\""+xnm+"\"," +
//                "\"xqm\":\""+xqm+"\",\"count\":\""+count+"\",\"password\":\""+password+"\"}";
        GetScoreOfUIDCommandSender
                .getInsatnce()
                .getPacket(schoolname, xnm, xqm, count, password)
                .execute(NettyClient.channel);

    }
    */





    //获得某用户自己的教务成绩
    public static void getscoByPh(String ph,int xnm,int xqm,String count,String pwd){

     //   String json = "{\"ph\":\""+ph+"\",\"xnm\":\""+xnm+"\",\"xqm\":\""+xqm+"\",\"count\":\""+count+"\",\"pwd\":\""+pwd+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetScoreOfUIDCommandSender
                        .getInsatnce()
                        .getPacket(xnm, xqm, count, pwd)
                        .execute(NettyClient.channel);
            }
        }).start();

    }

    //获得某用户自己的教务课表
    public static void getKBByPh(String ph,int xnm,int xqm,String count,String pwd){

       // String json = "{\"ph\":\""+ph+"\",\"xnm\":\""+xnm+"\",\"xqm\":\""+xqm+"\",\"count\":\""+count+"\",\"pwd\":\""+pwd+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetCourseSchByUIDCommandSender
                        .getInsatnce()
                        .getPacket(xnm, xqm, count, pwd)
                        .execute(NettyClient.channel);
            }
        }).start();


    }


    //社团组织成员导入自己的课表
    public static void loadCourse(int year,int xueqi,String count,String pwd){

        //String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"year\":\""+year+"\",\"xueqi\":\""+xueqi+"\",\"count\":\""+count+"\",\"pwd\":\""+pwd+"\"}";
        new Thread(new Runnable() {
            @Override
            public void run() {
                LoadCourseOfCorpCommandSender
                        .getInsatnce()
                        .getPacket(year, xueqi, count, pwd)
                        .execute(NettyClient.channel);
            }
        }).start();


    }


    //安排值班表
    public static void SA(int account, int year, int xq, int bz, int xz, Vector<RequestSchedule> requestSchedules, String type){

//        String json = "{\"account\":\""+Integer.parseInt(account)+"\",\"year\":\""+year+"\",\"xq\":\""+xq+"\",\"bz\":\""+bz+"\",\"xz\":\""+xz+"\",\"type\":\""+type+"\"}";
//        String s = new Gson().toJson(requestSchedules) ;
        new Thread(new Runnable() {
            @Override
            public void run() {
                SchduleArrangementCommandSender
                        .getInsatnce()
                        .getPacket( account,  year,  xq,  bz,  xz,  requestSchedules,  type)
                        .execute(NettyClient.channel);
            }
        }).start();


    }


    //获得自己加入的某个组织的值班表
    public static void getSDByGid(int groupid,int dnid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetSDOfGidCommandSender
                        .getInsatnce()
                        .getPacket( groupid, dnid)
                        .execute(NettyClient.channel);
            }
        }).start();
       // String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"gid\":\""+groupid+"\"}";


    }

    //获得自己加入的所有组织的值班表
    public static void getAllSD(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetAllSDCommandSender
                        .getInsatnce()
                        .getPacket()
                        .execute(NettyClient.channel);
            }
        }).start();
       // String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\"}";

    }


    //获得自己的未读的值班的通知
    public static void getAlldutynoti(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetAllDutyNotiNotReadCommandSender
                        .getInsatnce()
                        .getPacket()
                        .execute(NettyClient.channel);
            }
        }).start();
      //  String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\"}";



    }


    //已经读取值班的通知   向服务器反馈
    public static void readDutyNoti(int dnid,int gid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ReadDutyNotiCommandSender
                        .getInsatnce()
                        .getPacket( dnid, gid)
                        .execute(NettyClient.channel);
            }
        }).start();
      //  String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"dnid\":\""+dnid+"\",\"gid\":\""+gid+"\"}";


    }

    //社团组织获得   获得某几节课都有空的人
    public static void searchEmptyCourse(int groupid, int year,int xueqi ,Vector<SearchEmptyCourse> searchEmptyCourses){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SearchEmptyCourseCommandSender
                        .getInsatnce()
                        .getPacket(groupid,  year, xueqi , searchEmptyCourses)
                        .execute(NettyClient.channel);
            }
        }).start();
//        String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"gid\":\""+groupid+"\",\"year\":\""+year+"\",\"xueqi\":\""+xueqi+"\"}";
//        String s = new Gson().toJson(searchEmptyCourses);


    }


    //社团组织查看课表导入情况
    public static void corpLoadCourseRs(int groupid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                CorpLoadCourseRsCommandSender
                        .getInsatnce()
                        .getPacket(groupid)
                        .execute(NettyClient.channel);
            }
        }).start();
       // String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"gid\":\""+groupid+"\"}";


    }

    //社团组织群管理修改学年学期
    public static void alterCorpTerm(int groupid,int year,int xueqi,int zhou){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AlterCorpYearTermCommandSender
                        .getInsatnce()
                        .getPacket( groupid, year, xueqi, zhou)
                        .execute(NettyClient.channel);
            }
        }).start();
       // String json = "{\"ph\":\""+ObjectService.personalInfo.getPhonenumber()+"\",\"gid\":\""+groupid+"\",\"year\":\""+year+"\",\"xueqi\":\""+xueqi+"\",\"zhou\":\""+zhou+"\"}";


    }


    //添加好友时    首先查询     获得对方的头像
    public static void getUICOfSearchAddUser(String phonenumber){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetUICOfSearchAddUserCommandSender
                        .getInsatnce()
                        .getPacket(phonenumber)
                        .execute(NettyClient.channel);
            }
        }).start();

    }

    //获得某群头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
    public static void getNotiIconOfGroup(int groupid){

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetNotiIconOfGroupCommandSender
                        .getInsatnce()
                        .getPacket(groupid)
                        .execute(NettyClient.channel);
            }
        }).start();

    }

    //获得某用户头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
    public static void getNotiIconOfUser(String ph){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetNotiIconOfUserCommandSender
                        .getInsatnce()
                        .getPacket(ph)
                        .execute(NettyClient.channel);
            }
        }).start();

    }


    //添加群时    首先查询     获得群的头像
    public static void getGroupICOfSearchAddGroup(int groupid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetGroupICOfSearchAddGroupCommandSender
                        .getInsatnce()
                        .getPacket(groupid)
                        .execute(NettyClient.channel);
            }
        }).start();

    }

    //获得某用户头像
    public static void getUserIcByPh(String ph){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetUserIcByPhCommandSender
                        .getInsatnce()
                        .getPacket(ph)
                        .execute(NettyClient.channel);
            }
        }).start();

    }

    //获得用户的某群的信息   一般用在更新某群信息时
    public static void getGroupInfoByGid(int groupid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetGroupInfoByGidCommandSender
                        .getInsatnce()
                        .getPacket(groupid)
                        .execute(NettyClient.channel);
            }
        }).start();

    }


    //退出登陆
    public static void loginout(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                LoginOutCommandSender
                        .getInsatnce()
                        .getPacket()
                        .execute(NettyClient.channel);
            }
        }).start();


    }


    //获得群成员的头像
    public static void getUserIcOfGroupUsers(int groupid,String userph,String type){

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetGroupUserIconByPhCommandSender
                        .getInsatnce()
                        .getPacket(groupid,userph,type)
                        .execute(NettyClient.channel);
            }
        }).start();

    }


    public static void searchEmptyClassRoom(int xnm ,int xqm, String lh,int zcd ,int xqj,Vector<Integer> jieshu){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SearchEmptyClassRoomCommandSender
                        .getInsatnce()
                        .getPacket( xnm , xqm,  lh, zcd , xqj, jieshu)
                        .execute(NettyClient.channel);
            }
        }).start();
    }

}
