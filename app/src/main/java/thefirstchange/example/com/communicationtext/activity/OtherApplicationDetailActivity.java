package thefirstchange.example.com.communicationtext.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.util.MyFileTools;
import thefirstchange.example.com.communicationtext.util.MyViewTools;

public class OtherApplicationDetailActivity extends BaseForCloseActivity implements View.OnClickListener{

    private CircleImageView peopleIv;
    private TextView peopleNickname;
    private TextView peopleNumber;
    private RelativeLayout toPage;
    private TextView checkInfo;
    private TextView refusePeople;
    private TextView acceptPeople;
    private LinearLayout acceptedLay;
    private LinearLayout refuseOrAcceptLay;
    private ImageView finishSelf;

    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_application_detail);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);
        initView();

    }

    private void initView(){
        Intent intent=getIntent();
        String nickName=intent.getStringExtra("nickname");
        String msgBody=intent.getStringExtra("msgbody");
        String rs=intent.getStringExtra("rs");
        number=intent.getStringExtra("number");
        String type=intent.getStringExtra("type");
        finishSelf=(ImageView)findViewById(R.id.finish_application_detail);
        finishSelf.setOnClickListener(this);

        peopleIv=(CircleImageView)findViewById(R.id.application_detail_iv);
        peopleNickname=(TextView)findViewById(R.id.application_nickname);
        peopleNumber=(TextView)findViewById(R.id.application_detail_number);
        toPage=(RelativeLayout)findViewById(R.id.application_to_page);
        toPage.setOnClickListener(this);
        checkInfo=(TextView)findViewById(R.id.application_detail_info);
        refusePeople=(TextView)findViewById(R.id.application_refuse);
        refusePeople.setOnClickListener(this);
        acceptPeople=(TextView)findViewById(R.id.application_accept);
        acceptPeople.setOnClickListener(this);
        acceptedLay=(LinearLayout)findViewById(R.id.accepted_application_lay);
        refuseOrAcceptLay=(LinearLayout)findViewById(R.id.accept_or_refuse);
        peopleNickname.setText(nickName);
        checkInfo.setText(msgBody);
        peopleNumber.setText(number);
        if (rs.equals("nothandle")){
            refuseOrAcceptLay.setVisibility(View.VISIBLE);
            acceptedLay.setVisibility(View.GONE);
        }else {
            refuseOrAcceptLay.setVisibility(View.GONE);
            acceptedLay.setVisibility(View.VISIBLE);
        }

        String icpath = MyFileTools.getUserIconPath(number);
        MyViewTools.setCircleImage(peopleIv,icpath);

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.application_to_page :
                Intent intent=new Intent(OtherApplicationDetailActivity.this,PersonalHomePageActivity.class);
                intent.putExtra("from","otherpeople");
                intent.putExtra("id",number);
                startActivity(intent);
                break;
            case R.id.application_refuse:
                break;
            case R.id.application_accept:
                break;
            case R.id.finish_application_detail:
                finish();
                default:
                    break;
        }
    }
}
