package com.eghh.beerapp.common.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.eghh.beerapp.common.BeerModel;
import com.eghh.beerapp.common.DataBaseHelper;
import com.eghh.beerapp.common.DataListAdapter;
import com.eghh.beerapp.common.activities.BeerInfoActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for the 'to drink' tab.
 * Reads "to drink" beers from local datbase and shows results in list view.
 */
public class to_drink extends Fragment{
    public static to_drink newInstance()
    {
        return new to_drink();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        final ArrayList<HashMap<String, Object>> toDrinkList = DataBaseHelper.getToDrinkList();

        ListView lv = (ListView) getView().findViewById(R.id.to_drink_ListView);
        DataListAdapter adapter = new DataListAdapter(getActivity(), toDrinkList);
        lv.setAdapter(adapter);
        lv.setEmptyView(getView().findViewById(R.id.to_drink_empty));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Responds to click on an item in the search result's listView.
             * Opens a new activity with further information about the beer that was clicked.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent beerInfo = new Intent(getActivity(), BeerInfoActivity.class);
                byte[] mArr = (byte[]) toDrinkList.get(position).get("mPic");
                //byte[] lArr = (byte[]) toDrinkList.get(position).get("lPic");
                String[] sa = new String[15];
                sa[0] = (String) toDrinkList.get(position).get("BeerId");
                sa[1] = (String) toDrinkList.get(position).get("Name");
                sa[2] = (String) toDrinkList.get(position).get("Abv");
                sa[3] = (String) toDrinkList.get(position).get("Organic");
                sa[4] = (String) toDrinkList.get(position).get("Desc");
                sa[5] = (String) toDrinkList.get(position).get("GlassName");
                sa[6] = (String) toDrinkList.get(position).get("HasRated");
                sa[7] = "";
                sa[8] = "";
                sa[9] = (String) toDrinkList.get(position).get("Website");
                sa[10] = (String) toDrinkList.get(position).get("Country");
                sa[11] = (String) toDrinkList.get(position).get("Brewery");

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

    @Override
    //Locks the screen orientation to Portrait mode.
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
