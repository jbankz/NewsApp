package jbankz.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import jbankz.com.fragment.BuzzfeedFragment;
import jbankz.com.fragment.CnnFragment;
import jbankz.com.fragment.SportFragment;


/**
 * Created by King Jaycee on 26/10/2017.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                //cnn Fragment
                return new CnnFragment();
            case 1:
                //sport Fragment
                return new SportFragment();
            case 2:
                //buzz Fragment
                return new BuzzfeedFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                //cnn Fragment
                return "CNN";
            case 1:
                //cnn Fragment
                return "TALK SPORT";
            case 2:
                //buzzfeed Fragment
                return "BuzzFeed";
        }
        return null;

    }

}
