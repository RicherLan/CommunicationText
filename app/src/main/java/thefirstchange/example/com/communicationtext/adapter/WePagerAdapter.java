package thefirstchange.example.com.communicationtext.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

public class WePagerAdapter extends FragmentPagerAdapter {

    private FragmentManager mFragmentManger;
    private List<Fragment> mList;

    public WePagerAdapter(FragmentManager fragmentManager, List<Fragment> list){
        super(fragmentManager);
        mFragmentManger=fragmentManager;
        mList=list;
    }

    public Fragment getItem(int arg0){
        return mList.get(arg0);
    }

    public int getCount(){
        return mList.size();
    }
}
