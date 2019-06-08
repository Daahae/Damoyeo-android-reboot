package com.daahae.damoyeo2.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ScheduleArr {

    @SerializedName("scheduleArr")
    private ArrayList<ArrayList<Schedule>> scheduleArr;

    public ArrayList<ArrayList<Schedule>> getScheduleArr() {
        return scheduleArr;
    }

    public void setScheduleArr(ArrayList<ArrayList<Schedule>> scheduleArr) {
        this.scheduleArr = scheduleArr;
    }
}
