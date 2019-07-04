package thefirstchange.example.com.communicationtext.contacts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import thefirstchange.example.com.communicationtext.R;


public class LetterView extends LinearLayout{
    private Context mContext;
    private CharacterClickListener mListener;

    public LetterView(Context context,AttributeSet attrs) {
        super(context, attrs);
        mContext = context;//接收传进来的上下文
        setOrientation(VERTICAL);
        initView();
    }

    private void initView(){
        addView(buildImageLayout());

        for (char i = 'A';i<='Z';i++){
            final String character = i + "";
            TextView tv = buildTextLayout(character);
            addView(tv);
        }

        addView(buildTextLayout("#"));
    }

    private TextView buildTextLayout(final String character){
        LayoutParams layoutParams =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1);
        TextView tv = new TextView(mContext);
        tv.setLayoutParams(layoutParams);
        tv.setGravity(Gravity.CENTER);
        tv.setClickable(true);
        tv.setText(character);
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null){
                    mListener.clickCharacter(character);
                }
            }
        });
        return tv;
    }
    private ImageView buildImageLayout() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);

        ImageView iv = new ImageView(mContext);
        iv.setLayoutParams(layoutParams);

        iv.setBackgroundResource(R.color.white);

        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.clickArrow();
                }
            }
        });
        return iv;
    }

    public void setCharacterListener(CharacterClickListener listener) {
        mListener = listener;
    }

    public interface CharacterClickListener {
        void clickCharacter(String character);

        void clickArrow();
    }
}
