package com.eghh.beerapp.common.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.eghh.beerapp.common.utilities.BeerModel;
import com.eghh.beerapp.common.utilities.JSONParser;
import com.eghh.beerapp.common.activities.BeerInfoActivity;
import com.eghh.beerapp.common.volley.AppController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Class for the 'search' tab.
 * Lets user type in a search word and sends the search word over to SearchActivity class
 * where it is processed further.
 */
public class search extends Fragment {
    private Context mContext;
    private View mView;
    private ArrayList<BeerModel> search_result = new ArrayList<BeerModel>();
    ListView listView;
    ListViewAdapter listViewAdapter;
    SearchView searchView;
    ImageLoader imageLoader;
    TextView beer_name, beer_description, beer_abv;
    NetworkImageView beer_image;


    public static search newInstance()
    {
        return new search();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Initialize();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    public void Initialize() {
        mView = getView().findViewById(R.id.fragment_search);
        mContext = getActivity();
        imageLoader = AppController.getInstance().getImageLoader();
        listView = (ListView) mView.findViewById(R.id.search_listView);
        listView.setEmptyView(mView.findViewById(R.id.search_empty));
        searchView = (SearchView) mView.findViewById(R.id.search_searchView);
        searchView.setFocusable(false);
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s){
                s = s.replace(" ", "+");
                searchView.clearFocus();
                String URL = "http://api.brewerydb.com/v2/search?key=0bb957499c324525521a89186b87e785&q=" + s + "&withBreweries=Y";
                new getSearchResults(mContext).execute(URL);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private class getSearchResults extends AsyncTask<String, Void, ArrayList<BeerModel>> {
        private ProgressDialog mDialog;
        private Context mContext;

        public getSearchResults(Context context){
            this.mContext = context;
        }

        /**
         * Show dialog on start of the process
         */
        protected void onPreExecute() {
            this.mDialog = new ProgressDialog(mContext);
            this.mDialog.setMessage("Finding delicious beers...");
            this.mDialog.show();
            search_result.clear();
        }

        /**
         * Gets results from brewerydb.com and creates and returns an array list of beerModel
         * objects.
         */
        protected  ArrayList<BeerModel> doInBackground(String... args) {
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = jsonParser.getJSONArrayFromUrl(args[0]);

            for (int i = 0; i < jsonArray.length(); i++) {

                try {
                    JSONObject beer = jsonArray.getJSONObject(i);
                    String value_type = beer.getString("type");
                    String[] beerModelArray = new String[15];
                    if(value_type.equals("beer")){
                        JSONObject brewery =  beer.getJSONArray("breweries").getJSONObject(0);

                        beerModelArray[0] = beer.getString("id");
                        beerModelArray[1] = beer.getString("name");
                        beerModelArray[2] = beer.getString("abv");
                        beerModelArray[3] = beer.has("isOrganic") ? beer.getString("isOrganic") : "N";
                        beerModelArray[4] = beer.has("style") ? beer.getJSONObject("style").getString("description") : beer.getString("description");
                        beerModelArray[5] = beer.has("glass") ? beer.getJSONObject("glass").getString("name") : "No specific glassware";
                        beerModelArray[6] = "null";
                        beerModelArray[7] = beer.has("labels") ? beer.getJSONObject("labels").getString("medium") : "http://i44.photobucket.com/albums/f7/GilliGold/ic_launcher_zps7171f199.png~original";
                        beerModelArray[8] = beer.has("labels") ? beer.getJSONObject("labels").getString("large") : "http://i44.photobucket.com/albums/f7/GilliGold/ic_launcher_zps7171f199.png~original";
                        beerModelArray[9] = brewery.has("website") ? brewery.getString("website") : "No website";
                        beerModelArray[10] = brewery.has("locations") ? brewery.getJSONArray("locations").getJSONObject(0).getJSONObject("country").getString("displayName") : "No location";
                        beerModelArray[11] = brewery.has("name") ? brewery.getString("name") : "No brewery";

                        BeerModel beerModel = new BeerModel(beerModelArray);
                        search_result.add(beerModel);
                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            return search_result;
        }

        /**
         * Show results when the process is over
         */
        @Override
        protected void onPostExecute(final ArrayList<BeerModel> search_result) {
            if(mDialog.isShowing()) {
                mDialog.dismiss();
            }
            display_data();
        }
    }

    public void display_data() {
        listViewAdapter = new ListViewAdapter(mContext, search_result);
        listView.setAdapter(listViewAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * Responds to click on an item in the search result's listView.
             * Opens a new activity with further information about the beer that was clicked.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent beerInfoActivity = new Intent(mContext, BeerInfoActivity.class);
                beerInfoActivity.putExtra("beerModel", search_result.get(position));
                mContext.startActivity(beerInfoActivity);
            }
        });
    }

    private class ListViewAdapter extends BaseAdapter {
        Context context;
        ArrayList<BeerModel> search_result;

        public ListViewAdapter(Context context, ArrayList<BeerModel> output_list){
            this.context = context;
            this.search_result = output_list;
        }

        public int getCount(){
            return search_result.size();
        }

        public Object getItem(int arg0) {
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
                LayoutInflater mInflater = (LayoutInflater) context
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.listview_row, null);
            }

            if(imageLoader == null)
            {
                imageLoader = AppController.getInstance().getImageLoader();
            }

            beer_image = (NetworkImageView) convertView.findViewById(R.id.beer_image);
            beer_name = (TextView) convertView.findViewById(R.id.beer_name);
            beer_description = (TextView) convertView.findViewById(R.id.beer_description);
            beer_abv = (TextView) convertView.findViewById(R.id.beer_abv);

            beer_image.setImageUrl(search_result.get(position).mImage, imageLoader);
            beer_name.setText(search_result.get(position).beerName);
            beer_description.setText(search_result.get(position).beerDesc);
            beer_abv.setText("Alc. " + search_result.get(position).beerPercentage + "% vol.");

            return convertView;
        }
    }
}
