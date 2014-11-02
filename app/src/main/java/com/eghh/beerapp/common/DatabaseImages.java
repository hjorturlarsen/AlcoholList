package com.eghh.beerapp.common;

import android.content.ContentValues;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 Team : EGHH
 Dags : 10.10'14
 * This class gets the image from URL and then converts it to byte array.
 * That array is then stored as a BLOB in the database.
 */
public class DatabaseImages {
    private byte[] imageByte;

    public DatabaseImages(){

    }

    //Post: Return the image gotten from the URL as a byte array
    public byte[] convertImage(String url) throws IOException{
        url.replace("https","http");
        HttpClient mHttpClient = new DefaultHttpClient();
        HttpGet mHttpGet = new HttpGet(url);
        try{
            HttpResponse mHttpResponse = mHttpClient.execute(mHttpGet);
            if (mHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = mHttpResponse.getEntity();
                if ( entity != null) {
                    imageByte = EntityUtils.toByteArray(entity);
                }
            }
            else {
                Log.e("Error...", "Failed to download image");
            }
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
        return imageByte;
    }
}
