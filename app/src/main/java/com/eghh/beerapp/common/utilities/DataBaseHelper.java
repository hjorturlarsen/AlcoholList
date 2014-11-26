package com.eghh.beerapp.common.utilities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 Team : EGHH
 Dags : 23.10'14

 This class will handle everything related to the local database, creating, inserting, updating and deleting.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "TestDb";
//    private static String DB_NAME = "RealDbName"; Don forget to change before deploy

    //Possibly have static boolean array that holds data for achievements...
    //private static boolean[] achievements22 = {false, false, false, false, false, false};
    private static ArrayList<String> achievements = new ArrayList<String>();
    private static ArrayList<HashMap<String, Object>> sRatedList;
    private static ArrayList<HashMap<String, Object>> sToDrinkList;
    private static DatabaseImages dataImgHelper = new DatabaseImages();
    private static byte[] mImageByte = null;
    private static byte[] lImageByte = null;
    Context mContext;

    public DataBaseHelper(Context context){
        super(context,DB_NAME,null,1);
        mContext = context;
    }

    public static ArrayList<HashMap<String, Object>> getRatedList(){
        return sRatedList;
    }

    public static ArrayList<HashMap<String, Object>> getToDrinkList(){
        return sToDrinkList;
    }

    public static ArrayList<String> getAchievements(){
        return achievements;
    }

//    public void noAchievements(){
//        achievements.put("5 beers", false);
//        achievements.put("10 beers", false);
//        achievements.put("50 beers", false);
//        achievements.put("German explorer", false);
//        achievements.put("Czech explorer", false);
//        achievements.put("Penis", false);
//    }

    //Post: Creates table if it does not exist.
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
                " Rating INT," +
                " mPic BLOB," +
                " lPic BLOB," +
                " Website VARCHAR(200)," +
                " Country VARCHAR(100)," +
                " Brewery VARCHAR(200)," +
                " UNIQUE(BeerId));");
        //db.close();

        //db.execSQL("Delete from UserData");
//        db.execSQL("Delete from UserPass2");

    }
    //Post: Creates DB table if it does not exist and then gets info from DB
    //      and stores that info in 2 separate arrays.
    public void getExistingData() throws IOException{
        SQLiteDatabase sdb = this.getWritableDatabase();
        onCreate(sdb);
        //---Deletes based on unique ID---
        //deleteFromDb("test2H");
        //---Deletes based on unique ID---

        //---Inserts to DB---
//        String[] ss = new String[] {"someId", "Viking Gylltur", "7.6", "Y", "TEstDesc for beer", "gName", "true", "https://s3.amazonaws.com/brewerydbapi/beer/jPKm8m/upload_t0TE5l-medium.png", "https://s3.amazonaws.com/brewerydbapi/beer/jPKm8m/upload_t0TE5l-large.png"};
//        BeerModel bmod = new BeerModel(ss);
//        insertToDb(bmod, null);
        //---Inserts to DB---
        //updateToDrunk("DBPorn1", 9);
        //noAchievements();
        getInfoFromDb();
        //setAchievements();
    }

    //Post: Data about specific beer has been inserted to the DB
    public void insertToDb(BeerModel bm, Integer rating) throws SQLiteException, IOException{
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            double abv = Double.parseDouble(bm.beerPercentage);
            int isOrganic = bm.organic.equals("N") || bm.organic.equals("null") ? 0 : 1;
            int hasRated = rating == null ? 0 : 1;
            rating = rating == null ? -1 : rating;

            mImageByte = dataImgHelper.convertImage(bm.mImage);
            lImageByte = dataImgHelper.convertImage(bm.lImage);

            String sqlIns = "INSERT INTO UserData VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            SQLiteStatement insertStmt = db.compileStatement(sqlIns);
            insertStmt.clearBindings();
            insertStmt.bindNull(1);
            insertStmt.bindString(2, bm.beerId);
            insertStmt.bindString(3, bm.beerName);
            insertStmt.bindDouble(4, abv);
            insertStmt.bindLong(5, isOrganic);
            insertStmt.bindString(6, bm.beerDesc);
            insertStmt.bindString(7, bm.glassName);
            insertStmt.bindLong(8, hasRated);
            insertStmt.bindLong(9, rating);
            insertStmt.bindBlob(10, mImageByte);
            insertStmt.bindBlob(11, lImageByte);
            insertStmt.bindString(12, bm.website);
            insertStmt.bindString(13, bm.country);
            insertStmt.bindString(14, bm.brewery);
            insertStmt.executeInsert();
            db.close();
            getInfoFromDb();
        }
        catch (SQLiteException ex){
            Log.e("Error..." + ex, "Failed to insert");
            //Possibly display some warning to user???
        }
        catch (IOException ioex){
            Log.e("Error..." + ioex, "Failed to get image");
            //Possibly display some warning to user???
        }
    }
    //Post: Gets all data from DB and puts it in 2 separate lists,
    //      Rated list includes all beers user has rated.
    //      ToDrinkList includes all beers user wants to try later, but has not drank.
    public void getInfoFromDb(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<HashMap<String, Object>> ratedList = new ArrayList<HashMap<String, Object>>();
        ArrayList<HashMap<String, Object>> toDrinkList = new ArrayList<HashMap<String, Object>>();

        Cursor ratedResultSet = db.rawQuery("Select * from UserData WHERE HasRated = 1",null);
        Cursor unratedResultSet = db.rawQuery("Select * from UserData WHERE HasRated = 0",null);
        ratedResultSet.moveToFirst();
        unratedResultSet.moveToFirst();
        String[] cNames = ratedResultSet.getColumnNames();

        while(!ratedResultSet.isAfterLast()){
            HashMap<String, Object> map = new HashMap<String, Object>();
            for (String c : cNames){
                if (c.equals("mPic") || c.equals("lPic")){
                    map.put(c, ratedResultSet.getBlob(ratedResultSet.getColumnIndex(c)));
                }
                else{
                    map.put(c, ratedResultSet.getString(ratedResultSet.getColumnIndex(c)));
                }
            }
            ratedList.add(map);
            ratedResultSet.moveToNext();
        }
        while(!unratedResultSet.isAfterLast()){
            HashMap<String, Object> map = new HashMap<String, Object>();
            for (String c : cNames){
                if (c.equals("mPic") || c.equals("lPic")){
                    map.put(c, unratedResultSet.getBlob(unratedResultSet.getColumnIndex(c)));
                }
                else{
                    map.put(c, unratedResultSet.getString(unratedResultSet.getColumnIndex(c)));
                }
            }
            toDrinkList.add(map);
            unratedResultSet.moveToNext();
        }
        Collections.sort(ratedList, new HashmapComparator("Rating"));
        setToStatic(ratedList, toDrinkList);
        setAchievements();
    }
    //Post: Every time the getInfoFromDb method is called the 2 static lists are updated
    //      and can therefore be accessed with correct information every time
    public static void setToStatic(ArrayList<HashMap<String, Object>> a1, ArrayList<HashMap<String, Object>> a2){
        if (sRatedList != null && sToDrinkList != null){
            sRatedList.clear();
            sToDrinkList.clear();
        }
        sRatedList = a1;
        sToDrinkList = a2;
    }
    public void deleteFromDb(String beerId) throws SQLiteException{
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM UserData WHERE BeerId ='" + beerId + "'");
            db.close();
            getInfoFromDb();
        }
        catch (SQLiteException ex){
            Log.e("Error...", "Failed to delete");
            //Possibly display some warning to user???
        }
    }

    //Post: Next time the getInfoFromDb method is called the ratedList will have
    //      one more row than before this method was called.
    //      This method alters 2 column in one row each time.
    public void updateToDrunk(String beerId, int rating) throws SQLiteException{
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("UPDATE UserData SET HasRated = 1, Rating =" + rating + " WHERE BeerId ='" + beerId + "'");
            db.close();
            getInfoFromDb();
        }
        catch (SQLiteException ex){
            Log.e("Error...", "Failed to delete");
            //Possibly display some warning to user???
        }
    }

    public void setAchievements(){
        try{
            achievements.clear();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor curs = db.rawQuery("SELECT * FROM UserData WHERE HasRated = 1", null);
            Cursor organic = db.rawQuery("SELECT * FROM UserData WHERE Organic = 1", null);
            Cursor german = db.rawQuery("SELECT * FROM UserData WHERE Country = 'Germany'", null);
            Cursor austrian = db.rawQuery("SELECT * FROM UserData WHERE Country = 'Austria'", null);
            Cursor belgian = db.rawQuery("SELECT * FROM UserData WHERE Country = 'Belgian'", null);
            Cursor croatian = db.rawQuery("SELECT * FROM UserData WHERE Country = 'Croatian'", null);

            int totalDrunk = curs.getCount();
            int organicDrunk = organic.getCount();
            int germanDrunk = german.getCount();
            int austrianDrunk = austrian.getCount();
            int belgianDrunk = belgian.getCount();
            int croatianDrunk = croatian.getCount();
            db.close();

            if (totalDrunk >= 1){
                achievements.add("drunk 1 beers");
            }
            if (organicDrunk >= 1){
                achievements.add("drunk 1 organic beers");
            }
            if (totalDrunk >= 5){
                achievements.add("drunk 5 beers");
            }
            if (organicDrunk >= 5){
                achievements.add("drunk 5 organic beers");
            }
            if (germanDrunk >= 5){
                achievements.add("drunk 5 german beers");
            }
            if (totalDrunk >= 10){
                achievements.add("drunk 10 beers");
            }
            if (organicDrunk >= 10){
                achievements.add("drunk 10 organic beers");
            }
            if (totalDrunk >= 20){
                achievements.add("drunk 20 beers");
            }
            if (germanDrunk >= 10){
                achievements.add("drunk 10 german beers");
            }
            if (organicDrunk >= 20){
                achievements.add("drunk 20 organic beers");
            }
            if (austrianDrunk >= 5) {
                achievements.add("drunk 5 austrian beers");
            }
            if (totalDrunk >= 50){
                achievements.add("drunk 50 beers");
            }
            if (austrianDrunk >= 10){
                achievements.add("drunk 10 austrian beers");
            }
            if (organicDrunk >= 50){
                achievements.add("drunk 50 organic beers");
            }
            if (croatianDrunk >= 5){
                achievements.add("drunk 5 croatian beers");
            }
            if (belgianDrunk >= 20){
                achievements.add("drunk 20 belgian beers");
            }
            if (organicDrunk >= 100){
                achievements.add("drunk 100 organic beers");
            }
            if (totalDrunk >= 100){
                achievements.add("drunk 100 beers");
            }
        }
        catch (SQLiteException ex){
            Log.e("Error...", "Failed to set achievements");
            //Possibly display some warning to user???
        }
    }
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
//        onCreate(database);
    }
}
