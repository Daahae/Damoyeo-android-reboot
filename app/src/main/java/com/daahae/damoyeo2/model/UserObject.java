package com.daahae.damoyeo2.model;

import com.google.gson.annotations.SerializedName;

public class UserObject {
    @SerializedName("email")
    public String email;
    @SerializedName("nickname")
    public String nickname;
    @SerializedName("startLat")
    public String startLat;
    @SerializedName("startLng")
    public String startLng;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getStartLng() {
        return startLng;
    }

    public void setStartLng(String startLng) {
        this.startLng = startLng;
    }
}
