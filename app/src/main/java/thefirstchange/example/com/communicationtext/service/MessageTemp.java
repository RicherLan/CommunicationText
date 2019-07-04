package thefirstchange.example.com.communicationtext.service;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.gson.CorpUserNotLoadCourse;
import thefirstchange.example.com.communicationtext.gson.UserCorp;

public class MessageTemp {

    public static Vector<UserCorp> userCorps = new Vector<>();    //社团组织群查询空课时   服务器返回的人员

    public static Vector<CorpUserNotLoadCourse> corpUserNotLoadCourses = new Vector<>(); ////社团组织群课表导入情况   服务器返回的未导入人员

}
