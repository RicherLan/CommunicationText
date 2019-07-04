package thefirstchange.example.com.communicationtext.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.service.ObjectService;

public class ShowPersonalInfoActivity extends BaseForCloseActivity {

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
        setContentView(R.layout.activity_show_personal_info);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);
        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.person_detail_toolbar);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        initView();

        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView(){

        peopleImage=(ImageView)findViewById(R.id.person_detail_image);
        // peopleNameText=(TextView)findViewById(R.id.people_detail_name);
        peopleAdressText=(TextView)findViewById(R.id.person_detail_adress);
        peoplePartText=(TextView)findViewById(R.id.person_detail_part);
        peopleSexText=(TextView)findViewById(R.id.person_detail_sex);
        peoplePositionText=(TextView)findViewById(R.id.person_detail_position);
        peopleWeChatText=(TextView)findViewById(R.id.person_detail_wechat);
        peopleQqText=(TextView)findViewById(R.id.person_detail_qq);
        peoplePhoneText=(TextView)findViewById(R.id.person_detail_phone);

        collapsingToolbarLayout.setTitle(ObjectService.personalInfo.getNickname());
        peopleAdressText.setText("地址："+ObjectService.personalInfo.getAddress());
        peoplePartText.setText("部室：");
        peoplePhoneText.setText("电话："+ObjectService.personalInfo.getPhonenumber());
       // peoplePositionText.setText("职务："+PersonalInfo.getPosition());
        peopleQqText.setText("QQ："+ObjectService.personalInfo.getQq());
        peopleWeChatText.setText("Wechat："+ObjectService.personalInfo.getWeixin());
        peopleSexText.setText("性别："+ ObjectService.personalInfo.getSex());
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.info_toolbar_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.to_alter_info:
              //  Intent intent=new Intent(ShowPersonalInfoActivity.this,AlterPersonInfoActivity.class);
                //startActivity(intent);
                Toast.makeText(ShowPersonalInfoActivity.this,"编辑",Toast.LENGTH_SHORT).show();
                break;
                default:
        }
        return true;
    }


}
