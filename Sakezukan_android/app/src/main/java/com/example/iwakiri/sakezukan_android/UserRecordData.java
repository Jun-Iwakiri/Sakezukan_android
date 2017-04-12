package com.example.iwakiri.sakezukan_android;

/**
 * Created by iwakiri on 2017/04/13.
 */

public class UserRecordData {
    int userRecordID;
    String DateFounded;
    String DateTasted;
    int totalGrade;
    int flavorGrade;
    int tasteGrade;
    String review;

    public UserRecordData() {
    }

    public UserRecordData(int userRecordID, String dateFounded, String dateTasted,
                          int totalGrade, int flavorGrade, int tasteGrade, String review) {
        this.userRecordID = userRecordID;
        DateFounded = dateFounded;
        DateTasted = dateTasted;
        this.totalGrade = totalGrade;
        this.flavorGrade = flavorGrade;
        this.tasteGrade = tasteGrade;
        this.review = review;
    }

    public int getUserRecordID() {
        return userRecordID;
    }

    public void setUserRecordID(int userRecordID) {
        this.userRecordID = userRecordID;
    }

    public String getDateFounded() {
        return DateFounded;
    }

    public void setDateFounded(String dateFounded) {
        DateFounded = dateFounded;
    }

    public String getDateTasted() {
        return DateTasted;
    }

    public void setDateTasted(String dateTasted) {
        DateTasted = dateTasted;
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
}
