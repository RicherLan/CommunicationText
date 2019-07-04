package thefirstchange.example.com.communicationtext.fragment.pagefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thefirstchange.example.com.communicationtext.R;

/**
 * Created by WZJSB-01 on 2017/12/5.
 */

public class Fragment_Three extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_viewpager_layout, null);
        return view;
    }
}
