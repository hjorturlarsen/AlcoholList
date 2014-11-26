package com.eghh.beerapp.common.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.eghh.beerapp.common.fragments.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public class ListViewAdapter extends BaseAdapter {
    ArrayList<HashMap<String, Object>> beerList;
    Context ctx;

    public ListViewAdapter(Context context, ArrayList<HashMap<String, Object>> output_list){
        this.beerList = output_list;
        this.ctx = context;
    }

    public int getCount(){
        return beerList.size();
    }

    public Object getItem(int arg0)
    {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if(convertView == null){
            LayoutInflater mInflater = (LayoutInflater) ctx
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listview_row_rated_unrated, null);
        }

        final TextView name_text, description_text, percentage_text, rating_text;

        Object picObj = beerList.get(position).get("mPic");
        byte[] picArray = (byte[]) picObj;
        ImageView beer_image = (ImageView) convertView.findViewById(R.id.beer_image);
        ImageButton ibutt = (ImageButton) convertView.findViewById(R.id.remove_beer);

        name_text = (TextView) convertView.findViewById(R.id.beer_name);
        description_text = (TextView) convertView.findViewById(R.id.beer_description);
        percentage_text = (TextView) convertView.findViewById(R.id.beer_abv);
        rating_text = (TextView) convertView.findViewById(R.id.rating);

        beer_image.setImageBitmap(BitmapFactory.decodeByteArray(picArray, 0, picArray.length));
        name_text.setText((String) beerList.get(position).get("Name"));
        description_text.setText((String) beerList.get(position).get("Desc"));
        percentage_text.setText("Alc. " + beerList.get(position).get("Abv") + "% vol.");
        String actualRate = (String) beerList.get(position).get("Rating");
        String rate = actualRate.equals("-1") ? "" : actualRate;
        rating_text.setText(rate);
        //ibutt.setText("X");

        ibutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    public void run() {
                        DataBaseHelper dbh = new DataBaseHelper(ctx);
                        String bid = (String) beerList.get(position).get("BeerId");
                        dbh.deleteFromDb(bid);
                    }
                }).start();
            }
        });

        return convertView;
    }
}