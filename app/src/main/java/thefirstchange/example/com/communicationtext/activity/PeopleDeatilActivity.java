package thefirstchange.example.com.communicationtext.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import thefirstchange.example.com.communicationtext.R;

public class PeopleDeatilActivity extends BaseForCloseActivity {

    public static final String PEOPLE_NAME="people_name";
    public static final String PEOPLE_POSITION="people_position";
    public static final String PEOPLE_PHONE="people_phone";
    public static final String PEOPLE_PART="people_part";
    public static final String PEOPLE_SEX="people_sex";
    public static final String PEOPLE_QQ="people_qq";
    public static final String PEOPLE_WECHAT="people_wechat";
    public static final String PEOPLE_IMAGE_ID="people_image_id";
    public static final String PEOPLE_ADRESS="people_adress";

    private ImageView peopleImage;
    private TextView peopleSexText;
    private TextView peoplePhoneText;
    private TextView peoplePartText;
    private TextView peoplePositionText;
    private TextView peopleQqText;
    private TextView peopleWeChatText;
    private TextView peopleAdressText;
    private  CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_deatil);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);

        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.people_detail_toolbar);
         collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        initView();

        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(){

        final Intent intent=getIntent();
        String peopleName=intent.getStringExtra(PEOPLE_NAME);
        String peopleSex=intent.getStringExtra(PEOPLE_SEX);
        final String peoplePhone=intent.getStringExtra(PEOPLE_PHONE);
        String peoplePart=intent.getStringExtra(PEOPLE_PART);
        String peoplePosition=intent.getStringExtra(PEOPLE_POSITION);
        String peopleQq=intent.getStringExtra(PEOPLE_QQ);
        String peopleWeChat=intent.getStringExtra(PEOPLE_WECHAT);
        String peopleAdress=intent.getStringExtra(PEOPLE_ADRESS);
        int peopleImageId=intent.getIntExtra(PEOPLE_IMAGE_ID,0);

        peopleImage=(ImageView)findViewById(R.id.people_detail_image);
       // peopleNameText=(TextView)findViewById(R.id.people_detail_name);
        peopleAdressText=(TextView)findViewById(R.id.people_detail_adress);
        peoplePartText=(TextView)findViewById(R.id.people_detail_part);
        peopleSexText=(TextView)findViewById(R.id.people_detail_sex);
        peoplePositionText=(TextView)findViewById(R.id.people_detail_position);
        peopleWeChatText=(TextView)findViewById(R.id.people_detail_wechat);
        peopleQqText=(TextView)findViewById(R.id.people_detail_qq);
        peoplePhoneText=(TextView)findViewById(R.id.people_detail_phone);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.floating_call_on);

        collapsingToolbarLayout.setTitle(peopleName);
        Glide.with(this).load(peopleImageId).into(peopleImage);
        peopleSexText.setText("性别："+peopleSex);
        peopleAdressText.setText("地址："+peopleAdress);
        peoplePartText.setText("部室："+peoplePart);
        peoplePhoneText.setText("电话："+peoplePhone);
        peopleQqText.setText("QQ："+peopleQq);
        peopleWeChatText.setText("WeChat："+peopleWeChat);
        peoplePositionText.setText("职务："+peoplePosition);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse("tel:"+peoplePhone));
                startActivity(intent1);
            }
        });

    }
}
