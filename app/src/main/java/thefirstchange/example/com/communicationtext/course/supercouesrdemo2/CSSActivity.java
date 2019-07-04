package thefirstchange.example.com.communicationtext.course.supercouesrdemo2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.course.adapter.ChooseCorporationAdapter;
import thefirstchange.example.com.communicationtext.course.adapter.ChooseSameCourseAdapter;
import thefirstchange.example.com.communicationtext.course.adapter.ScoreAdapter;
import thefirstchange.example.com.communicationtext.course.adapter.ZhoushuAdapter;
import thefirstchange.example.com.communicationtext.course.database.dao.StuDao;
import thefirstchange.example.com.communicationtext.course.object.Course;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.widget.dialog.WheelDialogFragment;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.DensityUtiltwo;
import thefirstchange.example.com.communicationtext.util.MyTools;


/*
    css的意思是 course score schedule
 */

public class CSSActivity extends BaseForCloseActivity implements OnClickListener{

    private static final String MY="thefirstchange.example.com.communicationtext.CSS";

    private int year;        //当前年
    private int month ;       //当前月
    private int day;         //当前日期
    private int way;         //当前周几

    private static final int REQUEST_KEBIAO = 0;       //请求课程的活动返回的值
    private static final int REQUEST_SCORE = 1;       //请求成绩的活动返回的值

    private static  String [] TITLE_DATA = {"","周一","周二","周三","周四","周五","周六","周日"};
    private static  String [] TITLE_DATE = {"","","","","","","",""};
//    private static  String [] SCH_TITLE_DATA = {"","周一","周二","周三","周四","周五","周六","周日"};
//    private static  String [] SCH_TITLE_DATE = {"","","","","","",""};
    //安排下一周 next_zhou      安排本周  ben_zhou      安排今后7天  next_seven_days
//    private String type = "next_seven_days";

    private static final int GRID_ROW_COUNT = 30;
//    private static final int SCH_GRID_ROW_COUNT = 24;
    private static final int GRID_COL_COUNT = 16;


    //String 为周几加空格加节
    private Vector<String> sectionHasFilled = new Vector<>();  //谋一节课是否已经有 课程填充上

    private Map<String,Integer> coursecolor = new HashMap<>();   //课程对应显示的颜色
    private GridLayout mGlClsTitle;                 //课程表的
    private GridLayout mGlClsContent;

//    private GridLayout mGlSchTitle;                 //值班表的
//    private GridLayout mGlSchContent;

    private StuDao stuDao;        //存储课表的sqlite数据库
//    private StuScoreDao stuScoreDao;           //存储成绩的sqlite数据库
    private int mTableDistance;   //一列的宽度

    private TextView import_edu_tv;   //从服务器请求教务处的一些功能

    int randomnum= 0;           //产生的随机数   给课程颜色分配用的

    private String UILocation = "courseUI";   //当前在哪个界面  默认刚开始在课程表界面
    private int zhoushuLocation;             //当前点击在哪个周

    private RelativeLayout mainlayout;

    private TextView css_back;
    private TextView zhou_tv;
    private TextView xueqi_tv;

    private ScoreAdapter scoreAdapter;
    private ListView scorelistview;

    private  LinearLayoutManager linearLayoutManager;
    private ZhoushuAdapter zhoushuAdapter;
    private RecyclerView zhoushulistview;
    private boolean isclickzhoushu = false;   //zhoushulistview是否正在打开

    private LinearLayout zhou_layout;
    private LinearLayout courselayout;
    private LinearLayout scorelayout;

    private ImageView courseimageview;
    private TextView coursetextview;
    private ImageView scoreimageview;
    private TextView scoretextview;


    private TopRightMenu mtopRightMenu;
    private boolean ispopupwindow= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_css);

        initdata();           //以后删掉   应该在项目启动时  就都拿出
        initview();
        initevent();
        initpopupwindow();
    }

    /*
         以后删掉   应该在项目启动时  就都拿出
     */
    public void initdata(){

        SharedPreferences sp = getSharedPreferences(Config.sharedPreferences_edu_year_grade, Context.MODE_PRIVATE);
        CourseAndScore.nowzhoushu = sp.getInt("nowzhoushu", -1);

        CourseAndScore.zhou_time = sp.getLong("zhou_time",-1);

        CourseAndScore.courseGrade = sp.getInt("courseGrade", -1);
        CourseAndScore.courseXueqi =sp.getInt("courseXueqi", -1);
        CourseAndScore.scoreGrade =sp.getInt("scoreGrade", -1);
        CourseAndScore.scoreXueqi =sp.getInt("scoreXueqi", -1);

        SharedPreferences sp2 = getSharedPreferences(Config.sharedPreferences_duty_group, Context.MODE_PRIVATE);
        CourseAndScore.duty_groupid = sp2.getInt("duty_groupid", -1);

//        boolean flag = false;
        if( CourseAndScore.nowzhoushu==-1){
            CourseAndScore.nowzhoushu = 1;
//            flag = true;
        }


        if( CourseAndScore.courseGrade==-1){
            CourseAndScore.courseGrade = 1;
//            flag = true;
        }
        if( CourseAndScore.courseXueqi==-1){
            CourseAndScore.courseXueqi = 1;
//            flag = true;
        }
        if( CourseAndScore.scoreGrade==-1){
            CourseAndScore.scoreGrade = 1;
//            flag = true;
        }
        if( CourseAndScore.scoreXueqi==-1){
            CourseAndScore.scoreXueqi = 1;
//            flag = true;
        }


    }

    //初始化的时候  计算出当前是第几周
    public void initview(){

        mainlayout = (RelativeLayout) this.findViewById(R.id.mainlayout);
        zhou_layout = (LinearLayout)this.findViewById(R.id.zhou_layout);
        css_back = (TextView)this.findViewById(R.id.css_schduty);
        zhou_tv = (TextView)this.findViewById(R.id.zhou_tv);
        xueqi_tv = (TextView)this.findViewById(R.id.xueqi_tv);
        zhou_tv.setText("第"+CourseAndScore.nowzhoushu+"周");
        if(CourseAndScore.zhou_time!=-1){


                Date now = new Date();
                Date before = new Date(CourseAndScore.zhou_time);
                long l = now.getTime()-before.getTime();

                long day=l/(24*60*60*1000);
                long hour=(l/(60*60*1000)-day*24);
                long min=((l/(60*1000))-day*24*60-hour*60);
                long s=(l/1000-day*24*60*60-hour*60*60-min*60);
    //            System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");

                    int www = MyTools.getWeekday(before);     //设定当前周的那天是周几

                    long subzhou = (www+day)/7;

                    CourseAndScore.nowzhoushu +=subzhou;
                    zhou_tv.setText("第"+CourseAndScore.nowzhoushu+"周");

                    SharedPreferences mSharedPreferences = getSharedPreferences(Config.sharedPreferences_edu_year_grade, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();

                    editor.putInt("nowzhoushu", CourseAndScore.nowzhoushu);
                    editor.putLong("zhou_time", System.currentTimeMillis());
                    editor.putInt("courseXueqi", CourseAndScore.courseXueqi);
                    editor.putInt("courseGrade", CourseAndScore.courseGrade);
                    editor.putInt("scoreXueqi", CourseAndScore.scoreXueqi);
                    editor.putInt("scoreGrade", CourseAndScore.scoreGrade);

                    editor.commit();

        }




//        String str = "";
//        if(CourseAndScore.courseGrade - PersonalInfo.ruXueYer==0){
//            str="大一";
//        }else if(CourseAndScore.courseGrade -PersonalInfo.ruXueYer==1){
//            str="大二";
//        }else if(CourseAndScore.courseGrade -PersonalInfo.ruXueYer==2){
//            str="大三";
//        }else if(CourseAndScore.courseGrade -PersonalInfo.ruXueYer==3){
//            str="大四";
//        }else if(CourseAndScore.courseGrade -PersonalInfo.ruXueYer==4){
//            str="大五";
//        }else if(CourseAndScore.courseGrade -PersonalInfo.ruXueYer==5){
//            str="大六";
//        }else if(CourseAndScore.courseGrade -PersonalInfo.ruXueYer==6){
//            str="大七";
//        }
//        xueqi_tv.setText(str+" 第"+CourseAndScore.courseXueqi +"学期");
        Calendar c = Calendar.getInstance();
        year =  c.get(Calendar.YEAR) ;  //获取当前年

        initTitile_date(CourseAndScore.nowzhoushu);

        if(CourseAndScore.courseGrade!=-1){
            change_zhou_tv_text(CourseAndScore.courseGrade,CourseAndScore.courseXueqi);
        }else{

            int xueqi = 1;
            if(month>=9&&month<=12||month>=1&&month<=2){
                xueqi = 1;
            }else{
                year--;
                xueqi = 2;
            }
            change_zhou_tv_text(year,xueqi);
        }

        courseimageview = (ImageView) this.findViewById(R.id.courseimageview);
        coursetextview = (TextView) this.findViewById(R.id.coursetextview);
       scoreimageview = (ImageView) this.findViewById(R.id.scoreimageview);
        scoretextview = (TextView) this.findViewById(R.id.scoretextview);


        mGlClsTitle = this.findViewById(R.id.main_grid_title);
        mGlClsContent = this.findViewById(R.id.main_grid_content);
//        mGlSchTitle = this.findViewById(R.id.schedule_grid_title);
//        mGlSchContent = this.findViewById(R.id.schedule_grid_content);

        import_edu_tv = this.findViewById(R.id.import_edu_tv);
        mTableDistance = getScreenPixelWidth()/15;
        stuDao = StuDao.getInstance(this);
        courselayout = (LinearLayout)this.findViewById(R.id.course_layout) ;
        scorelayout = (LinearLayout)this.findViewById(R.id.score_layout) ;

        setUpClsTitle();
        setUpClsContent();


        Random random = new Random();
        randomnum = random.nextInt(20);

        this.stuDao = StuDao.getInstance(this);
        processLogin();



        scoreAdapter = new ScoreAdapter(CSSActivity.this,R.layout.score_item,CourseAndScore.scores);
        scorelistview = (ListView)this.findViewById(R.id.score_listview);
        scorelistview.setAdapter(scoreAdapter);

        zhoushulistview = (RecyclerView)findViewById(R.id.zhoushu_listview);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        zhoushulistview.setLayoutManager(linearLayoutManager);
        CourseAndScore.initzhoushulist();
        zhoushuAdapter = new ZhoushuAdapter(CSSActivity.this,CourseAndScore.zhoushulist);

        zhoushuAdapter.setOnItemClickListener(new ZhoushuAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {

                if(position+1!=zhoushuLocation){
                    zhoushuLocation = position+1;
                    initTitile_date(zhoushuLocation);
                    setUpClsTitle();
                    showCls(zhoushuLocation);
                }
            }
        });

        zhoushulistview.setAdapter(zhoushuAdapter);
        MoveToPosition(linearLayoutManager,zhoushulistview,CourseAndScore.nowzhoushu-2);


    }

    public void initevent(){
        css_back.setOnClickListener(this);
        zhou_layout.setOnClickListener(this);
        import_edu_tv.setOnClickListener(this);
        courseimageview.setOnClickListener(this);
        coursetextview.setOnClickListener(this);
        scoreimageview.setOnClickListener(this);
        scoretextview.setOnClickListener(this);
//        scheduleimageview.setOnClickListener(this);
//        scheduletextview.setOnClickListener(this);


    }

    public void initTitile_date(int zhou){

        Calendar c = Calendar.getInstance();
        month =  c.get(Calendar.MONTH)+1;// 获取当前月
        day = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
        way = c.get(Calendar.DAY_OF_WEEK)-1;// 获取当前日期的星期
        if(way==0){
            way = 7;
        }


        Date d=new Date();

        SimpleDateFormat df1=new SimpleDateFormat("MM");
        long dd =24 * 60 * 60 * 1000;
        String mon = Long.parseLong(df1.format(new Date(d.getTime() - (7*(CourseAndScore.nowzhoushu-zhou)+way-1) * dd)))+"";
        TITLE_DATA[0] = mon+"";

        SimpleDateFormat df=new SimpleDateFormat("dd");
        TITLE_DATE[way-1] =  Long.parseLong(df.format(new Date(d.getTime() + 7*(zhou-CourseAndScore.nowzhoushu) * dd)))+"";;
        for(int i=1;i<=way-1;++i){
            TITLE_DATE[i-1] =  Long.parseLong(df.format(new Date(d.getTime() - (7*(CourseAndScore.nowzhoushu-zhou)+way-i) * dd)))+"";
        }
        for(int i=way+1;i<=7;++i){
            TITLE_DATE[i-1] =  Long.parseLong(df.format(new Date(d.getTime() + (7*(zhou-CourseAndScore.nowzhoushu)+i-way) * dd)))+"";

        }

    }

    public void change_zhou_tv_text(int grade,int xueqi){

        String str = "";
        if(grade - ObjectService.personalInfo.getRuxueyear()==0){
            str="大一";
        }else if(grade - ObjectService.personalInfo.getRuxueyear()==1){
            str="大二";
        }else if(grade -ObjectService.personalInfo.getRuxueyear()==2){
            str="大三";
        }else if(grade -ObjectService.personalInfo.getRuxueyear()==3){
            str="大四";
        }else if(grade - ObjectService.personalInfo.getRuxueyear()==4){
            str="大五";
        }else if(grade -ObjectService.personalInfo.getRuxueyear()==5){
            str="大六";
        }else if(grade -ObjectService.personalInfo.getRuxueyear()==6){
            str="大七";
        }
        xueqi_tv.setText(str+" 第"+xueqi +"学期");

    }

    public void initpopupwindow(){

        mtopRightMenu = new TopRightMenu(CSSActivity.this);

        //添加菜单项
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(R.drawable.course_press, "更改当前周"));
        menuItems.add(new MenuItem(R.drawable.course_press, "更换课程表"));
        menuItems.add(new MenuItem(R.drawable.course_press, "查询成绩"));
        menuItems.add(new MenuItem(R.drawable.course_press, "查询空教室"));

        mtopRightMenu
                .setHeight(DensityUtiltwo.dp2px(this, 182))
                .setWidth(DensityUtiltwo.dp2px(this, 140))
                .showIcon(true)     //显示菜单图标，默认为true
                .dimBackground(true)        //背景变暗，默认为true
                .needAnimationStyle(true)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)
                .addMenuList(menuItems)

                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        ispopupwindow = true;
                        switch (position){

                            case 0:                                //更改当前周
                                Bundle bundle = new Bundle();
                                bundle.putBoolean(WheelDialogFragment.DIALOG_BACK, false);
                                bundle.putBoolean(WheelDialogFragment.DIALOG_CANCELABLE, false);
                                bundle.putBoolean(WheelDialogFragment.DIALOG_CANCELABLE_TOUCH_OUT_SIDE, false);
                                bundle.putString(WheelDialogFragment.DIALOG_LEFT, "取消");
                                bundle.putString(WheelDialogFragment.DIALOG_RIGHT, "确定");
                                //bundle.putStringArray(WheelDialogFragment.DIALOG_WHEEL, ResUtil.getStringArray(R.array.main_home_menu));
                                String[] zhoustrings = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27"};
                                bundle.putStringArray(WheelDialogFragment.DIALOG_WHEEL,zhoustrings);

                                WheelDialogFragment dialogFragment = WheelDialogFragment.newInstance(WheelDialogFragment.class, bundle);

                                dialogFragment.setWheelDialogListener(new WheelDialogFragment.OnWheelDialogListener() {
                                    @Override
                                    public void onClickLeft(DialogFragment dialog, String value) {
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onClickRight(DialogFragment dialog, String value) {
                                        dialog.dismiss();
                                        int i = Integer.parseInt(value);
                                       if(zhoushuLocation!=i){

                                           for(int j=0;j<CourseAndScore.zhoushulist.size();++j){

                                               CourseAndScore.zhoushulist.get(j).setBenzhou(false);

                                               if(CourseAndScore.zhoushulist.get(j).getZhou()==i){
                                                   CourseAndScore.zhoushulist.get(j).setBenzhou(true);
                                               }
                                           }

                                           CourseAndScore.nowzhoushu = i;
                                           zhoushuLocation=i;
                                           zhou_tv.setText("第"+CourseAndScore.nowzhoushu+"周");

                                           isclickzhoushu = false;
                                           zhoushuAdapter.refresh();
                                           zhoushulistview.setVisibility(View.GONE);
                                           showCls(zhoushuLocation);


                                          CourseAndScore.zhou_time = System.currentTimeMillis();

                                           SharedPreferences mSharedPreferences = getSharedPreferences(Config.sharedPreferences_edu_year_grade, Context.MODE_PRIVATE);
                                           SharedPreferences.Editor editor = mSharedPreferences.edit();

                                           editor.putInt("nowzhoushu", i);
                                           editor.putLong("zhou_time",CourseAndScore.zhou_time );

                                           editor.putInt("courseXueqi", CourseAndScore.courseXueqi);
                                           editor.putInt("courseGrade", CourseAndScore.courseGrade);
                                           editor.putInt("scoreXueqi", CourseAndScore.scoreXueqi);
                                           editor.putInt("scoreGrade", CourseAndScore.scoreGrade);



                                           editor.commit();

                                       }
                                    }

                                    @Override
                                    public void onValueChanged(DialogFragment dialog, String value) {

                                    }
                                });

                                dialogFragment.show(getSupportFragmentManager(), "");

                                break;
                            case 1:             //更换课程表

                                Intent intent = new Intent(getApplication(),RequestKebiaoOrScoreActivity.class);
                                intent.putExtra("type","getKBByPh");
                                startActivity(intent);
                                break;
                            case 2:               //查询成绩
                                Intent intent2 = new Intent(getApplication(),RequestKebiaoOrScoreActivity.class);
                                intent2.putExtra("type","getscoByPh");
                                startActivity(intent2);
                                break;

                            case 3:     //查询空教室

                                Intent intent3 = new Intent(getApplication(),SearchEmptyClassroomActivity.class);
                                startActivity(intent3);

                                break;
                                default:

                                    break;

                        }

                        }
                });

    }

    //设置表格显示星期的地方
    private void setUpClsTitle(){
        mGlClsTitle.removeAllViews();
        for (int i=0; i<TITLE_DATA.length; ++i){
            String content = TITLE_DATA[i];
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            //第一列的时候
            if (i == 0){
                params.width = mTableDistance;
            }
            else {
                //添加分割线
               // View divider = getLayoutInflater().inflate(R.layout.grid_title_form,mGlClsTitle,false);
               // mGlClsTitle.addView(divider);
                params.width = mTableDistance * 2;
            }
            params.height = GridLayout.LayoutParams.MATCH_PARENT;
            TextView textView = new TextView(this);
            textView.setTextColor(getResources().getColor(R.color.grey31));
            if(i==0){
                textView.setText(content+"\n"+"月");
            }else{
                textView.setText(content+"\n"+TITLE_DATE[i-1]);
            }

            textView.setGravity(Gravity.CENTER);
            mGlClsTitle.addView(textView,params);
        }
    }

    //初始化课表显示的格子    最左边的第几节课
    private void setUpClsContent(){
        //设置每行第几节课的提示
        for(int i=0; i<GRID_ROW_COUNT; ++i){
            int row = i;
            int col = 0;
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(row),GridLayout.spec(col)
            );
            params.width = mTableDistance;
            if (i == 0){
                params.height = 0;
               // params.height = (int) getResources().getDimension(R.dimen.table_row_height3);
                TextView textView = new TextView(this);
                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundColor(this.getResources().getColor(R.color.WhiteSmoke));
                mGlClsContent.addView(textView,params);
            }
            else {
                if(i%2!=0){
                    params.height = (int) getResources().getDimension(R.dimen.course_table_row_height);
                    TextView textView = new TextView(this);
                    textView.setTextColor(getResources().getColor(R.color.grey31));
                    textView.setText((i+1)/2+"");
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(this.getResources().getColor(R.color.WhiteSmoke));
                    mGlClsContent.addView(textView,params);
                }else{
                    params.height = (int) getResources().getDimension(R.dimen.course_table_row_height3);
                    TextView textView = new TextView(this);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(this.getResources().getColor(R.color.WhiteSmoke));
                    mGlClsContent.addView(textView,params);
                }

            }

        }
        //初始化表格列的距离
        for (int i=1; i<GRID_COL_COUNT; ++i){
            int row = 0;
            int col = i;
            if(i%2==0){

                GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                        GridLayout.spec(row),GridLayout.spec(col)
                );
                params.width = 4;
                params.height = 0;

                View view = new View(this);
                view.setBackgroundColor(getResources().getColor(R.color.white));
                mGlClsContent.addView(view,params);

            }else{

                GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                        GridLayout.spec(row),GridLayout.spec(col)
                );
                params.width = (mTableDistance-2)*2;
                params.height = 0;

                View view = new View(this);
                mGlClsContent.addView(view,params);

            }

        }
    }


    //显示课表
    private void showCls(int zhou){

        this.sectionHasFilled.clear();
//        int a = CourseAndScore.courseSameTime.size();
        int danshuangzhou = 0;      //0是都上   1是单周  2是双周
        for (int i = 0; i< CourseAndScore.courses.size(); ++i){
            final Course course = CourseAndScore.courses.get(i);
            danshuangzhou = 0;
            String time1 = course.getCT1();

            String[] strs = time1.split("周");
            boolean flag = false;
            for(int j=0;j<strs.length;++j){
                String string = strs[j];
                Vector<Integer> nums = MyTools.getIntFromString(string);
                if(nums.size()==1){
                    if(nums.get(0)==zhou){
                        flag = true;
                        break;
                    }
                }else if(nums.size()==2){
                    int num1=nums.get(0);
                    int num2=nums.get(1);
                    if(zhou>=num1&&zhou<=num2||zhou>=num2&&zhou<=num1){
                        flag=true;
                        break;
                    }
                }
            }
            //该课  这周不上  那么就将他处理为与该周的单双周情况冲突就行
            if(!flag){
                if(zhou%2==0){
                    danshuangzhou = 1;
                }else{
                    danshuangzhou = 2;
                }
            }else {
                if(time1.contains("双")){
                    danshuangzhou = 2;
                }else if(time1.contains("单")){
                    danshuangzhou = 1;
                }
            }


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
            int size= 0;               //这个课占了几节课
            int row1 = 1;             //下面用row1就行了  因为有size在  自动写占据几行
            int row2 = 1;
            //担心某种课 就占据一节课
            if(strings.length==1) {
                size=1;
                row1 = Integer.parseInt(strings[0]);
            }else{
                row1 = Integer.parseInt(strings[0]);
                row2 = Integer.parseInt(strings[1]);
                size = (row2-row1+1)*2-1;
            }

            final String key = mcol+" "+row1;

            //已经本周有能上的课填充在这了
            if(this.sectionHasFilled.contains(key)){
                continue;
            }

            //设定View在表格的哪行那列
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec((row1)*2-1,size),
                    GridLayout.spec(col)
            );

            //设置View的宽高
            params.width = (mTableDistance)*2;
           // params.height = (int) getResources().getDimension(R.dimen.table_row_height) * size;
            params.setGravity(Gravity.FILL);

            int colornum = 1;
            String coursename = course.getCN();
            if(coursecolor.containsKey(coursename)){
                colornum = coursecolor.get(coursename);
            }else{
                ++randomnum;
                coursecolor.put(coursename,randomnum);
                colornum = randomnum;
            }


            //通过代码改变<Shape>的背景颜色
            GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.cls_bg);
            drawable.setColor(getCourseColor(colornum));        //里面是颜色
            //假设该周是第4周    该课本周不上时  用这个颜色
            if(danshuangzhou!=0 && zhou%2!=danshuangzhou%2){
                drawable.setColor(getResources().getColor(R.color.gray91));
            }else{
                this.sectionHasFilled.add(key);
            }
            //设置View
            TextView textView = new TextView(this);
           // textView.setGravity(Gravity.CENTER);
            textView.setPadding(5,9,5,9);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setTextSize(13);
            textView.setText(course.getCN()+"\n"+"@"+course.getCP());
            textView.setBackground(drawable);
            final int courseindex = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {


                    if( CourseAndScore.courseSameTime.get(key).size()<=1){
                        Intent intent = new Intent(CSSActivity.this,CourseDetailActivity.class);
                        intent.putExtra("index",courseindex);   //传课程在课程集合中的下标
                        startActivity(intent);
                    }else{
                      Vector<Integer> samecourses = CourseAndScore.courseSameTime.get(key);
                      int index = -1;
                      int num = 0;
                      for(int i=0;i<samecourses.size();++i){
                          if(samecourses.get(i)==courseindex){
                              index = i;
                              break;
                          }
                      }
                        samecourses.remove(index);
                      samecourses.add(0,courseindex);
                      showSameTimeCourseDialog(samecourses);
                    }



                }
            });
            //添加到表格中
            mGlClsContent.addView(textView,params);

        }
    }



    //获取屏幕宽
    public int getScreenPixelWidth(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public int getScreenPixelHeight(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.import_edu_tv:

//                Intent intent = new Intent(this,RequestKebiaoOrScoreActivity.class);
//                startActivityForResult(intent,REQUEST_REQUEST_KEBIAO);
                //mtopRightMenu .showAsDropDown(import_edu_tv, -225, 0);    //带偏移量
                mtopRightMenu .showAsDropDown(import_edu_tv, -DensityUtiltwo.dp2px(this, 100), 0);    //带偏移量
                break;

            case R.id.courseimageview:
                intoCourseUI();
                break;

            case R.id.coursetextview:
                intoCourseUI();
                break;

            case R.id.scoreimageview:
               intoScoreUI();
                break;

            case R.id.scoretextview:
                intoScoreUI();
                break;



            case R.id.zhou_layout:
                if(UILocation.equals("courseUI")){
                    if(isclickzhoushu){
                        if(zhoushuLocation!=CourseAndScore.nowzhoushu){
                            zhoushuLocation=CourseAndScore.nowzhoushu;
                            initTitile_date(zhoushuLocation);
                            setUpClsTitle();
                            showCls(zhoushuLocation);

                        }
                        isclickzhoushu = false;
                        zhoushuAdapter.refresh();
                        zhoushulistview.setVisibility(View.GONE);

                    }else{
                        isclickzhoushu=true;
                        zhoushuAdapter = new ZhoushuAdapter(CSSActivity.this,CourseAndScore.zhoushulist);

                        zhoushuAdapter.setOnItemClickListener(new ZhoushuAdapter.ItemClickListener() {
                            @Override
                            public void onItemClick(int position) {

                                if(position+1!=zhoushuLocation){
                                    zhoushuLocation = position+1;
                                    initTitile_date(zhoushuLocation);
                                    setUpClsTitle();
                                    showCls(zhoushuLocation);
                                }
                            }
                        });

                        zhoushulistview.setAdapter(zhoushuAdapter);
                        MoveToPosition(linearLayoutManager,zhoushulistview,CourseAndScore.nowzhoushu-2);

                        zhoushulistview.setVisibility(View.VISIBLE);
                    }
                }else if(UILocation.equals("scheduleUI")){
                    if(CourseAndScore.groupDuty!=null&&CourseAndScore.groupDuty.size()!=0){
                        showCorporationDialog(CourseAndScore.groupDuty);
                    }

                }

                break;

            case R.id.css_schduty:

                if(UILocation.equals("scheduleUI")){
                    TextView title = new TextView(CSSActivity.this);
                    title.setText("选择值班方式");
                    title.setPadding(10, 10, 10, 10);
                    title.setGravity(Gravity.CENTER);
                    // title.setTextColor(getResources().getColor(R.color.greenBG));
                    title.setTextSize(21);
                    title.setTextColor(getResources().getColor(R.color.grey31));

                    AlertDialog.Builder builder = new AlertDialog.Builder(CSSActivity.this,R.style.AlertDialog);
                    builder.setCustomTitle(title);
                    //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                    View vv = LayoutInflater.from(CSSActivity.this).inflate(R.layout.dialog_choose_schduty_way, null);
                    //    设置我们自己定义的布局文件作为弹出框的Content
                    builder.setView(vv);


                   final AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_corner);
                    //设置大小
                    WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                    layoutParams.width = DensityUtiltwo.dp2px(this, 280);
                    layoutParams.height = DensityUtiltwo.dp2px(this, 181);
                    dialog.getWindow().setAttributes(layoutParams);

//                final TextView sch_year_TV = (TextView) dialog.getWindow().findViewById(R.id.sch_year_TV);
//                final TextView sch_xq_TV = (TextView)dialog.getWindow().findViewById(R.id.sch_xq_TV);


                    //安排下一周
                    dialog.getWindow().findViewById(R.id.dialog_choose_schduty_way1).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String type = "next_zhou";
                            Intent intent = new Intent(CSSActivity.this,ScheduleArrangementActivity.class);
                           intent.putExtra("type",type);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });

                    //安排本周
                    dialog.getWindow().findViewById(R.id.dialog_choose_schduty_way2).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String type = "ben_zhou";
                            Intent intent = new Intent(CSSActivity.this,ScheduleArrangementActivity.class);
                            intent.putExtra("type",type);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });

                    //安排今后7天
                    dialog.getWindow().findViewById(R.id.dialog_choose_schduty_way3).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String type = "next_seven_days";
                            Intent intent = new Intent(CSSActivity.this,ScheduleArrangementActivity.class);
                            intent.putExtra("type",type);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });

                }else{
                    finish();
                }
                break;

        }

    }

    //进入查成绩的页面
    private void intoScoreUI(){
        if(UILocation.equals("scoreUI")){
            return;
        }
//                if(isclickzhoushu){
//                    if(zhoushuLocation!=CourseAndScore.nowzhoushu){
//                        zhoushuLocation=CourseAndScore.nowzhoushu;
//                        showCls(zhoushuLocation);
//                    }
//                    isclickzhoushu = false;
//                    zhoushuAdapter.refresh();
//                    zhoushulistview.setVisibility(View.GONE);
//
//                }
        UILocation="scoreUI";
        zhou_tv.setText("第"+CourseAndScore.nowzhoushu+"周");
        css_back.setText("返回");
        if(CourseAndScore.scoreGrade!=-1){
            change_zhou_tv_text(CourseAndScore.scoreGrade,CourseAndScore.scoreXueqi);
        }else{
            Calendar c = Calendar.getInstance();
            int year =  c.get(Calendar.YEAR) ;// 获取当前年
            int month =  c.get(Calendar.MONTH) ;// 获取当前月
            int xueqi = 1;
            if(month>=9&&month<=12||month>=1&&month<=2){
                xueqi = 1;
            }else{
                year--;
                xueqi = 2;
            }
            change_zhou_tv_text(year,xueqi);
        }
        zhou_layout.setClickable(false);
        if(isclickzhoushu){
            isclickzhoushu = false;
            zhoushuAdapter.refresh();
            zhoushuLocation=CourseAndScore.nowzhoushu;
            zhoushulistview.setVisibility(View.GONE);
        }
        courseimageview.setImageResource(R.drawable.course_normal);
        coursetextview.setTextColor(getResources().getColor(R.color.grey31));

        scoreimageview.setImageResource(R.drawable.score_press);
        scoretextview.setTextColor(getResources().getColor(R.color.DeepSkyBlue1));
        courselayout.setVisibility(View.GONE);
        scorelayout.setVisibility(View.VISIBLE);
    }

    //进入课程表的页面
    private void intoCourseUI(){
        if(UILocation.equals("courseUI")){
            return;
        }
        UILocation="courseUI";
        zhou_tv.setText("第"+CourseAndScore.nowzhoushu+"周");
        css_back.setText("返回");
        zhou_layout.setClickable(true);

        if(CourseAndScore.courseGrade!=-1){
            change_zhou_tv_text(CourseAndScore.courseGrade,CourseAndScore.courseXueqi);
        }else{
            Calendar c = Calendar.getInstance();
            int year =  c.get(Calendar.YEAR) ;// 获取当前年
            int month =  c.get(Calendar.MONTH) ;// 获取当前月
            int xueqi = 1;
            if(month>=9&&month<=12||month>=1&&month<=2){
                xueqi = 1;
            }else{
                year--;
                xueqi = 2;
            }
            change_zhou_tv_text(year,xueqi);
        }

        courseimageview.setImageResource(R.drawable.course_press);
        coursetextview.setTextColor(getResources().getColor(R.color.DeepSkyBlue1));

        scoreimageview.setImageResource(R.drawable.score_normal);
        scoretextview.setTextColor(getResources().getColor(R.color.grey31));

        scorelayout.setVisibility(View.GONE);
        courselayout.setVisibility(View.VISIBLE);
    }



    //开启活动  加载完控件后   就从数据库导入课表
    protected void processLogin() {

        SharedPreferences sp = getSharedPreferences(Config.sharedPreferences_course_ph, Context.MODE_PRIVATE);
        String a = sp.getString("course_ph","");
        SharedPreferences sp2 = getSharedPreferences(Config.sharedPreferences_score_ph, Context.MODE_PRIVATE);
        String b = sp2.getString("score_ph","");

        if(a!=null&&ObjectService.personalInfo.getPhonenumber().equals(a)){
            //首先从数据库获取值
            CourseAndScore.courses = stuDao.getStuCouList();
            CourseAndScore.initcourseSameTime();
//        int a =  CourseAndScore.courseSameTime.size();
            showCls(CourseAndScore.nowzhoushu);
        }

        if(b!=null&&ObjectService.personalInfo.getPhonenumber().equals(b)){
            CourseAndScore.scores = stuDao.getStuScoList();
        }



//        CourseAndScore.clientArrangements = stuDao.getStuSchList();

//        for(int i=0;i<CourseAndScore.clientArrangements.size();++i){
//            if(!CourseAndScore.groupDuty.contains(CourseAndScore.clientArrangements.get(i).groupid)){
//                CourseAndScore.groupDuty.add(CourseAndScore.clientArrangements.get(i).groupid);
//            }
//        }

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.e("aaa","66666666666666666666666666666666666666666666661");
//        //销毁时候将数据添加到数据库中
//        mStuCourseDao.saveStuCouList(courses);
//    }


    //参数为 课程通过随机算法获得的颜色哈希值   该课的单双周情况
    public int getCourseColor( int rs){

     /*
   <!--  课表的颜色  -->
    <color name="Turquoise1">#00F5FF</color>
    <color name="Green3">#00CD00</color>
    <color name="MediumPurple">#9370DB</color>
    <color name="RoyalBlue">#4169E1</color>
    <color name="HotPink">#FF69B4</color>

    <!--某课不在本周上时  用这个-->
    <color name="Ivory2">#EEEEE0</color>
    */



     rs = rs%6;

        if(rs==0){
            return getResources().getColor(R.color.Turquoise1);
        }else if(rs==1){
            return getResources().getColor(R.color.Green2);
        }else if(rs==2){
            return getResources().getColor(R.color.MediumPurple);
        }else if(rs==3){
            return getResources().getColor(R.color.MediumTurquoise);
        }else{
            return getResources().getColor(R.color.HotPink);
        }

    }

    /*
        选择周数时  默认选中当前周-2
     */
    public static void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(0);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(0);
        }
    }


    protected void onResume(){
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(MY);
        registerReceiver(MyMessageReceiver, filter);

    }


    protected void onDestroy(){

        super.onDestroy();
        unregisterReceiver(MyMessageReceiver);
    }
    BroadcastReceiver MyMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MY)){
                String type = intent.getStringExtra("type");

                if(type.equals("getscoRs")){              //获得教务成绩

                    CourseAndScore.scoreph = ObjectService.personalInfo.getPhonenumber();

                    scoreAdapter = new ScoreAdapter(CSSActivity.this,R.layout.score_item,CourseAndScore.scores);
                    scorelistview.setAdapter(scoreAdapter);

                    intoScoreUI();
                    change_zhou_tv_text(CourseAndScore.scoreGrade,CourseAndScore.scoreXueqi);


                    new Thread(){

                        public void run(){
                            SharedPreferences mSharedPreferences = getSharedPreferences(Config.sharedPreferences_edu_year_grade, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = mSharedPreferences.edit();

                            editor.putInt("nowzhoushu", CourseAndScore.nowzhoushu);

                            editor.putLong("zhou_time", CourseAndScore.zhou_time);

                            editor.putInt("courseXueqi", CourseAndScore.courseXueqi);
                            editor.putInt("courseGrade", CourseAndScore.courseGrade);
                            editor.putInt("scoreXueqi", CourseAndScore.scoreXueqi);
                            editor.putInt("scoreGrade", CourseAndScore.scoreGrade);
                            editor.commit();

                            SharedPreferences mSharedPreferences2 = getSharedPreferences(Config.sharedPreferences_score_ph, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = mSharedPreferences2.edit();
                            editor2.putString("score_ph",ObjectService.personalInfo.getPhonenumber());
                            editor2.commit();

                            stuDao.removeAllScore();
                            stuDao.saveStuScoList(CourseAndScore.scores);
                        }

                    }.start();


                }else if(type.equals("getkBRs")){                //获得教务课表

                    CourseAndScore.courseph = ObjectService.personalInfo.getPhonenumber();
                    CourseAndScore.initcourseSameTime();
                    mGlClsContent.removeAllViews();
                    setUpClsContent();

                    showCls(CourseAndScore.nowzhoushu);

                    intoCourseUI();

                    change_zhou_tv_text(CourseAndScore.courseGrade,CourseAndScore.courseXueqi);


                    new Thread(){

                        public void run(){
                            SharedPreferences mSharedPreferences = getSharedPreferences(Config.sharedPreferences_edu_year_grade, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = mSharedPreferences.edit();

                            editor.putInt("nowzhoushu", CourseAndScore.nowzhoushu);
                            editor.putLong("zhou_time", CourseAndScore.zhou_time);
                            editor.putInt("courseXueqi", CourseAndScore.courseXueqi);
                            editor.putInt("courseGrade", CourseAndScore.courseGrade);
                            editor.putInt("scoreXueqi", CourseAndScore.scoreXueqi);
                            editor.putInt("scoreGrade", CourseAndScore.scoreGrade);
                            editor.commit();


                            SharedPreferences mSharedPreferences2 = getSharedPreferences(Config.sharedPreferences_course_ph, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = mSharedPreferences2.edit();
                            editor2.putString("course_ph",ObjectService.personalInfo.getPhonenumber());
                            editor2.commit();


                            stuDao.removeAllCourse();
                            stuDao.saveStuCouList(CourseAndScore.courses);
                        }

                    }.start();
                }

            }

        }
    };


    private void showSameTimeCourseDialog( Vector<Integer> list) {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_choose_course, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtiltwo.dp2px(this, 20f);
        params.height=getResources().getDisplayMetrics().heightPixels - DensityUtiltwo.dp2px(this, 400f);
        params.bottomMargin = DensityUtiltwo.dp2px(this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        RecyclerView recyclerView=(RecyclerView)contentView.findViewById(R.id.sametime_course_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ChooseSameCourseAdapter chooseCourseAdapter=new ChooseSameCourseAdapter(this,list,bottomDialog);
        recyclerView.setAdapter(chooseCourseAdapter);

    }

    private void showCorporationDialog( Vector<Integer> list) {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_choose_corporation, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtiltwo.dp2px(this, 20f);
        params.height=getResources().getDisplayMetrics().heightPixels - DensityUtiltwo.dp2px(this, 400f);
        params.bottomMargin = DensityUtiltwo.dp2px(this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        RecyclerView recyclerView=(RecyclerView)contentView.findViewById(R.id.corporation_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ChooseCorporationAdapter chooseCourseAdapter=new ChooseCorporationAdapter(this,list,bottomDialog);
        recyclerView.setAdapter(chooseCourseAdapter);

    }


}
