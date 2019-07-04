package thefirstchange.example.com.communicationtext.activity.myselfactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class AlterHisNameActivity extends BaseForCloseActivity implements View.OnClickListener{

    private ImageView finishSelf;
    private EditText newName;
    private TextView saveName;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_his_name);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);
        Intent intent=getIntent();
        phoneNumber=intent.getStringExtra("number");
        initView();
    }

    private void initView(){
        finishSelf=(ImageView)findViewById(R.id.finish_alter_friend_name);
        finishSelf.setOnClickListener(this);
        newName=(EditText)findViewById(R.id.write_new_name);
        saveName=(TextView)findViewById(R.id.save_new_name);
        saveName.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.finish_alter_friend_name:
                finish();
                break;
            case R.id.save_new_name:

                if(!NetworkUtils.isConnected(AlterHisNameActivity.this)){
                    Toast.makeText(AlterHisNameActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String name=newName.getText().toString();
                if (name.isEmpty()){
                    Toast.makeText(AlterHisNameActivity.this,"备注不能为空",Toast.LENGTH_SHORT).show();
                }else {

                            SendToServer.changeFriendRemark(phoneNumber,name);


                    StaticAllList.friendList.get(phoneNumber).setRemark(name);
                    finish();

                }
                break;
                default:
                    break;

        }
    }
}
