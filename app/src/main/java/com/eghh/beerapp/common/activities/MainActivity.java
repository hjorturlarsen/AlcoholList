package com.eghh.beerapp.common.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;

import com.eghh.beerapp.common.fragments.have_drunk;
import com.eghh.beerapp.common.view.SlidingTabLayout;
import com.eghh.beerapp.common.fragments.R;
import com.eghh.beerapp.common.fragments.achievements;
import com.eghh.beerapp.common.fragments.explore;
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
        tab.setCustomTabView(R.layout.custom_tab, 0);
        CustomPagerAdapter pagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        tab.setViewPager(pager);
        pager.setCurrentItem(2);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public class CustomPagerAdapter extends FragmentPagerAdapter
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
                return have_drunk.newInstance();
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
            private int[] res ={
                  R.drawable.i_achive,
                  R.drawable.i_empty_beer,
                  R.drawable.i_search,
                  R.drawable.i_full_beer,
                  R.drawable.i_explore
            };
        @Override
        public CharSequence getPageTitle(int position) {
            Drawable image = getResources().getDrawable(res[position]);
            image.setBounds(0,0,image.getIntrinsicWidth(), image.getIntrinsicHeight());
            SpannableString sb = new SpannableString(" ");
            ImageSpan imgSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imgSpan, 0 , 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }
    }
}