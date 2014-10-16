package com.eghh.beerapp.common.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.eghh.beerapp.common.BeerModel;
import com.eghh.beerapp.common.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends SampleActivityBase {

    private static String key = "0bb957499c324525521a89186b87e785";
    private static String url = "http://api.brewerydb.com/v2/search?key=" + key + "&q=";
    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
    //ArrayList<JSONObject> breweryList = new ArrayList<JSONObject>();
    ArrayList<BeerModel> beerList = new ArrayList<BeerModel>();
    private static final String type = "type";

    //----Search----
    public void setSearchQuery(String s){
        url = url + s;
        parseJson();
    }
    public void parseJson(){
        new ProgressTask(SearchActivity.this).execute();
    }

    private class ProgressTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog;

        public ProgressTask(SampleActivityBase activity){

            Log.i("1", "Called");
            context = activity;
            //dialog = new ProgressDialog(context);
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
