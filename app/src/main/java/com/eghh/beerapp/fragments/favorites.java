package com.eghh.beerapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class favorites extends Fragment {
    public static favorites newInstance()
    {
        return new favorites();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        //getView().findViewById(R.id.fragment_favorites);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstaceState)
    {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }
}
