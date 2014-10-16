package com.eghh.beerapp.common.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;

import com.eghh.beerapp.common.view.SlidingTabLayout;
import com.eghh.beerapp.common.fragments.R;
import com.eghh.beerapp.common.fragments.achievements;
import com.eghh.beerapp.common.fragments.explore;
import com.eghh.beerapp.common.fragments.favorites;
import com.eghh.beerapp.common.fragments.search;
import com.eghh.beerapp.common.fragments.to_drink;
import com.eghh.beerapp.common.BeerModel;
import com.eghh.beerapp.common.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends SampleActivityBase {
    private static String key = "0bb957499c324525521a89186b87e785";
    private static String url = "http://api.brewerydb.com/v2/search?key=" + key + "&q=";
    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
    //ArrayList<JSONObject> breweryList = new ArrayList<JSONObject>();
    ArrayList<BeerModel> beerList = new ArrayList<BeerModel>();
    private static final String type = "type";
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

    //----Search----
    public void setSearchQuery(String s){
        url = url + s;
        parseJson();
    }
    public void parseJson(){
        int x = 10;
        int y = 13;
        int z = x + y;
        new ProgressTask(MainActivity.this).execute();
        //
    }
    private class ProgressTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog;

        public ProgressTask(SampleActivityBase activity){

            Log.i("1", "Called");
            context = activity;

//            dialog = new ProgressDialog(context);
        }

        private Context context;

        protected void onPreExecute() {
//            this.dialog.setMessage("Progress start");
//            this.dialog.show();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//            }
            ArrayList<BeerModel> asd = beerList;
        }

        protected Boolean doInBackground(final String... args) {

            JSONParser jParser = new JSONParser();
            JSONArray json = jParser.getJSONFromUrl(url);

            for (int i = 0; i < json.length(); i++) {

                try {
                    JSONObject beer = json.getJSONObject(i);
                    String vtype = beer.getString(type);
                    HashMap<String, String> map = new HashMap<String, String>();
//                    if (vtype.equals("brewery")){
//                        breweryList.add(c);
//                    }
                    if(vtype.equals("beer")){

                        String beerId = beer.getString("id");
                        String beerName = beer.getString("name");
                        String percentage = beer.getString("abv");
                        String description = beer.has("style") ? beer.getJSONObject("style").getString("description") : beer.getString("description");
                        BeerModel bm = new BeerModel(beerId, beerName, description, percentage);
                        beerList.add(bm);
                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}