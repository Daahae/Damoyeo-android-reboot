package com.daahae.damoyeo2.model;

import com.daahae.damoyeo2.view.Constant;
import com.google.gson.annotations.SerializedName;

public class ChattingRequest {
    @SerializedName("roomNumber")
    private int roomNumber;

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "{" +
                "\"email\":\"" + Constant.email +"\","
                +"\"roomNumber\":" + roomNumber +"}";
    }
}
