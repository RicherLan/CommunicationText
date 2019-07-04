package thefirstchange.example.com.communicationtext.course.supercouesrdemo2;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import thefirstchange.example.com.communicationtext.course.object.Course;
import thefirstchange.example.com.communicationtext.course.object.ListViewScore;
import thefirstchange.example.com.communicationtext.course.object.ListviewZhoushu;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.schedulearrangement.ClientArrangement;
import thefirstchange.example.com.communicationtext.gson.ClassRoom;

public class CourseAndScore {

    public static Vector<ClassRoom> classRooms;

    public static Vector<Course> courses = new Vector<>();                        //课程表
    public static Vector<ListViewScore> scores = new Vector<>();                  //成绩表
    public static String courseph = null;                           //课程表是属于哪个账号的
    public static String scoreph = null;                           //成绩表是属于哪个账号的

    public static Vector<ClientArrangement> clientArrangements = new Vector<>();   //值班表
    public static Vector<ClientArrangement> clientArrangementsTemp = new Vector<>();   //临时的值班表  就是服务器刚传来的  主界面接收到服务广播界面时要用到

    public static Vector<Integer> groupDuty = new Vector<>();                   //当前有几张值班表  存储的是群号

    public static int duty_groupid = -1;                              //当前显示的是哪张值班表   群号
    public static String duty_name = null;                            //当前显示的是哪张值班表   名称

    //key为周几加空格加节    value是课程在CourseAndScore.courses中的下标
    // 比如周4的3-4节可能有好几种课  但是上的周数不一样  所以点击事件时  就要一一列举
    public static Map<String,Vector<Integer>> courseSameTime = new HashMap<>();


    public static int nowzhoushu=1;                //当前周
    public static long zhou_time;  //设置当前周的具体时间


    public static int courseXueqi =-1;                     //课程表显示的当前学期
    public static int courseGrade =-1;                  //课程表显示的当前年级

    public static int scoreXueqi =-1;                     //成绩表显示的当前学期
    public static int scoreGrade =-1;                  //成绩课程表显示的当前年级

    public static List<ListviewZhoushu> zhoushulist ;

    public static void initzhoushulist() {

        zhoushulist = new ArrayList<>();
        for(int i=1;i<=27;++i){
            ListviewZhoushu listviewZhoushu = new ListviewZhoushu();
            listviewZhoushu.setZhoushu("第"+i+"周");
            listviewZhoushu.setZhou(i);
            listviewZhoushu.setBenzhou(false);
            if(i==nowzhoushu){
                listviewZhoushu.setBenzhou(true);
            }
            zhoushulist.add(listviewZhoushu);
        }
    }

    public static void initcourseSameTime(){
        courseSameTime.clear();
        for (int i = 0; i< CourseAndScore.courses.size(); ++i) {
            final Course course = CourseAndScore.courses.get(i);
            String day = course.getCT2();      //这个课是星期几
            int mcol=1;
            if(day.equals("星期一")){
                mcol = 1;
            }else  if(day.equals("星期二")){
                mcol = 2;
            }else  if(day.equals("星期三")){
                mcol = 3;
            }else  if(day.equals("星期四")){
                mcol = 4;
            }else  if(day.equals("星期五")){
                mcol = 5;
            }else  if(day.equals("星期六")){
                mcol = 6;
            }else  if(day.equals("星期日")){
                mcol = 7;
            }
            int col = mcol*2-1;
            String detailtime = course.getCT3();    //上课具体时间，比如1-2节
            //原来为   1-2节   把最后的 节  去掉
            String str1 = detailtime.substring(0, course.getCT3().length()-1);
            String[] strings = str1.split("-");
            int row1 = 1;             //下面用row1就行了  因为有size在  自动写占据几行
            int row2 = 1;
            //担心某种课 就占据一节课
            if(strings.length==1) {

                row1 = Integer.parseInt(strings[0]);
            }else{
                row1 = Integer.parseInt(strings[0]);
                row2 = Integer.parseInt(strings[1]);
            }

            final String key = mcol+" "+row1;
            if(! CourseAndScore.courseSameTime.containsKey(key)){
                CourseAndScore.courseSameTime.put(key,new Vector<Integer>());
                CourseAndScore.courseSameTime.get(key).add(i);
            }else{
                CourseAndScore.courseSameTime.get(key).add(i);
            }
        }

    }


    public static void removeAll(){

        classRooms = new Vector<>();

       courses = new Vector<>();                        //课程表
         scores = new Vector<>();                  //成绩表
        courseph = null;                           //课程表是属于哪个账号的
       scoreph = null;                           //成绩表是属于哪个账号的

         clientArrangements = new Vector<>();   //值班表
        clientArrangementsTemp = new Vector<>();   //临时的值班表  就是服务器刚传来的  主界面接收到服务广播界面时要用到

         groupDuty = new Vector<>();                   //当前有几张值班表  存储的是群号

         duty_groupid = -1;                              //当前显示的是哪张值班表   群号
        duty_name = null;                            //当前显示的是哪张值班表   名称

        //key为周几加空格加节    value是课程在CourseAndScore.courses中的下标
        // 比如周4的3-4节可能有好几种课  但是上的周数不一样  所以点击事件时  就要一一列举
        courseSameTime = new HashMap<>();


         nowzhoushu=1;                //当前周
         zhou_time= -1;  //设置当前周的具体时间  因为以后系统要自己更换当前周 yyyy-MM-dd HH:mm:ss


        courseXueqi =-1;                     //课程表显示的当前学期
         courseGrade =-1;                  //课程表显示的当前年级

        scoreXueqi =-1;                     //成绩表显示的当前学期
        scoreGrade =-1;                  //成绩课程表显示的当前年级

         zhoushulist =null;
    }

}
