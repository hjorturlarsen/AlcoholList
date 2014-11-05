package com.eghh.beerapp.common;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import com.eghh.beerapp.common.DatabaseImages;

import junit.framework.Assert;

/**
 Team : EGHH
 Dags : 23.10'14

 This class will handle everything related to the local database, creating, inserting, updating and deleting.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "TestDb";
//    private static String DB_NAME = "RealDbName"; Don forget to change before deploy
    private static ArrayList<HashMap<String, Object>> sRatedList;
    private static ArrayList<HashMap<String, Object>> sToDrinkList;
    private static DatabaseImages dataImgHelper = new DatabaseImages();
    private static byte[] mImageByte = null;
    private static byte[] lImageByte = null;
    private Context mContext;

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
        getInfoFromDb();
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

            String sqlIns = "INSERT INTO UserData VALUES(?,?,?,?,?,?,?,?,?,?,?)";
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
            insertStmt.executeInsert();
            db.close();
        }
        catch (SQLiteException ex){
            Log.e("Error..." + ex, "Failed to insert");
            //Possibly display some warning to user???
        }
        catch (IOException ioex){
            Log.e("Error...", "Failed to get image");
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

        while(ratedResultSet.isAfterLast() == false){
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
        while(unratedResultSet.isAfterLast() == false){
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

        setToStatic(ratedList, toDrinkList);
    }
    //Post: Everytime the getInfoFromDb method is called the 2 static lists are updated
    //      and can therefore be accessed with correct information everytime
    public static void setToStatic(ArrayList<HashMap<String, Object>> a1, ArrayList<HashMap<String, Object>> a2){
        sRatedList.clear();
        sToDrinkList.clear();
        sRatedList = a1;
        sToDrinkList = a2;
    }
    public void deleteFromDb(String beerId) throws SQLiteException{
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM UserData WHERE BeerId ='" + beerId + "'");
            db.close();
        }
        catch (SQLiteException ex){
            Log.e("Error...", "Failed to delete");
            //Possibly display some warning to user???
        }
    }

    //Post: Next time the getInfoFromDb methos is called the ratedList will have
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
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
//        onCreate(database);
    }
}
