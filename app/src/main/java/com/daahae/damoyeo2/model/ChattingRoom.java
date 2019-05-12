package com.daahae.damoyeo2.model;

import android.graphics.drawable.Drawable;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.view.Constant;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChattingRoom {

    @SerializedName("roomNum")
    public int roomNum;

    @SerializedName("count")
    public String count;

    @SerializedName("midFlag")
    public String midFlag;

    @SerializedName("user1")
    public String user1;

    @SerializedName("user2")
    public String user2;

    @SerializedName("user3")
    public String user3;

    @SerializedName("user4")
    public String user4;

    @SerializedName("user5")
    public String user5;

    @SerializedName("user6")
    public String user6;

    public ChattingRoom(int roomNum, String count, String midFlag, String user1, String user2, String user3, String user4, String user5, String user6) {
        this.roomNum = roomNum;
        this.count = count;
        this.midFlag = midFlag;
        this.user1 = user1;
        this.user2 = user2;
        this.user3 = user3;
        this.user4 = user4;
        this.user5 = user5;
        this.user6 = user6;
    }
}
