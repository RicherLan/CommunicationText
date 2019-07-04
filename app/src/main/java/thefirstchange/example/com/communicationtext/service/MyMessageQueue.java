package thefirstchange.example.com.communicationtext.service;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

//import object.ChatMsg;
//import object.GroupChatMsg;
//import object.RequestFriendOrGroupMsg;
//import object.SingleChatMsg;


public class MyMessageQueue {

    //消息界面的聊天框     key是账号     value是人还是群single、group
    public static Map<String,String> chatframes = new HashMap<>();
    //消息界面的值班信息通知框   key是该组织的群号
    public static Vector<Integer> dutyframes = new Vector<>();


    //聊天未读消息队列     key 为发送者的id   value是消息
    public static Map<String,Vector<ChatMsg>> chatQueueNotRead = new HashMap<>();
    //聊天已读消息队列     key 为发送者的id   value是消息
    public static Map<String,Vector<ChatMsg>> chatQueueHadRead = new HashMap<>();

    //请求未处理消息队列
    public static Vector<ChatMsg> requestQueueNotHandle = new Vector<>();
    //请求已处理息队列
    public static Vector<ChatMsg> requestQueueHadHandled = new Vector<>();


    //值班表未读消息队列    key是该组织的群号
    public static Map<Integer,Vector<ChatMsg>> dutyQueueNotRead = new HashMap<>();
    //值班表已读消息队列
    public static Map<Integer,Vector<ChatMsg>> dutyQueueHadRead = new HashMap<>();




    //未读动态消息      里面存的是对方的头像     避免流量消耗    只有点开动态消息时   才请求
    public static Map<String,Bitmap> dongtaiMsgNotRead = new HashMap<>();



    public static void removeAll(){
        //消息界面的聊天框     key是账号     value是人还是群single、group
        chatframes = new HashMap<>();
        //消息界面的值班信息通知框   key是该组织的群号
        dutyframes = new Vector<>();


        //聊天未读消息队列     key 为发送者的id   value是消息
         chatQueueNotRead = new HashMap<>();
        //聊天已读消息队列     key 为发送者的id   value是消息
       chatQueueHadRead = new HashMap<>();

        //请求未处理消息队列
        requestQueueNotHandle = new Vector<>();
        //请求已处理息队列
         requestQueueHadHandled = new Vector<>();


        //值班表未读消息队列    key是该组织的群号
        dutyQueueNotRead = new HashMap<>();
        //值班表已读消息队列
        dutyQueueHadRead = new HashMap<>();

        //未读动态消息      里面存的是对方的头像     避免流量消耗    只有点开动态消息时   才请求
        dongtaiMsgNotRead = new HashMap<>();
    }

}
