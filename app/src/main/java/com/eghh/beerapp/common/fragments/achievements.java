package com.eghh.beerapp.common.fragments;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.eghh.beerapp.common.DataBaseHelper;
import com.eghh.beerapp.common.GridViewAdapter;

import java.util.ArrayList;

/**
 * Class for the 'achievement' tab
 */
public class achievements extends Fragment
{
    final ArrayList<String> test = DataBaseHelper.getAchievements();
    public static achievements newInstance()
    {
        return new achievements();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        GridView gv = (GridView) getView().findViewById(R.id.ach_gridview);
        GridViewAdapter gridAdapter = new GridViewAdapter(getActivity(), test);
        gv.setAdapter(gridAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_achievements, container, false);
    }

    @Override

    /**
     * Locks the screen orientation to Portrait mode.
     */
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

}
