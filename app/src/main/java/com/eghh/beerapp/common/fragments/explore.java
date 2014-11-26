package com.eghh.beerapp.common.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eghh.beerapp.common.utilities.GetRandomBeer;

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
                String request_URL = "http://api.brewerydb.com/v2/beer/random?key=0bb957499c324525521a89186b87e785&withBreweries=Y";
                new GetRandomBeer(getActivity()).execute(request_URL);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }
}
