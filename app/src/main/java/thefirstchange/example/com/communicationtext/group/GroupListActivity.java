package thefirstchange.example.com.communicationtext.group;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.activity.CreateGroupActivity;
import thefirstchange.example.com.communicationtext.activity.FindFriendOrGroupActivity;
import thefirstchange.example.com.communicationtext.adapter.ChooseGroupAdapter;
import thefirstchange.example.com.communicationtext.contacts.LetterView;
import thefirstchange.example.com.communicationtext.util.DensityUtiltwo;

public class GroupListActivity extends BaseForCloseActivity implements View.OnClickListener{

    private static String MY = "thefirstchange.example.com.communicationtext.GROUPLIST";

    private RecyclerView contactList;
    private LinearLayoutManager layoutManager;
    private LetterView letterView;
    private ChooseGroupAdapter adapter;
    private ImageView finishSelf;
    private ImageView addOrCreateGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_group);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);


        initView();
        letterView.setCharacterListener(new LetterView.CharacterClickListener() {
            @Override
            public void clickCharacter(String character) {
                layoutManager.scrollToPositionWithOffset(adapter.getScrollPosition(character),0);
            }

            @Override
            public void clickArrow() {
                layoutManager.scrollToPositionWithOffset(0,0);
            }
        });
    }

    public void initView(){

        contactList = (RecyclerView) findViewById(R.id.group_list);
        letterView = (LetterView) findViewById(R.id.group_letter_view);
        finishSelf=(ImageView)findViewById(R.id.finish_group_list);
        addOrCreateGroup=(ImageView)findViewById(R.id.add_or_create_group);
        addOrCreateGroup.setOnClickListener(this);
        finishSelf.setOnClickListener(this);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ChooseGroupAdapter(this,GroupListActivity.this);

        contactList.setLayoutManager(layoutManager);
        contactList.addItemDecoration(new DividerItemDecoration(this, thefirstchange.example.com.communicationtext.contacts.DividerItemDecoration.VERTICAL_LIST));
        contactList.setAdapter(adapter);

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.finish_group_list:
                finish();
                break;
            case R.id.add_or_create_group:
                showDeleteDialog();
                break;
        }
    }

    private void showDeleteDialog(){
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_create_add_group, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtiltwo.dp2px(this, 16f);
        params.bottomMargin = DensityUtiltwo.dp2px(this, 16f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        TextView toAddGroup=(TextView)contentView.findViewById(R.id.to_add_group);
        toAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GroupListActivity.this,FindFriendOrGroupActivity.class);
                intent.putExtra("is","group");
                startActivity(intent);
                bottomDialog.dismiss();
            }
        });
        TextView toCreateGroup=(TextView)contentView.findViewById(R.id.to_create_group);
        toCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GroupListActivity.this,CreateGroupActivity.class);
                startActivity(intent);
                bottomDialog.dismiss();
            }
        });

        TextView cancel=(TextView)contentView.findViewById(R.id.cancel_add_group);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });

    }


    protected  void onDestroy(){
        super.onDestroy();

        unregisterReceiver(broadcastReceiver);

    }

    protected  void onResume() {

        super.onResume();

        adapter = new ChooseGroupAdapter(this,GroupListActivity.this);
        contactList.setLayoutManager(layoutManager);
        contactList.addItemDecoration(new DividerItemDecoration(this, thefirstchange.example.com.communicationtext.contacts.DividerItemDecoration.VERTICAL_LIST));
        contactList.setAdapter(adapter);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MY);
        registerReceiver(broadcastReceiver,intentFilter);
//        if(shouldRefresh){

//            shouldRefresh = false;
//        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            adapter.notifyDataSetChanged();
        }
    };


}
