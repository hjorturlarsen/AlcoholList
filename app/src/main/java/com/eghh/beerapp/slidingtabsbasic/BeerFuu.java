package com.eghh.beerapp.slidingtabsbasic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 91hjo_000 on 15.10.2014.
 */
public class BeerFuu extends Fragment
{
    public static BeerFuu newInstance()
    {
        return new BeerFuu();
    }


    /*Þetta er í staðinn fyrir onCreate af einhverjum góðum ástæðum
      getView skilar veiw-inu sem við ætlum að vinna með, svo við getum útfært takka og annað
    */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        //getView().find
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.achievements, container, false);
    }
}
