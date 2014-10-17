package com.eghh.beerapp.common;

/**
 * Created by gudni on 16.10.2014.
 */
public class BeerModel {
    public String beerId, beerName, beerDesc, beerPercentage;

    public BeerModel(String beerId, String beerName, String beerDesc, String beerPercentage){
        this.beerId = beerId;
        this.beerName = beerName;
        this.beerDesc = beerDesc;
        this.beerPercentage = beerPercentage;
    }
}
