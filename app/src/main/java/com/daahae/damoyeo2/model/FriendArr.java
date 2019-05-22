package com.daahae.damoyeo2.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FriendArr {

    @SerializedName("friendArr")
    private ArrayList<Friend> friends;

    @SerializedName("waitingFriendArr")
    private ArrayList<Friend> friendRequesters;

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    public ArrayList<Friend> getFriendRequesters() {
        return friendRequesters;
    }

    public void setFriendRequesters(ArrayList<Friend> friendRequesters) {
        this.friendRequesters = friendRequesters;
    }
}
