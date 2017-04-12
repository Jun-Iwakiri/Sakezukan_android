package com.example.iwakiri.sakezukan_android;

/**
 * Created by iwakiri on 2017/04/13.
 */

public class UserInfoData {
    int userInfoID;
    String licenseName;
    int LicenseNumber;

    public UserInfoData() {
    }

    public UserInfoData(int userInfoID, String licenseName, int licenseNumber) {
        this.userInfoID = userInfoID;
        this.licenseName = licenseName;
        LicenseNumber = licenseNumber;
    }

    public int getUserInfoID() {
        return userInfoID;
    }

    public void setUserInfoID(int userInfoID) {
        this.userInfoID = userInfoID;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public int getLicenseNumber() {
        return LicenseNumber;
    }

    public void setLicenseNumber(int licenseNumber) {
        LicenseNumber = licenseNumber;
    }
}
