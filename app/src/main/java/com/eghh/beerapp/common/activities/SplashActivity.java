package com.eghh.beerapp.common.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.eghh.beerapp.common.fragments.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar

        setContentView(R.layout.activity_splash);

        // Start background work
        new DataBaseWorkWhileSplash().execute();
    }

    private class DataBaseWorkWhileSplash extends AsyncTask<String, Void, Boolean> {

        public DataBaseWorkWhileSplash(){

        }

        protected void onPreExecute() {
            //Maybe nothing

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            // Start main activity
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        }

        protected Boolean doInBackground(String... args) {
            int x = 0;
            while (x < 4000000){
                x++;
            }
            //DataBase work will be done here

            return null;
        }
    }
}
