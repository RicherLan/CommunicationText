package thefirstchange.example.com.communicationtext.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import thefirstchange.example.com.communicationtext.MainActivity;

public class BaseFragment extends Fragment {
    Activity mActivity;
    AppCompatActivity mAppCompatActivity;


    public void onViewCreated(View view,Bundle savedInstancedState){
        super.onViewCreated(view,savedInstancedState);
        mActivity=(MainActivity)getActivity();

        setHasOptionsMenu(true);

    }
    public  android.support.v7.widget.Toolbar initToolBar(int toolBarId,int title){
        AppCompatActivity mAppCompatActivity=(AppCompatActivity)mActivity;
        if (mActivity==null){

            return null;
        }
        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar)mAppCompatActivity.findViewById(toolBarId);
        mAppCompatActivity.setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar=mAppCompatActivity.getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        return toolbar;
    }
}
