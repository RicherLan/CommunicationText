package thefirstchange.example.com.communicationtext.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.fragment.addfriendgroupfragment.AddFriendFragment;
import thefirstchange.example.com.communicationtext.fragment.addfriendgroupfragment.AddGroupFragment;

public class FindFriendOrGroupActivity extends BaseForCloseActivity implements View.OnClickListener{

    private TextView findFriend;
    private TextView findGroup;
    android.support.v4.app.FragmentManager fragmentManager;
    Fragment fragment;
    private ImageView finishFindImag;
    public String PhoneNu="-1";

    public static FindFriendOrGroupActivity findFriendOrGroupActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend_or_group);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);
        initView();
        findFriendOrGroupActivity = this;
         fragmentManager=getSupportFragmentManager();
         fragment=fragmentManager.findFragmentById(R.id.friend_or_group_container);
         Intent intent=getIntent();
         String is=intent.getStringExtra("is");
         if (is.equals("group")){
             fragment=new AddGroupFragment();
             findFriend.setTextColor(getResources().getColor(R.color.gray));


         }else {
             if(is.equals("nogroup")){
                 PhoneNu=intent.getStringExtra("number");
             }
             fragment=new AddFriendFragment();
             findGroup.setTextColor(getResources().getColor(R.color.gray));


         }
        fragmentManager.beginTransaction().add(R.id.friend_or_group_container,fragment).commit();



    }

    public String getPhone(){
        return PhoneNu;
    }

    private void initView(){
        findFriend=(TextView)findViewById(R.id.choose_add_friend);
        findGroup=(TextView)findViewById(R.id.choose_add_group);
        finishFindImag=(ImageView)findViewById(R.id.finish_find);
        finishFindImag.setOnClickListener(this);

        findGroup.setOnClickListener(this);
        findFriend.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.choose_add_friend:
                fragment=new AddFriendFragment();
                fragmentManager.beginTransaction().replace(R.id.friend_or_group_container,fragment).commit();
                findGroup.setTextColor(getResources().getColor(R.color.gray));
                findFriend.setTextColor(getResources().getColor(R.color.black));
                break;
            case  R.id.choose_add_group:
                fragment=new AddGroupFragment();
                fragmentManager.beginTransaction().replace(R.id.friend_or_group_container,fragment).commit();
                findFriend.setTextColor(getResources().getColor(R.color.gray));
                findGroup.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.finish_find:
                finish();
                default:
                    break;

        }
    }
}
