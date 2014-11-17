package com.eghh.beerapp.common.activities;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.eghh.beerapp.common.BeerModel;
import com.eghh.beerapp.common.app.AppController;
import com.eghh.beerapp.common.fragments.R;
import com.eghh.beerapp.common.DataBaseHelper;

import java.io.IOException;

/**
 * Team: EGHH
 * Displays more information about a beer that has been clicked on in the search result list view.
 */
public class BeerInfoActivity extends Activity {

    TextView name, desc, percentage, country, website, brewery;
    NetworkImageView img;
    ImageView imageView;
    ImageButton btn_rateBeer, btn_tryLater;
    DataBaseHelper dbh = new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Initialize();
    }

    /**
     * Initializes view and variables and GUI objects.
     */
    public void Initialize() {
        Bundle extras = getIntent().getExtras();
        final BeerModel beerModel = extras.getParcelable("beerModel");
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        //Handle difference between search (from API) and list (from DB)
        String diffString = getIntent().getStringExtra("FromDB");
        if (diffString != null){
            //Do something f.ex. delete button instead of the add to list buttons
            setContentView(R.layout.activity_beer_info_rate_unrate);
            imageView = (ImageView) findViewById(R.id.info_img);
            byte[] bArr = getIntent().getByteArrayExtra("bArr");// beerModel.mImage.getBytes();
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(bArr, 0, bArr.length));
        }
        else{
            //Do something else
            setContentView(R.layout.activity_beer_info);
            img = (NetworkImageView) findViewById(R.id.info_img);
            btn_rateBeer = (ImageButton) findViewById(R.id.rate);
            btn_tryLater = (ImageButton) findViewById(R.id.try_later);
            img.setImageUrl(beerModel.mImage, imageLoader);

            btn_rateBeer.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                dbh.insertToDb(beerModel, 5);
                            }
                            catch (IOException ioe){
                                ioe.printStackTrace();
                            }
                        }
                    }).start();
                }
            });
            btn_tryLater.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v){
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                dbh.insertToDb(beerModel, null);
                            }
                            catch (IOException ioe){
                                ioe.printStackTrace();
                            }
                        }
                    }).start();
                }
            });
        }

        name = (TextView) findViewById(R.id.info_beerName);
        desc = (TextView) findViewById(R.id.info_beerDesc);
        percentage = (TextView) findViewById(R.id.info_beerPercentage);
        country = (TextView) findViewById(R.id.info_beerCountry);
        brewery = (TextView) findViewById(R.id.info_beerBrewery);
        website = (TextView) findViewById(R.id.info_beerWebsite);

        name.setText(beerModel.beerName);
        desc.setText(beerModel.beerDesc);
        percentage.setText("Alcohol " + beerModel.beerPercentage + "% volume");
        country.setText(beerModel.country);
        brewery.setText(beerModel.brewery);
        website.setText(beerModel.website);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
