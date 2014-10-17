package com.eghh.beerapp.common.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import com.eghh.beerapp.common.activities.SearchActivity;
import com.eghh.beerapp.common.activities.SearchActivity.ProgressTask;


public class search extends Fragment
{
    public static search newInstance()
    {
        return new search();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        View view = getView().findViewById(R.id.fragment_search);
        SearchView searchView = (SearchView) view.findViewById(R.id.search_searchView);

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s){
                //((TextView)view.findViewById(R.id.search_textView)).setText(s);
                SearchActivity sa = new SearchActivity();
                ProgressDialog pd = new ProgressDialog(getActivity());
                sa.parseJson(s, pd);
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
