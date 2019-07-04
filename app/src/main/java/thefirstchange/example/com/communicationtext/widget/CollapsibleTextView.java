package thefirstchange.example.com.communicationtext.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.ShowMoreCommentActivity;

public class CollapsibleTextView extends LinearLayout implements View.OnClickListener {

    private static final int DEFAULT_MAX_LINE_COUNT = 2;

    private static final int COLLAPSIBLE_STATE_NONE = 0;
    private static final int COLLAPSIBLE_STATE_SHRINKUP = 1;
    private static final int COLLAPSIBLE_STATE_SPREAD = 2;

    private TextView desc;
    private TextView descOp;

    private String shrinkup;
    private String spread;
    private int mState;
    private boolean flag;

    public CollapsibleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //        两种定义形式
//        shrinkup = context.getString(R.string.desc_shrinkup);
//        spread = context.getString(R.string.desc_spread);
        shrinkup="收起";
        spread="查看更多";
        View view = inflate(context, R.layout.collapsible_textview, this);
        view.setPadding(0, -1, 0, 0);
        desc = (TextView) view.findViewById(R.id.desc_tv);
        descOp = (TextView) view.findViewById(R.id.desc_op_tv);
        descOp.setOnClickListener(this);



    }

    public CollapsibleTextView(Context context) {
        this(context, null);
    }
    /**
     1.
     2. 对外提供暴漏的方法，为文本提供数据
     3. @param charSequence  文本内容是什么
     4. @param bufferType
     */
    public final void setDesc(CharSequence charSequence, TextView.BufferType bufferType) {
        //desc.setText(charSequence, bufferType);
        mState = COLLAPSIBLE_STATE_SPREAD;
        final SpannableStringBuilder style = new SpannableStringBuilder();
        style.append(charSequence);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getContext(), "触发点击事件!", Toast.LENGTH_SHORT).show();
            }
        };
        style.setSpan(clickableSpan, 11, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        desc.setText(style);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0000FF"));
        style.setSpan(foregroundColorSpan, 11, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        desc.setMovementMethod(LinkMovementMethod.getInstance());
        desc.setText(style);
        requestLayout();
    }

    @Override
    public void onClick(View v) {
        flag = false;
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!flag) {
            flag = true;
            if (desc.getLineCount() <= DEFAULT_MAX_LINE_COUNT) {
                mState = COLLAPSIBLE_STATE_NONE;
                descOp.setVisibility(View.GONE);
                desc.setMaxLines(DEFAULT_MAX_LINE_COUNT + 1);
            } else {
                post(new InnerRunnable());
            }
        }
    }

    class InnerRunnable implements Runnable {
        @Override
        public void run() {
            if (mState == COLLAPSIBLE_STATE_SPREAD) {
                desc.setMaxLines(DEFAULT_MAX_LINE_COUNT);
                descOp.setVisibility(View.VISIBLE);
                descOp.setText(spread);
                mState = COLLAPSIBLE_STATE_SHRINKUP;
            } else if (mState == COLLAPSIBLE_STATE_SHRINKUP) {
                desc.setMaxLines(Integer.MAX_VALUE);
                descOp.setVisibility(View.VISIBLE);
                descOp.setText(shrinkup);
                mState = COLLAPSIBLE_STATE_SPREAD;
            }
        }
    }
}
