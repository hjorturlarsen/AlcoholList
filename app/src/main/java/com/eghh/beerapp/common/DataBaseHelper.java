package com.eghh.beerapp.common;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by gudni on 23.10.2014.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "TestDb";
    private Context mContext;

    public DataBaseHelper(Context context){
        super(context,DB_NAME,null,1);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("Drop table UserData");
        db.execSQL("CREATE TABLE IF NOT EXISTS UserData(" +
                "_id INTEGER PRIMARY KEY," +
                " BeerId VARCHAR(20)," +
                " Name VARCHAR(100)," +
                " Abv REAL," +
                " Organic INT," +
                " Desc VARCHAR(3000)," +
                " GlassName VARCHAR(50)," +
                " HasRated INT," +
                " sPic BLOB," +
                " UNIQUE(BeerId));");

        //sdb.execSQL("Delete from UserData");
//        db.execSQL("Delete from UserPass2");
        //insertToDb(sdb);
        //getInfoFromDb(sdb);

    }
    public void getExistingData(){
        SQLiteDatabase sdb = this.getWritableDatabase();
        onCreate(sdb);
//        String[] s = new String[] {"test1H", "BjorNafn", "4.55", "Y", "Descari", "Glass", "1", "null"};
//        BeerModel bm1 = new BeerModel(s);
//        insertToDb(bm1);
        getInfoFromDb(sdb);
    }
    public void insertToDb(BeerModel bm){
        SQLiteDatabase db = this.getWritableDatabase();
        double abv = Double.parseDouble(bm.beerPercentage);
        int isOrganic = bm.organic.equals("N") || bm.organic.equals("null") ? 0 : 1;
        int hasRated = bm.hasRated.equals("true") ? 1 : 0;
        db.execSQL("INSERT INTO UserData VALUES(" +
                " null," +
                "'" + bm.beerId + "'," +
                "'" + bm.beerName + "'," +
                abv + " ," +
                isOrganic + " ," +
                "'" + bm.beerDesc + "'," +
                "'" + bm.glassName + "'," +
                hasRated + " ," +
                " null);");
        //Need to handle gracefully if there is an eecption
    }
    public void getInfoFromDb(SQLiteDatabase db){
        ArrayList<HashMap<String, String>> ratedList = new ArrayList<HashMap<String, String>>();
        ArrayList<HashMap<String, String>> toDrinkList = new ArrayList<HashMap<String, String>>();

        Cursor ratedResultSet = db.rawQuery("Select * from UserData WHERE HasRated == 1",null);
        Cursor unratedResultSet = db.rawQuery("Select * from UserData WHERE HasRated == 0",null);
        ratedResultSet.moveToFirst();
        unratedResultSet.moveToFirst();
        String[] cNames = ratedResultSet.getColumnNames();
        while(ratedResultSet.isAfterLast() == false){
            HashMap<String, String> map = new HashMap<String, String>();
            for (String c : cNames){
                map.put(c, ratedResultSet.getString(ratedResultSet.getColumnIndex(c)));
            }
            ratedList.add(map);
            ratedResultSet.moveToNext();
        }
        while(unratedResultSet.isAfterLast() == false){
            HashMap<String, String> map = new HashMap<String, String>();
            for (String c : cNames){
                map.put(c, unratedResultSet.getString(unratedResultSet.getColumnIndex(c)));
            }
            toDrinkList.add(map);
            unratedResultSet.moveToNext();
        }
    }
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
//        onCreate(database);

    }
}
