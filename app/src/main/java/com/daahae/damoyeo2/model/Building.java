package com.daahae.damoyeo2.model;

import com.daahae.damoyeo2.view.Constant;
import com.google.gson.annotations.SerializedName;

public class Building{

    // DetailBuilding
    private static Building instance = new Building(Constant.latitude, Constant.longitude, Constant.name, Constant.address, Constant.distance);

    public static synchronized Building getInstance() {
        return instance;
    }

    public static void setInstance(Building instance) {
        Building.instance = instance;
    }

    @SerializedName("buildingLat")
    private double latitude;

    @SerializedName("buildingLng")
    private double longitude;

    @SerializedName("buildingName")
    private String name;

    @SerializedName("buildingAddress")
    private String buildingAddress;

    @SerializedName("buildingRating")
    private double rating;

    @SerializedName("buildingDistance")
    private double distance;

    public Building(double latitude, double longitude, String name, String buildingAddress, double distance) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.buildingAddress = buildingAddress;
        this.distance = distance;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getDistance() {
        return distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    @Override
    public String toString() {
        return "Building{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                ", buildingAddress='" + buildingAddress + '\'' +
                ", distance=" + distance +
                '}';
    }
}
