package com.eghh.beerapp.common.activities;

/**
 * Created by Hjortur on 10.10.2014.
 */

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Base launcher activity, to handle most of the common plumbing for samples.
 */
public class SampleActivityBase extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected  void onStart() {
        super.onStart();
    }
}
