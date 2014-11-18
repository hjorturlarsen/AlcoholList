package com.eghh.beerapp.common.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.eghh.beerapp.common.BeerModel;
import com.eghh.beerapp.common.GridViewAdapter;
import com.eghh.beerapp.common.JSONParser;
import com.eghh.beerapp.common.fragments.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Gets random beers from the brewerydb.com API and displays them
 */
public class RandomBeersActivity extends Activity{

    public ArrayList<BeerModel> random_beers = new ArrayList<BeerModel>();
    GridView gridView;
    String request_URL = "http://api.brewerydb.com/v2/beer/random?key=0bb957499c324525521a89186b87e785&withBreweries=Y";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_beers);
        gridView = (GridView) findViewById(R.id.gridview_random_beers);

        new getRandomBeers(getApplicationContext()).execute(request_URL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sends request to the brewerydb.com API and asks for a random beer.
     * Does that several times and puts the beers in an ArrayList
     */
    private class getRandomBeers extends AsyncTask<String, Void, ArrayList<BeerModel>> {
        private ProgressDialog progressDialog;
        public Context mContext;

        public getRandomBeers(Context context){
            this.mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(RandomBeersActivity.this);
            this.progressDialog.show();
            this.progressDialog.setMessage("Fetching random beers...");

        }

        @Override
        protected ArrayList<BeerModel> doInBackground(String... args) {
            JSONParser jsonParser = new JSONParser();
            String[] beerModelArray = new String[15];
            try {
                for (int i = 0; i < 6; i++) {
                    JSONObject beer = jsonParser.getJSONObjectFromUrl(args[0]);
                    JSONObject brewery = beer.getJSONArray("breweries").getJSONObject(0);

                    beerModelArray[0] = beer.getString("id");
                    beerModelArray[1] = beer.getString("name");
                    beerModelArray[2] = beer.has("abv") ? beer.getString("abv") : "0";
                    beerModelArray[3] = beer.has("isOrganic") ? beer.getString("isOrganic") : "N";
                    beerModelArray[4] = beer.has("style") ? beer.getJSONObject("style").getString("description") : beer.getString("description");
                    beerModelArray[5] = beer.has("glass") ? beer.getJSONObject("glass").getString("name") : "No specific glassware";
                    beerModelArray[6] = "null";
                    beerModelArray[7] = beer.has("labels") ? beer.getJSONObject("labels").getString("medium") : "http://i240.photobucket.com/albums/ff100/turta_/beer_PNG2330_zpsa1794501.png";
                    beerModelArray[8] = beer.has("labels") ? beer.getJSONObject("labels").getString("large") : "http://i240.photobucket.com/albums/ff100/turta_/beer_PNG2330_zpsa1794501.png";
                    beerModelArray[9] = brewery.has("website") ? brewery.getString("website") : "No website";
                    beerModelArray[10] = brewery.has("locations") ? brewery.getJSONArray("locations").getJSONObject(0).getJSONObject("country").getString("displayName") : "No location";
                    beerModelArray[11] = brewery.has("name") ? brewery.getString("name") : "No brewery";
                    BeerModel beerModel = new BeerModel(beerModelArray);
                    random_beers.add(beerModel);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return random_beers;
        }

        @Override
        protected void onPostExecute(final ArrayList<BeerModel> random_beers) {
            progressDialog.dismiss();
            final GridViewAdapter gridViewAdapter = new GridViewAdapter(mContext, random_beers);
            gridView.setAdapter(gridViewAdapter);
        }
    }
}
