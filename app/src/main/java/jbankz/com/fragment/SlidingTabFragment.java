package jbankz.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jbankz.com.R;
import jbankz.com.adapter.TabsPagerAdapter;

/**
 * Created by King Jaycee on 26/10/2017.
 */

public class SlidingTabFragment extends Fragment {

    ViewPager viewPager;
    TabsPagerAdapter tabsPagerAdapter;
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //initialize widgets
        viewPager = view.findViewById(R.id.viewpager);
        tabsPagerAdapter = new TabsPagerAdapter(getFragmentManager());
        viewPager.setAdapter(tabsPagerAdapter);
        viewPager.setHorizontalScrollBarEnabled(false);

        tabLayout = view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
}
