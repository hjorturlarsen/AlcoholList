package com.eghh.beerapp.slidingtabsbasic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import com.eghh.beerapp.common.activities.SampleActivityBase;
import com.eghh.beerapp.common.view.SlidingTabLayout;

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

        pager.setAdapter(new CustomPageerAdapter(getSupportFragmentManager()));
        tab.setViewPager(pager);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        /*
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);
        */

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        switch(item.getItemId()) {
            case R.id.menu_toggle_log:
                mLogShown = !mLogShown;
                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
                if (mLogShown) {
                    output.setDisplayedChild(1);
                } else {
                    output.setDisplayedChild(0);
                }
                supportInvalidateOptionsMenu();
                return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }

    private class CustomPageerAdapter extends FragmentPagerAdapter
    {
        public CustomPageerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i)
        {
            if (i == 0)
            {
                return BeerFuu.newInstance();
            }
            else if (i == 1)
                return BeerFuu.newInstance();

            return null;
        }

        @Override
        public int getCount()
        {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
           if (position == 0) return "Beeeer";
           if (position == 1) return "Conan";
            return null;
        }
    }
}