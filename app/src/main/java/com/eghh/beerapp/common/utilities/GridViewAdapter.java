package com.eghh.beerapp.common.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eghh.beerapp.common.fragments.R;
import java.util.ArrayList;

/**
 * Base adapter to display random beers
 */
public class GridViewAdapter extends BaseAdapter {
    ArrayList<String> mAchieve;
    Context mContext;
    String achName;

    public GridViewAdapter(Context context, ArrayList<String> achieve) {
        this.mContext = context;
        this.mAchieve = achieve;
    }

    public int getCount(){
        return mAchieve.size();
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

        ImageView achievements;
        TextView random_beer_name;
        achName = mAchieve.get(position).replace(" ","");
        int imageId = mContext.getResources().getIdentifier(achName, "drawable", mContext.getPackageName());

        achievements = (ImageView) convertView.findViewById(R.id.achievement);
        random_beer_name = (TextView) convertView.findViewById(R.id.random_beer_name);

        achievements.setImageResource(imageId);
        random_beer_name.setAllCaps(true);
        random_beer_name.setText(mAchieve.get(position));

        return convertView;
    }
}