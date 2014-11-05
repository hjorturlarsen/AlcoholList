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
 * Class for the 'favorites' tab.
 * Gets rated beers from local database and displays them in list view.
 */
public class favorites extends Fragment {
    public static favorites newInstance()
    {
        return new favorites();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        ArrayList<HashMap<String, Object>> ratedList = DataBaseHelper.getRatedList();
        ListView lv = (ListView) getView().findViewById(R.id.favorites_ListView);
        DataListAdapter adapter = new DataListAdapter(getActivity(), ratedList);
        lv.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstaceState)
    {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
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
