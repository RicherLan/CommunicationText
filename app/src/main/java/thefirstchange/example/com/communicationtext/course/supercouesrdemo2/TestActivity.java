package thefirstchange.example.com.communicationtext.course.supercouesrdemo2;

import android.os.Bundle;
import android.support.annotation.Nullable;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;

public class TestActivity extends BaseForCloseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sametime_course_choose);
    }
}
