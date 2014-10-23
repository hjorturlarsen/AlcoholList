package com.eghh.beerapp.common.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import com.eghh.beerapp.common.BeerModel;
import com.eghh.beerapp.common.JSONParser;
import com.eghh.beerapp.common.fragments.R;
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


    public void parseJson(Context context, String s, ProgressDialog pd, View view){
        String url = "http://api.brewerydb.com/v2/search?key=" + key + "&q=" + s;
        new ProgressTask(context, pd, view).execute(url);
    }

    public class ProgressTask extends AsyncTask<String, Void, ArrayList<BeerModel>> {
        public ProgressDialog mDialog;
        public Context mContext;
        public View mView;

        public ProgressTask(Context context, ProgressDialog pd, View view){
            this.mDialog = pd;
            this.mContext = context;
            this.mView = view;
        }


        protected void onPreExecute() {
            this.mDialog.setMessage("Finding delicious beers...");
            this.mDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<BeerModel> output_list) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
            //Display outcome output_list
            TextView textView = (TextView) mView.findViewById(R.id.search_textView);
            for (int i = 0; i < beerList.size(); i++){
                textView.append(beerList.get(i).beerName + "\n");
                textView.append(beerList.get(i).beerDesc + "\n");
                textView.append("\n");
            }
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
                        String image = beer.has("labels") ? beer.getJSONObject("labels").getString("medium") : "http://zenit.senecac.on.ca/wiki/imgs/404-not-found.gif";
                        BeerModel bm = new BeerModel(beerId, beerName, description, percentage, image);
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
