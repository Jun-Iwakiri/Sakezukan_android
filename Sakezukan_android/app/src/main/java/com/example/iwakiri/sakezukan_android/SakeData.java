package com.example.iwakiri.sakezukan_android;

/**
 * Created by iwakiri on 2017/04/13.
 */

public class SakeData {
    int sakeID;
    String brand;
    String breweryAddress;
    float lowerAlcoholContent;
    float upperAlcoholContent;
    String Category;
    Boolean isFound;
    Boolean isTasted;

    public SakeData() {

    }

    public SakeData(int sakeID, String brand, String breweryAddress,
                    float lowerAlcoholContent, float upperAlcoholContent, String category,
                    Boolean isFound, Boolean isTasted) {
        this.sakeID = sakeID;
        this.brand = brand;
        this.breweryAddress = breweryAddress;
        this.lowerAlcoholContent = lowerAlcoholContent;
        this.upperAlcoholContent = upperAlcoholContent;
        Category = category;
        this.isFound = isFound;
        this.isTasted = isTasted;
    }

    public int getSakeID() {
        return sakeID;
    }

    public void setSakeID(int sakeID) {
        this.sakeID = sakeID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBreweryAddress() {
        return breweryAddress;
    }

    public void setBreweryAddress(String breweryAddress) {
        this.breweryAddress = breweryAddress;
    }

    public float getLowerAlcoholContent() {
        return lowerAlcoholContent;
    }

    public void setLowerAlcoholContent(float lowerAlcoholContent) {
        this.lowerAlcoholContent = lowerAlcoholContent;
    }

    public float getUpperAlcoholContent() {
        return upperAlcoholContent;
    }

    public void setUpperAlcoholContent(float upperAlcoholContent) {
        this.upperAlcoholContent = upperAlcoholContent;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public Boolean getFound() {
        return isFound;
    }

    public void setFound(Boolean found) {
        isFound = found;
    }

    public Boolean getTasted() {
        return isTasted;
    }

    public void setTasted(Boolean tasted) {
        isTasted = tasted;
    }
}
