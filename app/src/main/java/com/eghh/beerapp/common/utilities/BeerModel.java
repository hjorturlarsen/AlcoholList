package com.eghh.beerapp.common.utilities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 Team : EGHH
 Date : 10.10'14

 This class makes a model for each beer that comes up from the search result.
 **/
public class BeerModel implements Parcelable{
    public String beerId, beerName, beerPercentage, organic, beerDesc, glassName, hasRated, mImage, lImage, website, country, brewery;

    /**
     * Constructs a BeerModel from String values
     */
    public BeerModel(String[] strings){
        this.beerId = strings[0];
        this.beerName = strings[1];
        this.beerPercentage = strings[2];
        this.organic = strings[3];
        this.beerDesc = strings[4];
        this.glassName = strings[5];
        this.hasRated = strings[6];
        this.mImage = strings[7];
        this.lImage = strings[8];
        this.website = strings[9];
        this.country = strings[10];
        this.brewery = strings[11];
    }

    /**
     * Constructs a BeerModel from a Parcel
     * @param parcel Source Parcel
     */
    public BeerModel(Parcel parcel){
        this.beerId = parcel.readString();
        this.beerName = parcel.readString();
        this.beerDesc = parcel.readString();
        this.beerPercentage = parcel.readString();
        this.mImage = parcel.readString();
        this.lImage = parcel.readString();
        this.organic = parcel.readString();
        this.glassName = parcel.readString();
        this.hasRated = parcel.readString();
        this.website = parcel.readString();
        this.country = parcel.readString();
        this.brewery = parcel.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(beerId);
        dest.writeString(beerName);
        dest.writeString(beerDesc);
        dest.writeString(beerPercentage);
        dest.writeString(mImage);
        dest.writeString(lImage);
        dest.writeString(organic);
        dest.writeString(glassName);
        dest.writeString(hasRated);
        dest.writeString(website);
        dest.writeString(country);
        dest.writeString(brewery);
    }

    /**
     * Method to recreate a BeerModel from a Parcel
     */
    public static Creator<BeerModel> CREATOR = new Creator<BeerModel>() {
        @Override
        public BeerModel createFromParcel(Parcel parcel) {
            return new BeerModel(parcel);
        }

        @Override
        public BeerModel[] newArray(int i) {
            return new BeerModel[i];
        }
    };
}
