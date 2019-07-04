package thefirstchange.example.com.communicationtext.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.adapter.MoreCommentAdapter;
import thefirstchange.example.com.communicationtext.dongtai.Dongtai;
import thefirstchange.example.com.communicationtext.widget.CollapsibleTextView;

public class ShowMoreCommentActivity extends BaseForCloseActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MoreCommentAdapter adapter;
    private List<Dongtai> dongtaiList;
    private CircleImageView ivImage;
    private TextView nickNameText;
    private TextView contentText;
    private TextView timeText;
    private TextView replyText;
    private ImageView finishSelf;
    private CollapsibleTextView collapsibleTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more_comment);
        initView();
    }


    private void initView(){
//        recyclerView=(RecyclerView)findViewById(R.id.more_comment_recycler);
//        linearLayoutManager=new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        adapter=new MoreCommentAdapter(this,dongtaiList);
//        recyclerView.setAdapter(adapter);
        collapsibleTextView=(CollapsibleTextView)findViewById(R.id.more_comment_content);


        ivImage=(CircleImageView)findViewById(R.id.more_comment_iv);
        nickNameText=(TextView)findViewById(R.id.more_comment_nickname);
       // contentText=(TextView)findViewById(R.id.more_comment_content);
        timeText=(TextView)findViewById(R.id.more_comment_time);
        replyText=(TextView)findViewById(R.id.more_comment_reply);
        replyText.setOnClickListener(this);
        finishSelf=(ImageView)findViewById(R.id.finish_show_datil);
        finishSelf.setOnClickListener(this);
        String s="jkfsjflkjsdlkfjlksd飞机扣水电费健康独守空房了三等奖大家看是否健康深刻的法律速度快是考虑对方家里看电视开始的减肥路上的来说肯定放假了速度快老师的咖啡机";
        collapsibleTextView.setDesc(s, TextView.BufferType.NORMAL);
//        final SpannableStringBuilder style = new SpannableStringBuilder();
//        style.append(s);
//        ClickableSpan clickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//                Toast.makeText(ShowMoreCommentActivity.this, "触发点击事件!", Toast.LENGTH_SHORT).show();
//            }
//        };
//        style.setSpan(clickableSpan, 11, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        contentText.setText(style);
//        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0000FF"));
//        style.setSpan(foregroundColorSpan, 11, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        contentText.setMovementMethod(LinkMovementMethod.getInstance());
//        contentText.setText(style);




    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.finish_show_datil:
                finish();
                break;
            case R.id.more_comment_reply:

                default:
                    break;
        }
    }
}
