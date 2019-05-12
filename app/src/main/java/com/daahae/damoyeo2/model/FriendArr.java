package com.daahae.damoyeo2.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FriendArr {

    @SerializedName("userArr")
    private ArrayList<Friend> friends;

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }
}
