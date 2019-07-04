package thefirstchange.example.com.communicationtext.corpration.appointPos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;

/*
    社团   任命职位
 */
public class AppointPosActivity extends BaseForCloseActivity implements View.OnClickListener{


    private CardView zhuxi;
    private CardView buzhang;
    private CardView ganshi;
    private ImageView finishSelf;

    private int groupId;

    private boolean iswrong = false;    //是不是由 系统错误，请稍后再试 造成

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_pos);

        Intent intent=getIntent();
        groupId=intent.getIntExtra("groupid",-1);

        if(groupId==-1){
            Toast.makeText(this,"系统错误，请稍后再试!",Toast.LENGTH_SHORT).show();
            iswrong=true;
            finish();
        }
        initview();
        initevent();

    }

    private void initview(){
        finishSelf=(ImageView)findViewById(R.id.finish_appoint_pos_iv);
        zhuxi = (CardView)this.findViewById(R.id.appoint_zhuxi_cv);
        buzhang = (CardView)this.findViewById(R.id.appoint_buzhang_cv);
        ganshi = (CardView)this.findViewById(R.id.appoint_ganshi_cv);
    }

    private void initevent(){
        finishSelf.setOnClickListener(this);
        zhuxi.setOnClickListener(this);
        buzhang.setOnClickListener(this);
        ganshi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.finish_appoint_pos_iv:
                finish();
                break;
            case R.id.appoint_zhuxi_cv:
                Intent intent = new Intent(AppointPosActivity.this,AppointZhuXiActivity.class);
                intent.putExtra("groupid",groupId);
                startActivity(intent);
                break;
            case R.id.appoint_buzhang_cv:
                Intent intent2 = new Intent(AppointPosActivity.this,AppointBuZhangSelectPartActivity.class);
                intent2.putExtra("groupid",groupId);
                startActivity(intent2);
                break;
            case R.id.appoint_ganshi_cv:
                Intent intent3 = new Intent(AppointPosActivity.this,AppointGanShiActivity.class);
                intent3.putExtra("groupid",groupId);
                startActivity(intent3);
                break;

                default:
                    break;
        }

    }

}
