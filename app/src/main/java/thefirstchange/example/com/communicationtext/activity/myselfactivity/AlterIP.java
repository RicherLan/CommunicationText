package thefirstchange.example.com.communicationtext.activity.myselfactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.login.LoginActivity;
import thefirstchange.example.com.communicationtext.service.NettyService;

public class AlterIP extends BaseForCloseActivity {
    private EditText ipEd;
    private Button button;
    String old;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_ip);
        ipEd=(EditText)findViewById(R.id.alterip);
        ipEd.setText(Config.ServerIP);
        old=Config.ServerIP;
        button=(Button)findViewById(R.id.save_ip);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newip=ipEd.getText().toString().trim();
                if(!newip.isEmpty()){
                    Config.setServerIP(newip);
                    NettyService.disconnect();
                    NettyService.connect();
                }else {
                    Config.setServerIP(old);

                }
                Intent intent=new Intent(AlterIP.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
