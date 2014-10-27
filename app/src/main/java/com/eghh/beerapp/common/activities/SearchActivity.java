package com.eghh.beerapp.common.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.eghh.beerapp.common.BeerModel;
import com.eghh.beerapp.common.JSONParser;
import com.eghh.beerapp.common.fragments.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class SearchActivity extends SampleActivityBase {
    private static String key = "0bb957499c324525521a89186b87e785";
    //ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
    //ArrayList<JSONObject> breweryList = new ArrayList<JSONObject>();
    public static ArrayList<BeerModel> beerList = new ArrayList<BeerModel>();
    private static final String type = "type";
    private ListView lv;


    public void parseJson(Context context, String s, ProgressDialog pd, View view){
        String url = "http://api.brewerydb.com/v2/search?key=" + key + "&q=" + s;
        new ProgressTask(context, pd, view).execute(url);
    }

    private class ProgressTask extends AsyncTask<String, Void, ArrayList<BeerModel>> {
        public ProgressDialog mDialog;
        public Context mContext;
        public View mView;

        public ProgressTask(Context context, ProgressDialog pd, View view){
            this.mDialog = pd;
            this.mContext = context;
            this.mView = view;
        }


        protected void onPreExecute() {
            this.mDialog.setMessage("Finding delicious beers...");
            this.mDialog.show();
            beerList.clear();
        }

        @Override
        protected void onPostExecute(final ArrayList<BeerModel> output_list) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }

            class dataListAdapter extends BaseAdapter {
                ArrayList<BeerModel> beerList;
                Context ctx;

                public dataListAdapter(Context context, ArrayList<BeerModel> output_list){
                    this.beerList = output_list;
                    this.ctx = context;
                }

                public int getCount(){
                    return output_list.size();
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
                        convertView = mInflater.inflate(R.layout.custom_list_view, null);
                    }

                    TextView name_text, description_text, percentage_text;

                    name_text = (TextView) convertView.findViewById(R.id.name);
                    description_text = (TextView) convertView.findViewById(R.id.description);
                    percentage_text = (TextView) convertView.findViewById(R.id.percentage);

                    name_text.setText(beerList.get(position).beerName);
                    description_text.setText(beerList.get(position).beerDesc);
                    percentage_text.setText(beerList.get(position).beerPercentage + "%");

                    return convertView;
                }
            }

            lv = (ListView) mView.findViewById(R.id.search_listView);
            lv.setAdapter(new dataListAdapter(mContext, output_list));
        }

        protected  ArrayList<BeerModel> doInBackground(String... args) {

            JSONParser jParser = new JSONParser();
            JSONArray json = jParser.getJSONFromUrl(args[0]);

            for (int i = 0; i < json.length(); i++) {

                try {
                    JSONObject beer = json.getJSONObject(i);
                    String vtype = beer.getString(type);
                    String[] bmArray = new String[8];
                    if(vtype.equals("beer")){

                        bmArray[0] = beer.getString("id");
                        bmArray[1] = beer.getString("name");
                        bmArray[2] = beer.getString("abv");
                        bmArray[3] = beer.has("isOrganic") ? beer.getString("isOrganic") : "N";
                        bmArray[4] = beer.has("style") ? beer.getJSONObject("style").getString("description") : beer.getString("description");
                        bmArray[5] = beer.has("glass") ? beer.getJSONObject("glass").getString("name") : "No specific glassware";
                        bmArray[6] = "null";
                        bmArray[7] = beer.has("labels") ? beer.getJSONObject("labels").getString("medium") : "http://zenit.senecac.on.ca/wiki/imgs/404-not-found.gif";
                        BeerModel bm = new BeerModel(bmArray);
                        beerList.add(bm);
                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            return beerList;
        }
    }
}
