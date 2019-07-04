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
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.Random;
import java.util.Vector;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.course.adapter.ChooseCorporationAdapter;
import thefirstchange.example.com.communicationtext.course.database.dao.StuDao;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.schedulearrangement.ClientArrangement;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.DensityUtiltwo;


/*
    值班表的活动
 */
public class SchDutyActivity  extends BaseForCloseActivity implements View.OnClickListener {

    private boolean startneedloadfromdb = true;

    private static final String MY="thefirstchange.example.com.communicationtext.SCHDUTY";

    private int year;        //当前年
    private int month ;       //当前月
    private int day;         //当前日期
    private int way;         //当前周几

    private static  String [] SCH_TITLE_DATA = {"","","","","","","",""};
    private static  String [] SCH_TITLE_DATE = {"","","","","","",""};


    private static final int SCH_GRID_ROW_COUNT = 24;
    private static final int GRID_COL_COUNT = 16;

    private LinearLayout schduty_corp_layout;
    private TextView SchDuty_CorpName_tv;     //值班表界面的社团的名称
    private TextView SchDuty_Groupid_tv;      //值班表界面的社团的群的账号
    private  TextView schduty_tv;
    private TextView schduty_zhou_tv;    //当前是社团指定的第几周
    private GridLayout mGlSchTitle;                 //值班表的
    private GridLayout mGlSchContent;

    private StuDao stuDao;        //存储课表的sqlite数据库
    //    private StuScoreDao stuScoreDao;           //存储成绩的sqlite数据库
    private int mTableDistance;   //一列的宽度


    int zhou=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schduty);
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        if(from!=null&&from.equals("DutyNoti")){
            this.startneedloadfromdb = false;
        }
//        initdata();
        initview();
        initevent();
    }

    public void initdata(){
        if(this.startneedloadfromdb&& CourseAndScore.duty_groupid==-1){
            SharedPreferences sp2 = getSharedPreferences(Config.sharedPreferences_duty_group, Context.MODE_PRIVATE);
            CourseAndScore.duty_groupid = sp2.getInt("duty_groupid", -1);
        }

        if(StaticAllList.groupList.containsKey(CourseAndScore.duty_groupid)){

            SchDuty_CorpName_tv.setText(StaticAllList.groupList.get(CourseAndScore.duty_groupid).getGroupname());
            SchDuty_Groupid_tv.setText(CourseAndScore.duty_groupid+"");
            zhou = StaticAllList.groupList.get(CourseAndScore.duty_groupid).getZhou();
            schduty_zhou_tv.setText("第"+zhou+"周");
        }else{
            CourseAndScore.duty_groupid = -1;
            boolean flag = false;
            for(int gid:StaticAllList.groupList.keySet()){
                if(StaticAllList.groupList.get(gid).getCorppos().equals("官方账号")){
                    SchDuty_CorpName_tv.setText(StaticAllList.groupList.get(gid).getGroupname());
                    SchDuty_Groupid_tv.setText(gid+"");
                    zhou = StaticAllList.groupList.get(gid).getZhou();
                    schduty_zhou_tv.setText("第"+zhou+"周");
                    break;
                }

                if(StaticAllList.groupList.get(gid).getGrouptype().equals("社团群")){
                    SchDuty_CorpName_tv.setText(StaticAllList.groupList.get(gid).getGroupname());
                    SchDuty_Groupid_tv.setText(gid+"");
                    zhou = StaticAllList.groupList.get(gid).getZhou();
                    schduty_zhou_tv.setText("第"+zhou+"周");
                    flag = true;
                }
            }
            if(!flag){
                SchDuty_CorpName_tv.setText("无社团组织");
            }


        }


    }

    public void initview(){
        SchDuty_CorpName_tv = findViewById(R.id.SchDuty_CorpName_tv);
        SchDuty_Groupid_tv = findViewById(R.id.SchDuty_Groupid_tv);
        schduty_zhou_tv = (TextView)this.findViewById(R.id.schduty_zhou_tv);
        schduty_tv = findViewById(R.id.schduty_tv);

        String type = ObjectService.personalInfo.getType();
        if(type==null||!type.equals("社团官方")){
            schduty_tv.setVisibility(View.INVISIBLE);
        }

        mGlSchTitle = this.findViewById(R.id.schduty_grid_title);
        mGlSchContent = this.findViewById(R.id.schduty_grid_content);

        schduty_corp_layout = (LinearLayout)this.findViewById(R.id.schduty_corp_layout) ;
        mTableDistance = getScreenPixelWidth()/15;
        this.stuDao = StuDao.getInstance(this);

        processLogin();

    }


    public void initevent(){

        schduty_tv.setOnClickListener(this);
        schduty_corp_layout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.schduty_corp_layout:
                showCorporationDialog();

                break;

            case R.id.schduty_tv:

//                if(s.isEmpty()||s.equals("")){
//                    Toast.makeText(SchDutyActivity.this,"您当前没有社团组织!",Toast.LENGTH_SHORT).show();
//                    return;
//                }


                String s = SchDuty_Groupid_tv.getText().toString().trim();
                final int groupid = Integer.parseInt(s);

                if(!(StaticAllList.groupList.get(groupid).getAuthid()+"").equals(ObjectService.personalInfo.getPhonenumber())){
                    Toast.makeText(SchDutyActivity.this,"社团组织不匹配,请更换社团组织!",Toast.LENGTH_SHORT).show();
                    return;
                }


                TextView title = new TextView(SchDutyActivity.this);
                title.setText("选择值班方式");
                title.setPadding(10, 10, 10, 10);
                title.setGravity(Gravity.CENTER);
                // title.setTextColor(getResources().getColor(R.color.greenBG));
                title.setTextSize(21);
                title.setTextColor(getResources().getColor(R.color.grey31));

                AlertDialog.Builder builder = new AlertDialog.Builder(SchDutyActivity.this,R.style.AlertDialog);
                builder.setCustomTitle(title);
                //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                View vv = LayoutInflater.from(SchDutyActivity.this).inflate(R.layout.dialog_choose_schduty_way, null);
                //    设置我们自己定义的布局文件作为弹出框的Content
                builder.setView(vv);


                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_corner);
                //设置大小
                WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                layoutParams.width = DensityUtiltwo.dp2px(this, 280);
                layoutParams.height = DensityUtiltwo.dp2px(this, 173);
                dialog.getWindow().setAttributes(layoutParams);

//                final TextView sch_year_TV = (TextView) dialog.getWindow().findViewById(R.id.sch_year_TV);
//                final TextView sch_xq_TV = (TextView)dialog.getWindow().findViewById(R.id.sch_xq_TV);


                //安排下一周
                dialog.getWindow().findViewById(R.id.dialog_choose_schduty_way1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String type = "next_zhou";
                        Intent intent = new Intent(SchDutyActivity.this,ScheduleArrangementActivity.class);
                        intent.putExtra("type",type);
                        intent.putExtra("groupid",groupid);
                        intent.putExtra("zhou",zhou);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                //安排本周
                dialog.getWindow().findViewById(R.id.dialog_choose_schduty_way2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String type = "ben_zhou";
                        Intent intent = new Intent(SchDutyActivity.this,ScheduleArrangementActivity.class);
                        intent.putExtra("type",type);
                        intent.putExtra("groupid",groupid);
                        intent.putExtra("zhou",zhou);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                //安排今后7天
                dialog.getWindow().findViewById(R.id.dialog_choose_schduty_way3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String type = "next_seven_days";
                        Intent intent = new Intent(SchDutyActivity.this,ScheduleArrangementActivity.class);
                        intent.putExtra("type",type);
                        intent.putExtra("groupid",groupid);
                        intent.putExtra("zhou",zhou);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                break;
        }

    }


    /*
  下面是显示值班表的
   */
    //设置表格显示星期的地方
    private void setUpSchTitle(){

        for(int ww=1;ww<=7;++ww){
            String s = "周一";
            if(ww==1){
                s = "周一";
            }else if(ww==2){
                s = "周二";
            }else if(ww==3){
                s = "周三";
            }else if(ww==4){
                s = "周四";
            }else if(ww==5){
                s = "周五";
            }else if(ww==6){
                s = "周六";
            }else if(ww==7){
                s = "周日";
            }
            SCH_TITLE_DATA[ww]=s;
        }
        boolean flag = false;

        if(CourseAndScore.duty_groupid!=-1&&CourseAndScore.clientArrangements!=null&&CourseAndScore.clientArrangements.size()!=0){
            flag = true;

            ClientArrangement clientArrangement = null;
            for(int i=0;i<CourseAndScore.clientArrangements.size();++i){
                if(CourseAndScore.clientArrangements.get(i).groupid==CourseAndScore.duty_groupid){
                    clientArrangement = CourseAndScore.clientArrangements.get(i);
                    break;
                }
            }
//            if(clientArrangement==null){
//                return;
//            }
            int schyear = clientArrangement.getYear();
            int schmonth = clientArrangement.getMonth();
            int daytime = clientArrangement.getDaytime();
            int schway = clientArrangement.getWay();

            int a = daytime;
            SCH_TITLE_DATA[0] = schmonth+"";

            if(schway!=-1){
                for(int i=1;i<=7;++i){
                    int ww = (schway+i-1)%7;
                    if(ww==0){
                        ww = 7;
                    }

                    String s = "周一";
                    if(ww==1){
                        s = "周一";
                    }else if(ww==2){
                        s = "周二";
                    }else if(ww==3){
                        s = "周三";
                    }else if(ww==4){
                        s = "周四";
                    }else if(ww==5){
                        s = "周五";
                    }else if(ww==6){
                        s = "周六";
                    }else if(ww==7){
                        s = "周日";
                    }

                    SCH_TITLE_DATA[i] = s;

                }
            }

            for(int i=0;i<7;++i){
                SCH_TITLE_DATE[i] = daytime+i+"";
                if(schmonth==1||schmonth==3||schmonth==5||schmonth==7||schmonth==8||schmonth==10||schmonth==12){
                    if(SCH_TITLE_DATE[i].equals("32")){
                        SCH_TITLE_DATE[i]=1+"";
                    }
                }else if(schmonth==2){

                    if(schyear%4==0 && schyear%100!=0||schyear%400==0){
                        if(SCH_TITLE_DATE[i].equals("30")){
                            SCH_TITLE_DATE[i]=1+"";
                        }
                    }else{
                        if(SCH_TITLE_DATE[i].equals("29")){
                            SCH_TITLE_DATE[i]=1+"";
                        }
                    }

                }else{
                    if(SCH_TITLE_DATE[i].equals("31")){
                        SCH_TITLE_DATE[i]=1+"";
                    }
                }
            }

        }

        for (int i=0; i<SCH_TITLE_DATA.length; ++i){
            String content = SCH_TITLE_DATA[i];
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
            if(flag){
                if(i==0){
                    textView.setText(content+"\n"+"月");
                }else{
                    textView.setText(content+"\n"+SCH_TITLE_DATE[i-1]);
                }
            }else{
                if(i==0){
                    textView.setText(SCH_TITLE_DATA[i]);
                }else{
                    textView.setText(SCH_TITLE_DATA[i]);
                }
            }

            textView.setGravity(Gravity.CENTER);
            mGlSchTitle.addView(textView,params);
        }
    }

    //初始化值班表显示的格子    最左边的第几节课
    private void setUpSchContent(){
        //设置每行第几节课的提示
        for(int i=0; i<SCH_GRID_ROW_COUNT; ++i){
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
                mGlSchContent.addView(textView,params);
            }
            else {
                if(i%2!=0){
                    params.height = (int) getResources().getDimension(R.dimen.course_table_row_height);
                    TextView textView = new TextView(this);
                    textView.setTextColor(getResources().getColor(R.color.grey31));
                    textView.setText((i+1)/2+"");
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(this.getResources().getColor(R.color.WhiteSmoke));
                    mGlSchContent.addView(textView,params);
                }else{
                    params.height = (int) getResources().getDimension(R.dimen.course_table_row_height3);
                    TextView textView = new TextView(this);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(this.getResources().getColor(R.color.WhiteSmoke));
                    mGlSchContent.addView(textView,params);
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
                params.width = 8;
                params.height = 0;

                View view = new View(this);
                view.setBackgroundColor(getResources().getColor(R.color.white));
                mGlSchContent.addView(view,params);

            }else{

                GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                        GridLayout.spec(row),GridLayout.spec(col)
                );
                params.width = (mTableDistance-4)*2;
                params.height = 0;

                View view = new View(this);
                mGlSchContent.addView(view,params);

            }

        }
    }

    //显示某组织的值班表
    private void showSch(int groupid){

        if(groupid==-1){
            return;
        }


        for (int i = 0; i< CourseAndScore.clientArrangements.size(); ++i){
            final ClientArrangement clientArrangement = CourseAndScore.clientArrangements.get(i);
            if(clientArrangement.getGroupid()!=groupid){
                continue;
            }
            int arrweek = clientArrangement.getWeek();
            int arrday = clientArrangement.getWay();
            int arrsection = clientArrangement.getSection();
            int col = 1;                         //位于哪一列
            for(int j=1;j<SCH_TITLE_DATA.length;++j){
                String string = SCH_TITLE_DATA[j];
                int dd = 1;
                if(string.equals("周一")){
                    dd = 1;
                }else  if(string.equals("周二")){
                    dd = 2;
                }else  if(string.equals("周三")){
                    dd = 3;
                }else  if(string.equals("周四")){
                    dd = 4;
                }else  if(string.equals("周五")){
                    dd = 5;
                }else  if(string.equals("周六")){
                    dd = 6;
                }else  if(string.equals("周日")){
                    dd = 7;
                }

                if(arrday==dd){
                    col=j;
                    break;
                }
            }
            col = col*2-1;
            int row1 = arrsection;

            //设定View在表格的哪行那列
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec((row1)*2-1,3),
                    GridLayout.spec(col)
            );

            //设置View的宽高
            params.width = (mTableDistance-4)*2;
            // params.height = (int) getResources().getDimension(R.dimen.table_row_height) * size;
            params.setGravity(Gravity.FILL);


            //通过代码改变<Shape>的背景颜色
            GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.cls_bg);
            drawable.setColor(getScheduleColor());        //里面是颜色

            GradientDrawable drawable2 = (GradientDrawable) getResources().getDrawable(R.drawable.cls_bg);
            drawable2.setColor(getResources().getColor(R.color.white));        //里面是颜色

//            drawable.setColor(getResources().getColor(R.color.gray91));

            //设置View
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            // textView.setGravity(Gravity.CENTER);
            textView.setPadding(5,13,5,10);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setTextSize(12);
            String s = "";
            int size = clientArrangement.getNames().size();
            for(int k=0;k<size;++k){
                if(k>=8){
                    s+="......";
                    break;
                }else if(k==size-1){
                    s += clientArrangement.getNames().get(k);
                }else{
                    s += clientArrangement.getNames().get(k)+"\n";
                }
            }
            textView.setText(s);

            if(clientArrangement.getPhs().size()==0||clientArrangement.getPhs().get(0).trim().equals("")){
                textView.setBackground(drawable2);
            }else{
                textView.setBackground(drawable);
            }

            final int schindex = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clientArrangement.getPhs().size()==0||clientArrangement.getPhs().get(0).trim().equals("")){
                       return;
                    }
//                    int a = clientArrangement.getPhs().size();
//                    String str = clientArrangement.getPhs().get(0);
                    Intent intent = new Intent(SchDutyActivity.this,SchDutyUserInfoActivity.class);
                    intent.putExtra("index",schindex);
                    startActivity(intent);

                }
            });
            //添加到表格中
            mGlSchContent.addView(textView,params);

        }
    }

    //开启活动  加载完控件后   就从数据库导入值班表
    protected void processLogin() {

        initdata();

        if(this.startneedloadfromdb){
            CourseAndScore.clientArrangements = stuDao.getStuSchList();
        }


        initGroupDuty();
//        int a =  CourseAndScore.clientArrangements.size();
        setUpSchTitle();
        setUpSchContent();
        if(CourseAndScore.duty_groupid==-1){

            if(CourseAndScore.clientArrangements.size()!=0){
                for(int i=0;i<CourseAndScore.clientArrangements.size();++i){
                    int groupid = CourseAndScore.clientArrangements.get(i).groupid;
                    if(StaticAllList.groupList.containsKey(groupid)){
                        CourseAndScore.duty_groupid = groupid;
                        SharedPreferences mSharedPreferences = getSharedPreferences(Config.sharedPreferences_duty_group, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        editor.putInt("duty_groupid", CourseAndScore.duty_groupid);
                        editor.commit();
                    }
                }


            }
        }

        boolean flag = false;
        if(StaticAllList.groupList.containsKey(CourseAndScore.duty_groupid)){
            flag = true;
        }

        if(flag){
            showSch(CourseAndScore.duty_groupid);
        }else{
            showSch(-1);
        }

    }

    public void initGroupDuty(){
        for(int i=0;i<CourseAndScore.clientArrangements.size();++i){
            int groupid = CourseAndScore.clientArrangements.get(i).groupid;
            if(StaticAllList.groupList.containsKey(groupid)&&!CourseAndScore.groupDuty.contains(groupid)){
                CourseAndScore.groupDuty.add(CourseAndScore.clientArrangements.get(i).groupid);
            }
        }
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

    //获取屏幕宽
    public int getScreenPixelWidth(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public int getScreenPixelHeight(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    private void showCorporationDialog( ) {

        Vector<Integer> list = new Vector<Integer>();
        for(int gid : StaticAllList.groupList.keySet()){
            if(StaticAllList.groupList.get(gid).grouptype.equals("社团群")){
                list.add(gid);
            }
        }

        if(list.size()==0){
            Toast.makeText(SchDutyActivity.this,"您没有参与的社团组织!",Toast.LENGTH_SHORT).show();
            return;
        }

        final Vector<Integer> corplist = list;

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

        ChooseCorporationAdapter chooseCourseAdapter=new ChooseCorporationAdapter(this,corplist,bottomDialog);

        chooseCourseAdapter.setOnItemClickListener(new ChooseCorporationAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {

                final int groupid = corplist.get(position);
                String name = "";
                if(StaticAllList.groupList.containsKey(groupid)){
                    name = StaticAllList.groupList.get(groupid).getGroupname();
                }
                SchDuty_CorpName_tv.setText(name);
                SchDuty_Groupid_tv.setText(groupid+"");
                CourseAndScore.duty_groupid = groupid;

                mGlSchTitle.removeAllViews();
                setUpSchTitle();
                mGlSchContent.removeAllViews();
                setUpSchContent();
                showSch(CourseAndScore.duty_groupid);

                bottomDialog.dismiss();

                SharedPreferences mSharedPreferences = getSharedPreferences(Config.sharedPreferences_duty_group, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putInt("duty_groupid", groupid);
                editor.commit();

            }
        });
        recyclerView.setAdapter(chooseCourseAdapter);

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

                if(type.equals("DutySche")){               //获得值班表
                    final int groupid = intent.getIntExtra("groupid",-1);
                    if(groupid==-1){
                        return;
                    }
                    initGroupDuty();

                    boolean flag = false;
                    if(CourseAndScore.clientArrangements!=null){
                        for(int i=0;i< CourseAndScore.clientArrangements.size();++i){
                            if(CourseAndScore.clientArrangements.get(i).getPhs().size()>0){
                                flag = true;
                                break;
                            }
                        }
                    }
                    if(!flag){
                        Toast.makeText(SchDutyActivity.this,"无人员满足!",Toast.LENGTH_SHORT).show();
                    }

                    if(groupid==CourseAndScore.duty_groupid||CourseAndScore.duty_groupid==-1){
                        CourseAndScore.duty_groupid = groupid;
                        mGlSchTitle.removeAllViews();
                        setUpSchTitle();
                        mGlSchContent.removeAllViews();
                        setUpSchContent();
                        showSch(groupid);

                        for(int groupid2:StaticAllList.groupList.keySet()){
                            if(groupid2==CourseAndScore.duty_groupid){
                                SchDuty_CorpName_tv.setText(StaticAllList.groupList.get(groupid2).getGroupname());
                                SchDuty_Groupid_tv.setText(CourseAndScore.duty_groupid+"");
                                break;
                            }
                        }
                    }

                    /*
                    shareperferencr 这块现在这样写是因为  测试要用
                     */
                    new Thread(){
                        public void run(){

                            stuDao.removeSchduleBygroupid(groupid);
                            stuDao.saveStuSchList( CourseAndScore.clientArrangementsTemp);

//                           CourseAndScore.duty_groupid = groupid;
//                           SharedPreferences mSharedPreferences = getSharedPreferences(Config.sharedPreferences_duty_group, Context.MODE_PRIVATE);
//                           SharedPreferences.Editor editor = mSharedPreferences.edit();
//                           editor.putInt("duty_groupid", CourseAndScore.duty_groupid);
//                           editor.commit();
                        }
                    }.start();

                }

            }

        }
    };

}
