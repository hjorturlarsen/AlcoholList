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

    ArrayList<HashMap<String, Object>> drunken_beers;
    ListView listView;
    ListViewAdapter listViewAdapter;

    public static to_drink newInstance()
    {
        return new to_drink();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        drunken_beers = DataBaseHelper.getToDrinkList();
        listView = (ListView) getActivity().findViewById(R.id.to_drink_ListView);
        listViewAdapter = new ListViewAdapter(getActivity(), drunken_beers);
        listView.setAdapter(listViewAdapter);
        listView.setEmptyView(getActivity().findViewById(R.id.to_drink_empty));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Responds to click on an item in the search result's listView.
             * Opens a new activity with further information about the beer that was clicked.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent beerInfo = new Intent(getActivity(), BeerInfoActivity.class);
                byte[] mArr = (byte[]) drunken_beers.get(position).get("mPic");
                String[] sa = new String[15];
                sa[0] = (String) drunken_beers.get(position).get("BeerId");
                sa[1] = (String) drunken_beers.get(position).get("Name");
                sa[2] = (String) drunken_beers.get(position).get("Abv");
                sa[3] = (String) drunken_beers.get(position).get("Organic");
                sa[4] = (String) drunken_beers.get(position).get("Desc");
                sa[5] = (String) drunken_beers.get(position).get("GlassName");
                sa[6] = (String) drunken_beers.get(position).get("HasRated");
                sa[7] = "";
                sa[8] = "";
                sa[9] = (String) drunken_beers.get(position).get("Website");
                sa[10] = (String) drunken_beers.get(position).get("Country");
                sa[11] = (String) drunken_beers.get(position).get("Brewery");

                BeerModel beerModel = new BeerModel(sa);
                beerInfo.putExtra("beerModel", beerModel);
                beerInfo.putExtra("bArr", mArr);
                beerInfo.putExtra("FromDB", "FromDB");
                getActivity().startActivity(beerInfo);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_to_drink, container, false);
    }
}
