package com.eghh.beerapp.common.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
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
import java.util.Arrays;
import java.util.HashMap;


/**
 * Class for the 'favorites' tab.
 * Gets rated beers from local database and displays them in list view.
 */
public class have_drunk extends Fragment {
    public static have_drunk newInstance()
    {
        return new have_drunk();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        final ArrayList<HashMap<String, Object>> ratedList = DataBaseHelper.getRatedList();


        ListView lv = (ListView) getView().findViewById(R.id.favorites_ListView);
        DataListAdapter adapter = new DataListAdapter(getActivity(), ratedList);
        lv.setAdapter(adapter);
        lv.setEmptyView(getView().findViewById(R.id.have_drunk_empty));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Responds to click on an item in the search result's listView.
             * Opens a new activity with further information about the beer that was clicked.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent beerInfo = new Intent(getActivity(), BeerInfoActivity.class);
                byte[] mArr = (byte[]) ratedList.get(position).get("mPic");
                byte[] lArr = (byte[]) ratedList.get(position).get("lPic");
                String[] sa = new String[15];
                sa[0] = (String) ratedList.get(position).get("BeerId");
                sa[1] = (String) ratedList.get(position).get("Name");
                sa[2] = (String) ratedList.get(position).get("Abv");
                sa[3] = (String) ratedList.get(position).get("Organic");
                sa[4] = (String) ratedList.get(position).get("Desc");
                sa[5] = (String) ratedList.get(position).get("GlassName");
                sa[6] = (String) ratedList.get(position).get("HasRated");
                sa[7] = "";
                sa[8] = "";
                sa[9] = (String) ratedList.get(position).get("Website");
                sa[10] = (String) ratedList.get(position).get("Country");
                sa[11] = (String) ratedList.get(position).get("Brewery");

                BeerModel beerModel = new BeerModel(sa);
                beerInfo.putExtra("beerModel", beerModel);
                beerInfo.putExtra("bArr", mArr);
                beerInfo.putExtra("FromDB", "FromDB");
                getActivity().startActivity(beerInfo);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstaceState)
    {
        return inflater.inflate(R.layout.fragment_have_drunk, container, false);
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
