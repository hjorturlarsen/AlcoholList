package com.eghh.beerapp.common.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.eghh.beerapp.common.BeerModel;
import com.eghh.beerapp.common.JSONParser;
import com.eghh.beerapp.common.app.AppController;
import com.eghh.beerapp.common.fragments.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A class for background work done in search tab.
 * Creates a request for the www.brewerydb.com API and sends the request.
 * Gets and displays the results in a list view inside the search tab.
 */
public class SearchActivity extends FragmentActivity {
    public static ArrayList<BeerModel> beerList = new ArrayList<BeerModel>();
    private static final String type = "type";


    public void parseJson(Context context, String s, ProgressDialog progressDialog, View view){
        s = s.replace(" ", "+");
        String url = "http://api.brewerydb.com/v2/search?key=0bb957499c324525521a89186b87e785&q=" + s + "&withBreweries=Y";
        new ProgressTask(context, progressDialog, view).execute(url);
    }

    /**
     * Displays a dialog while searching for results.
     * Sends request to www.brewerydb.com and gathers information for the search input.
     * Loads images and text in listView in the search fragment.
     */
    private class ProgressTask extends AsyncTask<String, Void, ArrayList<BeerModel>> {
        public ProgressDialog mDialog;
        public Context mContext;
        public View mView;
        public ListView listView;

        public ProgressTask(Context context, ProgressDialog progressDialog, View view){
            this.mDialog = progressDialog;
            this.mContext = context;
            this.mView = view;
        }

        /**
         * Show dialog on start of the process
         */
        protected void onPreExecute() {
            this.mDialog.setMessage("Finding delicious beers...");
            this.mDialog.show();
            beerList.clear();
        }

        /**
         * Show results when the process is over
         */
        @Override
        protected void onPostExecute(final ArrayList<BeerModel> output_list) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }

            class dataListAdapter extends BaseAdapter {
                ArrayList<BeerModel> beerList;
                Context ctx;
                ImageLoader imageLoader = AppController.getInstance().getImageLoader();

                public dataListAdapter(Context context, ArrayList<BeerModel> output_list){
                    this.beerList = output_list;
                    this.ctx = context;
                }

                public int getCount(){
                    return output_list.size();
                }

                public Object getItem(int arg0)
                {
                    return null;
                }

                public long getItemId(int position) {
                    return position;
                }

                /**
                 * Set text and images for each list view.
                 */
                public View getView(int position, View convertView, ViewGroup parent)
                {
                    if(convertView == null){
                        LayoutInflater mInflater = (LayoutInflater) ctx
                                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                        convertView = mInflater.inflate(R.layout.listview_row, null);
                    }

                    if(imageLoader == null)
                    {
                        imageLoader = AppController.getInstance().getImageLoader();
                    }

                    TextView name_text, description_text, percentage_text;

                    NetworkImageView img = (NetworkImageView) convertView.findViewById(R.id.img);
                    name_text = (TextView) convertView.findViewById(R.id.name);
                    description_text = (TextView) convertView.findViewById(R.id.description);
                    percentage_text = (TextView) convertView.findViewById(R.id.percentage);

                    img.setImageUrl(beerList.get(position).mImage, imageLoader);
                    name_text.setText(beerList.get(position).beerName);
                    description_text.setText(beerList.get(position).beerDesc);
                    percentage_text.setText("Alc. " + beerList.get(position).beerPercentage + "% vol.");

                    return convertView;
                }
            }

            listView = (ListView) mView.findViewById(R.id.search_listView);
            dataListAdapter adapter = new dataListAdapter(mContext, output_list);
            listView.setAdapter(adapter);

            listView.setEmptyView(mView.findViewById(R.id.search_empty));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                /**
                 * Responds to click on an item in the search result's listView.
                 * Opens a new activity with further information about the beer that was clicked.
                 */
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent beerInfo = new Intent(mContext, BeerInfoActivity.class);
                    beerInfo.putExtra("beerModel", beerList.get(position));
                    mContext.startActivity(beerInfo);
                }
            });
        }


        /**
         * Gets results from brewerydb.com and creates and returns an array list of beerModel
         * objects.
         */
        protected  ArrayList<BeerModel> doInBackground(String... args) {

            JSONParser jParser = new JSONParser();
            JSONArray json = jParser.getJSONArrayFromUrl(args[0]);

            for (int i = 0; i < json.length(); i++) {

                try {
                    JSONObject beer = json.getJSONObject(i);
                    String vtype = beer.getString(type);
                    String[] beerModelArray = new String[15];
                    if(vtype.equals("beer")){
                        JSONObject brewery =  beer.getJSONArray("breweries").getJSONObject(0);

                        beerModelArray[0] = beer.getString("id");
                        beerModelArray[1] = beer.getString("name");
                        beerModelArray[2] = beer.getString("abv");
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
                        beerList.add(beerModel);
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
