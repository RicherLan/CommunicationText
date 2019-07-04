package thefirstchange.example.com.communicationtext.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import thefirstchange.example.com.communicationtext.db.dao.Dao;

/*
           存储常用的信息    例如 自己的群信息  自己的所有好友信息等    因为离线登陆要用到
 */
public class BasicDataDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Stu.db";
    private static final int DB_VERSION = 1;

    //自己的群列表
    public static final String TABLE_ALLGROUPINFO = "allGroupInfo";

    public static final String gbelongid = "belongid";
    public static final String ggroupid = "groupid";
    public static final String ggroupname="groupname";
    public static final String ggroupicon="groupicon";
    public static final String ggroupintro="groupintro";
    public static final String gcreatetime="createtime";
    public static final String gcreatorid="creatorid";
    public static final String gauthid="authid";
    public static final String ggrouptype="grouptype";  ////群类型    社团群还是普通群
    public static final String gusernum="usernum";      //群人数
    public static final String gjointime="jointime";
    public static final String guserauthority="userauthority";
    public static final String gpart="part";              //部室
    public static final String gcorppos="corppos";          //职称
    public static final String ggroupnickname="groupnickname";
    public static final String gcorppart="corppart";            //社团组织下的所有部门
    public static final String gyear="year";                    //社团组织课表的时间
    public static final String gxueqi="xueqi";
    public static final String gzhou="zhou";



    //创建表
    public static final String CREATE_ALLGROUPINFO = "create table IF NOT EXISTS "+TABLE_ALLGROUPINFO+
            " ("+ggroupid+" INTEGER PRIMARY KEY,"+
            gbelongid+" TEXT,"+
            ggroupname+" TEXT,"+
            ggroupicon+" TEXT,"+
            ggroupintro+" TEXT,"+
            gcreatetime+" LONG,"+
            gcreatorid+" TEXT,"+
            gauthid+" INTEGER,"+
            ggrouptype+" TEXT,"+
            gusernum+" INTEGER,"+
            gjointime+" LONG,"+
            guserauthority+" TEXT,"+
            gpart+" TEXT,"+
            gcorppos+" TEXT,"+
            ggroupnickname+" TEXT,"+
            gcorppart+" TEXT,"+
            gyear+" INTEGER,"+
            gxueqi+" INTEGER,"+
            gzhou+" INTEGER);";


    //自己的好友列表
    public static final String TABLE_ALLFRIENDINFO = "allFriendInfo";

    public static final String fbelongid = "belongid";
    public static final String ftype = "type";                //用户类型 普通 社团官方
    public static final String fphonenumber="phonenumber";
    public static final String fnickname="nickname";
    public static final String ficon="icon";
    public static final String fqq="qq";
    public static final String fweixin="weixin";
    public static final String faddress="address";
    public static final String fsex="sex";
    public static final String fschoolname="schoolname";
    public static final String fdepartmentname="departmentname";
    public static final String fmajorname="majorname";
    public static final String fcorporationname="corporationname";           //加入的部门   空格隔开
    public static final String fcorporationposition="corporationposition";     //在部门中的职位
    public static final String fbirthday="birthday";
    public static final String fruxueyear="ruxueyear";
    public static final String ffrom="fromwhere";
    public static final String fintroduce="introduce";
    public static final String fremark="remark";
    public static final String ffriendgroup = "friendgroup";


    //创建表
    public static final String CREATE_ALLFRIENDINFO = "create table IF NOT EXISTS "+TABLE_ALLFRIENDINFO+
            " ("+fphonenumber+" TEXT PRIMARY KEY,"+
            fbelongid+" TEXT,"+
            ftype+" TEXT,"+
            fnickname+" TEXT,"+
            ficon+" TEXT,"+
            fqq+" TEXT,"+
            fweixin+" TEXT,"+
            faddress+" TEXT,"+
            fsex+" TEXT,"+
            fschoolname+" TEXT,"+
            fdepartmentname+" TEXT,"+
            fmajorname+" TEXT,"+
            fcorporationname+" TEXT,"+
            fcorporationposition+" TEXT,"+
            fbirthday+" TEXT,"+
            fruxueyear+" INTEGER,"+
            ffrom+" TEXT,"+
            fintroduce+" TEXT,"+
            fremark+" TEXT,"+
            ffriendgroup+" INTEGER);";



    public BasicDataDbHelper(Context context) {
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
