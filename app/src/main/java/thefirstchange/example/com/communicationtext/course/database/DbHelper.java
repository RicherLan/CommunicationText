package thefirstchange.example.com.communicationtext.course.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import thefirstchange.example.com.communicationtext.db.dao.Dao;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Stu.db";
    private static final int DB_VERSION = 1;

    //表名
    public static final String TABLE_STUCOU = "stucou";
    //表中的字段
    public static final String courseName = "courseName";
    public static final String courseCollege = "courseCollege";
    public static final String coursePlace = "coursePlace";
    public static final String courseTime1 = "courseTime1";
    public static final String courseTime2 = "courseTime2";
    public static final String courseTime3 = "courseTime3";
    public static final String courseTeacherName = "courseTeacherName";

    //表名
    public static final String TABLE_STUSCO = "stusco";
    //表中的字段
    public static final String Syear = "year";               //学年
    public static final String Sgrade = "grade";             //学期
    public static final String ScourseName = "courseName";        //课程名
    public static final String ScourseScore = "courseScore";   //成绩
    public static final String ScourseCredit = "courseCredit";   //学分

    //表名
    public static final String TABLE_STUSCH = "stusch";
    //表中的字段
    public static final String week = "week";
    public static final String day = "day";
    public static final String section = "section";
    public static final String phs = "phs";
    public static final String names = "names";
    public static final String poss = "poss";
    public static final String groupid = "groupid";
    public static final String year = "year";               //值班周第一天的年
    public static final String month = "month";               //值班周第一天的月
    public static final String daytime = "daytime";           //值班周第一天的几号
    public static final String way = "way";                  //值班周第一天的周几
    public static final String buzhangnum = "buzhangnum";      //该次值班要求部长的数量
    public static final String ganshinum = "ganshinum";        //该次值班要求干事的数量

    //创建表
    public static final String CREATE_STUCOU = "create table IF NOT EXISTS "+TABLE_STUCOU+
            " ("+courseName+" TEXT,"+
            courseCollege+" TEXT,"+
            coursePlace+" TEXT,"+
            courseTime1+" TEXT,"+
            courseTime2+" TEXT,"+
            courseTime3+" TEXT,"+
            courseTeacherName+" TEXT);";

    //创建表
    public static final String CREATE_STUSCO = "create table IF NOT EXISTS "+TABLE_STUSCO+
            " ("+Syear+" INTEGER,"+
            Sgrade+" INTEGER,"+
            courseName+" REAL,"+
            ScourseScore+" REAL,"+
            ScourseCredit+" REAL);";

    //创建表
    public static final String CREATE_STUSCH = "create table IF NOT EXISTS "+TABLE_STUSCH+
            " ("+groupid+" INTEGER,"+
            week+" INTEGER,"+
            day+" INTEGER,"+
            section+" INTEGER,"+
            phs+" TEXT,"+
            names+" TEXT,"+
            poss+" TEXT,"+
            year+" INTEGER,"+
            month+" INTEGER,"+
            daytime+" INTEGER,"+
            way+" INTEGER,"+
            buzhangnum+" INTEGER,"+
            ganshinum+" INTEGER);";


    public DbHelper(Context context) {
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
