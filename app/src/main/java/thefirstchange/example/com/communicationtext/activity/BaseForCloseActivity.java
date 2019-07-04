package thefirstchange.example.com.communicationtext.activity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import thefirstchange.example.com.communicationtext.BroadCast.NetWorkStateReceiver;
import thefirstchange.example.com.communicationtext.util.ActivityCollector;

public class BaseForCloseActivity extends AppCompatActivity {

    NetWorkStateReceiver netWorkStateReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (netWorkStateReceiver == null) {
            netWorkStateReceiver = new NetWorkStateReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateReceiver, filter);

    }


    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        if(netWorkStateReceiver!=null){
            unregisterReceiver(netWorkStateReceiver);
        }

    }


}
