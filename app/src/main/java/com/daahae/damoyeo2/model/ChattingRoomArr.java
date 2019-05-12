package com.daahae.damoyeo2.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChattingRoomArr {

    @SerializedName("roomArr")
    private ArrayList<ChattingRoom> chattingRooms;

    @SerializedName("userObj")
    private ArrayList<UserObject> userObjects;

    public ArrayList<UserObject> getUserObjects() {
        return userObjects;
    }

    public void setUserObjects(ArrayList<UserObject> userObjects) {
        this.userObjects = userObjects;
    }

    public ArrayList<ChattingRoom> getChattingRooms() {
        return chattingRooms;
    }

    public void setChattingRooms(ArrayList<ChattingRoom> chattingRooms) {
        this.chattingRooms = chattingRooms;
    }
}
