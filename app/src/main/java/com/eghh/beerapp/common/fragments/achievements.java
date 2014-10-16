package com.eghh.beerapp.common.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class achievements extends Fragment
{
    private FragmentTabHost mTabHost;
    public static achievements newInstance()
    {
        return new achievements();
    }


    /*Þetta er í staðinn fyrir onCreate af einhverjum góðum ástæðum
      getView skilar veiw-inu sem við ætlum að vinna með, svo við getum útfært takka og annað
    */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        View view = getView().findViewById(R.id.fragment_achievements);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_achievements, container, false);
    }
}
