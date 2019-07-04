package thefirstchange.example.com.communicationtext.config;


import android.os.Environment;

public class Config {

//    public static boolean kipWelcomePage = false;
    //是否登陆   掉线重连时用到
    public static boolean isLogined = false;
    //掉线重连后  请求登陆的间隔
    public static int loginIntervalTime = 9000;

    //47.101.134.116
    public static final String HTTPURL = "http://47.101.134.116:8989";
    //47.101.134.116
    public static String ServerIP= "47.101.134.116";   //47.106.84.47    192.168.43.212  10.168.85.251
    public static int ServerPort = 8988;   //聊天时服务器给客户端发消息时   客户端指定的接收端口

    //是否点击了退出软件  这样再次进入的话不启动myapplication了   service通过activity计获
    public static boolean isExitSoft = false;


    public static String path= Environment.getExternalStorageDirectory()+"/schedule";
    public static  String path1 = path+"/.recorder_audios";
    public static  String path2 =  path+"/.recorder_images";
    public static String dongtaipath = path+"/.recorder_dongtai";
    public static String usericonpath = path+"/.recoder_usericon";      //用户头像
    public static String groupiconpath = path+"/.recoder_groupicon";      //群头像
    public static String groupusericonpath = path+"/.group_recoder_usericon";

    public static String DtIcpath = path+"/.recoder_dticon";


    public static String sharedPreferences_edu_account = "edu_account";
    public static String sharedPreferences_edu_year_grade = "edu_year_grade";

    /*
    sharedPreferences_edu_year_grade

     editor.putInt("nowzhoushu", i);
     editor.putString("zhou_time", CourseAndScore.zhou_time);

     editor.putInt("courseXueqi", CourseAndScore.courseXueqi);
     editor.putInt("courseGrade", CourseAndScore.courseGrade);
     editor.putInt("scoreXueqi", CourseAndScore.scoreXueqi);
     editor.putInt("scoreGrade", CourseAndScore.scoreGrade);
     */

    public static String sharedPreferences_course_ph= "course_ph";     //课程表是属于哪个账号的
    public static String sharedPreferences_score_ph= "score_ph";     //成绩表是属于哪个账号的



    public static String sharedPreferences_duty_group= "duty_group";     //当前显示的哪张值班表  群号
    public static String sharedPreferences_duty_name= "duty_name";     //当前显示的哪张值班表  名称
    /*
     sharedPreferences_duty_group

     editor.putInt("duty_groupid", CourseAndScore.groupid);
     editor.putInt("duty_name", CourseAndScore.groupid);
     */

    public static String sharedPreferences_personal_info= "personal_info";          //个人资料
    public static String sharedPreferences_keeplogin = "keeplogin_info";

    /*
        存储
         SharedPreferences sh = getSharedPreferences( Config.sharedPreferences_personal_info,Context.MODE_PRIVATE);

                Gson gson = new Gson();
                String json = gson.toJson(personalInfo);
                SharedPreferences.Editor editor = sh.edit() ;
                editor.putString(Config.sharedPreferences_personal_info, json) ; //存入json串
                editor.commit() ; //提交


         导出
             SharedPreferences sh = getSharedPreferences(Config.sharedPreferences_personal_info, Activity.MODE_PRIVATE);
        String json = sh.getString(Config.sharedPreferences_personal_info,"");
        if(json!=null&&!json.equals("")){

            Gson gson = new Gson();
            PersonalInfo personalInfo = gson.fromJson(json,PersonalInfo.class);
            if(personalInfo!=null){
                ObjectService.personalInfo = personalInfo;
            }
        }
     */

     public static void setServerIP(String ip){
         ServerIP=ip;
     }



}
