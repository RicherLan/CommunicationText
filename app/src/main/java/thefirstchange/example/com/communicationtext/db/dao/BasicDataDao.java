package thefirstchange.example.com.communicationtext.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.db.BasicDataDbHelper;
import thefirstchange.example.com.communicationtext.gson.MyFriend;
import thefirstchange.example.com.communicationtext.gson.UserGroup;
import thefirstchange.example.com.communicationtext.service.ObjectService;

/*
存储常用的信息    例如 自己的群信息  自己ide所有好友信息等    因为离线登陆要用到
数据库操作接口
 */
public class BasicDataDao  {

    private SQLiteDatabase dataBase;
    private static BasicDataDao basicDataDao;

    private BasicDataDao(Context context){

        dataBase = new BasicDataDbHelper(context).getWritableDatabase();

//        BasicDataDbHelper dbHelper = new BasicDataDbHelper(context);
//
//        dbHelper.deleteDatabase(context);
//        dataBase.execSQL("DROP TABLE "+ChatDbHelper.TABLE_SINGLE);
//        dataBase.execSQL("DROP TABLE "+ChatDbHelper.TABLE_GROUP);
//        dataBase.execSQL("DROP TABLE "+ChatDbHelper.TABLE_REQUEST);
//        dataBase.execSQL("DROP TABLE "+ChatDbHelper.TABLE_NOTICEFRAME);
//
//        dataBase.execSQL("DROP TABLE "+ TABLE_STUCOU);
//        dataBase.execSQL("DROP TABLE "+ TABLE_STUSCO);
//        dataBase.execSQL("DROP TABLE "+ TABLE_STUSCH);
//
//        dataBase.execSQL("DROP TABLE "+BasicDataDbHelper.TABLE_ALLFRIENDINFO);
//        dataBase.execSQL("DROP TABLE "+BasicDataDbHelper.TABLE_ALLGROUPINFO);

    }

    public static BasicDataDao getInstance (Context context){
        synchronized (ChatRecordDao.class){
            if (basicDataDao == null){
                basicDataDao = new BasicDataDao(context);
            }
        }
        return basicDataDao;
    }


    /*
        用户的所有群信息
     */

    public void saveUserGroupList(Vector<UserGroup> userGroups){
        // Log.e("aa","savesavesavesavesave"+"  "+courses.get(0).getCN());
        for (UserGroup userGroup : userGroups){
            saveGroup(userGroup);
        }
    }


    public void saveGroup(UserGroup userGroup){

        if(userGroup==null){
            return;
        }

        ContentValues values = new ContentValues();
        values.put(BasicDataDbHelper.gbelongid, ObjectService.personalInfo.getPhonenumber());
        values.put(BasicDataDbHelper.ggroupid, userGroup.getGroupid());
        values.put(BasicDataDbHelper.ggroupname, userGroup.getGroupname());
        values.put(BasicDataDbHelper.ggroupicon, userGroup.getGroupicon());
        values.put(BasicDataDbHelper.ggroupintro, userGroup.getGroupintro());
        values.put(BasicDataDbHelper.gcreatetime, userGroup.getCreatetime());
        values.put(BasicDataDbHelper.gcreatorid, userGroup.getCreatorid());
        values.put(BasicDataDbHelper.gauthid, userGroup.getAuthid());
        values.put(BasicDataDbHelper.ggrouptype, userGroup.getGrouptype());
        values.put(BasicDataDbHelper.gusernum, userGroup.getUsernum());
        values.put(BasicDataDbHelper.gjointime, userGroup.getJointime());
        values.put(BasicDataDbHelper.guserauthority, userGroup.getUserauthority());
        values.put(BasicDataDbHelper.gpart, userGroup.getPart());
        values.put(BasicDataDbHelper.gcorppos, userGroup.getCorppos());
        values.put(BasicDataDbHelper.ggroupnickname, userGroup.getGroupnickname());
        String corppartstring = "";
        if(userGroup.getCorppart()!=null){
            for(String s:userGroup.getCorppart()){
                corppartstring+=s+" ";
            }
        }
        corppartstring.trim();
        values.put(BasicDataDbHelper.gcorppart,corppartstring);
        values.put(BasicDataDbHelper.gyear, userGroup.getYear());
        values.put(BasicDataDbHelper.gxueqi, userGroup.getXueqi());
        values.put(BasicDataDbHelper.gzhou, userGroup.getZhou());


        //当相同的时候替换。
        dataBase.insertWithOnConflict(BasicDataDbHelper.TABLE_ALLGROUPINFO,null,values, SQLiteDatabase.CONFLICT_REPLACE);
//        String sql = "select last_insert_rowid() from " + ChatDbHelper.TABLE_SINGLE ;
//        Cursor cursor = dataBase.rawQuery(sql, null);

    }

    //更新群头像
    public void updateGroupIcon(int groupid,String icpath){

        String sql = "update "+BasicDataDbHelper.TABLE_ALLGROUPINFO+" set "+BasicDataDbHelper.ggroupicon+"='"+icpath+"' where "+BasicDataDbHelper.gbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"' and "+BasicDataDbHelper.ggroupid+"='"+groupid+"'";
        dataBase.execSQL(sql);
    }


    //删除用户某群
    public void deleteGroupByGid(int groupid){
        String sql = "delete from "+BasicDataDbHelper.TABLE_ALLGROUPINFO+" where "+BasicDataDbHelper.gbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"' and "+BasicDataDbHelper.ggroupid+"='"+groupid+"'";
        dataBase.execSQL(sql);
    }

    //删除某用户的所有群信息
    public void deleteUserGroupByPh(String ph){

        String sql = "delete from "+BasicDataDbHelper.TABLE_ALLGROUPINFO+" where "+BasicDataDbHelper.gbelongid+"='"+ph+"'";
        dataBase.execSQL(sql);

    }

    public Vector<UserGroup> getUserGroupList(String ph){
        Vector<UserGroup> userGroups = new Vector<>();
        Cursor cursor = dataBase.rawQuery("select * from "+ BasicDataDbHelper.TABLE_ALLGROUPINFO+" where "+BasicDataDbHelper.gbelongid+"='"+ph+"'",null);
        while (cursor.moveToNext()){
            UserGroup userGroup = new UserGroup();

            userGroup.setGroupid(cursor.getInt(
                    cursor.getColumnIndex(BasicDataDbHelper.ggroupid))
            );
            userGroup.setGroupname(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.ggroupname))
            );
            userGroup.setGroupicon(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.ggroupicon)
            ));
            userGroup.setGroupintro(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.ggroupintro)
            ));
            userGroup.setCreatetime(cursor.getLong(
                    cursor.getColumnIndex(BasicDataDbHelper.gcreatetime)
            ));
            userGroup.setCreatorid(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.gcreatorid)
            ));
            userGroup.setAuthid(cursor.getInt(
                    cursor.getColumnIndex(BasicDataDbHelper.gauthid)
            ));
            userGroup.setGrouptype(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.ggrouptype)
            ));
            userGroup.setUsernum(cursor.getInt(
                    cursor.getColumnIndex(BasicDataDbHelper.gusernum)
            ));
            userGroup.setJointime(cursor.getLong(
                    cursor.getColumnIndex(BasicDataDbHelper.gjointime)
            ));
            userGroup.setUserauthority(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.guserauthority)
            ));
            userGroup.setPart(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.gpart)
            ));
            userGroup.setCorppos(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.gcorppos)
            ));

            userGroup.setGroupnickname(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.ggroupnickname)
            ));
            String s = cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.gcorppart));
            if(s!=null&&!s.trim().equals("")){
                String[] corppart ;
                corppart = s.split(" ");
                userGroup.setCorppart(corppart);
            }

            userGroup.setYear(cursor.getInt(
                    cursor.getColumnIndex(BasicDataDbHelper.gyear)
            ));
            userGroup.setXueqi(cursor.getInt(
                    cursor.getColumnIndex(BasicDataDbHelper.gxueqi)
            ));
            userGroup.setZhou(cursor.getInt(
                    cursor.getColumnIndex(BasicDataDbHelper.gzhou)
            ));


            userGroups.add(userGroup);
        }
        cursor.close();
        return userGroups;
    }


    /*
    用户的所有好友信息
     */


    public void saveUserFriendList(Vector<MyFriend> myFriends){
        // Log.e("aa","savesavesavesavesave"+"  "+courses.get(0).getCN());
        String sql = "delete from "+BasicDataDbHelper.TABLE_ALLFRIENDINFO+" where "+BasicDataDbHelper.fbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"'";
        dataBase.execSQL(sql);

        for (MyFriend myFriend : myFriends){
            saveFriend(myFriend);
        }
    }

    public void saveFriend(MyFriend myFriend){
        if(myFriend==null){
            return;
        }
        ContentValues values = new ContentValues();
        values.put(BasicDataDbHelper.fbelongid, ObjectService.personalInfo.getPhonenumber());
        values.put(BasicDataDbHelper.ftype, myFriend.getType());
        values.put(BasicDataDbHelper.fphonenumber, myFriend.getPhonenumber());
        values.put(BasicDataDbHelper.fnickname, myFriend.getNickname());
        values.put(BasicDataDbHelper.ficon, myFriend.getIcon());
        values.put(BasicDataDbHelper.fqq, myFriend.getQq());
        values.put(BasicDataDbHelper.fweixin, myFriend.getWeixin());
        values.put(BasicDataDbHelper.faddress, myFriend.getAddress());
        values.put(BasicDataDbHelper.fsex, myFriend.getSex());
        values.put(BasicDataDbHelper.fschoolname, myFriend.getSchoolname());
        values.put(BasicDataDbHelper.fdepartmentname, myFriend.getDepartmentname());
        values.put(BasicDataDbHelper.fmajorname, myFriend.getMajorname());
        values.put(BasicDataDbHelper.fcorporationname, myFriend.getCorporationname());
        values.put(BasicDataDbHelper.fcorporationposition, myFriend.getCorporationposition());
        values.put(BasicDataDbHelper.fbirthday, myFriend.getBirthday());
        values.put(BasicDataDbHelper.fruxueyear,myFriend.getRuxueyear());
        values.put(BasicDataDbHelper.ffrom, myFriend.getFrom());
        values.put(BasicDataDbHelper.fintroduce, myFriend.getIntroduce());
        values.put(BasicDataDbHelper.fremark, myFriend.getRemark());
        values.put(BasicDataDbHelper.ffriendgroup, myFriend.getFriendgroup());


        //当相同的时候替换。
        dataBase.insertWithOnConflict(BasicDataDbHelper.TABLE_ALLFRIENDINFO,null,values, SQLiteDatabase.CONFLICT_REPLACE);
//        String sql = "select last_insert_rowid() from " + ChatDbHelper.TABLE_SINGLE ;
//        Cursor cursor = dataBase.rawQuery(sql, null);

    }

    //删除自己的一个好友
    public void deleteFriend(String ph){
        String sql = "delete from "+BasicDataDbHelper.TABLE_ALLFRIENDINFO+" where "+BasicDataDbHelper.fbelongid+"='"+ph+"' and "+BasicDataDbHelper.fphonenumber+"='"+ph+"'";
        dataBase.execSQL(sql);
    }

    //删除某用户的所有好友信息
    public void deleteUserFriendByPh(String ph){

        String sql = "delete from "+BasicDataDbHelper.TABLE_ALLFRIENDINFO+" where "+BasicDataDbHelper.fbelongid+"='"+ph+"'";
        dataBase.execSQL(sql);

    }

    public Vector<MyFriend> getUserFriendList(String ph){
        Vector<MyFriend> myFriends  = new Vector<>();
        Cursor cursor = dataBase.rawQuery("select * from "+ BasicDataDbHelper.TABLE_ALLFRIENDINFO+" where "+BasicDataDbHelper.fbelongid+"='"+ph+"'",null);
        while (cursor.moveToNext()){
            MyFriend myFriend = new MyFriend();

            myFriend.setType(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.ftype))
            );
            myFriend.setPhonenumber(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.fphonenumber))
            );
            myFriend.setNickname(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.fnickname))
            );
            myFriend.setIcon(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.ficon))
            );
            myFriend.setQq(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.fqq))
            );
            myFriend.setWeixin(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.fweixin))
            );
            myFriend.setAddress(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.faddress))
            );
            myFriend.setSex(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.fsex))
            );
            myFriend.setSchoolname(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.fschoolname))
            );
            myFriend.setDepartmentname(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.fdepartmentname))
            );
            myFriend.setMajorname(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.fmajorname))
            );
            myFriend.setCorporationname(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.fcorporationname))
            );
            myFriend.setCorporationposition(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.fcorporationposition))
            );
            myFriend.setBirthday(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.fbirthday))
            );
            myFriend.setRuxueyear(cursor.getInt(
                    cursor.getColumnIndex(BasicDataDbHelper.fruxueyear))
            );
            myFriend.setFrom(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.ffrom))
            );
            myFriend.setIntroduce(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.fintroduce))
            );
            myFriend.setRemark(cursor.getString(
                    cursor.getColumnIndex(BasicDataDbHelper.fintroduce))
            );
            myFriend.setFriendgroup(cursor.getInt(
                    cursor.getColumnIndex(BasicDataDbHelper.ffriendgroup))
            );

            myFriends.add(myFriend);
        }
        cursor.close();
        return myFriends;
    }







}
