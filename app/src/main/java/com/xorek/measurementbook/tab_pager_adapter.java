package com.xorek.measurementbook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by MEA on 8/23/2017.
 */

public class tab_pager_adapter extends FragmentPagerAdapter {

    int tabCount;
    long id;

    public tab_pager_adapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
        this.id = id;

    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                contact_frag tab1 = new contact_frag();

                return tab1;
            case 1:
                measure_fag tab2 = new measure_fag();
                return tab2;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return tabCount;
    }
}

