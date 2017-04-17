package com.example.iwakiri.sakezukan_android;

/**
 * Created by iwakiri on 2017/04/13.
 */

public class UnifiedData {
    private int userInfoId;
    private String licenseName;
    private int licenseNumber;
    private int sakeId;
    private String brand;
    private String breweryName;
    private String breweryaddress;
    private float lowerAlkoholContent;
    private float upperAlkoholContent;
    private String category;
    private Boolean hasFound;
    private Boolean hasTasted;
    private int userRecordId;
    private String dateFound;
    private String dataTasted;
    private int totalGrade;
    private int flavorGrade;
    private int tasteGrade;
    private String review;
    private int helpId;
    private String helpBody;
    private int informationId;
    private String informationBody;

    public int getUserInfoId() {
        return userInfoId;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public int getLicenseNumber() {
        return licenseNumber;
    }

    public int getSakeId() {
        return sakeId;
    }

    public String getBrand() {
        return brand;
    }

    public String getBreweryName() {
        return breweryName;
    }

    public String getBreweryAddress() {
        return breweryaddress;
    }

    public float getLowerAlkoholContent() {
        return lowerAlkoholContent;
    }

    public float getUpperAlkoholContent() {
        return upperAlkoholContent;
    }

    public String getCategory() {
        return category;
    }

    public Boolean getHasFound() {
        return hasFound;
    }

    public Boolean getHasTasted() {
        return hasTasted;
    }

    public int getUserRecordId() {
        return userRecordId;
    }

    public String getDateFound() {
        return dateFound;
    }

    public String getDateTasted() {
        return dataTasted;
    }

    public int getTotalGrade() {
        return totalGrade;
    }

    public int getFlavorGrade() {
        return flavorGrade;
    }

    public int getTasteGrade() {
        return tasteGrade;
    }

    public String getReview() {
        return review;
    }

    public int getHelpId() {
        return helpId;
    }

    public String getHelpBody() {
        return helpBody;
    }

    public int getInformationId() {
        return informationId;
    }

    public String getInformationBody() {
        return informationBody;
    }

    public void setUserInfoId(int userInfoId) {
        this.userInfoId = userInfoId;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public void setLicenseNumber(int licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setSakeId(int sakeId) {
        this.sakeId = sakeId;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setBreweryName(String breweryName) {
        this.breweryName = breweryName;
    }

    public void setBreweryAddress(String breweryaddress) {
        this.breweryaddress = breweryaddress;
    }

    public void setLowerAlkoholContent(float lowerAlkoholContent) {
        this.lowerAlkoholContent = lowerAlkoholContent;
    }

    public void setUpperAlkoholContent(float upperAlkoholContent) {
        this.upperAlkoholContent = upperAlkoholContent;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setHasFound(Boolean hasFound) {
        this.hasFound = hasFound;
    }

    public void setHasTasted(Boolean hasTasted) {
        this.hasTasted = hasTasted;
    }

    public void setUserRecordId(int userRecordId) {
        this.userRecordId = userRecordId;
    }

    public void setDateFound(String dateFound) {
        this.dateFound = dateFound;
    }

    public void setDateTasted(String dataTasted) {
        this.dataTasted = dataTasted;
    }

    public void setTotalGrade(int totalGrade) {
        this.totalGrade = totalGrade;
    }

    public void setFlavorGrade(int flavorGrade) {
        this.flavorGrade = flavorGrade;
    }

    public void setTasteGrade(int tasteGrade) {
        this.tasteGrade = tasteGrade;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setHelpId(int helpId) {
        this.helpId = helpId;
    }

    public void setHelpBody(String helpBody) {
        this.helpBody = helpBody;
    }

    public void setInformationId(int informationId) {
        this.informationId = informationId;
    }

    public void setInformationBody(String infromationBody) {
        this.informationBody = infromationBody;
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
        getLowerAlkoholContent();
        getUpperAlkoholContent();
        getCategory();
        getHasFound();
        getHasTasted();
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

    public void setUserInfo(int userInfoId, String licenseName, int licenseNumber) {
        setUserInfoId(userInfoId);
        setLicenseName(licenseName);
        setLicenseNumber(licenseNumber);
    }

    public void setSake(int sakeId, String brand, String breweryName, String breweryAddress, float lowerAlkoholContent,
                        float upperAlkoholContent, String category, boolean hasFound, boolean hasTasted) {
        setSakeId(sakeId);
        setBrand(brand);
        setBreweryName(breweryName);
        setBreweryAddress(breweryAddress);
        setLowerAlkoholContent(lowerAlkoholContent);
        setUpperAlkoholContent(upperAlkoholContent);
        setCategory(category);
        setHasFound(hasFound);
        setHasTasted(hasTasted);
    }

    public void setUserRecord(int userRecordId, String dateFound, String dataTasted,
                              int totalGrade, int flavorGrade, int tasteGrade, String review) {
        setUserRecordId(userRecordId);
        setDateFound(dateFound);
        setDateTasted(dataTasted);
        setTotalGrade(totalGrade);
        setFlavorGrade(flavorGrade);
        setTasteGrade(tasteGrade);
        setReview(review);
    }

    public void setInformation(int informationId, String infromationBody) {
        setInformationId(informationId);
        setInformationBody(infromationBody);
    }

    public void setHelp(int helpId, String helpBody) {
        setHelpId(helpId);
        setHelpBody(helpBody);
    }
}

