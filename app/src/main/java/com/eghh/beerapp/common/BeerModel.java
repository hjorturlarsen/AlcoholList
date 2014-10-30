package com.eghh.beerapp.common;
/**
 Team : EGHH
 Dags : 10.10'14

 This class makes a model for each beer that comes up from the search result.
 **/
public class BeerModel {
    public String beerId, beerName, beerDesc, beerPercentage, mImage, lImage, organic, glassName, hasRated;
    //private String[] allData = new String[8];

    public BeerModel(String[] s){
        this.beerId = s[0];
        this.beerName = s[1];
        this.beerPercentage = s[2];
        this.organic = s[3];
        this.beerDesc = s[4];
        this.glassName = s[5];
        this.hasRated = s[6];
        this.mImage = s[7];
        this.lImage = s[8];
    }
}
