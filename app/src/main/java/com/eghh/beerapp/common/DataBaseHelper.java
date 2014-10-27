package com.eghh.beerapp.common;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

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
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS UserPass(Username VARCHAR,Password VARCHAR);");
//        db.execSQL("CREATE TABLE IF NOT EXISTS UserPass2(Username2 VARCHAR,Password2 VARCHAR);");
//        db.execSQL("Delete from UserPass");
//        db.execSQL("Delete from UserPass2");
//        insertToDb(db);
//        getInfoFromDb(db);

    }
    public void insertToDb(SQLiteDatabase db){
//        db.execSQL("INSERT INTO UserPass VALUES('test1','passTest1');");
//        db.execSQL("INSERT INTO UserPass2 VALUES('test2','passTest2');");

    }
    public void getInfoFromDb(SQLiteDatabase db){
//        ArrayList<String> u = new ArrayList<String>();
//        ArrayList<String> p = new ArrayList<String>();
//
//        Cursor resultSet = db.rawQuery("Select * from UserPass",null);
//        Cursor resultSet2 = db.rawQuery("Select * from UserPass2",null);
//        resultSet.moveToFirst();
//        resultSet2.moveToFirst();
//
//        while(resultSet.isAfterLast() == false) {
//            u.add(resultSet.getString(resultSet.getColumnIndex("Username")));
//            Log.d("debug", "getting the name from cursor");
//            p.add(resultSet.getString(resultSet.getColumnIndex("Password")));
//            resultSet.moveToNext();
//        }
//        while(resultSet2.isAfterLast() == false) {
//            u.add(resultSet2.getString(resultSet2.getColumnIndex("Username2")));
//            Log.d("debug", "getting the name from cursor");
//            p.add(resultSet2.getString(resultSet2.getColumnIndex("Password2")));
//            resultSet2.moveToNext();
//        }
    }
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
//        onCreate(database);

    }
}
