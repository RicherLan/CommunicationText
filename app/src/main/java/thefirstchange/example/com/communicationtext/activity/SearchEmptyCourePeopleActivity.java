package thefirstchange.example.com.communicationtext.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.adapter.SearchEmptyCourseZhouAdapter;
import thefirstchange.example.com.communicationtext.gson.SearchEmptyCourse;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.MessageTemp;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

/*
    社团组织获得   获得某几节课都有空的人
 */
public class SearchEmptyCourePeopleActivity extends BaseForCloseActivity implements View.OnClickListener{

    private static final String MY="thefirstchange.example.com.communicationtext.SEARCHEMPTYCOURSEPEOPLE";
    private static  Vector<SearchEmptyCourse> searchEmptyCourses = new Vector<>();

    private static Vector<Integer>  noSchedule = new Vector<>();   //不能排班的  时间已经过了

    private static String [] TITLE_DATA = {"","周一","周二","周三","周四","周五","周六","周日"};
    private static final int GRID_ROW_COUNT = 24;
    private static final int GRID_COL_COUNT = 16;
    private static List<Integer> zhoulist = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27));

    private LinearLayout search_empty_course_choose_zhou;
    private TextView search_empty_course_search;
    private TextView search_empty_course_zhou_tv;
    private GridLayout mGlTitle;
    private GridLayout mGlContent;

    private int mTableDistance;   //一列的宽度
    private ImageView finishSelf;


    private boolean isclickzhoushu = false;
    private  LinearLayoutManager linearLayoutManager;
    private SearchEmptyCourseZhouAdapter zhoushuAdapter;
    private RecyclerView zhoushulistview;
    private int zhoushuLocation;

    private Timer timer = new Timer();

    private int groupid;
    private boolean iswrong = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_empty_coure_people);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);
        Intent intent = getIntent();
        groupid = intent.getIntExtra("groupid",-1);
        if(groupid==-1){
            Toast.makeText(SearchEmptyCourePeopleActivity.this,"系统作物,请稍后再试!",Toast.LENGTH_SHORT).show();
            iswrong=true;
            finish();
        }
        zhoushuLocation=StaticAllList.groupList.get(groupid).getZhou();
        initview();
        initevent();
        setUpTitle();
        setUpContent();
        show();
    }


    public void initview(){

        search_empty_course_choose_zhou = findViewById(R.id.search_empty_course_choose_zhou);
        search_empty_course_zhou_tv = findViewById(R.id.search_empty_course_zhou_tv);
        search_empty_course_zhou_tv.setText("第"+zhoushuLocation+"周");
        search_empty_course_search = findViewById(R.id.search_empty_course_search);
        mGlTitle = (GridLayout) this.findViewById(R.id.schedulearrangement_grid_title);
        mGlContent = (GridLayout) this.findViewById(R.id.schedulearrangement_grid_content);
        finishSelf=(ImageView)findViewById(R.id.finish_search_empty_course);
        finishSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTableDistance = getScreenPixelWidth()/15;

        zhoushulistview = (RecyclerView)findViewById(R.id.serarch_empty_course_zhoushu_listview);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        zhoushulistview.setLayoutManager(linearLayoutManager);
        zhoushuAdapter = new SearchEmptyCourseZhouAdapter(SearchEmptyCourePeopleActivity.this,zhoulist);

        zhoushuAdapter.setOnItemClickListener(new SearchEmptyCourseZhouAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {

                if(position+1!=zhoushuLocation){
                    zhoushuLocation = position+1;
                    search_empty_course_zhou_tv.setText("第"+zhoushuLocation+"周");
                }
                if(zhoushulistview!=null){
                    zhoushulistview.setVisibility(View.GONE);
                }
            }
        });

        zhoushulistview.setAdapter(zhoushuAdapter);

        MoveToPosition(linearLayoutManager,zhoushulistview,zhoushuLocation-2);

    }

    public void initevent(){
        search_empty_course_choose_zhou.setOnClickListener(this);
        search_empty_course_search.setOnClickListener(this);
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
            textView.setText(content);
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
                textView.setBackgroundColor(this.getResources().getColor(R.color.tecent_color));
                mGlContent.addView(textView,params);
            }
            else {
                if(i%2!=0){
                    params.height = (int) getResources().getDimension(R.dimen.table_row_height);      //25dp
                    TextView textView = new TextView(this);
                    textView.setTextColor(getResources().getColor(R.color.grey31));
                    textView.setText((i+1)/2+"");
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(this.getResources().getColor(R.color.tecent_color));
                    mGlContent.addView(textView,params);
                }else{
                    params.height = (int) getResources().getDimension(R.dimen.table_row_height3);
                    TextView textView = new TextView(this);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(this.getResources().getColor(R.color.tecent_color));
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
                params.width = 8;
                params.height = 0;

                View view = new View(this);
                view.setBackgroundColor(getResources().getColor(R.color.white));
                mGlContent.addView(view,params);

            }else{

                GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                        GridLayout.spec(row),GridLayout.spec(col)
                );
                params.width = (mTableDistance-4)*2;
                params.height = 0;

                View view = new View(this);
                mGlContent.addView(view,params);

            }

        }
    }


    private void show(){

        for (int row = 1; row<GRID_ROW_COUNT; row+=4){
            for(int j = 1;j<=7;++j){
                int col = j*2-1;
                if(row>=GRID_ROW_COUNT){
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
                drawable.setColor(getResources().getColor(R.color.gray91));


                //设置View
                final TextView textView = new TextView(this);
                // textView.setGravity(Gravity.CENTER);
                textView.setPadding(11,13,10,10);
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setTextSize(13);


                textView.setBackground(drawable);
                final int row2 = row;
                final int j2 = j;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int section =1;               //第几节课
                        switch(row2){
                            case 1:
                                section = 1;
                                break;
                            case 5:
                                section = 3;
                                break;
                            case 9:
                                section = 5;
                                break;
                            case 13:
                                section = 7;
                                break;
                            case 17:
                                section = 9;
                                break;
                            case 21:
                                section = 11;
                                break;
                        }

                        int way = j2;                  //周几
                        SearchEmptyCourse searchEmptyCourse2 = null;
                        for(int i=0;i<searchEmptyCourses.size();++i){
                            if(searchEmptyCourses.get(i).getWay()==way&&searchEmptyCourses.get(i).getSection()==section){
                                searchEmptyCourse2 = searchEmptyCourses.get(i);
                            }
                        }
                        if(searchEmptyCourse2!=null){                                  //已经点击过  现在点击  把颜色变回
                            searchEmptyCourses.remove(searchEmptyCourse2);
                            GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.cls_bg);
                            drawable.setColor(getResources().getColor(R.color.gray91));    //填没有被选中的颜色
                            textView.setBackground(drawable);


                        }else{                                    //没有点击过  现在点击
                            SearchEmptyCourse searchEmptyCourse = new SearchEmptyCourse();
                            searchEmptyCourse.setWay(way);
                            searchEmptyCourse.setSection(section);
                            searchEmptyCourses.add(searchEmptyCourse);

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
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.search_empty_course_choose_zhou:
                if(isclickzhoushu){

                    isclickzhoushu = false;
                    zhoushulistview.setVisibility(View.GONE);

               }else{
                    isclickzhoushu=true;
                    MoveToPosition(linearLayoutManager,zhoushulistview, zhoushuLocation);
                    zhoushulistview.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.search_empty_course_search:         //点击查询


                if(!NetworkUtils.isConnected(SearchEmptyCourePeopleActivity.this)){
                    Toast.makeText(SearchEmptyCourePeopleActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int d = groupid;
                for(int b:StaticAllList.groupList.keySet()){
                    int c = b;
                    int e;
                }

                if(!StaticAllList.groupList.containsKey(groupid)){
                    Toast.makeText(SearchEmptyCourePeopleActivity.this,"系统错误,请退出重试!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(searchEmptyCourses==null||searchEmptyCourses.size()==0){
                    Toast.makeText(SearchEmptyCourePeopleActivity.this,"请至少选择一节课!",Toast.LENGTH_SHORT).show();
                    return;
                }

//                if(StaticAllList.groupList.containsKey(groupid)){
//                    String userauthority = StaticAllList.groupList.get(groupid).getUserauthority();
//                    if(!userauthority.equals("群主")&&!userauthority.equals("管理员")){
//                        Toast.makeText(SearchEmptyCourePeopleActivity.this,"抱歉,您没有此功能的权限!",Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                }

                for(int i=0;i<searchEmptyCourses.size();++i){
                    searchEmptyCourses.get(i).setWeek(zhoushuLocation);
                }


                int year = StaticAllList.groupList.get(groupid).getYear();
                int xueqi = StaticAllList.groupList.get(groupid).getXueqi();

                SendToServer.searchEmptyCourse(groupid,year,xueqi,searchEmptyCourses);

                MyDialog.showBottomLoadingDialog(SearchEmptyCourePeopleActivity.this);
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        SearchEmptyCourePeopleActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyDialog.dismissBottomLoadingDialog();
                                Toast.makeText( SearchEmptyCourePeopleActivity.this,"服务器繁忙,请稍后重试!",Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                },50000);

                break;
        }

    }

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

                if (type.equals("SECP")) {
                    if (timer != null) {
                        timer.cancel();
                    }
                    MyDialog.dismissBottomLoadingDialog();
                    int groupid = intent.getIntExtra("groupid",-1);
                    if(groupid==-1){
                        Toast.makeText(SearchEmptyCourePeopleActivity.this,"系统错误,请稍后重试!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String rs = intent.getStringExtra("rs");
                    if(rs.equals("ok")){

                        if(MessageTemp.userCorps.size()==0){
                            Toast.makeText(SearchEmptyCourePeopleActivity.this,"无成员满足!",Toast.LENGTH_SHORT).show();

                        }else{
                            Intent intent1 = new Intent(SearchEmptyCourePeopleActivity.this,SearchEmptyCoursePeopleListActivity.class);
                            intent1.putExtra("groupid",groupid);
                            startActivity(intent1);
                        }

                    }else{
                        Toast.makeText(SearchEmptyCourePeopleActivity.this,"服务器繁忙,请稍后重试!",Toast.LENGTH_SHORT).show();
                    }

                }
            }

        }
    };
}
