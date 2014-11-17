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
import android.widget.Button;

import com.eghh.beerapp.common.activities.BeerInfoActivity;
import com.eghh.beerapp.common.activities.RandomBeersActivity;

/**
 * Class for the 'explore' tab.
 */
public class explore extends Fragment
{
    public static explore newInstance()
    {
        return new explore();
    }

    Button btn_random_beers;
    View view;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Initialize();
    }

    public void Initialize(){
        view = getView().findViewById(R.id.fragment_explore);
        btn_random_beers = (Button) view.findViewById(R.id.button_random_beers);

        btn_random_beers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RandomBeersActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_explore, container, false);
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
