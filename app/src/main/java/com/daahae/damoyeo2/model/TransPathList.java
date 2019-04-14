package com.daahae.damoyeo2.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TransPathList {

    private static TransPathList instance = new TransPathList();

    public static synchronized TransPathList getInstance() {
        return instance;
    }

    @SerializedName("userArr")
    private List<SearchPubTransPath> userArr;
    @SerializedName("midInfo")
    private MidInfo midInfo;

    private ArrayList<String> error;

    public List<SearchPubTransPath> getUserArr() {
        return userArr;
    }

    public void setUserArr(List<SearchPubTransPath> userArr) {
        this.userArr = userArr;
    }

    public MidInfo getMidInfo() {
        return midInfo;
    }

    public void setMidInfo(MidInfo midInfo) {
        this.midInfo = midInfo;
    }
}