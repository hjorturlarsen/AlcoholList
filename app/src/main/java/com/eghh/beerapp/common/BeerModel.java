package com.eghh.beerapp.common;

public class BeerModel {
    public String beerId, beerName, beerDesc, beerPercentage, image;

    public BeerModel(String beerId, String beerName, String beerDesc, String beerPercentage, String image){
        this.beerId = beerId;
        this.beerName = beerName;
        this.beerDesc = beerDesc;
        this.beerPercentage = beerPercentage;
        this.image = image;
    }
}
