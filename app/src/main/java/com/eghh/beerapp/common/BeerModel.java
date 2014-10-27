package com.eghh.beerapp.common;

public class BeerModel {
    public String beerId, beerName, beerDesc, beerPercentage, image, organic, glassName, hasRated;
    //private String[] allData = new String[8];

    public BeerModel(String[] s){
        this.beerId = s[0];
        this.beerName = s[1];
        this.beerPercentage = s[2];
        this.organic = s[3];
        this.beerDesc = s[4];
        this.glassName = s[5];
        this.hasRated = s[6];
        this.image = s[7];
    }
}
