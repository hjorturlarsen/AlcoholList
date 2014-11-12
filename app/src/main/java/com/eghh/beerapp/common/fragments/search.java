package com.eghh.beerapp.common.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import com.eghh.beerapp.common.activities.SearchActivity;

/**
 * Class for the 'search' tab.
 * Lets user type in a search word and sends the search word over to SearchActivity class
 * where it is processed further.
 */
public class search extends Fragment
{
    public static search newInstance()
    {
        return new search();
    }
    public Context mContext;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        final View mView = getView().findViewById(R.id.fragment_search);
        final SearchView searchView = (SearchView) mView.findViewById(R.id.search_searchView);

        searchView.setFocusable(false);
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s){
                SearchActivity sa = new SearchActivity();
                ProgressDialog pd = new ProgressDialog(getActivity());
                mContext = getActivity();
                sa.parseJson(mContext, s, pd, mView);
                searchView.clearFocus();
                return true;
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

    //Locks the screen orientation to Portrait mode.
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
