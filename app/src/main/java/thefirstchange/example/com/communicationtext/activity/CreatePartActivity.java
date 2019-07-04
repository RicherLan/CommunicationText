package thefirstchange.example.com.communicationtext.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.adapter.CreatePartAdapter;
import thefirstchange.example.com.communicationtext.corpration.CreateCorpResActivity;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;

public class CreatePartActivity extends BaseForCloseActivity{

    private static final String MY = "thefirstchange.example.com.communicationtext.CREATEPART";
    private Timer timer = new Timer();

    String corname;
    String cortype;
    String corinfo;
    int year;
    int xueqi;
    int corNumebr;
    private ImageView finishSelf;
    private TextView submit;
    private CreatePartAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    boolean iswrong = false;    //系统错误,请稍后再试

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_part);
        initView();

    }

    private void initView() {
        Intent intent=getIntent();
        corname=intent.getStringExtra("corname");
        cortype=intent.getStringExtra("cortype");
        corinfo=intent.getStringExtra("corInfo");
        corNumebr=intent.getIntExtra("cornumber",0);
        year=intent.getIntExtra("year",-1);
        xueqi=intent.getIntExtra("xueqi",-1);

        if(corNumebr==0||year==-1||xueqi==-1){
            Toast.makeText(CreatePartActivity.this, "系统错误,请稍后再试!", Toast.LENGTH_SHORT).show();
            finish();
        }

        finishSelf=(ImageView)findViewById(R.id.finish_create_part);
        finishSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(CreatePartActivity.this,CorporationCreateActivity.class);
                startActivity(intent1);
                finish();

            }
        });
        submit=(TextView)findViewById(R.id.submit_create_part);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String partname = "";
                String[] partNeme=adapter.getPartName();
                for(int i=0;i<corNumebr;i++){
                    if(partNeme[i]==null||partNeme[i].trim().equals("")){
                        int t=i+1;
                        Toast.makeText(CreatePartActivity.this,"第"+t+"分部名为空!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    partname+=partNeme[i]+" ";
                }

                final String finalPartname = partname;

                SendToServer.createCorp(corname,cortype,corinfo, finalPartname,year,xueqi);


                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        CreatePartActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CreatePartActivity.this,"服务器繁忙,请稍后再试!",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                },4000);


            }
        });

        adapter=new CreatePartAdapter(this,corNumebr);
        recyclerView=(RecyclerView)findViewById(R.id.create_part_recycler);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


    }

    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(MY);
        registerReceiver(MyMessageReceiver, filter);

    }


    protected void onDestroy() {
        super.onDestroy();
        if(!iswrong){
            unregisterReceiver(MyMessageReceiver);
            if(timer!=null){
                timer.cancel();
            }
        }

    }

    BroadcastReceiver MyMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MY)) {
                timer.cancel();
                String rs = intent.getStringExtra("rs");
                if (rs.equals("ok")) {

                    int count = intent.getIntExtra("count",-1);
                    String password = intent.getStringExtra("password");
                    final int groupid =intent.getIntExtra("groupid",-1);

                    Intent intent1 =new Intent(CreatePartActivity.this, CreateCorpResActivity.class);
                    intent1.putExtra("count",count);
                    intent1.putExtra("password",password);
                    intent1.putExtra("groupid",groupid);
                    CreatePartActivity.this.startActivity(intent1);
                    finish();


                    SendToServer.addGroupToListByGroupid(groupid);




                }else{
                    Toast.makeText(CreatePartActivity.this,"服务器繁忙,请稍后再试!",Toast.LENGTH_SHORT).show();
                }
            }
        }

    };



}
