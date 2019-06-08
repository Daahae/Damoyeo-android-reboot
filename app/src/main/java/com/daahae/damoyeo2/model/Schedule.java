package com.daahae.damoyeo2.model;

import com.google.gson.annotations.SerializedName;

public class Schedule {

    @SerializedName("startTime")
    public String startTime;

    @SerializedName("storeName")
    public String storeName;

    @SerializedName("category")
    public String category;

    @SerializedName("address")
    public String address;
}
