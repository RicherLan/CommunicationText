package thefirstchange.example.com.communicationtext.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.db.ChatDbHelper;
import thefirstchange.example.com.communicationtext.service.ChatMsg;
import thefirstchange.example.com.communicationtext.service.MyMessageQueue;
import thefirstchange.example.com.communicationtext.service.ObjectService;


/**
 * 聊天记录数据库操作接口
 *
 */
public class ChatRecordDao {
//    private SQLiteDatabase courseDataBase;
//    private SQLiteDatabase scoreDataBase;
//    private SQLiteDatabase schduleDataBase;
    private SQLiteDatabase dataBase;
//    private ChatDbHelper dbHelper;
    private static ChatRecordDao sStuCourseDao;

    private ChatRecordDao(Context context){

        dataBase = new ChatDbHelper(context).getWritableDatabase();

//        ChatDbHelper dbHelper = new ChatDbHelper(context);
//        dbHelper.deleteDatabase(context);

    }

    public static ChatRecordDao getInstance (Context context){
        synchronized (ChatRecordDao.class){
            if (sStuCourseDao == null){
                sStuCourseDao = new ChatRecordDao(context);
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


    public int saveSingle(ChatMsg chatMsg){       //返回自增id
        ContentValues values = new ContentValues();
        values.put(ChatDbHelper.sbelongid,ObjectService.personalInfo.getPhonenumber());
        values.put(ChatDbHelper.smsgid, chatMsg.getMsgid());
        values.put(ChatDbHelper.ssenderid, chatMsg.getSenderid());
        values.put(ChatDbHelper.ssendername, chatMsg.getSendername());
        values.put(ChatDbHelper.ssendersex, chatMsg.getSendersex());
        values.put(ChatDbHelper.ssendericon, chatMsg.getSendericon());
        values.put(ChatDbHelper.sreceiverid, chatMsg.getReceiverid());
        values.put(ChatDbHelper.sreceivername, chatMsg.getReceivername());
        values.put(ChatDbHelper.smsgtype, chatMsg.getMsgtype());
        values.put(ChatDbHelper.smsgbody, chatMsg.getMsgbody());
        values.put(ChatDbHelper.smsgtime, chatMsg.getMsgtime());
        values.put(ChatDbHelper.svoicetime, chatMsg.getVoicetime());
        values.put(ChatDbHelper.shandleRs, chatMsg.getHandleRs());

        //当相同的时候替换。
        dataBase.insertWithOnConflict(ChatDbHelper.TABLE_SINGLE,null,values, SQLiteDatabase.CONFLICT_REPLACE);
        String sql = "select last_insert_rowid() from " + ChatDbHelper.TABLE_SINGLE ;
        Cursor cursor = dataBase.rawQuery(sql, null);
        int a = -1;
        if(cursor.moveToFirst()){
            a = cursor.getInt(0);
        }
        return a;

    }

    //修改状态为已读
    public void changeSinglesStateHadRead(Vector<ChatMsg> chatMsgs){

       for(int i=0;i<chatMsgs.size();++i){
            ChatMsg chatMsg = chatMsgs.get(i);
            String sql = "update "+ChatDbHelper.TABLE_SINGLE+" set "+ChatDbHelper.shandleRs+"='hadread' where "+ChatDbHelper.sid+"='"+chatMsg.getId()+"'";
           dataBase.execSQL(sql);

       }

    }

    //修改状态为未读
    public void changeSinglesStateNotRead(Vector<ChatMsg> chatMsgs){
        for(int i=0;i<chatMsgs.size();++i){
            ChatMsg chatMsg = chatMsgs.get(i);
            String sql = "update "+ChatDbHelper.TABLE_SINGLE+" set "+ChatDbHelper.shandleRs+"='nothandle' where "+ChatDbHelper.smsgid+"='"+chatMsg.getMsgid()+"'";
            dataBase.execSQL(sql);
        }
    }

    public void saveSingleList(Vector<ChatMsg> chatMsgs){
       // Log.e("aa","savesavesavesavesave"+"  "+courses.get(0).getCN());
        for (ChatMsg chatMsg : chatMsgs){
            saveSingle(chatMsg);
        }
    }

    //拿到最后一条消息
    public ChatMsg getLastSingle(String ph){

        Cursor cursor = dataBase.rawQuery("select * from "+ ChatDbHelper.TABLE_SINGLE +" where "+ChatDbHelper.sbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"' and ("+ChatDbHelper.ssenderid+"='"+ph+"' or "+ChatDbHelper.sreceiverid+"='"+ph+"') order by "+ChatDbHelper.sid+" desc limit 0,1",null);
        ChatMsg course =null;
        if (cursor.moveToNext()){
            course = new ChatMsg();
            course.setType("single");
            course.setId(cursor.getInt(
                    cursor.getColumnIndex(ChatDbHelper.sid))
            );
            course.setMsgid(cursor.getInt(
                    cursor.getColumnIndex(ChatDbHelper.smsgid))
            );
            course.setSenderid(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.ssenderid)
            ));
            course.setSendername(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.ssendername)
            ));
            course.setSendersex(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.ssendersex)
            ));
            course.setSendericon(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.ssendericon)
            ));
            course.setReceiverid(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.sreceiverid)
            ));
            course.setReceivername(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.sreceivername)
            ));
            course.setMsgtype(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.smsgtype)
            ));
            course.setMsgbody(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.smsgbody)
            ));
            course.setMsgtime(cursor.getLong(
                    cursor.getColumnIndex(ChatDbHelper.smsgtime)
            ));
            course.setVoicetime(cursor.getDouble(
                    cursor.getColumnIndex(ChatDbHelper.svoicetime)
            ));
            course.setHandleRs(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.shandleRs)
            ));
        }
        cursor.close();
        return course;

    }

    public Vector<ChatMsg> getSingleList(){
        Vector<ChatMsg> courses = new Vector<>();
        Cursor cursor = dataBase.rawQuery("select * from "+ ChatDbHelper.TABLE_SINGLE,null);
        while (cursor.moveToNext()){
            ChatMsg course = new ChatMsg();
            course.setType("single");
            course.setId(cursor.getInt(
                    cursor.getColumnIndex(ChatDbHelper.sid))
            );
            course.setMsgid(cursor.getInt(
                    cursor.getColumnIndex(ChatDbHelper.smsgid))
            );
            course.setSenderid(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.ssenderid)
            ));
            course.setSendername(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.ssendername)
            ));
            course.setSendersex(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.ssendersex)
            ));
            course.setSendericon(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.ssendericon)
            ));
            course.setReceiverid(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.sreceiverid)
            ));
            course.setReceivername(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.sreceivername)
            ));
            course.setMsgtype(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.smsgtype)
            ));
            course.setMsgbody(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.smsgbody)
            ));
            course.setMsgtime(cursor.getLong(
                    cursor.getColumnIndex(ChatDbHelper.smsgtime)
            ));
            course.setVoicetime(cursor.getDouble(
                    cursor.getColumnIndex(ChatDbHelper.svoicetime)
            ));
            course.setHandleRs(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.shandleRs)
            ));

            courses.add(course);
        }
        cursor.close();
        return courses;
    }

    //对方的账号和  当前已经刷新出的消息的id    type是 刚进去  刷新 还是  进去后手动刷新
    public Vector<ChatMsg> getSingleListLimit(String type,int sid,String ph){
//
//         Cursor c = dataBase.rawQuery("select * from "+ChatDbHelper.TABLE_SINGLE+" where ("+ChatDbHelper.ssenderid+"= '"+ph+"' or "+ChatDbHelper.sreceiverid+"='"+ph+"') and "+ChatDbHelper.sbelongid+" = '"+PersonalInfo.getPhonenumber()+"'",null);
//        while(c.moveToNext()){
//            int d = c.getInt(c.getColumnIndex(ChatDbHelper.gid));
//            int e =  c.getInt(c.getColumnIndex(ChatDbHelper.gmsgid));
//            String a = c.getString(c.getColumnIndex(ChatDbHelper.gmsgbody));
//            int b = 0;
//        }

        Vector<ChatMsg> courses = new Vector<>();

        int sum = 15;
        while(sum!=0){
            Cursor cursor = null;
            if(type.equals("first")&&sid==-1){
                cursor = dataBase.rawQuery("select * from "+ChatDbHelper.TABLE_SINGLE+" where ("+ChatDbHelper.ssenderid+"= '"+ph+"' or "+ChatDbHelper.sreceiverid+"='"+ph+"') and "+ChatDbHelper.sbelongid+" = '"+ ObjectService.personalInfo.getPhonenumber()+"'order by id desc",null);
            }else if(sid!=-1){
//                cursor = dataBase.rawQuery("select * from "+ChatDbHelper.TABLE_GROUP+" where "+ChatDbHelper.ggroupid+"= '"+groupid+"' and "+ChatDbHelper.gmsgid+">'"+msgid+"' order by id desc",null);
                cursor = dataBase.rawQuery("select * from "+ChatDbHelper.TABLE_SINGLE+" where ("+ChatDbHelper.ssenderid+"= '"+ph+"' or "+ChatDbHelper.sreceiverid+"='"+ph+"') and "+ChatDbHelper.sid+"<'"+sid+"' and  "+ChatDbHelper.sbelongid+" = '"+ObjectService.personalInfo.getPhonenumber()+"' order by id desc",null);
            }

            while(cursor!=null&&cursor.moveToNext()){
                ChatMsg course = new ChatMsg();
                course.setType("single");
                sid = (cursor.getInt(
                        cursor.getColumnIndex(ChatDbHelper.gid))
                );
                course.setId(sid);
                int msgid =  (cursor.getInt(
                        cursor.getColumnIndex(ChatDbHelper.gmsgid))
                );
                course.setMsgid(msgid);
                course.setSenderid(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.ssenderid)
                ));
                course.setSendername(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.ssendername)
                ));
                course.setSendersex(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.ssendersex)
                ));
                course.setSendericon(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.ssendericon)
                ));
                course.setReceiverid(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.sreceiverid)
                ));
                course.setReceivername(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.sreceivername)
                ));
                course.setMsgtype(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.smsgtype)
                ));
                course.setMsgbody(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.smsgbody)
                ));
                course.setMsgtime(cursor.getLong(
                        cursor.getColumnIndex(ChatDbHelper.smsgtime)
                ));
                course.setVoicetime(cursor.getDouble(
                        cursor.getColumnIndex(ChatDbHelper.svoicetime)
                ));
                course.setHandleRs(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.shandleRs)
                ));



                courses.add(course);
                break;
            }
            if(cursor!=null){
                cursor.close();
            }
            sum--;
        }

        return courses;

    }

    public void removeAllSingle(){
        //删除全部记录
        dataBase.delete(ChatDbHelper.TABLE_SINGLE,null,null);
    }

/*
   群聊表
 */

    public int saveGroup(ChatMsg chatMsg){
        ContentValues values = new ContentValues();
        values.put(ChatDbHelper.gbelongid,ObjectService.personalInfo.getPhonenumber());
        values.put(ChatDbHelper.gmsgid, chatMsg.getMsgid());
        values.put(ChatDbHelper.ggroupid, chatMsg.getGroupid());
        values.put(ChatDbHelper.gsenderid, chatMsg.getSenderid());
        values.put(ChatDbHelper.gsendername, chatMsg.getSendername());
        values.put(ChatDbHelper.gsendersex, chatMsg.getSendersex());
        values.put(ChatDbHelper.gsendericon, chatMsg.getSendericon());
        values.put(ChatDbHelper.greceiverid, chatMsg.getReceiverid());
        values.put(ChatDbHelper.greceivername, chatMsg.getReceivername());
        values.put(ChatDbHelper.gmsgtype, chatMsg.getMsgtype());
        values.put(ChatDbHelper.gmsgbody, chatMsg.getMsgbody());
        values.put(ChatDbHelper.gmsgtime, chatMsg.getMsgtime());
        values.put(ChatDbHelper.gvoicetime, chatMsg.getVoicetime());
        values.put(ChatDbHelper.ghandleRs, chatMsg.getHandleRs());

        //当相同的时候替换。
        dataBase.insertWithOnConflict(ChatDbHelper.TABLE_GROUP,null,values, SQLiteDatabase.CONFLICT_REPLACE);
        String sql = "select last_insert_rowid() from " + ChatDbHelper.TABLE_GROUP ;
        Cursor cursor = dataBase.rawQuery(sql, null);
        int a = -1;
        if(cursor.moveToFirst()){
            a = cursor.getInt(0);
        }
        return a;

    }

    //修改状态为已读
    public void changeGroupsStateHadRead(Vector<ChatMsg> chatMsgs){
        for(int i=0;i<chatMsgs.size();++i){
            ChatMsg chatMsg = chatMsgs.get(i);
            String sql = "update "+ChatDbHelper.TABLE_GROUP+" set "+ChatDbHelper.ghandleRs+"='hadread' where "+ChatDbHelper.gmsgid+"='"+chatMsg.getMsgid()+"'";
            dataBase.execSQL(sql);
        }
    }

    //修改状态为未读
    public void changeGroupsStateNotRead(Vector<ChatMsg> chatMsgs){
        for(int i=0;i<chatMsgs.size();++i){
            ChatMsg chatMsg = chatMsgs.get(i);
            String sql = "update "+ChatDbHelper.TABLE_GROUP+" set "+ChatDbHelper.ghandleRs+"='nothandle' where "+ChatDbHelper.gmsgid+"='"+chatMsg.getMsgid()+"'";
            dataBase.execSQL(sql);
        }
    }

    public void saveGroupList(Vector<ChatMsg> chatMsgs){
        // Log.e("aa","savesavesavesavesave"+"  "+courses.get(0).getCN());
        for (ChatMsg chatMsg : chatMsgs){
            saveGroup(chatMsg);
        }
    }

    //获得最后一条
    public ChatMsg getLastGroup(int groupid){
        ChatMsg course = null;
        Cursor cursor = dataBase.rawQuery("select * from "+ ChatDbHelper.TABLE_GROUP +" where "+ChatDbHelper.gbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"' and "+ChatDbHelper.ggroupid+" = '"+groupid+"' order by "+ChatDbHelper.gid+" desc limit 0,1",null);
        if (cursor.moveToNext()){
            course = new ChatMsg();
            course.setType("group");
            course.setId(cursor.getInt(
                    cursor.getColumnIndex(ChatDbHelper.gid))
            );
            course.setMsgid(cursor.getInt(
                    cursor.getColumnIndex(ChatDbHelper.gmsgid))
            );
            course.setGroupid(cursor.getInt(
                    cursor.getColumnIndex(ChatDbHelper.ggroupid))
            );
            course.setSenderid(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.gsenderid)
            ));
            course.setSendername(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.gsendername)
            ));
            course.setSendersex(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.gsendersex)
            ));
            course.setSendericon(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.gsendericon)
            ));
            course.setReceiverid(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.greceiverid)
            ));
            course.setReceivername(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.greceivername)
            ));
            course.setMsgtype(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.gmsgtype)
            ));
            course.setMsgbody(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.gmsgbody)
            ));
            course.setMsgtime(cursor.getLong(
                    cursor.getColumnIndex(ChatDbHelper.gmsgtime)
            ));
            course.setVoicetime(cursor.getDouble(
                    cursor.getColumnIndex(ChatDbHelper.gvoicetime)
            ));
            course.setHandleRs(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.ghandleRs)
            ));

        }
        cursor.close();
        return course;
    }

    public Vector<ChatMsg> getGroupList(){
        Vector<ChatMsg> courses = new Vector<>();
        Cursor cursor = dataBase.rawQuery("select * from "+ ChatDbHelper.TABLE_GROUP+" where "+ChatDbHelper.gbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"'",null);
        while (cursor.moveToNext()){
            ChatMsg course = new ChatMsg();
            course.setType("group");
            course.setId(cursor.getInt(
                    cursor.getColumnIndex(ChatDbHelper.gid))
            );
            course.setMsgid(cursor.getInt(
                    cursor.getColumnIndex(ChatDbHelper.gmsgid))
            );
            course.setGroupid(cursor.getInt(
                    cursor.getColumnIndex(ChatDbHelper.ggroupid))
            );
            course.setSenderid(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.gsenderid)
            ));
            course.setSendername(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.gsendername)
            ));
            course.setSendersex(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.gsendersex)
            ));
            course.setSendericon(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.gsendericon)
            ));
            course.setReceiverid(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.greceiverid)
            ));
            course.setReceivername(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.greceivername)
            ));
            course.setMsgtype(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.gmsgtype)
            ));
            course.setMsgbody(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.gmsgbody)
            ));
            course.setMsgtime(cursor.getLong(
                    cursor.getColumnIndex(ChatDbHelper.gmsgtime)
            ));
            course.setVoicetime(cursor.getDouble(
                    cursor.getColumnIndex(ChatDbHelper.gvoicetime)
            ));
            course.setHandleRs(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.ghandleRs)
            ));

            courses.add(course);
        }
        cursor.close();
        return courses;
    }

    public Vector<ChatMsg> getGroupListLimit(String type,int gid,int groupid){
//        int gid = -1;
//        if(msgid==-1){
//
//            Cursor cursor2 = dataBase.rawQuery("select * from "+ChatDbHelper.TABLE_GROUP+" where "+ChatDbHelper.ggroupid+"= '"+groupid+"' order by id desc",null);
//            while(cursor2.moveToNext()){
//                gid = (cursor2.getInt(
//                        cursor2.getColumnIndex(ChatDbHelper.gid)));
//                break;
//            }
//            if(gid==-1){
//                return null;
//            }
//
//        }else{
//            Cursor cursor2 = dataBase.rawQuery("select * from "+ChatDbHelper.TABLE_GROUP+" where "+ChatDbHelper.ggroupid+"= '"+groupid+"' and "+ChatDbHelper.gmsgid+" ='"+msgid+"'",null);
//            if(cursor2.moveToNext()){
//                gid = (cursor2.getInt(
//                        cursor2.getColumnIndex(ChatDbHelper.gid)));
//            }
//            if(gid==-1){
//                return null;
//            }
//        }
//



//        Cursor c =  dataBase.rawQuery("select * from "+ChatDbHelper.TABLE_GROUP+" where "+ChatDbHelper.ggroupid+"='"+groupid+"'",null);
//        while(c.moveToNext()){
//            int d = c.getInt(c.getColumnIndex(ChatDbHelper.gid));
//            int e =  c.getInt(c.getColumnIndex(ChatDbHelper.gmsgid));
//            String a = c.getString(c.getColumnIndex(ChatDbHelper.gmsgbody));
//            int b = 0;
//        }

        Vector<ChatMsg> courses = new Vector<>();

//        if(msgid!=-1){
//            Cursor cursor2 = dataBase.rawQuery("select * from "+ChatDbHelper.TABLE_GROUP+" where "+ChatDbHelper.ggroupid+"= '"+groupid+"' and "+ChatDbHelper.gmsgid+"='"+msgid+"'",null);
//            if(cursor2.moveToNext()){
//                gid = (cursor2.getInt(
//                        cursor2.getColumnIndex(ChatDbHelper.gid))
//                );
//            }
//        }

        int sum = 15;
        while(sum!=0){
            Cursor cursor = null;
            if(type.equals("first")&&gid==-1){
                cursor = dataBase.rawQuery("select * from "+ChatDbHelper.TABLE_GROUP+" where "+ChatDbHelper.ggroupid+"= '"+groupid+"' and "+ChatDbHelper.gbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"' order by id desc",null);
            }else if(gid!=-1){
//                cursor = dataBase.rawQuery("select * from "+ChatDbHelper.TABLE_GROUP+" where "+ChatDbHelper.ggroupid+"= '"+groupid+"' and "+ChatDbHelper.gmsgid+">'"+msgid+"' order by id desc",null);
                cursor = dataBase.rawQuery("select * from "+ChatDbHelper.TABLE_GROUP+" where "+ChatDbHelper.ggroupid+"= '"+groupid+"' and "+ChatDbHelper.gid+"<'"+gid+"' and "+ChatDbHelper.gbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"' order by id desc",null);
            }
            while(cursor!=null&&cursor.moveToNext()){
                ChatMsg course = new ChatMsg();
                course.setType("group");
                gid = (cursor.getInt(
                        cursor.getColumnIndex(ChatDbHelper.gid))
                );
                course.setId(gid);
                int msgid =  (cursor.getInt(
                        cursor.getColumnIndex(ChatDbHelper.gmsgid))
                );
                course.setMsgid(msgid);
                course.setGroupid(cursor.getInt(
                        cursor.getColumnIndex(ChatDbHelper.ggroupid))
                );
                course.setSenderid(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.gsenderid)
                ));
                course.setSendername(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.gsendername)
                ));
                course.setSendersex(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.gsendersex)
                ));
                course.setSendericon(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.gsendericon)
                ));
                course.setReceiverid(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.greceiverid)
                ));
                course.setReceivername(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.greceivername)
                ));
                course.setMsgtype(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.gmsgtype)
                ));
                course.setMsgbody(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.gmsgbody)
                ));
                course.setMsgtime(cursor.getLong(
                        cursor.getColumnIndex(ChatDbHelper.gmsgtime)
                ));
                course.setVoicetime(cursor.getDouble(
                        cursor.getColumnIndex(ChatDbHelper.gvoicetime)
                ));
                course.setHandleRs(cursor.getString(
                        cursor.getColumnIndex(ChatDbHelper.ghandleRs)
                ));

                courses.add(course);
                break;
            }
            if(cursor!=null){
                cursor.close();
            }
            sum--;
        }

        return courses;
    }

    public void removeAllGroup(){
        //删除全部记录
        dataBase.delete(ChatDbHelper.TABLE_GROUP,null,null);
    }


/*
   好友或群请求表
 */

    //保存时  首先删除之前的同一个人的相同类型的请求
    public void saveRequest(ChatMsg chatMsg){
        if(chatMsg==null){
            return;
        }
        if(chatMsg.getType().contains("friend")||chatMsg.getType().contains("Friend")){
            String sql = "delete from "+ChatDbHelper.TABLE_REQUEST+" where "+ChatDbHelper.rbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"' and "+ChatDbHelper.rtype+"='"+chatMsg.getType()+"' and "+ChatDbHelper.rsenderid+"='"+chatMsg.getSenderid()+"'";
            dataBase.execSQL(sql);
        }else{
            String sql = "delete from "+ChatDbHelper.TABLE_REQUEST+" where "+ChatDbHelper.rbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"' and "+ChatDbHelper.rtype+"='"+chatMsg.getType()+"' and "+ChatDbHelper.rsenderid+"='"+chatMsg.getSenderid()+"' and "+ChatDbHelper.rgroupid+"='"+chatMsg.getGroupid()+"'";
            dataBase.execSQL(sql);
        }


        ContentValues values = new ContentValues();
        values.put(ChatDbHelper.rbelongid,ObjectService.personalInfo.getPhonenumber());
        values.put(ChatDbHelper.rmsgid, chatMsg.getMsgid());
        values.put(ChatDbHelper.rtype, chatMsg.getType());
        values.put(ChatDbHelper.rgroupid, chatMsg.getGroupid());
        values.put(ChatDbHelper.rsenderid, chatMsg.getSenderid());
        values.put(ChatDbHelper.rsendername, chatMsg.getSendername());
        values.put(ChatDbHelper.rsendersex, chatMsg.getSendersex());
        values.put(ChatDbHelper.rsendericon, chatMsg.getSendericon());
        values.put(ChatDbHelper.rreceiverid, chatMsg.getReceiverid());
        values.put(ChatDbHelper.rreceivername, chatMsg.getReceivername());
        values.put(ChatDbHelper.rmsgtype, chatMsg.getMsgtype());
        values.put(ChatDbHelper.rmsgbody, chatMsg.getMsgbody());
        values.put(ChatDbHelper.rmsgtime, chatMsg.getMsgtime());
        values.put(ChatDbHelper.rhandleRs, chatMsg.getHandleRs());

        //当相同的时候替换。
        dataBase.insertWithOnConflict(ChatDbHelper.TABLE_REQUEST,null,values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    //删除某人关于某群的加群请求  这个主要用在  “通知其他管理员 加群请求已经被其他管理员处理了”
    public void deleteAddGroupRequest(String userid,int groupid){
        String sql = "delete from "+ChatDbHelper.TABLE_REQUEST+" where "+ChatDbHelper.rbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"' and "+ChatDbHelper.rsenderid+"='"+userid+"' and "+ChatDbHelper.rgroupid+"='"+groupid+"'";
        dataBase.execSQL(sql);
    }


    public Vector<ChatMsg> getRequestListLimit(int id){
        Vector<ChatMsg> courses = new Vector<>();
        Cursor cursor = dataBase.rawQuery("select * from "+ ChatDbHelper.TABLE_REQUEST+" where "+ChatDbHelper.rbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"' and '"+ChatDbHelper.rtype+"' <>'schDuty' order by id desc limit '"+id+1+"','"+id+10+"'",null);
        while (cursor.moveToNext()){
            ChatMsg course = new ChatMsg();
            course.setId(cursor.getInt(
                    cursor.getColumnIndex(ChatDbHelper.rid))
            );
            course.setMsgid(cursor.getInt(
                    cursor.getColumnIndex(ChatDbHelper.rmsgid))
            );
            course.setType(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.rtype))
            );
            course.setGroupid(cursor.getInt(
                    cursor.getColumnIndex(ChatDbHelper.rgroupid))
            );
            course.setSenderid(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.rsenderid)
            ));
            course.setSendername(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.rsendername)
            ));
            course.setSendersex(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.rsendersex)
            ));
            course.setSendericon(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.rsendericon)
            ));
            course.setReceiverid(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.rreceiverid)
            ));
            course.setReceivername(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.rreceivername)
            ));
            course.setMsgtype(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.rmsgtype)
            ));
            course.setMsgbody(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.rmsgbody)
            ));
            course.setMsgtime(cursor.getLong(
                    cursor.getColumnIndex(ChatDbHelper.rmsgtime)
            ));
            course.setHandleRs(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.rhandleRs)
            ));

            Vector<Integer> integers = new Vector<>();
            for( int i=0;i<courses.size();++i){
                ChatMsg chatMsg = courses.get(i);
                if(chatMsg.getSenderid().equals(course.getSenderid())&&chatMsg.getType().equals(course.getType())){
                    integers.add(i);
                }
            }

            for(Integer index: integers){
                courses.removeElementAt(index);
            }

            courses.add(course);
        }
        cursor.close();
        return courses;
    }

    public void getRequestFromDB(){
        Vector<ChatMsg> chatMsgs = getRequestListLimit(-1);
        if(chatMsgs!=null){

            for(int i=0;i<chatMsgs.size();++i){

                ChatMsg chatMsg = chatMsgs.get(i);
//                        String mtype = chatMsg.getType();
//                        String handl = chatMsg.getHandleRs();

                if(chatMsgs.get(i).getHandleRs().equals("nothandle")||chatMsgs.get(i).getHandleRs().equals("hadread")){
                    boolean add = true;
                    for(ChatMsg chatMsg1 :  MyMessageQueue.requestQueueNotHandle){

                        if(chatMsgs.get(i).getSenderid().equals(chatMsg1.getSenderid())&&
                                chatMsgs.get(i).getType().equals(chatMsg1.getType())
                                ||chatMsgs.get(i).getMsgid()==chatMsg1.getMsgid()){
                            add=false;
                            break;
                        }
                    }
                    if(add){
                        MyMessageQueue.requestQueueNotHandle.add(chatMsgs.get(i));
                    }

                }else{

                    boolean add = true;
                    for(ChatMsg chatMsg1 :  MyMessageQueue.requestQueueNotHandle){
                        if(chatMsgs.get(i).getSenderid().equals(chatMsg1.getSenderid())&&
                                chatMsgs.get(i).getType().equals(chatMsg1.getType())
                                ||chatMsgs.get(i).getMsgid()==chatMsg1.getMsgid()){
                            add=false;
                            break;
                        }
                    }

                    for(ChatMsg chatMsg1 :  MyMessageQueue.requestQueueHadHandled){
                        if(chatMsgs.get(i).getSenderid().equals(chatMsg1.getSenderid())&&
                                chatMsgs.get(i).getType().equals(chatMsg1.getType())
                                ||chatMsgs.get(i).getMsgid()==chatMsg1.getMsgid()){
                            add=false;
                            break;
                        }
                    }

                    if(add){
                        MyMessageQueue.requestQueueHadHandled.add(chatMsgs.get(i));
                    }
                }
            }


        }

    }


/*
    值班通知
 */

    //保存
    public void saveSchDuty(ChatMsg chatMsg){
        if(chatMsg==null){
            return;
        }

        String sql = "delete from "+ChatDbHelper.TABLE_SCHDUTY+" where "+ChatDbHelper.sdbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"' and "+ChatDbHelper.sdgroupid+"='"+chatMsg.getGroupid()+"' and "+ChatDbHelper.sdmsgtime+"='"+chatMsg.getMsgtime()+"'";
        dataBase.execSQL(sql);

        ContentValues values = new ContentValues();
        values.put(ChatDbHelper.sdbelongid,ObjectService.personalInfo.getPhonenumber());
        values.put(ChatDbHelper.sdmsgid, chatMsg.getMsgid());
        values.put(ChatDbHelper.sdtype, chatMsg.getType());
        values.put(ChatDbHelper.sdgroupid, chatMsg.getGroupid());
        values.put(ChatDbHelper.sdsenderid, chatMsg.getSenderid());
        values.put(ChatDbHelper.sdsendername, chatMsg.getSendername());
        values.put(ChatDbHelper.sdsendericon, chatMsg.getSendericon());
        values.put(ChatDbHelper.sdreceiverid, chatMsg.getReceiverid());
        values.put(ChatDbHelper.sdreceivername, chatMsg.getReceivername());
        values.put(ChatDbHelper.sdmsgtype, chatMsg.getMsgtype());
        values.put(ChatDbHelper.sdmsgbody, chatMsg.getMsgbody());
        values.put(ChatDbHelper.sdmsgtime, chatMsg.getMsgtime());
        values.put(ChatDbHelper.sdhandleRs, chatMsg.getHandleRs());

        //当相同的时候替换。
        dataBase.insertWithOnConflict(ChatDbHelper.TABLE_SCHDUTY,null,values, SQLiteDatabase.CONFLICT_REPLACE);
    }



    public Vector<ChatMsg> getSchDutyListLimit(int id){
        Vector<ChatMsg> courses = new Vector<>();
        Cursor cursor = dataBase.rawQuery("select * from "+ ChatDbHelper.TABLE_SCHDUTY+" where "+ChatDbHelper.sdbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"' order by id desc limit '"+id+1+"','"+id+10+"'",null);
        while (cursor.moveToNext()){
            ChatMsg course = new ChatMsg();
            course.setId(cursor.getInt(
                    cursor.getColumnIndex(ChatDbHelper.sdid))
            );
            course.setMsgid(cursor.getInt(
                    cursor.getColumnIndex(ChatDbHelper.sdmsgid))
            );
            course.setType(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.sdtype))
            );
            course.setGroupid(cursor.getInt(
                    cursor.getColumnIndex(ChatDbHelper.sdgroupid))
            );
            course.setSenderid(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.sdsenderid)
            ));
            course.setSendername(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.sdsendername)
            ));
            course.setSendericon(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.sdsendericon)
            ));
            course.setReceiverid(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.sdreceiverid)
            ));
            course.setReceivername(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.sdreceivername)
            ));
            course.setMsgtype(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.sdmsgtype)
            ));
            course.setMsgbody(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.sdmsgbody)
            ));
            course.setMsgtime(cursor.getLong(
                    cursor.getColumnIndex(ChatDbHelper.sdmsgtime)
            ));
            course.setHandleRs(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.sdhandleRs)
            ));


            courses.add(course);
        }
        cursor.close();
        return courses;
    }

    //获得数据库中的自己的所有的社团值班表
    public void getSchDutyFromDB(){
        Vector<ChatMsg> chatMsgs = getSchDutyListLimit(-1);
        if(chatMsgs!=null){

            for(int i=0;i<chatMsgs.size();++i){
                if(chatMsgs.get(i).getHandleRs().equals("new")){
                    int groupid = chatMsgs.get(i).getGroupid();
                    if( MyMessageQueue.dutyQueueNotRead.containsKey(groupid)){
                        boolean add = true;
                        for(ChatMsg chatMsg1 :  MyMessageQueue.dutyQueueNotRead.get(groupid)){
                            if(chatMsgs.get(i).getGroupid()==(chatMsg1.getGroupid())&&
                                    chatMsgs.get(i).getMsgid()==(chatMsg1.getMsgid())){
                                add=false;
                                break;
                            }
                        }
                        if(add){
                            MyMessageQueue.dutyQueueNotRead.get(groupid).add(chatMsgs.get(i));
                        }
                    }else{
                        MyMessageQueue.dutyQueueNotRead.put(groupid,new Vector<ChatMsg>());
                        MyMessageQueue.dutyQueueNotRead.get(groupid).add(chatMsgs.get(i));
                    }


                }else{

                    int groupid = chatMsgs.get(i).getGroupid();
                    if( MyMessageQueue.dutyQueueHadRead.containsKey(groupid)){
                        boolean add = true;
                        for(ChatMsg chatMsg1 :  MyMessageQueue.dutyQueueHadRead.get(groupid)){
                            if(chatMsgs.get(i).getGroupid()==(chatMsg1.getGroupid())&&
                                    chatMsgs.get(i).getMsgid()==(chatMsg1.getMsgid())){
                                add=false;
                                break;
                            }
                        }
                        if(add){
                            MyMessageQueue.dutyQueueHadRead.get(groupid).add(chatMsgs.get(i));
                        }
                    }else{
                        MyMessageQueue.dutyQueueHadRead.put(groupid,new Vector<ChatMsg>());
                        MyMessageQueue.dutyQueueHadRead.get(groupid).add(chatMsgs.get(i));
                    }
                }
            }


        }

        if(MyMessageQueue.dutyQueueNotRead!=null){
            for(int groupid:MyMessageQueue.dutyQueueNotRead.keySet()){
                if(MyMessageQueue.dutyQueueNotRead.get(groupid)!=null&&MyMessageQueue.dutyQueueNotRead.get(groupid).size()!=0){
                    if(MyMessageQueue.dutyframes==null){
                        MyMessageQueue.dutyframes = new Vector<>();
                    }
                    if(!MyMessageQueue.dutyframes.contains(groupid)){
                        MyMessageQueue.dutyframes.add(groupid);
                    }

                }
            }
        }

        if(MyMessageQueue.dutyQueueHadRead!=null){
            for(int groupid:MyMessageQueue.dutyQueueHadRead.keySet()){
                if(MyMessageQueue.dutyQueueHadRead.get(groupid)!=null&&MyMessageQueue.dutyQueueHadRead.get(groupid).size()!=0){
                    if(MyMessageQueue.dutyframes==null){
                        MyMessageQueue.dutyframes = new Vector<>();
                    }
                    if(!MyMessageQueue.dutyframes.contains(groupid)){
                        MyMessageQueue.dutyframes.add(groupid);
                    }

                }
            }
        }

    }



/*
    通知界面窗口表
 */

    public void saveNoticeFrame(String type,String singleid,int groupid){

        ContentValues values = new ContentValues();
        values.put(ChatDbHelper.nfbelongid, ObjectService.personalInfo.getPhonenumber());
        values.put(ChatDbHelper.nftype, type);
        values.put(ChatDbHelper.nfsingleid, singleid);
        values.put(ChatDbHelper.nfgroupid, groupid);

        //当相同的时候替换。
        dataBase.insertWithOnConflict(ChatDbHelper.TABLE_NOTICEFRAME,null,values, SQLiteDatabase.CONFLICT_REPLACE);
    }


    public void getNoticeFrameList(){

        boolean hasgetRequest = false;
        Cursor cursor = dataBase.rawQuery("select * from "+ ChatDbHelper.TABLE_NOTICEFRAME+" where belongid='"+ObjectService.personalInfo.getPhonenumber()+"'",null);

        while (cursor.moveToNext()){

            String type = (cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.nftype))
            );
            String singleid =(cursor.getString(
                    cursor.getColumnIndex(ChatDbHelper.nfsingleid))
            );
           int groupid = (cursor.getInt(
                    cursor.getColumnIndex(ChatDbHelper.nfgroupid))
            );

           if(type.equals("single")){

              ChatMsg chatMsg = getLastSingle(singleid);
              if(chatMsg!=null){
                  if(MyMessageQueue.chatframes.containsKey(singleid)){
                      continue;
                  }
                  MyMessageQueue.chatframes.put(singleid,"single");
                  if(chatMsg.getHandleRs().equals("hadread")){
                      if(!MyMessageQueue.chatQueueHadRead.containsKey(singleid)){
                          MyMessageQueue.chatQueueHadRead.put(singleid,new Vector<ChatMsg>());
                      }

                      for(int i=0;i<MyMessageQueue.chatQueueHadRead.get(singleid).size();++i){
                          if(chatMsg.getMsgid()==MyMessageQueue.chatQueueHadRead.get(singleid).get(i).getMsgid()){
                            return;
                          }
                      }

                      MyMessageQueue.chatQueueHadRead.get(singleid).add(chatMsg);


                  }else if(chatMsg.getHandleRs().equals("nothandle")){
                      if(!MyMessageQueue.chatQueueNotRead.containsKey(singleid)){
                          MyMessageQueue.chatQueueNotRead.put(singleid,new Vector<ChatMsg>());
                      }

                      boolean hasexist = false;
                      for(int i=0;i<MyMessageQueue.chatQueueNotRead.get(singleid).size();++i){
                          if(chatMsg.getMsgid()==MyMessageQueue.chatQueueNotRead.get(singleid).get(i).getMsgid()){
                             return;
                          }
                      }

                      MyMessageQueue.chatQueueNotRead.get(singleid).add(chatMsg);


                  }
              }


           }else if(type.equals("group")){
               ChatMsg chatMsg = getLastGroup(groupid);
               if(chatMsg!=null){
                   if(MyMessageQueue.chatframes.containsKey(groupid+"")){
                       continue;
                   }
                   MyMessageQueue.chatframes.put(groupid+"","group");
                   if(chatMsg.getHandleRs().equals("hadread")){
                       if(!MyMessageQueue.chatQueueHadRead.containsKey(groupid+"")){
                           MyMessageQueue.chatQueueHadRead.put(groupid+"",new Vector<ChatMsg>());
                       }
                       for(int i=0;i<MyMessageQueue.chatQueueHadRead.get(groupid+"").size();++i){
                           if(chatMsg.getMsgid()==MyMessageQueue.chatQueueHadRead.get(groupid+"").get(i).getMsgid()){
                               return;
                           }
                       }
                       MyMessageQueue.chatQueueHadRead.get(groupid+"").add(chatMsg);
                   }else if(chatMsg.getHandleRs().equals("nothandle")){
                       if(!MyMessageQueue.chatQueueNotRead.containsKey(groupid+"")){
                           MyMessageQueue.chatQueueNotRead.put(groupid+"",new Vector<ChatMsg>());
                       }
                       for(int i=0;i<MyMessageQueue.chatQueueNotRead.get(groupid+"").size();++i){
                           if(chatMsg.getMsgid()==MyMessageQueue.chatQueueNotRead.get(groupid+"").get(i).getMsgid()){
                               return;
                           }
                       }
                       MyMessageQueue.chatQueueNotRead.get(groupid+"").add(chatMsg);
                   }
               }

           }else if(type.equals("schDuty")){
              getSchDutyFromDB();

           }else if(type.equals("request")){
               if(hasgetRequest){
                   continue;
               }
               hasgetRequest = true;
               getRequestFromDB();

           }

        }
        cursor.close();

    }

    public void removeAllNoticeFrame(){
        //删除全部记录
        dataBase.delete(ChatDbHelper.TABLE_NOTICEFRAME,null,null);
    }

    public void deleteNoticeFrame(String type,String singleid,int groupid){
        if(type.equals("single")){
            String sql = "delete from "+ChatDbHelper.TABLE_NOTICEFRAME+" where "+ChatDbHelper.nfbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"' and "+ChatDbHelper.nftype+"='"+type+"' and "+ChatDbHelper.nfsingleid+"='"+singleid+"'";
            dataBase.execSQL(sql);
        }else if(type.equals("group")){
            String sql = "delete from "+ChatDbHelper.TABLE_NOTICEFRAME+" where "+ChatDbHelper.nfbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"' and "+ChatDbHelper.nftype+"='"+type+"' and "+ChatDbHelper.nfgroupid+"='"+groupid+"'";
            dataBase.execSQL(sql);
        }else if(type.equals("schduty")){
            String sql = "delete from "+ChatDbHelper.TABLE_NOTICEFRAME+" where "+ChatDbHelper.nfbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"' and "+ChatDbHelper.nftype+"='"+type+"' and "+ChatDbHelper.nfgroupid+"='"+groupid+"'";
            dataBase.execSQL(sql);
        }else if(type.equals("request")){
            String sql = "delete from "+ChatDbHelper.TABLE_NOTICEFRAME+" where "+ChatDbHelper.nfbelongid+"='"+ObjectService.personalInfo.getPhonenumber()+"' and "+ChatDbHelper.nftype+"='"+type+"'";
            dataBase.execSQL(sql);
        }

    }

}
