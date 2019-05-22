package com.daahae.damoyeo2.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserArr {

    @SerializedName("userArr")
    private ArrayList<ArrayList<UserObject>> userArrayList;

    @SerializedName("roomNum")
    private int roomNum;

    @SerializedName("count")
    private String count;

    @SerializedName("midFlag")
    private String midFlag;

    public void setUserArrayList(ArrayList<ArrayList<UserObject>> userArrayList) {
        this.userArrayList = userArrayList;
    }

    public ArrayList<ArrayList<UserObject>> getUserArrayList() {
        return userArrayList;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMidFlag() {
        return midFlag;
    }

    public void setMidFlag(String midFlag) {
        this.midFlag = midFlag;
    }
}
