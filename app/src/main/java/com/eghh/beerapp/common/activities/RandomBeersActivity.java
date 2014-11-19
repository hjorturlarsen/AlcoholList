package com.eghh.beerapp.common.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.eghh.beerapp.common.BeerModel;
import com.eghh.beerapp.common.JSONParser;
import com.eghh.beerapp.common.app.AppController;
import com.eghh.beerapp.common.fragments.R;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Gets random beers from the brewerydb.com API and displays them
 */

//TODO: activate buttons

public class RandomBeersActivity extends FragmentActivity{

    private BeerModel random_beer;
    private Context mContext;
    TextView beer_name, beer_abv, beer_description, beer_website, beer_country, beer_brewery;
    NetworkImageView beer_image;
    ImageLoader imageLoader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Initialize();
    }

    /**
     * Initialize GUI Objects and variables.
     */
    public void Initialize() {
        setContentView(R.layout.activity_beer_info);
        mContext = RandomBeersActivity.this;
        beer_name = (TextView) findViewById(R.id.beer_name);
        beer_abv = (TextView) findViewById(R.id.beer_abv);
        beer_description = (TextView) findViewById(R.id.beer_description);
        beer_website = (TextView) findViewById(R.id.beer_website);
        beer_country = (TextView) findViewById(R.id.beer_country);
        beer_brewery = (TextView) findViewById(R.id.beer_brewery);
        beer_image = (NetworkImageView) findViewById(R.id.beer_image);

        get_random_beer();
    }

    /**
     * Calls the AsyncTask
     * Get random beer from the brewerydb.com API
     */
    public void get_random_beer(){
        String request_URL = "http://api.brewerydb.com/v2/beer/random?key=0bb957499c324525521a89186b87e785&withBreweries=Y";
        new getRandomBeer(mContext).execute(request_URL);
    }

    public void show_random_beer(){
        if(imageLoader == null)
        {
            imageLoader = AppController.getInstance().getImageLoader();
        }

        beer_name.setText(random_beer.beerName);
        beer_abv.setText("Alc. " + random_beer.beerPercentage + "% Alc.");
        beer_description.setText(random_beer.beerDesc);
        beer_website.setText(random_beer.website);
        beer_country.setText(random_beer.country);
        beer_brewery.setText(random_beer.brewery);
        beer_image.setImageUrl(random_beer.mImage, imageLoader);
    }


    /////////////////////////////////////////////////////////////////////
    /**
     * Sends request to the brewerydb.com API and asks for a random beer.
     * Does that several times and puts the beers in an ArrayList
     */
    private class getRandomBeer extends AsyncTask<String, Void, BeerModel> {
        private ProgressDialog progressDialog;
        private Context mContext;

        public getRandomBeer(Context mContext){
            this.mContext = mContext;
        }

        /**
         * Display a dialog while we get the information
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(mContext);
            this.progressDialog.show();
            this.progressDialog.setMessage("Fetching a random beer...");
        }

        /**
         * Sends a request to the database and adds a random beer to our ArrayList
         */
        @Override
        protected BeerModel doInBackground(String... args) {
            JSONParser jsonParser = new JSONParser();
            String[] beerModelArray = new String[12];
            try {

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
                random_beer = new BeerModel(beerModelArray);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return random_beer;
        }

        /**
         * Remove the dialog.
         * Display the random beer
         */
        @Override
        protected void onPostExecute(final BeerModel random_beer) {
            progressDialog.dismiss();
            show_random_beer();
        }
    }
}
