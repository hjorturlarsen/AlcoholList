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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for the 'to drink' tab.
 * Reads "to drink" beers from local datbase and shows results in list view.
 */
public class to_drink extends Fragment{

    public ArrayList<HashMap<String, Object>> to_drink;
    ListView listView;
    ListViewAdapter listViewAdapter;

    public static to_drink newInstance()
    {
        return new to_drink();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Initialize();
    }

    public void Initialize() {
        to_drink = DataBaseHelper.getToDrinkList();
        listView = (ListView) getView().findViewById(R.id.to_drink_ListView);
        listViewAdapter = new ListViewAdapter(getActivity(), to_drink);
        listView.setAdapter(listViewAdapter);
        listView.setEmptyView(getActivity().findViewById(R.id.to_drink_empty));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Responds to click on an item in the search result's listView.
             * Opens a new activity with further information about the beer that was clicked.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent beerInfoActivity = new Intent(getActivity(), BeerInfoActivity.class);
                byte[] mArr = (byte[]) to_drink.get(position).get("mPic");
                String[] array_beer_model = new String[15];
                array_beer_model[0] = (String) to_drink.get(position).get("beerId");
                array_beer_model[1] = (String) to_drink.get(position).get("Name");
                array_beer_model[2] = (String) to_drink.get(position).get("Abv");
                array_beer_model[3] = (String) to_drink.get(position).get("Organic");
                array_beer_model[4] = (String) to_drink.get(position).get("Desc");
                array_beer_model[5] = (String) to_drink.get(position).get("GlassName");
                array_beer_model[6] = (String) to_drink.get(position).get("HasRated");
                array_beer_model[7] = "";
                array_beer_model[8] = "";
                array_beer_model[9] = (String) to_drink.get(position).get("Website");
                array_beer_model[10] = (String) to_drink.get(position).get("Country");
                array_beer_model[11] = (String) to_drink.get(position).get("Brewery");

                BeerModel beerModel = new BeerModel(array_beer_model);
                beerInfoActivity.putExtra("beerModel", beerModel);
                beerInfoActivity.putExtra("bArr", mArr);
                beerInfoActivity.putExtra("FromDB", "FromDB");
                getActivity().startActivity(beerInfoActivity);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_to_drink, container, false);
    }
}
