package thefirstchange.example.com.communicationtext.course.supercouesrdemo2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.angmarch.views.NiceSpinner;

import java.util.Calendar;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.widget.dialog.WheelDialogFragment;
import thefirstchange.example.com.communicationtext.educationSystem.UJN;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.util.MyDialog;

/*
    查询空教室
 */
public class SearchEmptyClassroomActivity extends BaseForCloseActivity implements View.OnClickListener{

    private static final String MY="thefirstchange.example.com.communicationtext.SEARCHEMPTYCLASSROOM";


    NiceSpinner school_search_empty_classroom_ns;
    NiceSpinner building_search_empty_classroom_ns;

    String schoolname;   //所在校区
    String buildings;    //所在教学楼


    ImageView search_empty_classroom_back_iv;

    TextView week_search_empty_classroom_tv;
    TextView day_search_empty_classroom_tv;


    EditText account_search_empty_classroom_et;
    EditText password_search_empty_classroom_et;

    Button search_empty_classroom_bt;


    private GridLayout mGlContent;

    private static String [] TITLE_DATA = {"上午","下午","晚上"};
    private static final int GRID_ROW_COUNT = 6;
    private static final int GRID_COL_COUNT = 9;

    private int mTableDistance;   //一列的宽度


    //选择的所有节数
    private Vector<Integer> jiesvec = new Vector<>();

    private Timer timer;


    //选中的
    private int weekok;
    private int dayok;
    private Vector<Integer> jieshuok;
    private String buildingnameOk;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_empty_classroom);

        init();
        initview();
        initevent();


        setUpContent();
        show();


    }

    private void init(){
        UJN.init();
    }

    private void initview(){

        school_search_empty_classroom_ns = (NiceSpinner)findViewById(R.id.school_search_empty_classroom_ns);
        school_search_empty_classroom_ns.setTextColor(Color.GREEN);
        school_search_empty_classroom_ns.setTextSize(14);
        school_search_empty_classroom_ns.attachDataSource(UJN.schoolname);

        building_search_empty_classroom_ns = (NiceSpinner)findViewById(R.id.building_search_empty_classroom_ns);
        building_search_empty_classroom_ns.setTextColor(Color.GREEN);
        school_search_empty_classroom_ns.setTextSize(14);

        if(UJN.schoolname==null){
            UJN.init();
        }

        building_search_empty_classroom_ns.attachDataSource(UJN.getBuildings(UJN.schoolname.get(0)));
        building_search_empty_classroom_ns.setText("第十教学楼");
        schoolname = UJN.schoolname.get(0);
        buildings = UJN.getBuildings(schoolname).get(0);

        search_empty_classroom_back_iv = (ImageView)findViewById(R.id.search_empty_classroom_back_iv);

        week_search_empty_classroom_tv = (TextView)findViewById(R.id.week_search_empty_classroom_tv);
        day_search_empty_classroom_tv = (TextView)findViewById(R.id.day_search_empty_classroom_tv);


        account_search_empty_classroom_et = (EditText)findViewById(R.id.account_search_empty_classroom_et);
        password_search_empty_classroom_et = (EditText)findViewById(R.id.password_search_empty_classroom_et);


        search_empty_classroom_bt = (Button) findViewById(R.id.search_empty_classroom_bt);

        mGlContent = (GridLayout) this.findViewById(R.id.jie_search_empty_classroom_grid_content);

        mTableDistance = (getScreenPixelWidth()-16)/GRID_COL_COUNT;


        SharedPreferences sp = getSharedPreferences(Config.sharedPreferences_edu_account, Context.MODE_PRIVATE);
        String  username = sp.getString("username", "");  //如果取不到值就取后面的""
        String password = sp.getString("password", "");

        if(!username.trim().isEmpty()&&!password.trim().isEmpty()){
            account_search_empty_classroom_et.setText(username);
            password_search_empty_classroom_et.setText(password);
        }


    }

    private void initevent(){

        search_empty_classroom_back_iv.setOnClickListener(this);
        week_search_empty_classroom_tv.setOnClickListener(this);
        day_search_empty_classroom_tv.setOnClickListener(this);

        school_search_empty_classroom_ns.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                schoolname = UJN.schoolname.get(position);
                building_search_empty_classroom_ns.attachDataSource(UJN.getBuildings(schoolname));
            }
        });

        building_search_empty_classroom_ns.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                buildings = UJN.getBuildings(schoolname).get(position);

            }
        });

        search_empty_classroom_bt.setOnClickListener(this);
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
            params.width = mTableDistance*2;
//            if (i == 0){
//                params.height = 0;
//                // params.height = (int) getResources().getDimension(R.dimen.table_row_height3);
//                TextView textView = new TextView(this);
//                textView.setGravity(Gravity.CENTER);
//                textView.setBackgroundColor(this.getResources().getColor(R.color.tecent_color));
//                mGlContent.addView(textView,params);
//            }
//            else {
                if(i%2==0){
                    params.height = (int) getResources().getDimension(R.dimen.table_row_height);      //25dp
                    TextView textView = new TextView(this);
                    textView.setTextColor(getResources().getColor(R.color.grey31));
                    textView.setTextSize(15);
                    textView.setText(TITLE_DATA[i/2]);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(this.getResources().getColor(R.color.white));
                    mGlContent.addView(textView,params);
                }else{
                    params.height = (int) getResources().getDimension(R.dimen.table_row_height3);
                    TextView textView = new TextView(this);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(this.getResources().getColor(R.color.white));
                    mGlContent.addView(textView,params);
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
                params.width = mTableDistance/3;
                params.height = 0;

                View view = new View(this);
                view.setBackgroundColor(getResources().getColor(R.color.white));
                mGlContent.addView(view,params);

            }else{

                GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                        GridLayout.spec(row),GridLayout.spec(col)
                );
                params.width = (mTableDistance)+(mTableDistance)/3;
                params.height = 0;

                View view = new View(this);
                mGlContent.addView(view,params);

            }

        }
    }


    private void show(){

        int jie = 1;
        for (int row = 0; row<GRID_ROW_COUNT; row+=2){
            for(int j = 1;j<=4;++j){
                int col = j*2-1;
                if(row>=GRID_ROW_COUNT){
                    break;
                }
                //设定View在表格的哪行那列
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                        GridLayout.spec(row,1),
                        GridLayout.spec(col)
                );

                //设置View的宽高
//                params.width = (mTableDistance)*2;
                params.width = (mTableDistance)+(mTableDistance)/3;
                // params.height = (int) getResources().getDimension(R.dimen.table_row_height) * size;
                params.setGravity(Gravity.FILL);

                //通过代码改变<Shape>的背景颜色
                GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.cls_bg);
                drawable.setColor(getResources().getColor(R.color.gray91));


                //设置View
                final TextView textView = new TextView(this);
                // textView.setGravity(Gravity.CENTER);
                textView.setPadding(11,13,10,10);
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setTextSize(15);
                textView.setText(jie+"");
                textView.setGravity(Gravity.CENTER);
                final int fjie = jie;
                jie++;

                textView.setBackground(drawable);
                final int row2 = row;
                final int j2 = j;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if(jiesvec.contains(fjie)){                                  //已经点击过  现在点击  把颜色变回

                            GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.cls_bg);
                            drawable.setColor(getResources().getColor(R.color.gray91));    //填没有被选中的颜色
                            textView.setBackground(drawable);

                            jiesvec.removeElement(fjie);

                        }else{                                    //没有点击过  现在点击

                            jiesvec.addElement(fjie);
                            GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.cls_bg);
                            drawable.setColor(getResources().getColor(R.color.blue));    //填被选中的颜色
                            textView.setBackground(drawable);
                        }

                    }
                });
                //添加到表格中
                mGlContent.addView(textView,params);

            }

        }
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.search_empty_classroom_back_iv:
                finish();
                break;
            case R.id.week_search_empty_classroom_tv:

                Bundle bundle2 = new Bundle();
                bundle2.putBoolean(WheelDialogFragment.DIALOG_BACK, false);
                bundle2.putBoolean(WheelDialogFragment.DIALOG_CANCELABLE, false);
                bundle2.putBoolean(WheelDialogFragment.DIALOG_CANCELABLE_TOUCH_OUT_SIDE, false);
                bundle2.putString(WheelDialogFragment.DIALOG_LEFT, "取消");
                bundle2.putString(WheelDialogFragment.DIALOG_RIGHT, "确定");
                String[] weeks = new String[]{"第1周","第2周","第3周","第4周","第5周","第6周",
                        "第7周","第8周","第9周","第10周","第11周","第12周","第13周","第14周","第15周",
                        "第16周","第17周","第18周","第19周","第20周","第21周"};

                bundle2.putStringArray(WheelDialogFragment.DIALOG_WHEEL,weeks);

                WheelDialogFragment dialogFragment2 = WheelDialogFragment.newInstance(WheelDialogFragment.class, bundle2);

                dialogFragment2.setWheelDialogListener(new WheelDialogFragment.OnWheelDialogListener() {
                    @Override
                    public void onClickLeft(DialogFragment dialog, String value) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onClickRight(DialogFragment dialog, String value) {
                        dialog.dismiss();
                        week_search_empty_classroom_tv.setText(value);
                    }

                    @Override
                    public void onValueChanged(DialogFragment dialog, String value) {

                    }
                });

                dialogFragment2.show(getSupportFragmentManager(), "");
                break;
            case R.id.day_search_empty_classroom_tv:

                Bundle bundle = new Bundle();
                bundle.putBoolean(WheelDialogFragment.DIALOG_BACK, false);
                bundle.putBoolean(WheelDialogFragment.DIALOG_CANCELABLE, false);
                bundle.putBoolean(WheelDialogFragment.DIALOG_CANCELABLE_TOUCH_OUT_SIDE, false);
                bundle.putString(WheelDialogFragment.DIALOG_LEFT, "取消");
                bundle.putString(WheelDialogFragment.DIALOG_RIGHT, "确定");
                String[] days = new String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};

                bundle.putStringArray(WheelDialogFragment.DIALOG_WHEEL,days);

                WheelDialogFragment dialogFragment = WheelDialogFragment.newInstance(WheelDialogFragment.class, bundle);

                dialogFragment.setWheelDialogListener(new WheelDialogFragment.OnWheelDialogListener() {
                    @Override
                    public void onClickLeft(DialogFragment dialog, String value) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onClickRight(DialogFragment dialog, String value) {
                        dialog.dismiss();
                        day_search_empty_classroom_tv.setText(value);
                    }

                    @Override
                    public void onValueChanged(DialogFragment dialog, String value) {

                    }
                });

                dialogFragment.show(getSupportFragmentManager(), "");
                break;



            case R.id.search_empty_classroom_bt:

                if(jiesvec.size()==0){
                    Toast.makeText(SearchEmptyClassroomActivity.this,"请至少选择一节课",Toast.LENGTH_SHORT).show();
                    return;
                }

                int week = 1;
                int day = 1;
                String weekstr = week_search_empty_classroom_tv.getText().toString();
                String temp = weekstr.substring(1,weekstr.length()-1);
                week = Integer.parseInt(temp);


                String daystr = day_search_empty_classroom_tv.getText().toString();
                if(daystr.contains("一")) {
                    day=1;
                }else if(daystr.contains("二")) {
                    day=2;
                }else if(daystr.contains("三")) {
                    day=3;
                }else if(daystr.contains("四")) {
                    day=4;
                }else if(daystr.contains("五")) {
                    day=5;
                }else if(daystr.contains("六")) {
                    day=6;
                } else if(daystr.contains("日")) {
                    day=7;
                }

                String schoolname = school_search_empty_classroom_ns.getText().toString();
                String buildingname = building_search_empty_classroom_ns.getText().toString();

                String buildCode = UJN.getBuildingCode(schoolname,buildingname);
                Collections.sort(jiesvec);

                Calendar cale = null;
                cale = Calendar.getInstance();
                int year = cale.get(Calendar.YEAR);
                int month = cale.get(Calendar.MONTH) + 1;

                int xqm = 1;
                int xnm = year;
                //第二学期
                if(month>=2&&month<=8){
                    xnm--;
                    xqm++;
                }

                weekok = week;
                dayok = day;
                buildingnameOk = buildingname;
                jieshuok = jiesvec;

                MyDialog.showBottomLoadingDialog(SearchEmptyClassroomActivity.this);

                SendToServer.searchEmptyClassRoom( xnm , xqm,  buildCode, week , day, jiesvec);

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                            MyDialog.dismissBottomLoadingDialog();
                            SearchEmptyClassroomActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SearchEmptyClassroomActivity.this,"查询失败!",Toast.LENGTH_SHORT).show();
                                }
                            });
                    }
                },100000);
                break;
        }

    }


    //获取屏幕宽
    public int getScreenPixelWidth(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return metrics.widthPixels;
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
        if(timer!=null){
            timer.cancel();
        }
    }
    BroadcastReceiver MyMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MY)){
                String type = intent.getStringExtra("type");
                if(type.equals("searchEmptyClassroom")){
                    if(timer!=null){
                        timer.cancel();
                    }
                    MyDialog.dismissBottomLoadingDialog();
                    String rs = intent.getStringExtra("rs");
                    if(rs.equals("ok")){
                        Toast.makeText(SearchEmptyClassroomActivity.this,"查询成功!",Toast.LENGTH_SHORT).show();

                        /*
                            跳转显示界面
                         */

                        Intent intent1 = new Intent(SearchEmptyClassroomActivity.this,SearchEmptyClassroomResultActivity.class);
                        intent1.putExtra("week",weekok);
                        intent1.putExtra("day",dayok);
                        String jieshustr = "";
                        for(Integer i : jieshuok){
                            jieshustr+=i+" ";
                        }
                        intent1.putExtra("jieshu",jieshustr);
                        intent1.putExtra("buildname",buildingnameOk);

                        startActivity(intent1);
                    }else{
                        Toast.makeText(SearchEmptyClassroomActivity.this,"查询失败!",Toast.LENGTH_SHORT).show();
                    }
                }


            }

        }
    };

}
