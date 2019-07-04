package thefirstchange.example.com.communicationtext.datescroller.widget.listener;


import thefirstchange.example.com.communicationtext.datescroller.widget.DateScrollerDialog;


/**
 * 日期设置的监听器
 *
 * @author C.L. Wang
 */
public interface OnDateSetListener {
    void onDateSet(DateScrollerDialog timePickerView, long milliseconds);
}
