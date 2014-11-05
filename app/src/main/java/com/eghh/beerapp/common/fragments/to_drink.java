package com.eghh.beerapp.common.fragments;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.eghh.beerapp.common.DataBaseHelper;
import com.eghh.beerapp.common.DataListAdapter;

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

        ArrayList<HashMap<String, Object>> toDrinkList = DataBaseHelper.getToDrinkList();
        ListView lv = (ListView) getView().findViewById(R.id.to_drink_ListView);
        DataListAdapter adapter = new DataListAdapter(getActivity(), toDrinkList);
        lv.setAdapter(adapter);
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
