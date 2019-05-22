package com.daahae.damoyeo2.model;

import com.google.gson.annotations.SerializedName;

public class FriendAcceptRequestForm {

    @SerializedName("myEmail")
    public String myEmail;

    @SerializedName("destEmail")
    public String destEmail;

    @SerializedName("accept")
    public int accept ;

    public String getMyEmail() {
        return myEmail;
    }

    public void setMyEmail(String myEmail) {
        this.myEmail = myEmail;
    }

    public String getDestEmail() {
        return destEmail;
    }

    public void setDestEmail(String destEmail) {
        this.destEmail = destEmail;
    }

    public int getAccept() {
        return accept;
    }

    public void setAccept(int accept) {
        this.accept = accept;
    }

    @Override
    public String toString() {
        return "{" +
                "\"myEmail\":\"" + myEmail +
                "\",\"destEmail\":\"" + destEmail +
                "\",\"accept\":" + accept +
                "}";
    }

}
