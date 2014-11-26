package com.eghh.beerapp.common.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.eghh.beerapp.common.utilities.BeerModel;
import com.eghh.beerapp.common.utilities.DataBaseHelper;
import com.eghh.beerapp.common.utilities.ListViewAdapter;
import com.eghh.beerapp.common.activities.BeerInfoActivity;
import com.eghh.beerapp.common.utilities.HashmapComparator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
}
