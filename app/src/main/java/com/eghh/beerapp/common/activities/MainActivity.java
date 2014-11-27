package com.eghh.beerapp.common.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import com.eghh.beerapp.common.fragments.have_drunk;
import com.eghh.beerapp.common.slidingtabs.SlidingTabLayout;
import com.eghh.beerapp.common.fragments.R;
import com.eghh.beerapp.common.fragments.achievements;
import com.eghh.beerapp.common.fragments.explore;
import com.eghh.beerapp.common.fragments.search;
import com.eghh.beerapp.common.fragments.to_drink;
import com.eghh.beerapp.common.utilities.DataBaseHelper;

import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 */
public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
    ViewPager viewPager;
    SlidingTabLayout tab;
    CustomPagerAdapter customPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Initialize();
    }

    public void Initialize() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        customPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(customPagerAdapter);
        tab = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        tab.setCustomTabView(R.layout.custom_tab, 0);
        tab.setViewPager(viewPager);
        tab.setOnPageChangeListener(this);
        viewPager.setCurrentItem(2);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {
        if (i == 1) {
            Fragment fragment = customPagerAdapter.getRegisteredFragment(i);
            have_drunk page = ((have_drunk) fragment);
            ArrayList<HashMap<String, Object>> old_rated_beers = DataBaseHelper.getRatedList();
            if(old_rated_beers != page.rated_beers){
                page.Initialize();
            }
        }

        else if (i == 3) {
            Fragment fragment = customPagerAdapter.getRegisteredFragment(i);
            to_drink page = ((to_drink) fragment);
            ArrayList<HashMap<String, Object>> current_to_drink = DataBaseHelper.getToDrinkList();
            if(current_to_drink != page.to_drink){
                page.Initialize();
            }
        }
    }

    @Override
    public void onPageSelected(int i) {
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public class CustomPagerAdapter extends FragmentStatePagerAdapter
    {
        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

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
            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            SpannableString spannableString = new SpannableString(" ");
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }
}