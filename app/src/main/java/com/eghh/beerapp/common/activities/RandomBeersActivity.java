package com.eghh.beerapp.common.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.eghh.beerapp.common.BeerModel;
import com.eghh.beerapp.common.JSONParser;
import com.eghh.beerapp.common.app.AppController;
import com.eghh.beerapp.common.fragments.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Random;

/**
 * Gets random beers from the brewerydb.com API and displays them
 */
public class RandomBeersActivity extends Activity{

    //derp derp
    public ArrayList<BeerModel> random_beers = new ArrayList<BeerModel>();
    GridView gridView;
    NetworkImageView random_beer_image;
    TextView random_beer_name;
    private static Context context;
    String request_URL = "http://api.brewerydb.com/v2/beer/random?key=0bb957499c324525521a89186b87e785&withBreweries=Y";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gridView = (GridView) findViewById(R.id.gridview_random_beers);

        setContentView(R.layout.activity_random_beers);
        new getRandomBeers().execute(request_URL);
        context = getApplicationContext();
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

    private class getRandomBeers extends AsyncTask<String, Void, ArrayList<BeerModel>> {
        private ProgressDialog progressDialog;

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
                try{
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
                catch (JSONException e){
                    e.printStackTrace();
                }
            return random_beers;
        }

        @Override
        protected void onPostExecute(final ArrayList<BeerModel> randomBeers) {

            super.onPostExecute(randomBeers);
            this.progressDialog.dismiss();

            class gridViewAdapter extends BaseAdapter {
                Context ctx;
                ImageLoader imageLoader = AppController.getInstance().getImageLoader();

                public gridViewAdapter(Context context){
                    this.ctx = context;
                }

                public int getCount(){
                    return random_beers.size();
                }

                public Object getItem(int arg0)
                {
                    return null;
                }

                public long getItemId(int position) {
                    return position;
                }

                /**
                 * Set text and images for each grid box.
                 */
                public View getView(int position, View convertView, ViewGroup parent)
                {
                    if(convertView == null){
                        LayoutInflater mInflater = (LayoutInflater) ctx
                                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                        convertView = mInflater.inflate(R.layout.gridview_box, null);
                    }

                    if(imageLoader == null)
                    {
                        imageLoader = AppController.getInstance().getImageLoader();
                    }

                    random_beer_image = (NetworkImageView) convertView.findViewById(R.id.random_beer_image);
                    random_beer_name = (TextView) convertView.findViewById(R.id.random_beer_name);

                    random_beer_image.setImageUrl(random_beers.get(position).mImage, imageLoader);

                    return convertView;
                }
            }

            gridViewAdapter gridViewAdapter = new gridViewAdapter(context);
            gridView.setAdapter(gridViewAdapter);

            /*
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                /**
                 * Responds to click on a random beer item and displays more information
                 * about that beer.
                 */
            /*
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent beerInfo = new Intent(context, RandomBeersActivity.class);
                    beerInfo.putExtra("beerModel", random_beers.get(position));
                    context.startActivity(beerInfo);
                }
            });
            */


        }
    }
}
