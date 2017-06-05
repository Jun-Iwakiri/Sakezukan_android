package com.example.iwakiri.sakezukan_android;

import java.util.ArrayList;

/**
 * Created by iwakiri on 2017/04/13.
 */

public class UnifiedData extends ArrayList<UnifiedData> {
    private long userInfoId;
    private String userName;
    private String licenseName;
    private Integer licenseNumber;

    private long sakeId;
    private String brand;
    private String breweryName;
    private String breweryAddress;
    private float lowerAlcoholContent;
    private float upperAlcoholContent;
    private String category;
    private long masterSakeId;

    private long userRecordId;
    private String foundDate;
    private String tastedDate;
    private String totalGrade;
    private String flavorGrade;
    private String tasteGrade;
    private String userRecordImage;
    private String review;
    private long recordedMasterSakeId;
    private long userSakeId;

    private long informationId;
    private String informationTitle;
    private String informationBody;

    private long helpCategoryId;
    private String helpCategoryBody;

    private long helpContentId;
    private long recordedHelpCategoryId;
    private String helpTitle;
    private String helpBody;

    public long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public Integer getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(Integer licenseNumber) {
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

    public String getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(String foundDate) {
        this.foundDate = foundDate;
    }

    public String getTastedDate() {
        return tastedDate;
    }

    public void setTastedDate(String tastedDate) {
        this.tastedDate = tastedDate;
    }

    public String getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(String totalGrade) {
        this.totalGrade = totalGrade;
    }

    public String getFlavorGrade() {
        return flavorGrade;
    }

    public void setFlavorGrade(String flavorGrade) {
        this.flavorGrade = flavorGrade;
    }

    public String getTasteGrade() {
        return tasteGrade;
    }

    public void setTasteGrade(String tasteGrade) {
        this.tasteGrade = tasteGrade;
    }

    public String getUserRecordImage() {
        return userRecordImage;
    }

    public void setUserRecordImage(String userRecordImage) {
        this.userRecordImage = userRecordImage;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public long getRecordedMasterSakeId() {
        return recordedMasterSakeId;
    }

    public void setRecordedMasterSakeId(long recordedMasterSakeId) {
        this.recordedMasterSakeId = recordedMasterSakeId;
    }

    public long getUserSakeId() {
        return userSakeId;
    }

    public void setUserSakeId(long userSakeId) {
        this.userSakeId = userSakeId;
    }

    public long getInformationId() {
        return informationId;
    }

    public void setInformationId(long informationId) {
        this.informationId = informationId;
    }

    public String getInformationTitle() {
        return informationTitle;
    }

    public void setInformationTitle(String informationTitle) {
        this.informationTitle = informationTitle;
    }

    public String getInformationBody() {
        return informationBody;
    }

    public void setInformationBody(String informationBody) {
        this.informationBody = informationBody;
    }

    public long getHelpCategoryId() {
        return helpCategoryId;
    }

    public void setHelpCategoryId(long helpCategoryId) {
        this.helpCategoryId = helpCategoryId;
    }

    public String getHelpCategoryBody() {
        return helpCategoryBody;
    }

    public void setHelpCategoryBody(String helpCategoryBody) {
        this.helpCategoryBody = helpCategoryBody;
    }

    public long getHelpContentId() {
        return helpContentId;
    }

    public void setHelpContentId(long helpContentId) {
        this.helpContentId = helpContentId;
    }

    public long getRecordedHelpCategoryId() {
        return recordedHelpCategoryId;
    }

    public void setRecordedHelpCategoryId(long recordedHelpCategoryId) {
        this.recordedHelpCategoryId = recordedHelpCategoryId;
    }

    public String getHelpTitle() {
        return helpTitle;
    }

    public void setHelpTitle(String helpTitle) {
        this.helpTitle = helpTitle;
    }

    public String getHelpBody() {
        return helpBody;
    }

    public void setHelpBody(String helpBody) {
        this.helpBody = helpBody;
    }

    public void setSake(long sakeId, String brand, String breweryName, String breweryAddress, float lowerAlcoholContent,
                        float upperAlcoholContent, String category, long masterSakeId) {
        setSakeId(sakeId);
        setBrand(brand);
        setBreweryName(breweryName);
        setBreweryAddress(breweryAddress);
        setLowerAlcoholContent(lowerAlcoholContent);
        setUpperAlcoholContent(upperAlcoholContent);
        setCategory(category);
        setMasterSakeId(masterSakeId);
    }

    public void setUserRecord(long userRecordId, String foundDate, String tastedDate, String totalGrade, String flavorGrade, String tasteGrade, String review) {
        setUserRecordId(userRecordId);
        setFoundDate(foundDate);
        setTastedDate(tastedDate);
        setTotalGrade(totalGrade);
        setFlavorGrade(flavorGrade);
        setTasteGrade(tasteGrade);
        setReview(review);
    }
}

