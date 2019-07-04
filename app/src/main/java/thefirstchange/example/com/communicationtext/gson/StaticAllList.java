package thefirstchange.example.com.communicationtext.gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import thefirstchange.example.com.communicationtext.db.dao.BasicDataDao;
import thefirstchange.example.com.communicationtext.db.dao.ChatRecordDao;
import thefirstchange.example.com.communicationtext.dongtai.DTComRoot;
import thefirstchange.example.com.communicationtext.dongtai.DTComRootCh;
import thefirstchange.example.com.communicationtext.dongtai.Dongtai;
import thefirstchange.example.com.communicationtext.dongtai.DongtaiMsg;

public class StaticAllList {

    //支持的  高校教务的学校
    public static List<String> StuEduSchoolLists = new ArrayList<>(Arrays.asList("济南大学"));

    public static ChatRecordDao chatRecordDao;
    public static BasicDataDao basicDataDao;

//    public static Map<Integer,Bitmap>

//    public static Vector<User> friendList=new Vector<>();
    public static Map<String,MyFriend> friendList = new HashMap<>();
    //已经请求过的好友头像
    public static Map<String,Long> friendsIconHadRequest = new HashMap<>();


    public static Map<Integer,UserGroup> groupList=new HashMap<>();
    //上一次请求某群的头像的时间
    public static Map<Integer,Long> groupIconLastRequest = new HashMap<>();
    //群成员头像的上次请求时间
    public static Map<String ,Long> groupUserIconLastRequestTime = new HashMap<>();

    public static Vector<Dongtai> dongtais=new Vector<>();
    public static Map<String,Vector<Dongtai>> personalDongtais = new HashMap<>();   //进入某人主页时的动态


    public static Dongtai nowDongtai = new Dongtai();               //自己正在发表的动态  成功的话就加入dongtais   失败就等于null
    public static String dongtaiatate = "nodongtai";             //动态的状态   nodongtai   uploading  failed
    public static int dongtaiImageHasnotUpload=0;                 // 发表动态时  下一步应该发第几张照片  0对应第1张照片


    public static Vector<DongtaiMsg> dongtaiMsgs = new Vector<>();          //动态消息队列
    public static Vector<Dongtai> dongtaisMsgDongtai = new Vector<>();       //动态消息中的动态的内容

    public static int dongtaiMsgNotReadNum = 0;


    public static Map<String,User> usertemps= new HashMap<>();          //查看某人的资料时   服务器返回的资料暂时保存在这

    //key是动态的id    value是动态的根评论    进入某个动态的界面时   显示评论就是这个map
    public static Map<Integer,Vector<DTComRoot>> dTComRootMap = new HashMap<>();
    //key是根评论的id    value是该根评论下的所有的评论
    public static Map<Integer,Vector<DTComRootCh>> dTComRootChMap = new HashMap<>();


    public static Map<Integer,Vector<UserInGroupInfo>> groupUsersInfo=new HashMap<>();       //群成员信息

    public static void removeAll(){
        StaticAllList. friendList = new HashMap<>();
        friendsIconHadRequest = new HashMap<>();
        StaticAllList.  groupList=new HashMap<>();
        groupIconLastRequest = new HashMap<>();
        groupUserIconLastRequestTime = new HashMap<>();
        StaticAllList. personalDongtais = new HashMap<>();   //进入某人主页时的动态

        StaticAllList. nowDongtai = new Dongtai();               //自己正在发表的动态  成功的话就加入dongtais   失败就等于null
        StaticAllList. dongtaiatate = "nodongtai";             //动态的状态   nodongtai   uploading  failed
        StaticAllList. dongtaiImageHasnotUpload=0;                 // 发表动态时  下一步应该发第几张照片  0对应第1张照片


        StaticAllList. dongtaiMsgs = new Vector<>();          //动态消息队列
        StaticAllList. dongtaisMsgDongtai = new Vector<>();       //动态消息中的动态的内容

        StaticAllList. dongtaiMsgNotReadNum = 0;


        StaticAllList.usertemps.clear();     //查看某人的资料时   服务器返回的资料暂时保存在这

        //key是动态的id    value是动态的根评论    进入某个动态的界面时   显示评论就是这个map
        StaticAllList. dTComRootMap = new HashMap<>();
        //key是根评论的id    value是该根评论下的所有的评论
        StaticAllList.dTComRootChMap = new HashMap<>();


        StaticAllList. groupUsersInfo=new HashMap<>();       //群成员信息

    }


}
