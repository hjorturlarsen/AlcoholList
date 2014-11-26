package com.eghh.beerapp.common.activities;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.View.OnClickListener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.eghh.beerapp.common.utilities.BeerModel;
import com.eghh.beerapp.common.volley.AppController;
import com.eghh.beerapp.common.fragments.R;
import com.eghh.beerapp.common.utilities.DataBaseHelper;
import java.io.IOException;

/**
 * Team: EGHH
 * Displays more information about a beer that has been clicked on in the search result list view.
 */
public class BeerInfoActivity extends FragmentActivity {

    TextView beer_name, beer_description, beer_abv, beer_country, beer_website, beer_brewery;
    NetworkImageView beer_image;
    ImageView imageView;
    ImageButton button_rate_beer, button_try_later;
    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
    Bundle extras;
    ImageLoader imageLoader;
    BeerModel beerModel;
    Boolean is_from_database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_info);
        Initialize();
        display_data();
    }

    /**
     * Initializes view and variables and GUI objects.
     */
    public void Initialize() {
        is_from_database = getIntent().getStringExtra("FromDB") != null;

        if(is_from_database) {
            setContentView(R.layout.activity_beer_info_rate_unrate);
            imageView = (ImageView) findViewById(R.id.beer_image);
        }
        else {
            setContentView(R.layout.activity_beer_info);
            beer_image = (NetworkImageView) findViewById(R.id.beer_image);
            button_rate_beer = (ImageButton) findViewById(R.id.beer_rate);
            button_try_later = (ImageButton) findViewById(R.id.beer_try_later);
        }

        beer_name = (TextView) findViewById(R.id.beer_name);
        beer_abv = (TextView) findViewById(R.id.beer_abv);
        beer_description = (TextView) findViewById(R.id.beer_description);
        beer_country = (TextView) findViewById(R.id.beer_country);
        beer_website = (TextView) findViewById(R.id.beer_website);
        beer_brewery = (TextView) findViewById(R.id.beer_brewery);
        extras = getIntent().getExtras();
        beerModel = extras.getParcelable("beerModel");
        imageLoader = AppController.getInstance().getImageLoader();
    }

    public void display_data() {
        if (is_from_database) {
            byte[] bArr = getIntent().getByteArrayExtra("bArr");
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(bArr, 0, bArr.length));
        }
        else {
            beer_image.setImageUrl(beerModel.mImage, imageLoader);
            button_rate_beer.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.popupwindow_rating, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    final RatingBar rating = (RatingBar)popupView.findViewById(R.id.rating_bar);
                    final Button submitRating = (Button)popupView.findViewById(R.id.submit_rating);
                    submitRating.setOnClickListener(new Button.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            double rate = rating.getRating();
                            final int beerRate = (int)Math.floor(rate*2);
                            new Thread(new Runnable() {
                                public void run() {
                                    try {
                                        dataBaseHelper.insertToDb(beerModel, beerRate);
                                    } catch (IOException ioe) {
                                        ioe.printStackTrace();
                                    }
                                }
                            }).start();
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
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
        beer_name.setText(beerModel.beerName);
        beer_description.setText(beerModel.beerDesc);
        beer_abv.setText("Abv: " + beerModel.beerPercentage + "%");
        beer_country.setText("Country: " + beerModel.country);
        beer_brewery.setText("Brewery: " + beerModel.brewery);
        beer_website.setText("Website: " + beerModel.website);
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
