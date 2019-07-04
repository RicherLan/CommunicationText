package thefirstchange.example.com.communicationtext.course.supercouesrdemo2;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.schedulearrangement.RequestSchedule;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.DensityUtil;
import thefirstchange.example.com.communicationtext.util.DensityUtiltwo;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.MyTools;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;



/*
    自定义排班的活动
 */

public class ScheduleArrangementActivity extends BaseForCloseActivity implements View.OnClickListener{

    //安排下一周 next_zhou      安排本周  ben_zhou      安排今后7天  next_seven_days
    private String type = "next_zhou";

    private static final String MY="thefirstchange.example.com.communicationtext.SCHEDULEARRANGEMENT";
    private Timer timer = new Timer();


    int year;
    int xq;

    int yeartemp;
    int xqtemp;

    int groupid;
    private int corpyear;
    private int corpxueqi;


    private Vector<RequestSchedule> requestSchedules = new Vector<>();

    private  int month;   //现在的月
    private  int day;     //现在的日期
    private  int way;     //现在的星期
    private  int hour;    //现在的小时
    private  int minute;  // 现在的分钟

    private  Vector<Integer>  noSchedule = new Vector<>();   //不能排班的  时间已经过了

    private  String [] TITLE_DATA = {"","","","","","","",""};
    private  String [] TITLE_DATE = {"","","","","","","",""};
    private  final int GRID_ROW_COUNT = 24;
    private  final int GRID_COL_COUNT = 16;

    private LinearLayout duty_year_xq_layout;
    private TextView schhome_year_TV;
    private TextView schhome_xq_TV;
    private TextView schhome_zhou_tv;

    private GridLayout mGlTitle;
    private GridLayout mGlContent;

    private int mTableDistance;   //一列的宽度

    private TextView schedule_back_TV;
    private TextView ascedule_ok_TV;
    //下面的修改值班人数的控件
    private TextView  ganshinum_TV;
    private TextView  buzhangnum_TV;

    boolean iswrong = false;
    private int zhou;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedulearrangement);
        Intent intent = getIntent();
        groupid = intent.getIntExtra("groupid",-1);
        zhou=intent.getIntExtra("zhou",-1);
        if(groupid==-1||zhou==-1){
            iswrong=true;
            finish();
        }

        this.type = intent.getStringExtra("type");
        if(this.type==null){
            type = "next_zhou";
        }
        getDate();
        init();
        initevent();
    }

    public void getDate(){

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month =  c.get(Calendar.MONTH) + 1;// 获取当前月份

        year= StaticAllList.groupList.get(groupid).year;
        xq=StaticAllList.groupList.get(groupid).xueqi;

        yeartemp = year;
        xqtemp = xq;
        day = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
        way = c.get(Calendar.DAY_OF_WEEK)-1;// 获取当前日期的星期
        if(way==0){
            way = 7;
        }
        hour = c.get(Calendar.HOUR_OF_DAY);//时
        minute = c.get(Calendar.MINUTE);//分

        TITLE_DATA[0] = month+"";

        if(type.equals("next_zhou")){       //安排下周
            Date d=new Date();
            SimpleDateFormat df1=new SimpleDateFormat("dd");
            SimpleDateFormat df2=new SimpleDateFormat("MM");
            int sub = 7-way+1;
            long dd =24 * 60 * 60 * 1000;
            Long mmm = Long.parseLong(df2.format(new Date(d.getTime() + (sub)*dd)));
            TITLE_DATA[0] = mmm+"";
            for(int i=1;i<=7;++i){

                Long ddd = Long.parseLong(df1.format(new Date(d.getTime() + (sub+i-1)* dd)));
                TITLE_DATE[i] = ddd+"";
            }
            TITLE_DATA[1]="周一";
            TITLE_DATA[2]="周二";
            TITLE_DATA[3]="周三";
            TITLE_DATA[4]="周四";
            TITLE_DATA[5]="周五";
            TITLE_DATA[6]="周六";
            TITLE_DATA[7]="周日";

        }else {

            int x = 1;
            for(int i=way;i<way+7;++i){
                int weekday = (i)%7;
                if(weekday==1){
                    String str = "周一";
                    TITLE_DATA[x++] = str;
                }else if(weekday==2){
                    String str = "周二";
                    TITLE_DATA[x++] = str;
                }else if(weekday==3){
                    String str = "周三";
                    TITLE_DATA[x++] = str;
                }else if(weekday==4){
                    String str = "周四";
                    TITLE_DATA[x++] = str;
                }else if(weekday==5){
                    String str = "周五";
                    TITLE_DATA[x++] = str;
                }else if(weekday==6){
                    String str = "周六";
                    TITLE_DATA[x++] = str;
                }else if(weekday==0){
                    String str = "周日";
                    TITLE_DATA[x++] = str;
                }
            }

            if(hour>=8){
                if(hour<10){
                    noSchedule.add(1);
                }else if(hour<14){
                    noSchedule.add(1);
                    noSchedule.add(5);
                }else if(hour<16){
                    noSchedule.add(1);
                    noSchedule.add(5);
                    noSchedule.add(9);
                }else if(hour<19){
                    noSchedule.add(1);
                    noSchedule.add(5);
                    noSchedule.add(9);
                    noSchedule.add(13);
                }else if(hour<21){
                    noSchedule.add(1);
                    noSchedule.add(5);
                    noSchedule.add(9);
                    noSchedule.add(13);
                    noSchedule.add(17);
                }else if(hour<23){
                    noSchedule.add(1);
                    noSchedule.add(5);
                    noSchedule.add(9);
                    noSchedule.add(13);
                    noSchedule.add(17);
                    noSchedule.add(21);
                }
            }

            Date d=new Date();
            SimpleDateFormat df1=new SimpleDateFormat("dd");
            for(int i=1;i<=7;++i){

                long dd =24 * 60 * 60 * 1000;
                Long ddd = Long.parseLong(df1.format(new Date(d.getTime() + (i-1)* dd)));

                TITLE_DATE[i] = ddd+"";
            }

            if(type.equals("ben_zhou")){    //安排本周



            }else if(type.equals("next_seven_days")){  //安排今后7天


            }
        }


    }

    public void init(){

        duty_year_xq_layout = (LinearLayout)findViewById(R.id.duty_year_xq_layout);
        schhome_year_TV = (TextView) findViewById(R.id.schhome_year_TV);
        schhome_xq_TV = (TextView) findViewById(R.id.schhome_xq_tv);
        schhome_zhou_tv = (TextView)findViewById(R.id.schhome_zhou_tv);
        if(type.equals("next_zhou")){
            schhome_zhou_tv.setText(" 第"+(zhou+1)+"周");
        }else{
            schhome_zhou_tv.setText(" 第"+zhou+"周");
        }


        corpyear = StaticAllList.groupList.get(groupid).year;
        corpxueqi = StaticAllList.groupList.get(groupid).xueqi;

        schhome_year_TV.setText(corpyear+"-"+(corpyear+1)+"学年");
        schhome_xq_TV.setText("第"+corpxueqi+"学期");

        schedule_back_TV = (TextView)this.findViewById(R.id.schedule_back_TV);
        ascedule_ok_TV = (TextView) this.findViewById(R.id.ascedule_ok_TV);

        mGlTitle = (GridLayout) this.findViewById(R.id.schedulearrangement_grid_title);
        mGlContent = (GridLayout) this.findViewById(R.id.schedulearrangement_grid_content);

        mTableDistance = getScreenPixelWidth()/15;

        setUpTitle();
        setUpContent();
        show();


    }

    public void initevent(){
        duty_year_xq_layout.setOnClickListener(this);
        schedule_back_TV.setOnClickListener(this);
        ascedule_ok_TV.setOnClickListener(this);
    }

    //设置表格显示星期的地方
    private void setUpTitle(){
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
                textView.setText(content+"\n"+TITLE_DATE[i]);
            }

            textView.setGravity(Gravity.CENTER);
            mGlTitle.addView(textView,params);
        }
    }

    //初始化课表显示的格子    最左边的第几节课
    private void setUpContent(){
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
                mGlContent.addView(textView,params);
            }
            else {
                if(i%2!=0){
                    params.height = (int) getResources().getDimension(R.dimen.table_row_height);
                    TextView textView = new TextView(this);
                    textView.setTextColor(getResources().getColor(R.color.grey31));
                    textView.setText((i+1)/2+"");
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(this.getResources().getColor(R.color.WhiteSmoke));
                    mGlContent.addView(textView,params);
                }else{
                    params.height = (int) getResources().getDimension(R.dimen.table_row_height3);
                    TextView textView = new TextView(this);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(this.getResources().getColor(R.color.WhiteSmoke));
                    mGlContent.addView(textView,params);
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
                mGlContent.addView(view,params);

            }else{

                GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                        GridLayout.spec(row),GridLayout.spec(col)
                );
                params.width = (mTableDistance-2)*2;
                params.height = 0;

                View view = new View(this);
                mGlContent.addView(view,params);

            }

        }
    }

    //是否选择了一键清空
    boolean hadClear = false;
    //显示值班安排表
    private void show(){

        requestSchedules.clear();
        for (int row = 1; row<GRID_ROW_COUNT; row+=4){
            for(int j = 1;j<=7;++j){
                int col = j*2-1;
                if(row+3>30){
                    break;
                }
                //设定View在表格的哪行那列
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                        GridLayout.spec(row,3),
                        GridLayout.spec(col)
                );

                //设置View的宽高
//                params.width = (mTableDistance)*2;
                params.width = (mTableDistance-4)*2;
                // params.height = (int) getResources().getDimension(R.dimen.table_row_height) * size;
                params.setGravity(Gravity.FILL);

                //通过代码改变<Shape>的背景颜色
                GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.cls_bg);

                if(hadClear){
                    drawable.setColor(getResources().getColor(R.color.gray91));
                }
                else if(j==1&&noSchedule.contains(row)){
                    drawable.setColor(getResources().getColor(R.color.gray91));
                }else if(type.equals("ben_zhou")&&j+way-2>=7){
                    drawable.setColor(getResources().getColor(R.color.gray91));
                } else if(row>=16||TITLE_DATA[j].equals("周六")||TITLE_DATA[j].equals("周日")){
                    drawable.setColor(getResources().getColor(R.color.gray91));
                }else{
                    drawable.setColor(getScheduleColor());        //里面是颜色
                }

                //设置View
                final TextView textView = new TextView(this);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                // textView.setGravity(Gravity.CENTER);
                textView.setPadding(11,13,10,10);
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setTextSize(13);

                if(hadClear){
                    if(j==1&&noSchedule.contains(row)){

                    }else{
                        textView.setText("部长\n0人"+"\n"+"\n"+"干事\n0人");
                    }

                }
                else if(j==1&&noSchedule.contains(row)){    //空的就行

                }else if(row>=16||TITLE_DATA[j].equals("周六")||TITLE_DATA[j].equals("周日")){
                    textView.setText("部长\n0人"+"\n"+"\n"+"干事\n0人");
                }else if(type.equals("ben_zhou")&&j+way-2>=7){
                    textView.setText("部长\n0人"+"\n"+"\n"+"干事\n0人");
                } else {
                    textView.setText("部长\n0人" + "\n" + "\n" + "干事\n2人");
                }
                    RequestSchedule requestSchedule = new RequestSchedule();
                    int nowweek = zhou;
                    int nowway = (way+j-1)%7;
                    //安排下一周 next_zhou      安排本周  ben_zhou      安排今后7天  next_seven_days



                    if(type.equals("next_zhou")){
                        nowweek++;
                        nowway=j;
                    }else if(type.equals("ben_zhou")){

                    }else if(type.equals("next_seven_days")) {
                        if(j>=7-zhou+1){
                            nowweek++;
                        }
                    }

                    int nowsection = 0;
                    if(row==1){
                        nowsection=1;
                    }else if(row==5){
                        nowsection = 3;
                    }else if(row==9){
                        nowsection = 5;
                    }else if(row==13){
                        nowsection = 7;
                    }else if(row==17){
                        nowsection = 9;
                    }else if(row==21){
                        nowsection = 11;
                    }
                    requestSchedule.setWeek(nowweek);
                    requestSchedule.setWay(nowway);
                    requestSchedule.setSection(nowsection);

                if(hadClear){
                    requestSchedule.setBuzhangnum(0);
                    requestSchedule.setGanshinum(0);
                }
                else if(row>=16||TITLE_DATA[j].equals("周六")||TITLE_DATA[j].equals("周日")){
                    requestSchedule.setBuzhangnum(0);
                    requestSchedule.setGanshinum(0);
                }else if(type.equals("ben_zhou")&&j+way-2>=7){
                    requestSchedule.setBuzhangnum(0);
                    requestSchedule.setGanshinum(0);
                 } else {
                    requestSchedule.setBuzhangnum(0);
                    requestSchedule.setGanshinum(2);
                }

                    requestSchedules.add(requestSchedule);

                final int row2 = row;
                final int j2 = j;
                textView.setBackground(drawable);
                //长按 弹出功能框
                textView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                      showClearSchDialog();
                        return true;
                    }
                });
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(textView.getText().toString().trim().isEmpty()){
                            return;
                        }
                        TextView title = new TextView(ScheduleArrangementActivity.this);
                        title.setText("更改人员数量");
                        title.setPadding(10, 10, 10, 10);
                        title.setGravity(Gravity.CENTER);
                        // title.setTextColor(getResources().getColor(R.color.greenBG));
                        title.setTextSize(21);
                        title.setTextColor(getResources().getColor(R.color.DeepSkyBlue1));

                        AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleArrangementActivity.this,R.style.AlertDialog);
                        builder.setCustomTitle(title);
                        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                        View v = LayoutInflater.from(ScheduleArrangementActivity.this).inflate(R.layout.schedule_dialog, null);
                        //    设置我们自己定义的布局文件作为弹出框的Content
                        builder.setView(v);

                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                                String a = buzhangnum_TV.getText().toString();
                                int buzhangnum = 0;
                                for(int i=0;i<a.length();++i){
                                    if(MyTools.isInteger(a.charAt(i)+"")){
                                        buzhangnum = buzhangnum*10+Integer.parseInt(a.charAt(i)+"");
                                    }else{
                                        break;
                                    }
                                }

                                String b = ganshinum_TV.getText().toString();
                                int ganshinum = 0;
                                for(int i=0;i<b.length();++i){
                                    if(MyTools.isInteger(b.charAt(i)+"")){
                                        ganshinum = ganshinum*10+Integer.parseInt(b.charAt(i)+"");
                                    }else{
                                        break;
                                    }
                                }

                                String str = textView.getText().toString().trim();
                                int n1 = 0;
                                int n2 = 0;
                                int x=0;
                                boolean flag = false;
                                for(int j=0;j<str.length();++j){
                                    if(MyTools.isInteger(str.charAt(j)+"")){
                                        n1 = n1*10+Integer.parseInt(str.charAt(j)+"");
                                        flag = true;
                                    }else if(flag){
                                        x=j;
                                        break;
                                    }
                                }
                                flag = false;
                                for(int j=x+1;j<str.length();++j){
                                    if(MyTools.isInteger(str.charAt(j)+"")){
                                        n2 = n2*10+Integer.parseInt(str.charAt(j)+"");
                                        flag = true;
                                    }else if(flag){
                                        x=j;
                                        break;
                                    }
                                }
                                textView.setText("部长\n"+buzhangnum+"人"+"\n"+"\n"+"干事\n"+ganshinum+"人");
                                if(ganshinum==0&&buzhangnum==0){
                                    GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.cls_bg);
                                    drawable.setColor(getResources().getColor(R.color.gray91));
                                    textView.setBackground(drawable);
                                }else if(n1==0&&n2==0){
                                    GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.cls_bg);
                                    drawable.setColor(getScheduleColor());
                                    textView.setBackground(drawable);
                                }

                                int nowweek = zhou;
                                int nowway = (way+j2-1)%7;
                                //安排下一周 next_zhou      安排本周  ben_zhou      安排今后7天  next_seven_days
                                if(type.equals("next_zhou")){
                                    nowweek++;
                                    nowway=j2;
                                }else if(type.equals("ben_zhou")){

                                }else if(type.equals("next_seven_days")) {
                                    if(j2>=7-zhou+1){
                                        nowweek++;
                                    }
                                }

//                                int nowweek = CourseAndScore.nowzhoushu;
//                                if(j2>=7-CourseAndScore.nowzhoushu+1){
//                                    nowweek++;
//                                }
//
//                                int nowday = (way+j2-1)%7;
                                int nowsection = 0;
                                if(row2==1){
                                    nowsection=1;
                                }else if(row2==5){
                                    nowsection = 3;
                                }else if(row2==9){
                                    nowsection = 5;
                                }else if(row2==13){
                                    nowsection = 7;
                                }else if(row2==17){
                                    nowsection = 9;
                                }else if(row2==21){
                                    nowsection = 11;
                                }
                                int index = -1;
                                for(int k=0;k<requestSchedules.size();++k){
                                    int aa = nowweek;
                                    int s1 = requestSchedules.get(k).getWeek();
                                    int s2 = requestSchedules.get(k).getWay();
                                    int s3 = requestSchedules.get(k).getSection();
                                    if(requestSchedules.get(k).getWeek()==nowweek&&requestSchedules.get(k).getWay() ==nowway&&requestSchedules.get(k).getSection()==nowsection){
                                        index = k;
                                        break;
                                    }
                                }

                                /*
                                    buzhangnum和ganshinum为0的话  先不用remove
                                    要不然更改时还要再重新生成，提交时  统一删除为0的
                                 */
                                if(index!=-1){
//                                    if(buzhangnum==0&&ganshinum==0){
//                                        requestSchedules.remove(index);
//                                    }else{
                                        requestSchedules.get(index).setBuzhangnum(buzhangnum);
                                        requestSchedules.get(index).setGanshinum(ganshinum);
//                                    }

                                }

                              }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                            }
                        });

//                        builder.show();
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_corner);
                        //设置大小
                        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                        layoutParams.width = DensityUtil.dip2px(ScheduleArrangementActivity.this,300) ;
                        layoutParams.height =  DensityUtil.dip2px(ScheduleArrangementActivity.this,175);
                        dialog.getWindow().setAttributes(layoutParams);

                        buzhangnum_TV = (TextView) dialog.getWindow().findViewById(R.id.buzhangnum_TV);
                        ganshinum_TV = (TextView)dialog.getWindow().findViewById(R.id.ganshinum_TV);
                        String str = textView.getText().toString().trim();

                        int n1 = 0;
                        int n2 = 0;
                        int x=0;
                        boolean flag = false;
                        for(int j=0;j<str.length();++j){
                            if(MyTools.isInteger(str.charAt(j)+"")){
                                n1 = n1*10+Integer.parseInt(str.charAt(j)+"");
                                flag = true;
                            }else if(flag){
                                x=j;
                                break;
                            }
                        }
                        flag = false;
                        for(int j=x+1;j<str.length();++j){
                            if(MyTools.isInteger(str.charAt(j)+"")){
                                n2 = n2*10+Integer.parseInt(str.charAt(j)+"");
                                flag = true;
                            }else if(flag){
                                x=j;
                                break;
                            }
                        }

                        buzhangnum_TV.setText(n1+"人");
                        ganshinum_TV.setText(n2+"人");

                        dialog.getWindow().findViewById(R.id.sub_ganshinum_IV).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String b = ganshinum_TV.getText().toString();
                                int num2 = 0;
                                for(int i=0;i<b.length();++i){
                                    if(MyTools.isInteger(b.charAt(i)+"")){
                                        num2 = num2*10+Integer.parseInt(b.charAt(i)+"");
                                    }else{
                                        break;
                                    }
                                }
                                if(num2<=0){
                                    return;
                                }
                                num2--;
                                ganshinum_TV.setText(num2+"人");
                            }
                        });

                        dialog.getWindow().findViewById(R.id.add_ganshinum_IV).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String d = ganshinum_TV.getText().toString();
                                int num4 = 0;
                                for(int i=0;i<d.length();++i){
                                    if(MyTools.isInteger(d.charAt(i)+"")){
                                        num4 = num4*10+Integer.parseInt(d.charAt(i)+"");
                                    }else{
                                        break;
                                    }
                                }
                                if(num4>=10){
                                    return;
                                }
                                num4++;
                                ganshinum_TV.setText(num4+"人");
                            }
                        });

                        dialog.getWindow().findViewById(R.id.sub_buzhangnum_IV).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String a = buzhangnum_TV.getText().toString();
                                int num = 0;
                                for(int i=0;i<a.length();++i){
                                    if(MyTools.isInteger(a.charAt(i)+"")){
                                        num = num*10+Integer.parseInt(a.charAt(i)+"");
                                    }else{
                                        break;
                                    }
                                }
                                if(num<=0){
                                    return;
                                }
                                num--;
                                buzhangnum_TV.setText(num+"人");
                            }
                        });

                        dialog.getWindow().findViewById(R.id.add_buzhangnum_IV).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String c = buzhangnum_TV.getText().toString();
                                int num3 = 0;
                                for(int i=0;i<c.length();++i){
                                    if(MyTools.isInteger(c.charAt(i)+"")){
                                        num3 = num3*10+Integer.parseInt(c.charAt(i)+"");
                                    }else{
                                        break;
                                    }
                                }
                                if(num3>=10){
                                    return;
                                }
                                num3++;
                                buzhangnum_TV.setText(num3+"人");
                            }
                        });


                        int week=1;
                        int section=1;
                        if(TITLE_DATA[j2].equals("周一")){
                            week = 1;
                        }else  if(TITLE_DATA[j2].equals("周二")){
                            week = 2;
                        }else  if(TITLE_DATA[j2].equals("周三")){
                            week = 3;
                        }else  if(TITLE_DATA[j2].equals("周四")){
                            week = 4;
                        }else  if(TITLE_DATA[j2].equals("周五")){
                            week = 5;
                        }else  if(TITLE_DATA[j2].equals("周六")){
                            week = 6;
                        }else  if(TITLE_DATA[j2].equals("周日")){
                            week = 7;
                        }

                        if(row2==1){
                            section = 1;
                        }else if(row2==5){
                            section = 3;
                        }else if(row2==9){
                            section = 5;
                        }else if(row2==13){
                            section = 7;
                        }else if(row2==17){
                            section = 9;
                        }else if(row2==21){
                            section = 11;
                        }

                    }
                });
                //添加到表格中
                mGlContent.addView(textView,params);

            }

        }

        hadClear = false;
    }

    //一键清空
    private void showClearSchDialog(){
        final Dialog bottomDialog = new Dialog(ScheduleArrangementActivity.this, R.style.BottomDialog);
        View contentView = LayoutInflater.from((ScheduleArrangementActivity.this)).inflate(R.layout.dialog_delete_alarm, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getApplicationContext().getResources().getDisplayMetrics().widthPixels - DensityUtiltwo.dp2px(ScheduleArrangementActivity.this, 90f);
        params.bottomMargin = DensityUtiltwo.dp2px(ScheduleArrangementActivity.this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        TextView yesText=contentView.findViewById(R.id.yes_delete);
        TextView infoText=contentView.findViewById(R.id.delete_alarm_info);


        infoText.setText("是否一键清空？");

        yesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestSchedules.clear();
                hadClear = true;
                show();
                bottomDialog.dismiss();
            }
        });
        TextView noText=contentView.findViewById(R.id.no_delete);
        noText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });

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


    public int getScheduleColor(){

        Random r = new Random();
        int rs = r.nextInt(5);

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

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.schedule_back_TV:
                finish();
                break;

            case R.id.ascedule_ok_TV:

                if(!NetworkUtils.isConnected(ScheduleArrangementActivity.this)){
                    Toast.makeText(ScheduleArrangementActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

              /*  Vector<RequestSchedule> requestSchedules2 = new Vector<>();
                for(int i=0;i<requestSchedules.size();++i){
                    if(requestSchedules.get(i).getBuzhangnum()==0&&requestSchedules.get(i).getGanshinum()==0){
                        requestSchedules2.add(requestSchedules.get(i));
                    }
                }

                for(int i=0;i<requestSchedules2.size();++i){
                    requestSchedules.remove(requestSchedules2.get(i));
                }*/

                if(requestSchedules.size()==0){
                    Toast.makeText(ScheduleArrangementActivity.this,"请至少安排一节课",Toast.LENGTH_SHORT).show();
                    return;
                }

                MyDialog.showBottomLoadingDialog(ScheduleArrangementActivity.this);

//                        int account,int year,int xq,int bz,int xz,Vector<RequestSchedule> requestSchedules
                        int bz = requestSchedules.get(0).getWeek();
                        int xz = requestSchedules.get(0).getWeek();
                        //安排下一周 next_zhou      安排本周  ben_zhou      安排今后7天  next_seven_days
                        if(type.equals("next_zhou")){
                            bz = -1;
                            xz = zhou+1;
                        }else if(type.equals("ben_zhou")){
                            bz = zhou;
                            xz = -1;
                        }else if(type.equals("next_seven_days")) {
                            for (int i = 1; i < requestSchedules.size(); ++i) {
                                if (bz > requestSchedules.get(i).getWeek()) {
                                    bz = requestSchedules.get(i).getWeek();
                                }
                                if (xz < requestSchedules.get(i).getWeek()) {
                                    xz = requestSchedules.get(i).getWeek();
                                }
                            }
                        }

                        SendToServer.SA(Integer.parseInt(ObjectService.personalInfo.getPhonenumber()),year,xq,bz,xz,requestSchedules,type);



                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ScheduleArrangementActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyDialog.dismissBottomLoadingDialog();
                                Toast.makeText( ScheduleArrangementActivity.this,"服务器繁忙,请稍后重试!",Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                },120000);

//                for(int i=0;i<requestSchedules.size();++i){
//                    RequestSchedule requestSchedule = requestSchedules.get(i);
//                    Log.e("aaa","第"+requestSchedule.week+"周   周"+requestSchedule.way +"   第"+requestSchedule.section+"节"+"  部长 "+requestSchedule.buzhangnum+" 人  "+" 干事 "+requestSchedule.ganshinum+" 人\n");
//                }
                break;

            case R.id.duty_year_xq_layout:     //修改排班的学年和学期
                TextView title = new TextView(ScheduleArrangementActivity.this);
                title.setText("更改学年和学期");
                title.setPadding(10, 10, 10, 10);
                title.setGravity(Gravity.CENTER);
                // title.setTextColor(getResources().getColor(R.color.greenBG));
                title.setTextSize(21);
                title.setTextColor(getResources().getColor(R.color.DeepSkyBlue1));

                AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleArrangementActivity.this,R.style.AlertDialog);
                builder.setCustomTitle(title);
                //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                View vv = LayoutInflater.from(ScheduleArrangementActivity.this).inflate(R.layout.change_schdule_year_xq_dialog, null);
                //    设置我们自己定义的布局文件作为弹出框的Content
                builder.setView(vv);


                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    year = yeartemp;
                    xq = xqtemp;
                    schhome_year_TV.setText(year+"-"+(year+1)+"学年");
                    schhome_xq_TV.setText("第"+xq+"学期");

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

//                        builder.show();
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_corner);
                //设置大小
                WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                layoutParams.width = 800;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(layoutParams);

               final TextView sch_year_TV = (TextView) dialog.getWindow().findViewById(R.id.sch_year_TV);
               final TextView sch_xq_TV = (TextView)dialog.getWindow().findViewById(R.id.sch_xq_TV);


                sch_year_TV.setText(schhome_year_TV.getText());
                sch_xq_TV.setText(schhome_xq_TV.getText());

                dialog.getWindow().findViewById(R.id.sub_sch_year_IV).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(yeartemp>=2013){
                            yeartemp--;
                            sch_year_TV.setText(yeartemp+"-"+(yeartemp+1)+"学年");
                        }

                    }
                });

                dialog.getWindow().findViewById(R.id.add_sch_year_IV).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(yeartemp<=2026){
                            yeartemp++;
                            sch_year_TV.setText(yeartemp+"-"+(yeartemp+1)+"学年");
                        }

                    }
                });

                dialog.getWindow().findViewById(R.id.sub_sch_xq_IV).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(xqtemp>=2){
                            xqtemp--;
                            sch_xq_TV.setText("第"+xqtemp+"学期");
                        }
                    }
                });

                dialog.getWindow().findViewById(R.id.add_sch_xq_IV).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(xqtemp<=2){
                            xqtemp++;
                            sch_xq_TV.setText("第"+xqtemp+"学期");
                        }
                    }
                });
                break;

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
        if(!iswrong){
            unregisterReceiver(MyMessageReceiver);
        }

        if(timer!=null){
            timer.cancel();
        }

    }
    BroadcastReceiver MyMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MY)) {
                String type = intent.getStringExtra("type");

                if (type.equals("SARs")) {             //组织的值班管理者  安排值班的结果
                    if (timer != null) {
                        timer.cancel();
                    }
                  MyDialog.dismissBottomLoadingDialog();
                    String rs = intent.getStringExtra("rs");
                    if (rs.equals("ok")) {

                        finish();

                    } else {
                        Toast.makeText( ScheduleArrangementActivity.this,"服务器繁忙,请稍后重试!",Toast.LENGTH_SHORT).show();
                    }


                }
            }

        }
    };


}
