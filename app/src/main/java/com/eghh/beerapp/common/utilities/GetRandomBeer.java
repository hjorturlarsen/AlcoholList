package com.eghh.beerapp.common.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.eghh.beerapp.common.activities.BeerInfoActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Sends request to the brewerydb.com API and asks for a random beer.
 * Does that several times and puts the beers in an ArrayList
 */
public class GetRandomBeer extends AsyncTask<String, Void, BeerModel> {
    private ProgressDialog progressDialog;
    private Context mContext;
    private BeerModel random_beer;

    public GetRandomBeer(Context mContext){
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
            beerModelArray[7] = beer.has("labels") ? beer.getJSONObject("labels").getString("medium") : "http://i44.photobucket.com/albums/f7/GilliGold/ic_launcher_zps7171f199.png~original";
            beerModelArray[8] = beer.has("labels") ? beer.getJSONObject("labels").getString("large") : "http://i44.photobucket.com/albums/f7/GilliGold/ic_launcher_zps7171f199.png~original";
            beerModelArray[9] = brewery.has("website") ? brewery.getString("website") : "No website";
            beerModelArray[10] = brewery.has("locations") ? brewery.getJSONArray("locations").getJSONObject(0).getJSONObject("country").getString("displayName") : "No location";
            beerModelArray[11] = brewery.has("name") ? brewery.getString("name") : "No brewery";
            random_beer = new BeerModel(beerModelArray);
        }
        catch (JSONException e) {
            e.printStackTrace();
            beerModelArray[4] = "No description";
            beerModelArray[5] = "No specific glassware";
            beerModelArray[6] = "null";
            beerModelArray[7] = "http://i44.photobucket.com/albums/f7/GilliGold/ic_launcher_zps7171f199.png~original";
            beerModelArray[8] = "http://i44.photobucket.com/albums/f7/GilliGold/ic_launcher_zps7171f199.png~original";
            beerModelArray[9] = "No website";
            beerModelArray[10] = "No location";
            beerModelArray[11] = "No brewery";
            random_beer = new BeerModel(beerModelArray);
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
        Intent beerInfoActivity = new Intent(mContext, BeerInfoActivity.class);
        beerInfoActivity.putExtra("beerModel", random_beer);
        mContext.startActivity(beerInfoActivity);
        //show_random_beer();
    }
}
