package com.example.iwakiri.sakezukan_android;

import java.util.ArrayList;

/**
 * Created by iwakiri on 2017/04/13.
 */

public class UnifiedData extends ArrayList<UnifiedData> {
    private long userInfoId;
    private String licenseName;
    private int licenseNumber;
    private long sakeId;
    private String brand;
    private String breweryName;
    private String breweryAddress;
    private float lowerAlcoholContent;
    private float upperAlcoholContent;
    private String category;
    private Boolean hasFound;
    private Boolean hasTasted;
    private long masterSakeId;
    private long userRecordId;
    private String dateFound;
    private String dateTasted;
    private int totalGrade;
    private int flavorGrade;
    private int tasteGrade;
    private String review;
    private long helpId;
    private String helpBody;
    private long informationId;
    private String informationBody;

    public long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public int getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(int licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public long getSakeId() {
        return sakeId;
    }

    public void setSakeId(long sakeId) {
        this.sakeId = sakeId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBreweryName() {
        return breweryName;
    }

    public void setBreweryName(String breweryName) {
        this.breweryName = breweryName;
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
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getHasFound() {
        return hasFound;
    }

    public void setHasFound(Boolean hasFound) {
        this.hasFound = hasFound;
    }

    public Boolean getHasTasted() {
        return hasTasted;
    }

    public void setHasTasted(Boolean hasTasted) {
        this.hasTasted = hasTasted;
    }

    public long getMasterSakeId() {
        return masterSakeId;
    }

    public void setMasterSakeId(long masterSakeId) {
        this.masterSakeId = masterSakeId;
    }

    public long getUserRecordId() {
        return userRecordId;
    }

    public void setUserRecordId(long userRecordId) {
        this.userRecordId = userRecordId;
    }

    public String getDateFound() {
        return dateFound;
    }

    public void setDateFound(String dateFound) {
        this.dateFound = dateFound;
    }

    public String getDateTasted() {
        return dateTasted;
    }

    public void setDateTasted(String dateTasted) {
        this.dateTasted = dateTasted;
    }

    public int getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(int totalGrade) {
        this.totalGrade = totalGrade;
    }

    public int getFlavorGrade() {
        return flavorGrade;
    }

    public void setFlavorGrade(int flavorGrade) {
        this.flavorGrade = flavorGrade;
    }

    public int getTasteGrade() {
        return tasteGrade;
    }

    public void setTasteGrade(int tasteGrade) {
        this.tasteGrade = tasteGrade;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public long getHelpId() {
        return helpId;
    }

    public void setHelpId(long helpId) {
        this.helpId = helpId;
    }

    public String getHelpBody() {
        return helpBody;
    }

    public void setHelpBody(String helpBody) {
        this.helpBody = helpBody;
    }

    public long getInformationId() {
        return informationId;
    }

    public void setInformationId(long informationId) {
        this.informationId = informationId;
    }

    public String getInformationBody() {
        return informationBody;
    }

    public void setInformationBody(String informationBody) {
        this.informationBody = informationBody;
    }

    public void getUserInfo() {
        getUserInfoId();
        getLicenseName();
        getLicenseNumber();
    }

    public void getSake() {
        getSakeId();
        getBrand();
        getBreweryName();
        getBreweryAddress();
        getLowerAlcoholContent();
        getUpperAlcoholContent();
        getCategory();
        getHasFound();
        getHasTasted();
        getMasterSakeId();
    }

    public void getUserRecord() {
        getUserRecordId();
        getDateFound();
        getDateTasted();
        getTotalGrade();
        getFlavorGrade();
        getTasteGrade();
        getReview();
    }

    public void getInformation() {
        getInformationId();
        getInformationBody();
    }

    public void getHelp() {
        getHelpId();
        getHelpBody();
    }

    public void setUserInfo(long userInfoId, String licenseName, int licenseNumber) {
        setUserInfoId(userInfoId);
        setLicenseName(licenseName);
        setLicenseNumber(licenseNumber);
    }

    public void setSake(long sakeId, String brand, String breweryName, String breweryAddress, float lowerAlkoholContent,
                        float upperAlkoholContent, String category,long masterSakeId) {
        setSakeId(sakeId);
        setBrand(brand);
        setBreweryName(breweryName);
        setBreweryAddress(breweryAddress);
        setLowerAlcoholContent(lowerAlkoholContent);
        setUpperAlcoholContent(upperAlkoholContent);
        setCategory(category);
        setHasFound(hasFound);
        setHasTasted(hasTasted);
        setMasterSakeId(masterSakeId);
    }

    public void setUserRecord(long userRecordId, String dateFound, String dataTasted,
                              int totalGrade, int flavorGrade, int tasteGrade, String review) {
        setUserRecordId(userRecordId);
        setDateFound(dateFound);
        setDateTasted(dataTasted);
        setTotalGrade(totalGrade);
        setFlavorGrade(flavorGrade);
        setTasteGrade(tasteGrade);
        setReview(review);
    }

    public void setInformation(long informationId, String infromationBody) {
        setInformationId(informationId);
        setInformationBody(infromationBody);
    }

    public void setHelp(long helpId, String helpBody) {
        setHelpId(helpId);
        setHelpBody(helpBody);
    }
}

