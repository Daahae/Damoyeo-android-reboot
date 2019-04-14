package com.daahae.damoyeo2.model;

import com.google.gson.annotations.SerializedName;

public class RequestForm {
    @SerializedName("email")
    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "{" +
                "\"email\":\"" + email +"\"}";
    }
}
