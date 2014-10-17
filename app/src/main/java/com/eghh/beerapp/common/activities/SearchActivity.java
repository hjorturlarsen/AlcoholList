package com.eghh.beerapp.common.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import com.eghh.beerapp.common.BeerModel;
import com.eghh.beerapp.common.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class SearchActivity extends SampleActivityBase {
    private static String key = "0bb957499c324525521a89186b87e785";
    //ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
    //ArrayList<JSONObject> breweryList = new ArrayList<JSONObject>();
    public static ArrayList<BeerModel> beerList = new ArrayList<BeerModel>();
    private static final String type = "type";


    public void parseJson(String s, ProgressDialog pd){
        String url = "http://api.brewerydb.com/v2/search?key=" + key + "&q=" + s;
        new ProgressTask(pd).execute(url);
    }

    public static class ProgressTask extends AsyncTask<String, Void, ArrayList<BeerModel>> {
        private ProgressDialog dialog;
        public ProgressTask(ProgressDialog pd){
            dialog = pd;
        }


        protected void onPreExecute() {
            this.dialog.setMessage("Finding delicious beers...");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<BeerModel> output_list) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            //Display outcome output_list
        }

        protected  ArrayList<BeerModel> doInBackground(String... args) {

            JSONParser jParser = new JSONParser();
            JSONArray json = jParser.getJSONFromUrl(args[0]);

            for (int i = 0; i < json.length(); i++) {

                try {
                    JSONObject beer = json.getJSONObject(i);
                    String vtype = beer.getString(type);
                    /*
                    HashMap<String, String> map = new HashMap<String, String>();
                    if (vtype.equals("brewery")){
                        breweryList.add(c);
                    }
                    */
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
            return beerList;
        }
    }
}
