package com.daahae.damoyeo2.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchPubTransPath {

    @SerializedName("subPathArr")
    private ArrayList<SubPath> subPathList;
    @SerializedName("payment")
    private int payment;
    @SerializedName("totalTime")
    private int totalTime;
    @SerializedName("timeBySubway")
    private int timeBySubway;
    @SerializedName("timeByBus")
    private int timeByBus;
    @SerializedName("timeByWalk")
    private int timeByWalk;
    @SerializedName("error")
    private String error;

    private int totalHour;
    private int totalMinute;

    public ArrayList<SubPath> getSubPathList() {
        return subPathList;
    }

    public int getPayment() {
        return payment;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getTimeBySubway() {
        return timeBySubway;
    }

    public int getTimeByBus() {
        return timeByBus;
    }

    public int getTimeByWalk() {
        return timeByWalk;
    }

    public String getError() {
        return error;
    }

    public int getTotalHour() {
        return totalHour;
    }

    public int getTotalMinute() {
        return totalMinute;
    }

    public void setTotalHour(int totalHour) {
        this.totalHour = totalHour;
    }

    public void setTotalMinute(int totalMinute) {
        this.totalMinute = totalMinute;
    }
}