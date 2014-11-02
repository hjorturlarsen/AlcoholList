package com.eghh.beerapp.common.activities;

import android.app.Activity;
import android.media.Rating;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.eghh.beerapp.common.BeerModel;
import com.eghh.beerapp.common.app.AppController;
import com.eghh.beerapp.common.fragments.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Displays more information about a beer that has been clicked on in the search.
 */
public class BeerInfoActivity extends Activity {

    TextView name, desc, percentage;
    NetworkImageView img;
    CheckBox haveDrunk;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_info);
        Bundle extras = getIntent().getExtras();
        BeerModel beerModel = extras.getParcelable("beerModel");

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        name = (TextView) findViewById(R.id.info_beerName);
        desc = (TextView) findViewById(R.id.info_beerDesc);
        percentage = (TextView) findViewById(R.id.info_beerPercentage);
        img = (NetworkImageView) findViewById(R.id.info_img);
        haveDrunk = (CheckBox) findViewById(R.id.have_drunk);
        ratingBar = (RatingBar) findViewById(R.id.rating);

        name.setText(beerModel.beerName);
        desc.setText(beerModel.beerDesc);
        percentage.setText(beerModel.beerPercentage);
        img.setImageUrl(beerModel.mImage, imageLoader);

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
