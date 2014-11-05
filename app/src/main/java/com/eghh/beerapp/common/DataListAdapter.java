package com.eghh.beerapp.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eghh.beerapp.common.fragments.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gudni on 2.11.2014.
 */
public class DataListAdapter extends BaseAdapter {
    ArrayList<HashMap<String, Object>> beerList;
    Context ctx;

    public DataListAdapter(Context context, ArrayList<HashMap<String, Object>> output_list){
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

    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null){
            LayoutInflater mInflater = (LayoutInflater) ctx
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_row_rated_unrated, null);
        }

        TextView name_text, description_text, percentage_text;

        Object picObj = beerList.get(position).get("mPic");
        byte[] picArray = (byte[]) picObj;
        ImageView img = (ImageView) convertView.findViewById(R.id.img2);

        name_text = (TextView) convertView.findViewById(R.id.name);
        description_text = (TextView) convertView.findViewById(R.id.description);
        percentage_text = (TextView) convertView.findViewById(R.id.percentage);

        img.setImageBitmap(BitmapFactory.decodeByteArray(picArray, 0, picArray.length));
        name_text.setText((String) beerList.get(position).get("Name"));
        description_text.setText((String) beerList.get(position).get("Desc"));
        percentage_text.setText("Alc. " + beerList.get(position).get("Abv") + "% vol.");

        return convertView;
    }
}