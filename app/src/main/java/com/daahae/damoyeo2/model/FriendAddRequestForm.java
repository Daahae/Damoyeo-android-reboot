package com.daahae.damoyeo2.model;

import android.graphics.drawable.Drawable;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.view.Constant;
import com.google.gson.annotations.SerializedName;

public class FriendAddRequestForm {

    @SerializedName("myEmail")
    private String myEmail;

    @SerializedName("destEmail")
    public String destEmail;


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

    @Override
    public String toString() {
        return "{" +
                "\"myEmail\":\"" + myEmail +
                "\",\"destEmail\":\"" + destEmail +
                "\"}";
    }

}
