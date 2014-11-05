package com.eghh.beerapp.common.activities;

/**
 * Team: EGHH
 * Date: 10.10.2014
 * Related to sliding tab view.
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
