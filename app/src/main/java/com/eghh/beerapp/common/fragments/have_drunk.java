package com.eghh.beerapp.common.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.eghh.beerapp.common.utilities.BeerModel;
import com.eghh.beerapp.common.utilities.DataBaseHelper;
import com.eghh.beerapp.common.activities.BeerInfoActivity;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Class for the 'favorites' tab.
 * Gets rated beers from local database and displays them in list view.
 */
public class have_drunk extends Fragment{

    ListView listView;
    ListViewAdapter listViewAdapter;
    public ArrayList<HashMap<String, Object>> rated_beers;

    public static have_drunk newInstance(){
        return new have_drunk();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Initialize();
    }

    public void Initialize() {
        rated_beers = DataBaseHelper.getRatedList();
        listView = (ListView) getView().findViewById(R.id.favorites_ListView);
                listViewAdapter = new ListViewAdapter(getActivity(), rated_beers);
                listView.setAdapter(listViewAdapter);
                listView.setEmptyView(getView().findViewById(R.id.have_drunk_empty));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    /**
                     * Responds to click on an item in the search result's listView.
                     * Opens a new activity with further information about the beer that was clicked.
                     */
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent beerInfoActivity = new Intent(getActivity(), BeerInfoActivity.class);
                        byte[] mArr = (byte[]) rated_beers.get(position).get("mPic");
                        String[] array_beer_model = new String[15];
                        array_beer_model[0] = (String) rated_beers.get(position).get("beerId");
                        array_beer_model[1] = (String) rated_beers.get(position).get("Name");
                        array_beer_model[2] = (String) rated_beers.get(position).get("Abv");
                        array_beer_model[3] = (String) rated_beers.get(position).get("Organic");
                        array_beer_model[4] = (String) rated_beers.get(position).get("Desc");
                        array_beer_model[5] = (String) rated_beers.get(position).get("GlassName");
                        array_beer_model[6] = (String) rated_beers.get(position).get("HasRated");
                        array_beer_model[7] = "";
                array_beer_model[8] = "";
                array_beer_model[9] = (String) rated_beers.get(position).get("Website");
                array_beer_model[10] = (String) rated_beers.get(position).get("Country");
                array_beer_model[11] = (String) rated_beers.get(position).get("Brewery");

                BeerModel beerModel = new BeerModel(array_beer_model);
                beerInfoActivity.putExtra("beerModel", beerModel);
                beerInfoActivity.putExtra("bArr", mArr);
                beerInfoActivity.putExtra("FromDB", "FromDB");
                getActivity().startActivity(beerInfoActivity);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstaceState)
    {
        return inflater.inflate(R.layout.fragment_have_drunk, container, false);
    }

    /**
     *
     */
    private class ListViewAdapter extends BaseAdapter {
        ArrayList<HashMap<String, Object>> beerList;
        Context ctx;

        public ListViewAdapter(Context context, ArrayList<HashMap<String, Object>> output_list){
            this.beerList = output_list;
            this.ctx = context;
        }

        public int getCount(){
            return beerList.size();
        }

        public Object getItem(int arg0)
        {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent)
        {
            if(convertView == null){
                LayoutInflater mInflater = (LayoutInflater) ctx
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.listview_row_rated_unrated, null);
            }

            final TextView name_text, description_text, percentage_text;
            final RatingBar ratingBar;

            Object picObj = beerList.get(position).get("mPic");
            byte[] picArray = (byte[]) picObj;
            ImageView beer_image = (ImageView) convertView.findViewById(R.id.beer_image);
            ImageButton button_delete = (ImageButton) convertView.findViewById(R.id.remove_beer);

            name_text = (TextView) convertView.findViewById(R.id.beer_name);
            description_text = (TextView) convertView.findViewById(R.id.beer_description);
            percentage_text = (TextView) convertView.findViewById(R.id.beer_abv);
            ratingBar = (RatingBar) convertView.findViewById(R.id.beer_rating);

            beer_image.setImageBitmap(BitmapFactory.decodeByteArray(picArray, 0, picArray.length));
            name_text.setText((String) beerList.get(position).get("Name"));
            description_text.setText((String) beerList.get(position).get("Desc"));
            percentage_text.setText("Alc. " + beerList.get(position).get("Abv") + "% vol.");
            ratingBar.setVisibility(View.VISIBLE);
            String actualRate = (String) beerList.get(position).get("Rating");
            String rate = actualRate.equals("-1") ? "0" : actualRate;
            Float rate_float = Float.parseFloat(rate);
            ratingBar.setRating(rate_float/2);


            button_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Thread data_thread = new Thread(new Runnable() {
                        public void run() {
                            DataBaseHelper dbh = new DataBaseHelper(ctx);
                            try{
                                String beerId = (String) beerList.get(position).get("BeerId");
                                dbh.deleteFromDb(beerId);
                            }
                            catch (IndexOutOfBoundsException ex){
                                Log.e("Error: ", "While trying to delete item from database");
                            }
                            finally {
                                dbh.close();
                            }
                        }
                    });
                    data_thread.start();
                    try{
                        data_thread.join();
                    }
                    catch (InterruptedException ex){
                        ex.printStackTrace();
                    }
                    notifyDataSetChanged();
                    Initialize();
                }
            });
            return convertView;
        }
    }
}
