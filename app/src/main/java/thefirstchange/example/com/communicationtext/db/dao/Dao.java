package thefirstchange.example.com.communicationtext.db.dao;

import android.database.sqlite.SQLiteDatabase;

import thefirstchange.example.com.communicationtext.course.database.DbHelper;
import thefirstchange.example.com.communicationtext.db.BasicDataDbHelper;
import thefirstchange.example.com.communicationtext.db.ChatDbHelper;

public class Dao {

    public static void createTables(SQLiteDatabase db){

        db.execSQL(BasicDataDbHelper.CREATE_ALLGROUPINFO);
        db.execSQL(BasicDataDbHelper.CREATE_ALLFRIENDINFO);

        db.execSQL(ChatDbHelper.CREATE_SINGLE);
        db.execSQL(ChatDbHelper.CREATE_GROUP);
        db.execSQL(ChatDbHelper.CREATE_REQUEST);
        db.execSQL(ChatDbHelper.CREATE_SCHDUTY);
        db.execSQL(ChatDbHelper.CREATE_NOTICEFRAME);

        db.execSQL(DbHelper.CREATE_STUCOU);
        db.execSQL(DbHelper.CREATE_STUSCO);
        db.execSQL(DbHelper.CREATE_STUSCH);

    }


}
