package thefirstchange.example.com.communicationtext.course.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.util.Vector;

import thefirstchange.example.com.communicationtext.course.database.DbHelper;
import thefirstchange.example.com.communicationtext.course.object.Course;
import thefirstchange.example.com.communicationtext.course.object.ListViewScore;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.schedulearrangement.ClientArrangement;


/**
 * 课表数据库
 *
 */
public class StuDao {
//    private SQLiteDatabase courseDataBase;
//    private SQLiteDatabase scoreDataBase;
//    private SQLiteDatabase schduleDataBase;
    private SQLiteDatabase dataBase;
//    private ChatDbHelper dbHelper;
    private static StuDao sStuCourseDao;

    private StuDao(Context context){
//        courseDataBase = new CourseDbHelper(context).getWritableDatabase();
//        scoreDataBase = new ScoreDbHelper(context).getWritableDatabase();
//        schduleDataBase = new SchArrDbHelper(context).getWritableDatabase();
        dataBase = new DbHelper(context).getWritableDatabase();
//        ChatDbHelper dbHelper = new ChatDbHelper(context);
//        dbHelper.deleteDatabase(context);
//        dataBase.execSQL("DROP TABLE "+ChatDbHelper.TABLE_STUCOU);
//        dataBase.execSQL("DROP TABLE "+ChatDbHelper.TABLE_STUSCO);
//        dataBase.execSQL("DROP TABLE "+ChatDbHelper.TABLE_STUSCH);

    }

    public static StuDao getInstance (Context context){
        synchronized (StuDao.class){
            if (sStuCourseDao == null){
                sStuCourseDao = new StuDao(context);
            }
        }
        return sStuCourseDao;
    }

//      " ("+courseName+" TEXT,"+
//    courseCollege+" TEXT,"+
//    coursePlace+" TEXT,"+
//    courseTime1+" TEXT,"+
//    courseTime2+" TEXT,"+
//    courseTime3+" TEXT,"+
//    courseTeacherName+" TEXT,"+
//    courseTeacherType+" TEXT);";
//

    /*
       课程表
     */
    public void saveStuCou(Course course){
        ContentValues values = new ContentValues();
        values.put(DbHelper.courseName, course.getCN());
        values.put(DbHelper.courseCollege, course.getCC());
        values.put(DbHelper.coursePlace, course.getCP());
        values.put(DbHelper.courseTime1, course.getCT1());
        values.put(DbHelper.courseTime2, course.getCT2());
        values.put(DbHelper.courseTime3, course.getCT3());
        values.put(DbHelper.courseTeacherName, course.getCTN());

        //当相同的时候替换。
        dataBase.insertWithOnConflict(DbHelper.TABLE_STUCOU,null,values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void saveStuCouList(Vector<Course> courses){
       // Log.e("aa","savesavesavesavesave"+"  "+courses.get(0).getCN());
        for (Course course : courses){
            saveStuCou(course);
        }
    }

    public Vector<Course> getStuCouList(){
        Vector<Course> courses = new Vector<>();
        Cursor cursor = dataBase.rawQuery("select * from "+ DbHelper.TABLE_STUCOU,null);
        while (cursor.moveToNext()){
            Course course = new Course();
            course.setCN(cursor.getString(
                    cursor.getColumnIndex(DbHelper.courseName))
            );
            course.setCC(cursor.getString(
                    cursor.getColumnIndex(DbHelper.courseCollege)
            ));
            course.setCP(cursor.getString(
                    cursor.getColumnIndex(DbHelper.coursePlace)
            ));
            course.setCT1(cursor.getString(
                    cursor.getColumnIndex(DbHelper.courseTime1)
            ));
            course.setCT2(cursor.getString(
                    cursor.getColumnIndex(DbHelper.courseTime2)
            ));
            course.setCT3(cursor.getString(
                    cursor.getColumnIndex(DbHelper.courseTime3)
            ));
            course.setCTN(cursor.getString(
                    cursor.getColumnIndex(DbHelper.courseTeacherName)
            ));


            courses.add(course);
        }
        cursor.close();
        return courses;
    }

    public void removeAllCourse(){
        //删除全部记录
        dataBase.delete(DbHelper.TABLE_STUCOU,null,null);
    }

/*
   成绩表
 */

    public void saveStuSco(ListViewScore score){
        ContentValues values = new ContentValues();
        values.put(DbHelper.Syear, score.year);
        values.put(DbHelper.Sgrade, score.grade);
        values.put(DbHelper.ScourseName, score.courseName);
        values.put(DbHelper.ScourseScore, score.courseScore);
        values.put(DbHelper.ScourseCredit, score.courseCredit);

        //当相同的时候替换。
        dataBase.insertWithOnConflict(DbHelper.TABLE_STUSCO,null,values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void saveStuScoList(Vector<ListViewScore> courses){
        // Log.e("aa","savesavesavesavesave"+"  "+courses.get(0).getCN());
        for (ListViewScore course : courses){
            saveStuSco(course);
        }
    }

    public Vector<ListViewScore> getStuScoList(){
        Vector<ListViewScore> courses = new Vector<>();
        Cursor cursor = dataBase.rawQuery("select * from "+ DbHelper.TABLE_STUSCO,null);
        while (cursor.moveToNext()){
            ListViewScore course = new ListViewScore();
            course.year = (cursor.getInt(
                    cursor.getColumnIndex(DbHelper.Syear))
            );
            course.grade = (cursor.getInt(
                    cursor.getColumnIndex(DbHelper.Sgrade)
            ));
            course.courseName = (cursor.getString(
                    cursor.getColumnIndex(DbHelper.ScourseName))
            );
            course.courseScore = (cursor.getDouble(
                    cursor.getColumnIndex(DbHelper.ScourseScore)
            ));
            course.courseCredit = (cursor.getDouble(
                    cursor.getColumnIndex(DbHelper.ScourseCredit)
            ));


            courses.add(course);
        }
        cursor.close();
        return courses;
    }

    public void removeAllScore(){
        //删除全部记录
        dataBase.delete(DbHelper.TABLE_STUSCO,null,null);
    }

/*
   值班表
 */
    public void saveStuSch(ClientArrangement clientArrangement){
        ContentValues values = new ContentValues();
        values.put(DbHelper.groupid,clientArrangement.getGroupid());
        values.put(DbHelper.week, clientArrangement.getWeek());
//        values.put(ChatDbHelper.day, clientArrangement.getDay());
        values.put(DbHelper.section,clientArrangement.getSection());
        String phs = "";
        for(int i=0;i<clientArrangement.getPhs().size();++i){
            phs+=clientArrangement.getPhs().get(i)+" ";
        }
        phs.trim();

        String names = "";
        for(int i=0;i<clientArrangement.getNames().size();++i){
            names+=clientArrangement.getNames().get(i)+" ";
        }
        names.trim();

        String poss = "";
        for(int i=0;i<clientArrangement.getPoss().size();++i){
            poss+=clientArrangement.getPoss().get(i)+" ";
        }
        poss.trim();

        values.put(DbHelper.phs, phs);
        values.put(DbHelper.names, names);
        values.put(DbHelper.poss, poss);

        values.put(DbHelper.year,clientArrangement.getYear());
        values.put(DbHelper.month,clientArrangement.getMonth());
        values.put(DbHelper.daytime,clientArrangement.getDaytime());
        values.put(DbHelper.way,clientArrangement.getWay());
        values.put(DbHelper.buzhangnum,clientArrangement.getBuzhangnum());
        values.put(DbHelper.ganshinum,clientArrangement.getGanshinum());

        //当相同的时候替换。
        dataBase.insertWithOnConflict(DbHelper.TABLE_STUSCH,null,values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void saveStuSchList(Vector<ClientArrangement> clientArrangements){
        // Log.e("aa","savesavesavesavesave"+"  "+courses.get(0).getCN());
        for (ClientArrangement clientArrangement : clientArrangements){
            saveStuSch(clientArrangement);
        }
    }


    public Vector<ClientArrangement> getStuSchList(){
        Vector<ClientArrangement> courses = new Vector<>();
        Cursor cursor = dataBase.rawQuery("select * from "+ DbHelper.TABLE_STUSCH,null);
        while (cursor.moveToNext()){
            ClientArrangement course = new ClientArrangement();
            course.setGroupid(cursor.getInt(cursor.getColumnIndex(DbHelper.groupid)));
            course.setWeek(cursor.getInt(cursor.getColumnIndex(DbHelper.week)));

//            course.setDay(cursor.getInt(cursor.getColumnIndex(ChatDbHelper.day)));

            course.setSection(cursor.getInt(cursor.getColumnIndex(DbHelper.section)));

            String phs = cursor.getString(cursor.getColumnIndex(DbHelper.phs));
            String names = cursor.getString(cursor.getColumnIndex(DbHelper.names));
            String poss = cursor.getString(cursor.getColumnIndex(DbHelper.poss));
            Vector<String> phsVec = new Vector<>();
            Vector<String> namesVec = new Vector<>();
            Vector<String> possVec = new Vector<>();
            String[] strings1 = phs.split(" ");
            String[] strings2 = names.split(" ");
            String[] strings3 = poss.split(" ");
            if(!phs.equals("")){
                for(int i=0;i<strings1.length;++i){
                    phsVec.add(strings1[i]);
                }
                for(int i=0;i<strings2.length;++i){
                    namesVec.add(strings2[i]);
                }
                for(int i=0;i<strings3.length;++i){
                    possVec.add(strings3[i]);
                }
            }

            course.setPhs(phsVec);
            course.setNames(namesVec);
            course.setPoss(possVec);

            course.setYear(cursor.getInt(cursor.getColumnIndex(DbHelper.year)));
            course.setMonth(cursor.getInt(cursor.getColumnIndex(DbHelper.month)));
            course.setDaytime(cursor.getInt(cursor.getColumnIndex(DbHelper.daytime)));
            course.setWay(cursor.getInt(cursor.getColumnIndex(DbHelper.way)));
            course.setBuzhangnum(cursor.getInt(cursor.getColumnIndex(DbHelper.buzhangnum)));
            course.setGanshinum(cursor.getInt(cursor.getColumnIndex(DbHelper.ganshinum)));

            courses.add(course);
        }
        cursor.close();
        return courses;
    }

    public void removeAllSchdule(){
        //删除全部记录
        dataBase.delete(DbHelper.TABLE_STUSCH,null,null);
    }

    public void removeSchduleBygroupid(int groupid){

        dataBase.delete(DbHelper.TABLE_STUSCH,DbHelper.groupid+"=?",new String[]{""+groupid+""});
    }

}
