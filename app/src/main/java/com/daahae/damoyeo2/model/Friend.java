package com.daahae.damoyeo2.model;

import android.graphics.drawable.Drawable;

public class Friend {
    public String name;
    public Drawable profilePicture;

    public Friend() {
    }

    public Friend(String name, Drawable profilePicture) {
        this.name = name;
        this.profilePicture = profilePicture;
    }

}
