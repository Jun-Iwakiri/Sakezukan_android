package com.example.iwakiri.sakezukan_android;

/**
 * Created by iwakiri on 2017/04/13.
 */

public class HelpData {
    int HelpID;
    String HelpBody;

    public HelpData(int helpID, String helpBody) {
        HelpID = helpID;
        HelpBody = helpBody;
    }

    public HelpData() {

    }

    public int getHelpID() {
        return HelpID;
    }

    public void setHelpID(int helpID) {
        HelpID = helpID;
    }

    public String getHelpBody() {
        return HelpBody;
    }

    public void setHelpBody(String helpBody) {
        HelpBody = helpBody;
    }

}
