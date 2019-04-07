package com.daahae.damoyeo2.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserPos {

    private static ArrayList<UserPos> instance = new ArrayList<>();

    @SerializedName("resArr")
    private ArrayList<UserPos> userPosList;

    @SerializedName("email")
    private String email;
    @SerializedName("startLat")
    private double startLat;
    @SerializedName("startLng")
    private double startLng;

    public UserPos(String email, double startLat, double startLng) {
        this.email = email;
        this.startLat = startLat;
        this.startLng = startLng;
    }

    public static synchronized ArrayList<UserPos> getInstance() {
        return instance;
    }

    public ArrayList<UserPos> getUserPosList() {
        return userPosList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public double getStartLng() {
        return startLng;
    }

    public void setStartLng(double startLng) {
        this.startLng = startLng;
    }

    public String toString() {
        return "{" +
                "\"email\":\"" + email +
                "\", \"startLat\":\"" + startLat +
                "\", \"startLng\":\"" + startLng +
                "\"}";
    }
}
