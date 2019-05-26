package com.daahae.damoyeo2.model;

import android.graphics.drawable.Drawable;

public class ChattingList {
    public String name;
    public Drawable profilePicture;
    public String time;

    public ChattingList() {
    }

    public ChattingList(String name, Drawable profilePicture) {
        this.name = name;
        this.profilePicture = profilePicture;
    }

}
