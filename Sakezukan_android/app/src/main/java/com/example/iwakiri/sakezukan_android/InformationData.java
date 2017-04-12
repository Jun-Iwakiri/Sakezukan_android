package com.example.iwakiri.sakezukan_android;

/**
 * Created by iwakiri on 2017/04/13.
 */

public class InformationData {
    int informationId;
    String informationBody;

    public InformationData(int informationId, String informationBody) {
        this.informationId = informationId;
        this.informationBody = informationBody;
    }

    public InformationData() {

    }

    public int getInformationId() {
        return informationId;
    }

    public void setInformationId(int informationId) {
        this.informationId = informationId;
    }

    public String getInformationBody() {
        return informationBody;
    }

    public void setInformationBody(String informationBody) {
        this.informationBody = informationBody;
    }
}
