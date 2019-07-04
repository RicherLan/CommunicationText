package thefirstchange.example.com.communicationtext.activity.myselfactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.corpration.CorpHelpActivity;

public class HelpServiceListActivity extends BaseForCloseActivity implements View.OnClickListener {

    private LinearLayout helpcenter_corp_help_layout;
    private ImageView helpcenter_back_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpservicelist);
        initview();
        initevent();
    }

    private void initview(){
        helpcenter_back_tv = (ImageView)findViewById(R.id.helpcenter_back_tv);
        helpcenter_corp_help_layout = (LinearLayout)findViewById(R.id.helpcenter_corp_help_layout);
    }

    private  void initevent(){
        helpcenter_back_tv.setOnClickListener(this);
        helpcenter_corp_help_layout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.helpcenter_back_tv:
                finish();
                break;

            case R.id.helpcenter_corp_help_layout:
                Intent intent = new Intent(HelpServiceListActivity.this, CorpHelpActivity.class);
                startActivity(intent);
                break;
        }

    }
}
