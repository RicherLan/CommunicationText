package thefirstchange.example.com.communicationtext.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import thefirstchange.example.com.communicationtext.db.dao.Dao;

public class ChatDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Stu.db";
    private static final int DB_VERSION = 1;

    //表名           单人聊天记录
    public static final String TABLE_SINGLE = "singlechat";
    //表中的字段
    public static final String sbelongid = "belongid";
    public static final String sid = "id";
    public static final String smsgid = "msgid";
    public static final String ssenderid = "senderid";
    public static final String ssendername = "sendername";
    public static final String ssendersex = "sendersex";
    public static final String ssendericon = "sendericon";
    public static final String sreceiverid = "receiverid";
    public static final String sreceivername = "receivername";
    public static final String smsgtype = "msgtype";
    public static final String smsgbody = "msgbody";
    public static final String smsgtime = "msgtime";
    public static final String svoicetime = "voicetime";
    public static final String shandleRs = "handleRs";

    //表名         群聊天记录
    public static final String TABLE_GROUP = "groupchat";
    //表中的字段
    public static final String gbelongid = "belongid";
    public static final String gid = "id";
    public static final String gmsgid = "msgid";
    public static final String ggroupid = "groupid";
    public static final String gsenderid = "senderid";
    public static final String gsendername = "sendername";
    public static final String gsendersex = "sendersex";
    public static final String gsendericon = "sendericon";
    public static final String greceiverid = "receiverid";
    public static final String greceivername = "receivername";
    public static final String gmsgtype = "msgtype";
    public static final String gmsgbody = "msgbody";
    public static final String gmsgtime = "msgtime";
    public static final String gvoicetime = "voicetime";
    public static final String ghandleRs = "handleRs";

    //表名            好友或群请求
    public static final String TABLE_REQUEST = "requestgrouporfriend";
    //表中的字段
    public static final String rbelongid = "belongid";
    public static final String rid = "id";
    public static final String rmsgid = "msgid";
    public static final String rtype = "type";
    public static final String rgroupid = "groupid";
    public static final String rsenderid = "senderid";
    public static final String rsendername = "sendername";
    public static final String rsendersex = "sendersex";
    public static final String rsendericon = "sendericon";
    public static final String rreceiverid = "receiverid";
    public static final String rreceivername = "receivername";
    public static final String rmsgtype = "msgtype";
    public static final String rmsgbody = "msgbody";
    public static final String rmsgtime = "msgtime";
    public static final String rhandleRs = "handleRs";

    //表名            值班
    public static final String TABLE_SCHDUTY = "schduty";
    //表中的字段
    public static final String sdbelongid = "belongid";
    public static final String sdid = "id";
    public static final String sdmsgid = "msgid";
    public static final String sdtype = "type";
    public static final String sdgroupid = "groupid";
    public static final String sdsenderid = "senderid";
    public static final String sdsendername = "sendername";
    public static final String sdsendericon = "sendericon";
    public static final String sdreceiverid = "receiverid";
    public static final String sdreceivername = "receivername";
    public static final String sdmsgtype = "msgtype";
    public static final String sdmsgbody = "msgbody";
    public static final String sdmsgtime = "msgtime";
    public static final String sdhandleRs = "handleRs";




    //表名    通知界面的框
    public static final String TABLE_NOTICEFRAME = "noticeframe";
    //表中的字段
    public static final String nfbelongid = "belongid";   //本次登陆的账号
    public static final String nftype = "type";          //single  group   schduty   request
    public static final String nfsingleid = "singleid";
    public static final String nfgroupid = "groupid";


    //创建表
    public static final String CREATE_SINGLE = "create table IF NOT EXISTS "+TABLE_SINGLE+
            " ("+sid+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            sbelongid+" TEXT,"+
            smsgid+" INTEGER,"+
            ssenderid+" TEXT,"+
            ssendername+" TEXT,"+
            ssendersex+" TEXT,"+
            ssendericon+" TEXT,"+
            sreceiverid+" TEXT,"+
            sreceivername+" TEXT,"+
            smsgtype+" TEXT,"+
            smsgbody+" TEXT,"+
            smsgtime+" LONG,"+
            svoicetime+" REAL,"+
            shandleRs+" TEXT);";

    //创建表
    public static final String CREATE_GROUP = "create table IF NOT EXISTS "+TABLE_GROUP+
            " ("+gid+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            gbelongid+" TEXT,"+
            gmsgid+" INTEGER,"+
            ggroupid+" INTEGER,"+
            gsenderid+" TEXT,"+
            gsendername+" TEXT,"+
            gsendersex+" TEXT,"+
            gsendericon+" TEXT,"+
            greceiverid+" TEXT,"+
            greceivername+" TEXT,"+
            gmsgtype+" TEXT,"+
            gmsgbody+" TEXT,"+
            gmsgtime+" INTEGER,"+
            gvoicetime+" REAL,"+
            ghandleRs+" TEXT);";

    //创建表
    public static final String CREATE_REQUEST = "create table IF NOT EXISTS "+TABLE_REQUEST+
            " ("+rid+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            rbelongid+" TEXT,"+
            rmsgid+" INTEGER,"+
            rtype+" TEXT,"+
            rgroupid+" INTEGER,"+
            rsenderid+" TEXT,"+
            rsendername+" TEXT,"+
            rsendersex+" TEXT,"+
            rsendericon+" TEXT,"+
            rreceiverid+" TEXT,"+
            rreceivername+" TEXT,"+
            rmsgtype+" TEXT,"+
            rmsgbody+" TEXT,"+
            rmsgtime+" INTEGER,"+
            rhandleRs+" REAL);";

    //创建表
    public static final String CREATE_SCHDUTY = "create table IF NOT EXISTS "+TABLE_SCHDUTY+
            " ("+sdid+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            sdbelongid+" TEXT,"+
            sdmsgid+" INTEGER,"+
            sdtype+" TEXT,"+
            sdgroupid+" INTEGER,"+
            sdsenderid+" TEXT,"+
            sdsendername+" TEXT,"+
            sdsendericon+" TEXT,"+
            sdreceiverid+" TEXT,"+
            sdreceivername+" TEXT,"+
            sdmsgtype+" TEXT,"+
            sdmsgbody+" TEXT,"+
            sdmsgtime+" INTEGER,"+
            sdhandleRs+" REAL);";

    //创建表
    public static final String CREATE_NOTICEFRAME = "create table IF NOT EXISTS "+TABLE_NOTICEFRAME+
            " ("+nftype+" TEXT,"+
            nfbelongid+" TEXT,"+
            nfsingleid+" TEXT,"+
            nfgroupid+" INTEGER);";



    public ChatDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Dao.createTables(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //暂时先不写
    }

    //  删除数据库
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(DB_NAME);
    }

}
