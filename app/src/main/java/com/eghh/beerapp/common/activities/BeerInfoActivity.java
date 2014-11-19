package com.eghh.beerapp.common.activities;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

    TextView beer_name, beer_description, beer_abv, beer_country, beer_website, beer_brewery;
    NetworkImageView beer_image;
    ImageView imageView;
    ImageButton button_rate_beer, button_try_later;
    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
    Bundle extras;
    ImageLoader imageLoader;
    BeerModel beerModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Initialize();
    }

    /**
     * Initializes view and variables and GUI objects.
     */
    public void Initialize() {
        setContentView(R.layout.activity_beer_info);
        beer_name = (TextView) findViewById(R.id.beer_name);
        beer_abv = (TextView) findViewById(R.id.beer_abv);
        beer_description = (TextView) findViewById(R.id.beer_description);
        beer_country = (TextView) findViewById(R.id.beer_country);
        beer_website = (TextView) findViewById(R.id.beer_website);
        beer_brewery = (TextView) findViewById(R.id.beer_brewery);
        beer_image = (NetworkImageView) findViewById(R.id.beer_image);
        extras = getIntent().getExtras();
        beerModel = extras.getParcelable("beerModel");
        imageLoader = AppController.getInstance().getImageLoader();

        display_data();
    }

    public void display_data() {
        beer_name.setText(beerModel.beerName);
        beer_description.setText(beerModel.beerDesc);
        beer_abv.setText("Alcohol " + beerModel.beerPercentage + "% volume");
        beer_country.setText(beerModel.country);
        beer_brewery.setText(beerModel.brewery);
        beer_website.setText(beerModel.website);
        beer_image.setImageUrl(beerModel.mImage, imageLoader);


        //Handle difference between search (from API) and list (from DB)
        String is_from_database = getIntent().getStringExtra("FromDB");
        if (is_from_database != null) {
            //Do something f.ex. delete button instead of the add to list buttons
            setContentView(R.layout.activity_beer_info_rate_unrate);
            imageView = (ImageView) findViewById(R.id.beer_image);
            byte[] bArr = getIntent().getByteArrayExtra("bArr");// beerModel.mImage.getBytes();
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(bArr, 0, bArr.length));
        }
        else {
            beer_image = (NetworkImageView) findViewById(R.id.beer_image);
            button_rate_beer = (ImageButton) findViewById(R.id.beer_rate);
            button_try_later = (ImageButton) findViewById(R.id.beer_try_later);

            button_rate_beer.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                dataBaseHelper.insertToDb(beerModel, 5);
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        }
                    }).start();
                }
            });
            button_try_later.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                dataBaseHelper.insertToDb(beerModel, null);
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        }
                    }).start();
                }
            });
        }
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
