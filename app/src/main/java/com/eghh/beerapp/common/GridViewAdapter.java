package com.eghh.beerapp.common;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.eghh.beerapp.common.app.AppController;
import com.eghh.beerapp.common.fragments.R;
import java.util.ArrayList;

/**
 * Base adapter to display random beers
 */
public class GridViewAdapter extends BaseAdapter {
    ArrayList<BeerModel> mRandomBeers;
    Context mContext;

    public GridViewAdapter(Context context, ArrayList<BeerModel> randomBeers) {
        this.mContext = context;
        this.mRandomBeers = randomBeers;
    }

    public int getCount(){
        return mRandomBeers.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.gridview_box, null);
        }

        NetworkImageView random_beer_image;
        TextView random_beer_name;
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        random_beer_image = (NetworkImageView) convertView.findViewById(R.id.random_beer_image);
        random_beer_name = (TextView) convertView.findViewById(R.id.random_beer_name);

        random_beer_image.setImageUrl(mRandomBeers.get(position).mImage, imageLoader);
        random_beer_name.setText(mRandomBeers.get(position).beerName);

        return convertView;
    }
}