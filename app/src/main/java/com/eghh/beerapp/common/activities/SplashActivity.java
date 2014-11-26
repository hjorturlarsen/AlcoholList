package com.eghh.beerapp.common.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.eghh.beerapp.common.utilities.DataBaseHelper;
import com.eghh.beerapp.common.fragments.R;

public class SplashActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new DataBaseWorkWhileSplash().execute();
    }

    private class DataBaseWorkWhileSplash extends AsyncTask<String, Void, Boolean> {

        protected void onPreExecute() {
        }

        protected Boolean doInBackground(String... args) {
            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException ie){
                ie.printStackTrace();
            }
            try{
                DataBaseHelper dbh = new DataBaseHelper(SplashActivity.this);
                dbh.getExistingData();
            }
            catch (Exception io){
                Log.e("","");
            }
            return null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        }
    }
}
