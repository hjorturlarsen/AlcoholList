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

 This class will handle everything related to the local database, creating, inserting and deleting.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "TestDb";
//    private static String DB_NAME = "RealDbName"; Don forget to change before deploy
    private static DatabaseImages dataImgHelper = new DatabaseImages();
    private static byte[] mImageByte = null;
    private static byte[] lImageByte = null;
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
                " mPic BLOB," +
                " lPic BLOB," +
                " UNIQUE(BeerId));");
        db.close();

        //sdb.execSQL("Delete from UserData");
//        db.execSQL("Delete from UserPass2");

    }
    public void getExistingData() throws IOException{
        //SQLiteDatabase sdb = this.getWritableDatabase();
        //onCreate(sdb);
        //---Deletes based on unique ID---
        //deleteFromDb("test2H");
        //---Deletes based on unique ID---

        //---Inserts to DB---
//        String[] ss = new String[] {"DBPorn2", "TestName", "3.22", "Y", "TEstDesc", "gName", "false", "https://s3.amazonaws.com/brewerydbapi/beer/jPKm8m/upload_t0TE5l-medium.png", "https://s3.amazonaws.com/brewerydbapi/beer/jPKm8m/upload_t0TE5l-large.png"};
//        BeerModel bmod = new BeerModel(ss);
//        insertToDb(bmod);
        //---Inserts to DB---
        //getInfoFromDb();
    }
    public void insertToDb(BeerModel bm) throws SQLiteException, IOException{
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            double abv = Double.parseDouble(bm.beerPercentage);
            int isOrganic = bm.organic.equals("N") || bm.organic.equals("null") ? 0 : 1;
            int hasRated = bm.hasRated.equals("true") ? 1 : 0;

            mImageByte = dataImgHelper.convertImage(bm.mImage);
            lImageByte = dataImgHelper.convertImage(bm.lImage);

            String sqlIns = "INSERT INTO UserData VALUES(?,?,?,?,?,?,?,?,?,?)";
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
            insertStmt.bindBlob(9, mImageByte);
            insertStmt.bindBlob(10, lImageByte);
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
    public ArrayList<HashMap<String, Object>> getInfoFromDb(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<HashMap<String, Object>> ratedList = new ArrayList<HashMap<String, Object>>();
        ArrayList<HashMap<String, Object>> toDrinkList = new ArrayList<HashMap<String, Object>>();

        Cursor ratedResultSet = db.rawQuery("Select * from UserData WHERE HasRated = 1",null); //virkar
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
        return ratedList;
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
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
//        onCreate(database);

    }
}
