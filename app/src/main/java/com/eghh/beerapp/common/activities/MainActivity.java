package com.eghh.beerapp.common.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import com.eghh.beerapp.common.view.SlidingTabLayout;
import com.eghh.beerapp.common.fragments.R;
import com.eghh.beerapp.common.fragments.achievements;
import com.eghh.beerapp.common.fragments.explore;
import com.eghh.beerapp.common.fragments.favorites;
import com.eghh.beerapp.common.fragments.search;
import com.eghh.beerapp.common.fragments.to_drink;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends SampleActivityBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
        SlidingTabLayout tab = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        pager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));
        tab.setViewPager(pager);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private class CustomPagerAdapter extends FragmentPagerAdapter
    {
        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i)
        {
            if (i == 0)
            {
                return achievements.newInstance();
            }
            else if (i == 1)
            {
                return favorites.newInstance();
            }
            else if(i == 2)
            {
                return search.newInstance();
            }
            else if(i == 3)
            {
                return to_drink.newInstance();
            }
            else if(i == 4)
            {
                return explore.newInstance();
            }
            return null;
        }

        @Override
        public int getCount()
        {
            return 5;
        }

        Resources res = getResources();
        String[] titles = res.getStringArray(R.array.titles);
        @Override
        public CharSequence getPageTitle(int position) {
           return titles[position];
        }
    }
}