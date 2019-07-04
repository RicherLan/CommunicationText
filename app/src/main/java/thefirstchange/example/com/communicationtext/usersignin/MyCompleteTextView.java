package thefirstchange.example.com.communicationtext.usersignin;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

public class MyCompleteTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {
    private RegisterSchoolInfoActivity mainActivity;
    private int flag = 1;
    public void setThis(RegisterSchoolInfoActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public MyCompleteTextView(Context context) {

        super(context);
    }

    public MyCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if(flag==1){
            flag = 0;
        }else{
            flag = 1;
        }
        if(flag==1){
            return true;
        }
       // Log.e("aaa","22222222222222222222222222222222222222222222");
       if(isSoftShowing()){
         //  Log.e("aaa","4444444444444444444444444444444444444444444444442");
           hideKeyboard();
            return true;
        }
        if(this.isPopupShowing()){
            this.setText("");
            return true;
        }
       mainActivity.finish();
       return true;
       // throw new RuntimeException("Stub!");
    }

    private boolean hideKeyboard() {

        InputMethodManager imm = (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            if (mainActivity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(mainActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return true;
            }
        }
        return false;
    }

    //判断键盘是否打开
    private boolean isSoftShowing() {

//        Log.d("aa","1111111111111111111111111111111111111");

        //获取当屏幕内容的高度
        int screenHeight = mainActivity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        //DecorView即为activity的顶级view
        mainActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        return screenHeight*2/3 > rect.bottom;

    }



}
