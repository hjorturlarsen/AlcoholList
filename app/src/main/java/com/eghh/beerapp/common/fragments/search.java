package com.eghh.beerapp.common.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eghh.beerapp.common.BeerModel;
import com.eghh.beerapp.common.activities.MainActivity;
import com.eghh.beerapp.common.activities.SearchActivity;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

public class search extends Fragment{


    public static search newInstance()
    {
        return new search();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        final View view = getView().findViewById(R.id.fragment_search);

        //search window
        final SearchView searchView = (SearchView) view.findViewById(R.id.search_search);
        //expand by default
        searchView.setIconifiedByDefault(false);
        //listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s){
                //((TextView)view.findViewById(R.id.search_textView)).setText(s);
                SearchActivity ma = new SearchActivity();
                ma.setSearchQuery(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}
