package thefirstchange.example.com.communicationtext.view.drop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class WaterDropNoAction extends RelativeLayout {
    private Paint mPaint = new Paint();
    private TextView mTextView;

    public WaterDropNoAction(Context context) {
        super(context);
        init();
    }

    public WaterDropNoAction(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setText(String str) {
        mTextView.setText(str);
    }

    public void setTextSize(int size) {
        mTextView.setTextSize(size);
    }



    private void init() {
        mPaint.setAntiAlias(true);
        if (VERSION.SDK_INT > 11) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        mTextView = new TextView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mTextView.setTextSize(13);
        mTextView.setTextColor(0xffffffff);
        mTextView.setLayoutParams(params);
        addView(mTextView);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        mPaint.setColor(0xffff0000);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mPaint);
        super.dispatchDraw(canvas);
    }


}
