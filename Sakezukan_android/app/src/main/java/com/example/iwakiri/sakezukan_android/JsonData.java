package com.example.iwakiri.sakezukan_android;

import java.util.ArrayList;

/**
 * Created by iwakiri on 2017/05/25.
 */

public class JsonData {

    public UserInfo userInfo;
    public ArrayList<Sake> sakes;
    public ArrayList<UserSake> userSakes;
    public ArrayList<UserRecord> userRecords;
    public ArrayList<Information> informations;
    public ArrayList<HelpCategory> helpCategories;
    public ArrayList<HelpContent> helpContents;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public ArrayList<Sake> getSakes() {
        return sakes;
    }

    public ArrayList<UserSake> getUserSakes() {
        return userSakes;
    }

    public ArrayList<UserRecord> getUserRecords() {
        return userRecords;
    }

    public ArrayList<Information> getInformations() {
        return informations;
    }

    public ArrayList<HelpCategory> getHelpCategories() {
        return helpCategories;
    }

    public ArrayList<HelpContent> getHelpContents() {
        return helpContents;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setSakes(ArrayList<Sake> sakes) {
        this.sakes = sakes;
    }

    public void setUserSakes(ArrayList<UserSake> userSakes) {
        this.userSakes = userSakes;
    }

    public void setUserRecords(ArrayList<UserRecord> userRecords) {
        this.userRecords = userRecords;
    }

    public void setInformations(ArrayList<Information> informations) {
        this.informations = informations;
    }

    public void setHelpCategories(ArrayList<HelpCategory> helpCategories) {
        this.helpCategories = helpCategories;
    }

    public void setHelpContents(ArrayList<HelpContent> helpContents) {
        this.helpContents = helpContents;
    }

    public class UserInfo {
        public long userInfoId;
        public String userName;
        public String licenseName;
        public Integer licenseNumber;

        public long getUserInfoId() {
            return userInfoId;
        }

        public String getUserName() {
            return userName;
        }

        public String getLicenseName() {
            return licenseName;
        }

        public Integer getLicenseNumber() {
            return licenseNumber;
        }

        public void setUserInfoId(long userInfoId) {
            this.userInfoId = userInfoId;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setLicenseName(String licenseName) {
            this.licenseName = licenseName;
        }

        public void setLicenseNumber(Integer licenseNumber) {
            this.licenseNumber = licenseNumber;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("user_info {user_info_id=");
            builder.append(userInfoId);
            builder.append(",user_name=");
            builder.append(userName);
            builder.append(",license_name=");
            builder.append(licenseName);
            builder.append(",license_number=");
            builder.append(licenseNumber);
            builder.append("}");
            return builder.toString();
        }
    }

    public class Sake {
        public long sakeId;
        public String brand;
        public String breweryName;
        public String breweryAddress;
        public float lowerAlcoholContent;
        public float upperAlcoholContent;
        public String category;
        public long masterSakeId;

        public long getSakeId() {
            return sakeId;
        }

        public String getBrand() {
            return brand;
        }

        public String getBreweryName() {
            return breweryName;
        }

        public String getBreweryAddress() {
            return breweryAddress;
        }

        public float getLowerAlcoholContent() {
            return lowerAlcoholContent;
        }

        public float getUpperAlcoholContent() {
            return upperAlcoholContent;
        }

        public String getCategory() {
            return category;
        }

        public long getMasterSakeId() {
            return masterSakeId;
        }
    }

    public static class UserSake {
        public long sakeId;
        public String brand;
        public String breweryName;
        public String breweryAddress;
        public float lowerAlcoholContent;
        public float upperAlcoholContent;
        public String category;
        public long masterSakeId;

        public long getSakeId() {
            return sakeId;
        }

        public String getBrand() {
            return brand;
        }

        public String getBreweryName() {
            return breweryName;
        }

        public String getBreweryAddress() {
            return breweryAddress;
        }

        public float getLowerAlcoholContent() {
            return lowerAlcoholContent;
        }

        public float getUpperAlcoholContent() {
            return upperAlcoholContent;
        }

        public String getCategory() {
            return category;
        }

        public long getMasterSakeId() {
            return masterSakeId;
        }
    }

    public class UserRecord {
        public long userRecordId;
        public String foundDate;
        public String tastedDate;
        public String totalGrade;
        public String flavorGrade;
        public String tasteGrade;
        public String userRecordImage;
        public String review;
        public long recordedMasterSakeId;
        public long userSakeId;

        public long getUserRecordId() {
            return userRecordId;
        }

        public String getFoundDate() {
            return foundDate;
        }

        public String getTastedDate() {
            return tastedDate;
        }

        public String getTotalGrade() {
            return totalGrade;
        }

        public String getFlavorGrade() {
            return flavorGrade;
        }

        public String getTasteGrade() {
            return tasteGrade;
        }

        public String getUserRecordImage() {
            return userRecordImage;
        }

        public String getReview() {
            return review;
        }

        public long getRecordedMasterSakeId() {
            return recordedMasterSakeId;
        }

        public long getUserSakeId() {
            return userSakeId;
        }

        public void setUserRecordId(long userRecordId) {
            this.userRecordId = userRecordId;
        }

        public void setFoundDate(String foundDate) {
            this.foundDate = foundDate;
        }

        public void setTastedDate(String tastedDate) {
            this.tastedDate = tastedDate;
        }

        public void setTotalGrade(String totalGrade) {
            this.totalGrade = totalGrade;
        }

        public void setFlavorGrade(String flavorGrade) {
            this.flavorGrade = flavorGrade;
        }

        public void setTasteGrade(String tasteGrade) {
            this.tasteGrade = tasteGrade;
        }

        public void setUserRecordImage(String userRecordImage) {
            this.userRecordImage = userRecordImage;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public void setRecordedMasterSakeId(long recordedMasterSakeId) {
            this.recordedMasterSakeId = recordedMasterSakeId;
        }

        public void setUserSakeId(long userSakeId) {
            this.userSakeId = userSakeId;
        }

        public UserRecord() {
            super();
        }
    }

    public class Information {
        public long informationId;
        public String informationTitle;
        public String informationBody;

        public long getInformationId() {
            return informationId;
        }

        public String getInformationTitle() {
            return informationTitle;
        }

        public String getInformationBody() {
            return informationBody;
        }
    }

    public static class HelpCategory {
        public long helpCategoryId;
        public String helpCategoryBody;

        public long getHelpCategoryId() {
            return helpCategoryId;
        }

        public String getHelpCategoryBody() {
            return helpCategoryBody;
        }

        public void setHelpCategoryId(long helpCategoryId) {
            this.helpCategoryId = helpCategoryId;
        }

        public void setHelpCategoryBody(String helpCategoryBody) {
            this.helpCategoryBody = helpCategoryBody;
        }
    }

    public class HelpContent {
        public long helpContentId;
        public long recordedHelpCategoryId;
        public String helpTitle;
        public String helpBody;

        public long getHelpContentId() {
            return helpContentId;
        }

        public long getRecordedHelpCategoryId() {
            return recordedHelpCategoryId;
        }

        public String getHelpTitle() {
            return helpTitle;
        }

        public String getHelpBody() {
            return helpBody;
        }
    }
}
