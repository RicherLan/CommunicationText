package thefirstchange.example.com.communicationtext.usersignin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.usersignin.adapter.ListviewYear;
import thefirstchange.example.com.communicationtext.usersignin.adapter.SelectRuxueYearAdapter;

public class SelectRuxueYearActivity extends BaseForCloseActivity implements View.OnClickListener{

    private String schoolname;            //学校名字
    private String collegename ;          //学院名字
    private String majorname;             //所修专业

    private ImageView select_ruxue_year_back_tv;
    private TextView select_ruxue_year_next_tv;

    private LinearLayoutManager linearLayoutManager;
    private SelectRuxueYearAdapter selectRuxueYearAdapter;
    private RecyclerView ruxueyearlistview;

    private static   List<ListviewYear> yearList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_ruxue_year);

        Intent intent = getIntent();
        schoolname = intent.getStringExtra("schoolname");
        collegename = intent.getStringExtra("collegename");
        majorname = intent.getStringExtra("majorname");
        init();
        initevent();

    }

    public void init(){

        for(int i=2012;i<=2026;++i){
            ListviewYear listviewYear = new ListviewYear();
            listviewYear.setYear(i);
            this.yearList.add(listviewYear);
        }

        select_ruxue_year_back_tv = (ImageView)this.findViewById(R.id.select_ruxue_year_back_tv);
        select_ruxue_year_next_tv = (TextView)this.findViewById(R.id.select_ruxue_year_next_tv);

        ruxueyearlistview = (RecyclerView)findViewById(R.id.ruxueyear_listview);
        linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ruxueyearlistview.setLayoutManager(linearLayoutManager);

        selectRuxueYearAdapter = new SelectRuxueYearAdapter(SelectRuxueYearActivity.this,yearList);

        selectRuxueYearAdapter.setOnItemClickListener(new SelectRuxueYearAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int year = yearList.get(position).getYear();
                Intent intent = new Intent(SelectRuxueYearActivity.this,RegisterPhonenumberActivity.class);
                intent.putExtra("schoolname",schoolname);
                intent.putExtra("collegename",collegename);
                intent.putExtra("majorname",majorname);
                intent.putExtra("ruxueyear",year);
                startActivity(intent);
            }
        });

        ruxueyearlistview.setAdapter(selectRuxueYearAdapter);
        //添加Android自带的分割线
        ruxueyearlistview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


    }

    public void initevent(){

        select_ruxue_year_back_tv.setOnClickListener(this);
        select_ruxue_year_next_tv.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select_ruxue_year_back_tv:

                break;
            case R.id.select_ruxue_year_next_tv:
                break;
                default:
                    break;
        }
    }
}
